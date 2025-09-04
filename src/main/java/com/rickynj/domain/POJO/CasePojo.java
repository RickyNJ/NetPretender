package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickynj.responses.HoldsResponse;
import java.util.List;

public class CasePojo implements HoldsResponse {
    @JsonProperty("case")
    public String caseStatement;
    public ResponsePojo response;
    public String operation;


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
