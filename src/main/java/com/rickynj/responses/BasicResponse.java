package com.rickynj.responses;

import com.rickynj.domain.CommandContext;


public class BasicResponse extends ResponseBase{
    private final String response;

    public BasicResponse(String response) {
        this.response = response;
    }

    public void respond(CommandContext ctx) throws InterruptedException {
        Thread.sleep(this.delayMs.get());
        System.out.println(ResponseUtility.replaceVariablesWithValues(response, ctx));
    }
}