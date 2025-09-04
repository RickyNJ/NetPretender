package com.rickynj.commands;

import com.rickynj.actions.condition.Condition;
import com.rickynj.domain.CommandContext;
import com.rickynj.actions.operation.Operation;
import com.rickynj.responses.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BasicNode {
    protected String token;
    protected List<BasicNode> nextNodes = new ArrayList<>();
    protected List<Condition> condition = new ArrayList<>();
    protected Response response;
    protected Map<String, Response> responseMap = new HashMap<>();
    protected Map<String, Operation> operationMap = new HashMap<>();
    protected Operation operation;

    public BasicNode(String token) {
        this.token = token;
    }

    public void respond(CommandContext ctx) throws InterruptedException {
        if (response == null) {
            return;
        }
        response.respond(ctx);
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    public void execute(CommandContext ctx) {
        if (operation == null) {
            return;
        }
        operation.execute(ctx);
    }

    public void evaluate(CommandContext ctx) throws InterruptedException {
        if (condition == null) {
            return;
        }
        for (Condition c : condition) {
            String evaluation = c.eval(ctx);
            response = responseMap.get(evaluation);
            if (response != null) {
                response.respond(ctx);
            }
        }
    }

    public void addConditionToNode(Condition condition) {
        this.condition.add(condition);
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

    public void setConditionalOperation(String evaluation, Operation operation) {
        operationMap.put(evaluation, operation);
    }

}
