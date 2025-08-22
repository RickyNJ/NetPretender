package com.rickynj.commands;

import com.rickynj.actions.evaluate.Evaluate;
import com.rickynj.domain.CommandContext;
import com.rickynj.actions.execute.Execute;
import com.rickynj.responses.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BasicNode {
    protected String token;
    protected List<BasicNode> nextNodes = new ArrayList<>();
    protected Evaluate evaluate;
    protected Response response;
    protected Map<Boolean, Response> responseMap;
    protected Execute operation;

    public BasicNode(String token) {
        this.token = token;
    }

    public void respond(CommandContext ctx) throws InterruptedException {
        response.respond(ctx);
    }
    public void execute(CommandContext ctx) {
        if (operation == null) {
            return;
        }
        operation.execute(ctx);
    }

    public void evaluate(CommandContext ctx) {
        if (evaluate == null) {
            return;
        }
        response = responseMap.get(evaluate.eval(ctx));
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setOperation(Execute operation) {
        this.operation = operation;
    }
    public boolean hasOperation() {
        return operation != null;
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
}
