package com.rickynj.commands;

import java.util.List;

public class VariableNode extends LiteralNode {
    public final List<String> allowedValues;

    public VariableNode(String token, List<String> allowedValues) {
        super(token);
        this.allowedValues = allowedValues;
    }

    public List<String> getAcceptableValues() {
        return allowedValues;
    }
}