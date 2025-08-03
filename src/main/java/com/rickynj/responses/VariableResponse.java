package com.rickynj.responses;

import com.rickynj.domain.CommandContext;


public class VariableResponse extends ResponseBase{
    private final String responseTemplate;

    public VariableResponse(String responseTemplate) {
        this.responseTemplate = responseTemplate;
    }

    @Override
    public void respond(CommandContext ctx) {
        System.out.println(ResponseUtility.replaceVariablesWithValues(responseTemplate, ctx));
    }
}
