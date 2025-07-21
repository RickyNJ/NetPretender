package com.rickynj.device;

import com.rickynj.commands.CommandNode;
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

    public void addCommand(CommandPojo c){
        // Obviously fix this
        String[] tokens = c.command.split("");
        String rootToken = tokens[0];
        CommandNode rootNode = getCommandRootNode(rootToken);
        if (rootNode == null) {
            rootNode = new CommandNode(rootToken);
        }

        for (String token : tokens[1:]) {
            System.out.println(token);
        }

    }


    public CommandNode getCommandRootNode(String token) {
        for (CommandNode commandNode : commandRoots) {
            if (commandNode.getToken().equals(token)) {
                return commandNode;
            }
        }

        if (defaultResponse == null) {
            throw new CommandNotMockedException("Command of this root not found.");
        } else {
            return defaultResponse;
        }


    }
}