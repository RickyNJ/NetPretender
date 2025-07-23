package com.rickynj.responses;

public class BasicResponse extends ResponseBase{
    private final String response;

    public BasicResponse(String response) {
        this.response = response;
    }

    public void respond() throws InterruptedException {
        Thread.sleep(delayMs.get());
        System.out.println(response);
    }
}