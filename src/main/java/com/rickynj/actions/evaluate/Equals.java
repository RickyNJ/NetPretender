package com.rickynj.actions.evaluate;

import com.rickynj.domain.CommandContext;
import java.util.Objects;

public class Equals implements Evaluate {
  public String actual;
  public String expected;

  public Equals(String actual, String expected) {
    this.actual = actual;
    this.expected = expected;
  }

  @Override
  public boolean eval(CommandContext ctx) {
    String actualValue = ctx.getValueForKey(actual);
    return Objects.equals(expected, actualValue);
  }
}
