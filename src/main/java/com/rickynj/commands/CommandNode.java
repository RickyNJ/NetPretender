package com.rickynj.commands;

public class CommandNode extends BasicNode {

    public CommandNode(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
