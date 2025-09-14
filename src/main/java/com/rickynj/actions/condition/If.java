package com.rickynj.actions.condition;

import com.rickynj.actions.operation.OperationUtility;
import com.rickynj.commands.BasicNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.domain.pojo.ConditionPojo;
import com.rickynj.responses.ResponseUtility;
import java.util.List;
import java.util.Objects;

import static com.rickynj.config.Constants.FALSE;
import static com.rickynj.config.Constants.TRUE;

public class If implements Condition {
  public String actual;
  public String expected;

  public If(String statement) {
    List<String> splitStatement = List.of(statement.split("\\s+"));
    this.actual = splitStatement.get(0);
    this.expected = splitStatement.get(2);
  }

  @Override
  public String eval(CommandContext ctx) {
    String actualValue = ctx.getValueForKey(actual);
    String expectedValue = ctx.getValueForKey(expected);

    if (Objects.equals(expectedValue, actualValue)) {
      return TRUE;
    } else {
      return FALSE;
    }
  }

  @Override
  public void addConditionToNode(BasicNode node, ConditionPojo condition) {
    node.setConditionalResponse(TRUE, ResponseUtility.getResponseType(condition.response));
    node.setConditionalOperation(TRUE, OperationUtility.getOperationType(condition));

    node.addConditionToNode(this);
  }
}
