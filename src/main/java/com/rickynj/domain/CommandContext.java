package com.rickynj.domain;

import com.rickynj.device.Device;
import com.rickynj.repository.valkey.ValkeyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandContext {
    public final String command;
    public final Device device;
    public final Map<String, String> vars;
    private final ValkeyClient client = ValkeyClient.getValkeyClient();
    transient private Logger logger = LoggerFactory.getLogger(CommandContext.class);

    public CommandContext(String command, Device device) {
        logger.info("Starting new commandContext with Command: {}", command);
        this.command = command;
        this.device = device;
        vars = new HashMap<>(device.getState());
        vars.put("${command}", command);
        logger.info("Found these variables: {}", vars);
    }

    public String getCommand() {
        return command;
    }

    public String getValueForKey(String key){
        logger.info("Looking for value for key: {}, {}", key, device.name);
        String value = client.getValueFromValkey(key, this);
        if (value == null) {
            logger.info("Variable has not yet been stored in valkey. {},  {}", key, device.name);
            // value not stored set value in valkey from local context.
            value = vars.get(key);
            client.setValueInValkey(key, value, this);
        }

        logger.info("Value found: {}", value);
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
