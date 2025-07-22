package com.rickynj.commands;


import java.util.List;

public class VariableNode extends CommandNode{
    private List<String> acceptableValues;

    public VariableNode(String token) {
        super(token);
    }
}