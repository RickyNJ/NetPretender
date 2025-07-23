package com.rickynj.device;

import com.rickynj.commands.BasicNode;
import com.rickynj.commands.CommandNode;
import com.rickynj.commands.VariableNode;
import com.rickynj.exception.CommandNotMockedException;
import com.rickynj.domain.CommandPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private void buildCommandTree(List<String> remainingTokens, BasicNode lastNode, CommandPojo c) {
        // if you're on the last token, add response to the last node
        if (remainingTokens.isEmpty()) {
            lastNode.setResponse(null);
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


    public CommandNode getCommandRootNode(String token) {
        for (CommandNode commandNode : commandRoots) {
            if (commandNode.getToken().equals(token)) {
                return commandNode;
            }
        }
        return null;
    }

    public CommandNode matchCommandWithRootNode(String token) {
        CommandNode root = getCommandRootNode(token);
        if (root != null) {
            return root;
        } else {
            if (defaultResponse == null) {
                throw new CommandNotMockedException("Command of this root not found.");
            } else {
                return defaultResponse;
            }
        }
    }
}