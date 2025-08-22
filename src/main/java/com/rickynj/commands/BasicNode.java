package com.rickynj.commands;

import com.rickynj.actions.condition.Condition;
import com.rickynj.domain.CommandContext;
import com.rickynj.actions.execute.Execute;
import com.rickynj.responses.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BasicNode {
    protected String token;
    protected List<BasicNode> nextNodes = new ArrayList<>();
    protected Condition condition;
    protected Response response;
    protected Map<String, Response> responseMap = new HashMap<>();
    protected Execute operation;

    public BasicNode(String token) {
        this.token = token;
    }

    public void respond(CommandContext ctx) throws InterruptedException {
        response.respond(ctx);
    }

    public void setOperation(Execute operation) {
        this.operation = operation;
    }
    public void execute(CommandContext ctx) {
        if (operation == null) {
            return;
        }
        operation.execute(ctx);
    }

    public void evaluate(CommandContext ctx) {
        if (condition == null) {
            return;
        }
        response = responseMap.get(condition.eval(ctx));
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }


    public void addNextNode(BasicNode node) {
        nextNodes.add(node);
    }

    public List<BasicNode> getNextNodes() {
        return nextNodes;
    }

    public String getToken() {
        return token;
    }

    public void setConditionalResponse(String evaluation, Response response) {
        responseMap.put(evaluation, response);
    }

}
