package com.rickynj.responses;

import java.util.List;
import java.util.Map;

public class MultipartResponse extends ResponseBase{
    private List<String> response;

    @Override
    public void respond(Map<String, String> vars) throws InterruptedException {
        for (String part : response) {
            Thread.sleep(delayMs.get());
            System.out.println(part);
        }
    }
}
