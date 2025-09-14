package com.rickynj.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickynj.actions.operation.HoldsOperation;
import java.util.List;

import static com.rickynj.config.Constants.*;

public class ConditionPojo implements HoldsOperation {
  @JsonProperty(IF)
  public String ifStatement;
  @JsonProperty(ELSE)
  public String elseStatement;
  @JsonProperty(SWITCH)
  public String switchStatement;
  public List<CasePojo> cases;
  public ResponsePojo response;
  public String operation;

  @Override
  public String getOperation() {
    return operation;
  }
}
