package com.rickynj.responses;

import java.util.List;

public class MultipartResponse extends ResponseBase{
    private List<String> response;

    @Override
    public void respond() throws InterruptedException {
        for (String part : response) {
            Thread.sleep(delayMs.get());
            System.out.println(part);
        }
    }
}
