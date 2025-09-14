package com.rickynj.actions.condition;

import com.rickynj.actions.operation.OperationUtility;
import com.rickynj.commands.BasicNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.domain.pojo.ConditionPojo;
import com.rickynj.responses.ResponseUtility;

import static com.rickynj.config.Constants.FALSE;

public class Else implements Condition {
  // The Else condition is invoked when If condition evaluates to false.
  // Therefore, it does not have a eval method.
  @Override
  public String eval(CommandContext commandContext) {
    return null;
  }

  @Override
  public void addConditionToNode(BasicNode node, ConditionPojo condition) {
    node.setConditionalResponse(FALSE, ResponseUtility.getResponseType(condition.response));
    node.setConditionalOperation(FALSE, OperationUtility.getOperationType(condition));
    node.addConditionToNode(this);
  }
}
