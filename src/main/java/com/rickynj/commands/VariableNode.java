package com.rickynj.commands;

import com.rickynj.responses.Response;

import java.util.List;

public class VariableNode extends CommandNode{
    private List<String> acceptableValues;

    public VariableNode(String word, Response response) {
        super(word, response);
    }
}