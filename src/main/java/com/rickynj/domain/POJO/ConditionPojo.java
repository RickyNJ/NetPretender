package com.rickynj.domain.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ConditionPojo {
  @JsonProperty("if")
  public String ifStatement;
  @JsonProperty("else")
  public String elseStatement;

  public String response;
  public List<String> multiPartResponse;
  public String responseFile;
  public List<String> allowed_values;
  public String operation;
}
