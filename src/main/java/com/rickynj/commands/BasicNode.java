package com.rickynj.commands;

import com.rickynj.domain.CommandContext;
import com.rickynj.responses.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicNode {

    protected String token;
    protected List<BasicNode> nextNodes = new ArrayList<>();
    protected Response response;

    public void respond(CommandContext ctx) throws InterruptedException {
        response.respond(ctx);
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
}
