package com.rickynj.device;

import com.rickynj.commands.BasicNode;
import com.rickynj.commands.LiteralNode;
import com.rickynj.commands.VariableNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.exception.CommandNotMockedException;
import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.responses.BasicResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Device {
    private LiteralNode defaultResponse;
    private final List<LiteralNode> commandRoots = new ArrayList<>();
    private Map<String, String> state;

    public void addDevice(Object d){
        System.out.println("adding Device");
    }

    private boolean isVariableToken(String token) {
        return (token.startsWith("${") && token.endsWith("}"));
    }

    public void respondToCommand(CommandContext ctx) throws InterruptedException, CommandNotMockedException {

        List<String> tokens = List.of(ctx.getCommand().split(" "));
        BasicNode root = getCommandRootNode(tokens.getFirst());
        if (root == null) {
            if (defaultResponse != null) {
                defaultResponse.respond(ctx);
            } else {
                throw new CommandNotMockedException("Command of this root not found.");
            }
        }

        BasicNode responseNode = traverseCommandTree(tokens.subList(1, tokens.size()), root);
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

    private BasicNode traverseCommandTree(List<String> tokens, BasicNode node) {
        if (tokens.isEmpty()) {
            return node;
        }
        String nextToken = tokens.getFirst();
        List<VariableNode> varNodes = new ArrayList<>();
        for (BasicNode n : node.getNextNodes()) {
            if (Objects.equals(n.getToken(), nextToken)){
               return traverseCommandTree(tokens.subList(1, tokens.size()), n);
            }
            if (n instanceof VariableNode vn) {
                varNodes.add(vn);
            }
        }
        for (VariableNode vn : varNodes) {
            if (vn.getAcceptableValues().contains(nextToken)) {
                return traverseCommandTree(tokens.subList(1, tokens.size()), vn);
            }
        }
        return null;
    }

    private void buildCommandTree(List<String> remainingTokens, BasicNode lastNode, CommandPojo c) {
        // if you're on the last token, add response to the last node
        if (remainingTokens.isEmpty()) {
            // TODO find the type of response needed here.
            BasicResponse r = new BasicResponse(c.response);
            r.setDelay(c.delay);
            lastNode.setResponse(r);
            return;
        }

        String currentToken = remainingTokens.getFirst();
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

    public void addCommand(CommandPojo c){
        List<String> tokens = List.of(c.command.split(" "));
        String rootToken = tokens.getFirst();

        LiteralNode rootNode = getCommandRootNode(rootToken);
        if (rootNode == null) {
            rootNode = new LiteralNode(rootToken);
            commandRoots.add(rootNode);
        }

        buildCommandTree(tokens.subList(1, tokens.size()), rootNode, c);
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