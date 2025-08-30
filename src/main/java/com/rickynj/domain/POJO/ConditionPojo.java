package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ConditionPojo implements HoldsResponse {
  @JsonProperty("if")
  public String ifStatement;
  @JsonProperty("else")
  public String elseStatement;

  public String response;
  public List<String> multiPartResponse;
  public String responseFile;
  public List<String> allowed_values;
  public String operation;
  public int delay;

  @JsonAnySetter
  public void set(String key, Object value) {
    if ("else".equals(key)) {
      this.elseStatement = "__PRESENT__"; // or some sentinel
    }
  }
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
