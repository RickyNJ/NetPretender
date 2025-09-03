package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rickynj.responses.HoldsResponse;
import java.util.List;

public class ConditionPojo implements HoldsResponse {
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
  public String getResponse() {
    return "";
  }
  @Override
  public List<String> getMultipartResponse() {
    return List.of();
  }
  @Override
  public String getResponseFile() {
    return "";
  }
  @Override
  public int getDelay() {
    return 0;
  }
}
