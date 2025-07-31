package com.rickynj.domain;

import java.util.HashMap;
import java.util.Map;

public class CommandContext {
    private final String command;
    private final Map<String, String> vars;
    public CommandContext(String command) {
        this.command = command;
        vars = new HashMap<>();
    }

    public String getCommand() {
        return command;
    }

    public String getValueForKey(String key) {
        return vars.get(key);
    }
}
