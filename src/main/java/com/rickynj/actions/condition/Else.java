package com.rickynj.actions.condition;

import com.rickynj.actions.operation.OperationUtility;
import com.rickynj.commands.BasicNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.domain.POJO.ConditionPojo;
import com.rickynj.responses.ResponseUtility;

public class Else implements Condition {

  @Override
  public String eval(CommandContext commandContext) {
    return "";
  }

  @Override
  public void addConditionToNode(BasicNode node, ConditionPojo condition) {
    node.setConditionalResponse("false", ResponseUtility.getResponseType(condition.response));
    node.setConditionalOperation("false", OperationUtility.getOperationType(condition));
    node.addConditionToNode(this);
  }
}
