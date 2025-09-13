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
    public final ValkeyClient client;

    public CommandContext(String command, Device device, ValkeyClient client) {
        this.client =  client;
        this.command = command;
        this.device = device;

        vars = new HashMap<>(device.getState());
        vars.put("${command}", command);
    }

    // Only fetch device state variables from valkey.
    // Variables in the command are local and should not be cached.
    public String getValueForKey(String key){
        String value = null;
        if (getCaching()) { value = client.getValueFromValkey(key, this);}
        // command has not been set in valkey.
        if (value == null) { value = vars.get(key); }
        // value is String literal
        if (value == null) { value = key; }

        return value;
    }

    // Context vars are scoped to a single command, therefore no valkey operations are performed.
    public void setContextVar(String key, String val) {
        vars.put(key, val);
    }

    public void setDeviceVar(String key, String val) {
        if (getCaching()) {
            client.setValueInValkey(key, val, this);
        }
        device.setState(key, val);
    }

    public boolean getCaching() {
        return device.caching;
    }
}
