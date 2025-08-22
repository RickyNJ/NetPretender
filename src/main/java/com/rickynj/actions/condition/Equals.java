package com.rickynj.actions.condition;

import com.rickynj.domain.CommandContext;
import java.util.Objects;

public class Equals implements Condition {
  public String actual;
  public String expected;

  public Equals(String actual, String expected) {
    this.actual = actual;
    this.expected = expected;
  }

  @Override
  public String eval(CommandContext ctx) {
    String actualValue = ctx.getValueForKey(actual);

    if (Objects.equals(expected, actualValue)) {
      return "true";
    } else {
      return "false";
    }
  }
}
