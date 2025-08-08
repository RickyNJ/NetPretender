package com.rickynj.domain;

import com.rickynj.device.Device;
import com.rickynj.repository.valkey.ValkeyClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandContext {
    public final String command;
    public final Device device;
    public final boolean caching;
    public final Map<String, String> vars;

    public CommandContext(String command, Device device, boolean caching) {
        this.command = command;
        this.device = device;
        this.caching = caching;
        vars = new HashMap<>(device.getState());
        vars.put("${command}", command);
    }

    public String getCommand() {
        return command;
    }

    public Optional<String> getValueForKey(String key) {
        return Optional.ofNullable(vars.get(key));
    }

    public void setContextVar(String key, String val) {
        vars.put(key, val);
    }

    public void setDeviceVar(String key, String val) {
        device.setState(key, val);
    }

}
