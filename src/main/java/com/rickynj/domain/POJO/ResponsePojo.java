package com.rickynj.domain.POJO;

import java.util.List;

public class ResponsePojo {
    public String response;
    public List<String> multiPartResponse;
    public String responseFile;
    public List<String> allowed_values;
    public String operation;
    public int delay;

    public ResponsePojo(String response, List<String> multiPartResponse, String responseFile, List<String> allowed_values, String operation, int delay) {
        this.response = response;
        this.multiPartResponse = multiPartResponse;
        this.responseFile = responseFile;
        this.allowed_values = allowed_values;
        this.operation = operation;
        this.delay = delay;
    }

}
