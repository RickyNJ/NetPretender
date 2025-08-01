package com.rickynj.device;

import com.rickynj.Main;
import com.rickynj.commands.BasicNode;
import com.rickynj.commands.LiteralNode;
import com.rickynj.commands.VariableNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.exception.CommandNotMockedException;
import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.responses.BasicResponse;
import com.rickynj.responses.MultipartResponse;
import com.rickynj.responses.Response;
import com.rickynj.responses.VariableResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Device {
    private LiteralNode defaultResponse;
    private final List<LiteralNode> commandRoots = new ArrayList<>();
    private Map<String, String> state;

    public void  setState(Map<String, String> state) {
        this.state = state;
    }

    public Map<String, String> getState() {
        return state;
    }

    private boolean isVariableToken(String token) {
        return (token.startsWith("${") && token.endsWith("}"));
    }

    public void respondToCommand(CommandContext ctx) throws InterruptedException, CommandNotMockedException {
        List<String> tokens = List.of(ctx.getCommand().split(" "));
        BasicNode root = getCommandRootNode(tokens.get(0));
        if (root == null) {
            if (defaultResponse != null) {
                defaultResponse.respond(ctx);
            } else {
                throw new CommandNotMockedException("Command of this root not found.");
            }
        }

        BasicNode responseNode = traverseCommandTree(ctx, tokens.subList(1, tokens.size()), root);
        if (responseNode == null || responseNode.getResponse() == null) {
            if (defaultResponse != null) {
                defaultResponse.respond(ctx);
            } else {
                throw new CommandNotMockedException(String.format("Command: \"%s\", has no mocked response.", ctx.getCommand()));
            }
        } else {
            responseNode.respond(ctx);
        }
    }

    private BasicNode traverseCommandTree(CommandContext ctx, List<String> tokens, BasicNode node) {
        if (tokens.isEmpty()) {
            return node;
        }

        String nextToken = tokens.get(0);
        List<VariableNode> varNodes = new ArrayList<>();
        for (BasicNode n : node.getNextNodes()) {
            if (Objects.equals(n.getToken(), nextToken)){
               return traverseCommandTree(ctx, tokens.subList(1, tokens.size()), n);
            }
            if (n instanceof VariableNode vn) {
                varNodes.add(vn);
            }
        }

        for (VariableNode vn : varNodes) {
            if (vn.getAcceptableValues().contains(nextToken)) {
                ctx.setValueForKey(vn.getToken(), nextToken);
                return traverseCommandTree(ctx, tokens.subList(1, tokens.size()), vn);
            }
        }
        return null;
    }

    public void addCommand(CommandPojo c){
        List<String> tokens = List.of(c.command.split(" "));
        String rootToken = tokens.get(0);

        LiteralNode rootNode = getCommandRootNode(rootToken);
        if (rootNode == null) {
            rootNode = new LiteralNode(rootToken);
            commandRoots.add(rootNode);
        }

        buildCommandTree(tokens.subList(1, tokens.size()), rootNode, c);
    }

    private void buildCommandTree(List<String> remainingTokens, BasicNode lastNode, CommandPojo c) {
        // if you're on the last token, add response to the last node
        if (remainingTokens.isEmpty()) {
            // TODO find the type of response needed here.
            Response r = null;
            if (c.response != null) {
                r = new BasicResponse(c.response);
                r.setDelay(c.delay);
            } else if (c.multiPartResponse != null) {
                r = new MultipartResponse(c.multiPartResponse);
                r.setDelay(c.delay);
            } else if (c.responseTemplate != null) {
                r = new VariableResponse(c.responseTemplate);
            }
            lastNode.setResponse(r);
            return;
        }

        String currentToken = remainingTokens.get(0);
        remainingTokens = remainingTokens.subList(1, remainingTokens.size());

        for (BasicNode node : lastNode.getNextNodes()) {
            if (Objects.equals(node.getToken(), currentToken)) {
                buildCommandTree(remainingTokens, node, c);
                return;
            }
        }

        BasicNode nextNode;
        if (isVariableToken(currentToken)) {
            nextNode = new VariableNode(currentToken, c.allowed_values);
        } else {
            nextNode = new LiteralNode(currentToken);
        }
        lastNode.addNextNode(nextNode);
        buildCommandTree(remainingTokens, nextNode, c);
    }



    private LiteralNode getCommandRootNode(String token) {
        for (LiteralNode commandNode : commandRoots) {
            if (commandNode.getToken().equals(token)) {
                return commandNode;
            }
        }
        return null;
    }
}