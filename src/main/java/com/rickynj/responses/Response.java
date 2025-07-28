package com.rickynj.responses;

import java.util.Map;

public interface Response {
    void respond(Map<String, String> vars) throws InterruptedException;
}
