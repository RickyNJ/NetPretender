package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CasePojo {
    @JsonProperty("case")
    public String caseStatement;
    public ResponsePojo response;
}
