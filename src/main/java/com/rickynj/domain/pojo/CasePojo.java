package com.rickynj.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickynj.actions.operation.HoldsOperation;

import static com.rickynj.config.Constants.CASE;

public class CasePojo implements HoldsOperation {
    @JsonProperty(CASE)
    public String caseStatement;
    public ResponsePojo response;
    public String operation;

    @Override
    public String getOperation() {
        return operation;
    }
}
