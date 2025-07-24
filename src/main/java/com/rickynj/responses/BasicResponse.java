package com.rickynj.responses;

public class BasicResponse extends ResponseBase{
    private final String response;

    public BasicResponse(String response) {
        this.response = response;
    }

    public void respond() throws InterruptedException {
        long d = this.delayMs.get();
        Thread.sleep(this.delayMs.get());
        System.out.println(response);
    }
}