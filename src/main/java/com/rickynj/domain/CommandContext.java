package com.rickynj.domain;

import com.rickynj.device.Device;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandContext {
    private final String command;
    private final Device device;
    private final Map<String, String> vars;

    public CommandContext(String command, Device device) {
        this.command = command;
        this.device = device;
        vars = new HashMap<>(device.getState());
    }

    public String getCommand() {
        return command;
    }

    public Optional<String> getValueForKey(String key) {
        return Optional.ofNullable(vars.get(key));
    }

    public void setValueForKey(String key, String val) {
        vars.put(key, val);
    }
}
