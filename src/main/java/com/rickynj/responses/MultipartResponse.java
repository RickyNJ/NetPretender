package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.util.List;

public class MultipartResponse extends ResponseBase{
    private final List<String> response;

    public MultipartResponse(List<String> multiPartResponse) {
        this.response = multiPartResponse;
    }

    @Override
    public void respond(CommandContext ctx) throws InterruptedException {
        for (String part : response) {
            Thread.sleep(delayMs.get());
            System.out.println(ResponseUtility.replaceVariablesWithValues(part, ctx));
        }
    }
}
