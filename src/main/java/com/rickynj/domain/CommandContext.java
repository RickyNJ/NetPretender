package com.rickynj.domain;

import com.rickynj.organisation.Device;
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
    private final static Logger logger = LoggerFactory.getLogger(CommandContext.class);

    public CommandContext(String command, Device device) {
        logger.info("Starting new commandContext with Command: {}", command);
        this.command = command;
        this.device = device;
        vars = new HashMap<>(device.getState());
        vars.put("${command}", command);
        logger.info("Found these variables: {}", vars);
    }

    // Only fetch device state variables from valkey.
    // Variables in the command are local and should not be cached.
    public String getValueForKey(String key){
        String value = null;
        logger.info("Looking for value for key: {}, {}", key, device.name);
        if (getCaching() && device.state.containsKey(key)) {
            value = client.getValueFromValkey(key, this);
        }
        if (value == null) {
            // command has not been set in valkey.
            value = vars.get(key);
        }
        return value;
    }

    // Context vars are scoped to a single command, therefore no valkey operations are performed.
    public void setContextVar(String key, String val) {
        vars.put(key, val);
    }

    public void setDeviceVar(String key, String val) {
        logger.info("Setting device var {}, caching: {}", command, getCaching());
        if (getCaching()) {
            client.setValueInValkey(key, val, this);
        }
        device.setState(key, val);
    }

    public boolean getCaching() {
        return device.caching;
    }
}
