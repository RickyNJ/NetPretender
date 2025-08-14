package com.rickynj.organisation;

import com.rickynj.commands.BasicNode;
import com.rickynj.commands.VariableNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.exception.CommandNotMockedException;
import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.functions.Assign;
import com.rickynj.functions.Operation;
import com.rickynj.functions.Reset;
import com.rickynj.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Device {
    transient private Logger logger = LoggerFactory.getLogger(Device.class);
    public BasicNode defaultResponse;
    public final List<BasicNode> commandRoots = new ArrayList<>();
    public Map<String, String> defaultState;
    public Map<String, String> state;
    public String name;

    public void setState(String key, String value) {
        state.put(key, value);
    }

    public Map<String, String> getState() {
        return state;
    }

    private boolean isVariableToken(String token) {
        return (token.startsWith("${") && token.endsWith("}"));
    }

    public void respondToCommand(CommandContext ctx) throws InterruptedException, CommandNotMockedException {
        List<String> tokens = List.of(ctx.command.split(" "));
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
                throw new CommandNotMockedException(String.format("Command: \"%s\", has no mocked response.", ctx.command));
            }
        } else {
            responseNode.respond(ctx);
            if (responseNode.hasOperation()) {
                responseNode.execute(ctx);
            }
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
                ctx.setContextVar(vn.getToken(), nextToken);
                return traverseCommandTree(ctx, tokens.subList(1, tokens.size()), vn);
            }
        }
        return null;
    }

    public void addCommand(CommandPojo c){
        logger.info("Adding command to device. {}, {}", c, this);
        List<String> tokens = List.of(c.command.split(" "));
        String rootToken = tokens.get(0);

        BasicNode rootNode = getCommandRootNode(rootToken);
        if (rootNode == null) {
            logger.info("New root command found. {}, {}", rootToken, this);
            rootNode = new BasicNode(rootToken);
            commandRoots.add(rootNode);
        }
        buildCommandTree(tokens.subList(1, tokens.size()), rootNode, c);
    }

    private void buildCommandTree(List<String> remainingTokens, BasicNode lastNode, CommandPojo c) {
        // if you're on the last token, add response to the last node
        if (remainingTokens.isEmpty()) {
            if (c.operation != null) {
                lastNode.setOperation(getOperationType(c));
            }
            lastNode.setResponse(getResponseType(c));
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
            nextNode = new BasicNode(currentToken);
        }
        lastNode.addNextNode(nextNode);
        buildCommandTree(remainingTokens, nextNode, c);
    }

    private BasicNode getCommandRootNode(String token) {
        for (BasicNode commandNode : commandRoots) {
            if (commandNode.getToken().equals(token)) {
                return commandNode;
            }
        }
        return null;
    }

    private Response getResponseType(CommandPojo c) {
        Response r = null;
        if (c.response != null) {
            r = new BasicResponse(c.response);
            r.setDelay(c.delay);
        } else if (c.multiPartResponse != null) {
            r = new MultipartResponse(c.multiPartResponse);
            r.setDelay(c.delay);
        } else if (c.responseFile != null ) {
            r = new FileResponse(c.responseFile);
        }
        return r;
    }

    private Operation getOperationType(CommandPojo c) {
        // TODO find a more sustainable way of doing this
        String[] op = c.operation.split(" ");
        if (Objects.equals(op[0], "reset")) {
            return new Reset();
        } else {
            return new Assign(op[0], op[2]);
        }
    }
}