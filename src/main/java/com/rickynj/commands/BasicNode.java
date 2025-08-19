package com.rickynj.commands;

import com.rickynj.domain.CommandContext;
import com.rickynj.actions.Action;
import com.rickynj.responses.Response;

import java.util.ArrayList;
import java.util.List;


public class BasicNode {
    protected String token;
    protected List<BasicNode> nextNodes = new ArrayList<>();
    protected Response response;
    protected Action operation;

    public BasicNode(String token) {
        this.token = token;
    }

    public void respond(CommandContext ctx) throws InterruptedException {
        response.respond(ctx);
    }
    public void execute(CommandContext ctx) {
        operation.execute(ctx);
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setOperation(Action operation) {
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
