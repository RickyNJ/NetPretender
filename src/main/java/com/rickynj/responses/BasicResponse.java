package com.rickynj.responses;

import java.util.Map;

public class BasicResponse extends ResponseBase{
    private final String response;

    public BasicResponse(String response) {
        this.response = response;
    }

    public void respond(Map<String, String> vars) throws InterruptedException {
        long d = this.delayMs.get();
        Thread.sleep(this.delayMs.get());
        System.out.println(response);
    }
}