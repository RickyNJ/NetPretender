package com.rickynj.commands;


import java.util.List;

public class VariableNode extends LiteralNode {
    private List<String> acceptableValues;

    public VariableNode(String token) {
        super(token);
    }
}