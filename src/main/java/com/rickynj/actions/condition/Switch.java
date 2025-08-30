package com.rickynj.actions.condition;

import com.rickynj.domain.CommandContext;

public class Switch implements Condition{
    String var;

    public Switch(String var) {
        this.var = var;
    }
    @Override
    public String eval(CommandContext commandContext) {
        return commandContext.getValueForKey(var);
    }
}
