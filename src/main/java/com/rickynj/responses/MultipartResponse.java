package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.util.ArrayList;
import java.util.List;

public class MultipartResponse extends ResponseBase{
    private final List<String> response;

    public MultipartResponse(List<String> multiPartResponse) {
        this.response = multiPartResponse;
    }

    @Override
    public void respond(CommandContext ctx) {
        response.forEach((part) -> {
            try {
                Thread.sleep(this.getDelay());
                System.out.println(ResponseUtility.replaceVariablesWithValues(part, ctx));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
