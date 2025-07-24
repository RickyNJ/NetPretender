package com.rickynj.device;

import com.rickynj.commands.BasicNode;
import com.rickynj.commands.CommandNode;
import com.rickynj.commands.VariableNode;
import com.rickynj.exception.CommandNotMockedException;
import com.rickynj.domain.CommandPojo;
import com.rickynj.responses.BasicResponse;
import com.rickynj.responses.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// THIS IS A WIP
public class Device {
    private CommandNode defaultResponse;
    private final List<CommandNode> commandRoots = new ArrayList<CommandNode>();
    private Map<String, String> state;

    public void addDevice(Object d){
        System.out.println("adding Device");
    }

    private boolean isVariableToken(String token) {
        return (token.startsWith("${") && token.endsWith("}"));
    }

    public void respondToCommand(String command) throws InterruptedException, CommandNotMockedException {
        List<String> tokens = List.of(command.split(" "));
        BasicNode root = getCommandRootNode(tokens.getFirst());
        if (root == null) {
            if (defaultResponse != null) {
                defaultResponse.respond();
            } else {
                throw new CommandNotMockedException("Command of this root not found.");
            }
        }

        BasicNode responseNode = traverseCommandTree(tokens.subList(1, tokens.size()), root);
        if (responseNode == null || responseNode.getResponse() == null) {
            if (defaultResponse != null) {
                defaultResponse.respond();
            } else {
                throw new CommandNotMockedException(String.format("Command: \"%s\", has no mocked response.", command));
            }
        } else {
            responseNode.respond();
        }
    }

    private BasicNode traverseCommandTree(List<String> tokens, BasicNode node) {
        if (tokens.isEmpty()) {
            return node;
        }
        String nextToken = tokens.getFirst();
        for (BasicNode n : node.getNextNodes()) {
            if (Objects.equals(n.getToken(), nextToken)){
               return traverseCommandTree(tokens.subList(1, tokens.size()), n);
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
            if (node.getToken().equals(remainingTokens.getFirst())) {
                buildCommandTree(remainingTokens, node, c);
            }
        }

        BasicNode nextNode;
        if (isVariableToken(currentToken)) {
            nextNode = new VariableNode(currentToken);
        } else {
            nextNode = new CommandNode(currentToken);
        }
        lastNode.addNextNode(nextNode);
        buildCommandTree(remainingTokens, nextNode, c);
    }

    public void addCommand(CommandPojo c){
        List<String> tokens = List.of(c.command.split(" "));
        String rootToken = tokens.getFirst();

        CommandNode rootNode = getCommandRootNode(rootToken);
        if (rootNode == null) {
            rootNode = new CommandNode(rootToken);
            commandRoots.add(rootNode);
        }

        buildCommandTree(tokens.subList(1, tokens.size()), rootNode, c);
    }

    private CommandNode getCommandRootNode(String token) {
        for (CommandNode commandNode : commandRoots) {
            if (commandNode.getToken().equals(token)) {
                return commandNode;
            }
        }
        return null;
    }
}