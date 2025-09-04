package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickynj.actions.operation.HoldsOperation;
import java.util.List;

public class ConditionPojo implements HoldsOperation {
  @JsonProperty("if")
  public String ifStatement;
  @JsonProperty("else")
  public String elseStatement;
  @JsonProperty("switch")
  public String switchStatement;
  public List<CasePojo> cases;
  public ResponsePojo response;
  public String operation;

  @Override
  public String getOperation() {
    return operation;
  }
}
