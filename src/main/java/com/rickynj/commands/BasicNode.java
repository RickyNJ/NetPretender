package com.rickynj.commands;

import com.rickynj.domain.CommandContext;
import com.rickynj.functions.Operation;
import com.rickynj.responses.Response;

import java.util.ArrayList;
import java.util.List;


public class BasicNode {
    public String token;
    public List<BasicNode> nextNodes = new ArrayList<>();
    public Response response;
    public Operation operation;

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

    public void setOperation(Operation operation) {
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
