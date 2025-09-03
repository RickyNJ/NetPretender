package com.rickynj.domain.POJO;

import java.util.List;

public class ResponsePojo {
    public String response;
    public List<String> multiPartResponse;
    public String responseFile;
    public List<String> allowed_values;
    public String operation;
    public int delay;

    public void setResponse(String response) {
        this.response = response;
    }
    public void setMultiPartResponse(List<String> multiPartResponse) {
        this.multiPartResponse = multiPartResponse;
    }
    public void setResponseFile(String responseFile) {
        this.responseFile = responseFile;
    }
    public void setAllowed_values(List<String> allowed_values) {
        this.allowed_values = allowed_values;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }

}
