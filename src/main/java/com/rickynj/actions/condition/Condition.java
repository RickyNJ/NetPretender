package com.rickynj.actions.condition;

import com.rickynj.domain.CommandContext;

public interface Condition {
  String eval(CommandContext commandContext);
}
