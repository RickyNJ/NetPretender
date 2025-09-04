package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickynj.actions.operation.HoldsOperation;

public class CasePojo implements HoldsOperation {
    @JsonProperty("case")
    public String caseStatement;
    public ResponsePojo response;
    public String operation;

    @Override
    public String getOperation() {
        return operation;
    }
}
