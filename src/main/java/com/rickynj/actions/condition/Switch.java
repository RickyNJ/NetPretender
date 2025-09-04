package com.rickynj.actions.condition;

import com.rickynj.commands.BasicNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.domain.POJO.CasePojo;
import com.rickynj.domain.POJO.ConditionPojo;
import com.rickynj.responses.ResponseUtility;

public class Switch implements Condition{
    String var;

    public Switch(String var) {
        this.var = var;
    }
    @Override
    public String eval(CommandContext commandContext) {
        return commandContext.getValueForKey(var);
    }

    @Override
    public void addConditionToNode(BasicNode node, ConditionPojo condition) {
        for (CasePojo casePojo : condition.cases) {
            node.setConditionalResponse(casePojo.caseStatement, ResponseUtility.getResponseType(casePojo));
        }
        node.addConditionToNode(this);
    }
}
