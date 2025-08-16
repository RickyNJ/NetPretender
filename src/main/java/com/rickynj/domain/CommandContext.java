package com.rickynj.domain;

import com.rickynj.organisation.Device;
import com.rickynj.repository.valkey.ValkeyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandContext {
    public final boolean caching;
    public final String command;
    public final Device device;
    public final Map<String, String> vars;
    private final ValkeyClient client = ValkeyClient.getValkeyClient();
    private final static Logger logger = LoggerFactory.getLogger(CommandContext.class);

    public CommandContext(String command, Device device) {
        this.caching = device.caching;
        logger.info("Starting new commandContext with Command: {}", command);
        this.command = command;
        this.device = device;

        vars = new HashMap<>(device.getState());
        vars.put("${command}", command);
        logger.info("Found these variables: {}", vars);
    }

    public String getValueForKey(String key){
        logger.info("Looking for value for key: {}, {}", key, device.name);
        String value = client.getValueFromValkey(key, this);
        logger.info("Value found: {}", value);
        if (value == null) {
            // command has not been set externally
            value = vars.get(key);
        }
        return value;
    }

    public void setContextVar(String key, String val) {
        vars.put(key, val);
    }

    public void setDeviceVar(String key, String val) {
        client.setValueInValkey(key, val, this);
        device.setState(key, val);
    }
}
