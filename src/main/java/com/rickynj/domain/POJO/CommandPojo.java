package com.rickynj.domain.POJO;

import com.rickynj.responses.HoldsResponse;
import java.util.List;
import jdk.dynalink.Operation;

public class CommandPojo implements HoldsResponse {
    public String command;
    public List<ConditionPojo> condition;
    public String operation;
    public ResponsePojo response;

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
