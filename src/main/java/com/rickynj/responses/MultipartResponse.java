package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.util.List;
import java.util.Map;

public class MultipartResponse extends ResponseBase{
    private List<String> response;

    @Override
    public void respond(CommandContext ctx) throws InterruptedException {
        for (String part : response) {
            Thread.sleep(delayMs.get());
            System.out.println(part);
        }
    }
}
