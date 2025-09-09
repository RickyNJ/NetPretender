package com.rickynj.actions.operation;

import java.util.Objects;

public class OperationUtility {
  public static Operation getOperationType(HoldsOperation c) {
    if (c.getOperation() == null) {
      return null;
    }

    String[] op = c.getOperation().split(" ");
    if (Objects.equals(op[0], "reset")) {
      return new Reset();
    } else {
      return new Assign(op[0], op[2]);
    }
  }

  private OperationUtility() {}
}