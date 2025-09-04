package com.rickynj.organisation;

import com.rickynj.actions.condition.Condition;
import com.rickynj.actions.condition.ConditionUtility;
import com.rickynj.commands.BasicNode;
import com.rickynj.commands.VariableNode;
import com.rickynj.domain.CommandContext;
import com.rickynj.domain.POJO.ConditionPojo;
import com.rickynj.exception.CommandNotMockedException;
import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.actions.operation.Assign;
import com.rickynj.actions.operation.Execute;
import com.rickynj.actions.operation.Reset;
import com.rickynj.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Device  {
    private static final Logger logger = LoggerFactory.getLogger(Device.class);
    public final List<BasicNode> commandRoots = new ArrayList<>();
    public boolean caching;
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
            throw new CommandNotMockedException("Command of this root not found.");
        }

        BasicNode responseNode = traverseCommandTree(ctx, tokens.subList(1, tokens.size()), root);
        if (responseNode == null || responseNode.getResponse() == null) {
            throw new CommandNotMockedException(String.format("Command: \"%s\", has no mocked response.", ctx.command));
        } else {
            responseNode.evaluate(ctx);
            responseNode.respond(ctx);
            responseNode.execute(ctx);
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
            if (vn.any || vn.getAcceptableValues().contains(nextToken)) {
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
            populateNode(lastNode, c);
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
            nextNode = new VariableNode(currentToken, c.response.allowed_values);
        } else {
            nextNode = new BasicNode(currentToken);
        }
        lastNode.addNextNode(nextNode);
        buildCommandTree(remainingTokens, nextNode, c);
    }

    private void populateNode(BasicNode node, CommandPojo c) {
        if (c.operation != null) {
            node.setOperation(getOperationType(c));
        }
        if (c.condition != null) {
            for (ConditionPojo conditionPojo : c.condition) {
                Condition condition = ConditionUtility.getConditionType(conditionPojo);
                condition.addConditionToNode(node, conditionPojo);
            }
        }
        if (c.response != null) {
            node.setResponse(ResponseUtility.getResponseType(c));
        }
    }

    private BasicNode getCommandRootNode(String token) {
        for (BasicNode commandNode : commandRoots) {
            if (commandNode.getToken().equals(token)) {
                return commandNode;
            }
        }
        return null;
    }

    private Execute getOperationType(CommandPojo c) {
        // TODO find a more sustainable way of doing this
        String[] op = c.operation.split(" ");
        if (Objects.equals(op[0], "reset")) {
            return new Reset();
        } else {
            return new Assign(op[0], op[2]);
        }
    }
}