package com.rickynj.actions.condition;

import com.rickynj.domain.pojo.ConditionPojo;

public class ConditionUtility {
  public static Condition getConditionType(ConditionPojo conditionPojo) {
    if (conditionPojo.ifStatement != null ) {
      return new If(conditionPojo.ifStatement);
    }

    if (conditionPojo.elseStatement != null ) {
      return new Else();
    }

    if (conditionPojo.switchStatement != null ) {
      return new Switch(conditionPojo.switchStatement);
    }
    return null;
  }

}
