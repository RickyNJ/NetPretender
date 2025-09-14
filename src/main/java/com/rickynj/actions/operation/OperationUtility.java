package com.rickynj.actions.operation;

import java.util.Objects;

import static com.rickynj.config.Constants.RESET;

public class OperationUtility {
  public static Operation getOperationType(HoldsOperation c) {
    if (c.getOperation() == null) {
      return null;
    }

    String[] op = c.getOperation().split(" \\s+");
    if (Objects.equals(op[0], RESET)) {
      return new Reset();
    } else {
      return new Assign(op[0], op[2]);
    }
  }

  private OperationUtility() {}
}