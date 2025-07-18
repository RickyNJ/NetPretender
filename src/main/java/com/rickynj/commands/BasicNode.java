package com.rickynj.commands;

import com.rickynj.responses.Response;

import java.util.List;
import java.util.Map;

public class BasicNode {

    protected List<BasicNode> nextNodes;
    protected Response response;

    // all variables input that preceded getting to this point, used to populate the output.
    protected Map<String, String> variables;

    public void respond() throws InterruptedException {
        response.respond();
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
