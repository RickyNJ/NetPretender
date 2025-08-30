package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ConditionPojo implements  HoldsResponse{
  @JsonProperty("if")
  public String ifStatement;
  @JsonProperty("else")
  public String elseStatement;
  @JsonProperty("switch")
  public String switchStatement;

  public List<CasePojo> cases;

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


//  public String response;
//  public List<String> multiPartResponse;
//  public String responseFile;
//  public List<String> allowed_values;
//  public String operation;
//  public int delay;

}
