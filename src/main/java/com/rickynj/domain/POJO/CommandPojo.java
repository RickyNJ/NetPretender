package com.rickynj.domain.POJO;

import java.util.List;

public class CommandPojo implements HoldsResponse {
    public String command;
    public List<ConditionPojo> condition;
    public ResponsePojo response;
    public List<String> allowed_values;
    public String operation;
//    public String response;
//    public List<String> multiPartResponse;
//    public String responseFile;
//    public int delay;

    @Override
    public String getResponse() {
        return response.response;
    }

    @Override
    public List<String> getMultipartResponse() {
        return response.multiPartResponse;
    }

    @Override
    public String getResponseFile() {
        return response.responseFile;
    }

    @Override
    public int getDelay() {
        return response.delay;
    }
}
