package com.rickynj.responses;

public class BasicResponse extends ResponseBase{
    private String response;

    public void respond() throws InterruptedException {
        Thread.sleep(delayMs.get());
        System.out.println(response);
    }
}