package com.rickynj.domain.POJO;

import com.rickynj.responses.HoldsResponse;
import java.util.List;

public class CommandPojo implements HoldsResponse {
    public String command;
    public int delay;
    public List<ConditionPojo> condition;
    public String response;
    public List<String> multiPartResponse;
    public String responseFile;
    public List<String> allowed_values;
    public String operation;

    @Override
    public String getResponse() {
        return response;
    }

    @Override
    public List<String> getMultipartResponse() {
        return multiPartResponse;
    }

    @Override
    public String getResponseFile() {
        return responseFile;
    }

    @Override
    public int getDelay() {
        return delay;
    }
}
