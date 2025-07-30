package com.rickynj.domain;

public class CommandContext {
    private String command;
    public CommandContext(String command) {
        this.command = command;
    }


    public String getCommand() {
        return command;
    }
}
