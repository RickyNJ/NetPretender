package com.rickynj.actions.condition;

import com.rickynj.commands.BasicNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.domain.pojo.ConditionPojo;

public interface Condition {
  String eval(CommandContext commandContext);
  void addConditionToNode(BasicNode node, ConditionPojo condition);
}
