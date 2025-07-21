package com.rickynj.device;

import com.rickynj.commands.CommandNode;
import com.rickynj.exception.CommandNotMockedException;

import java.util.List;
import java.util.Map;

// THIS IS A WIP
public class Device {
    private CommandNode defaultResponse;
    private List<CommandNode> commandRoots;
    private Map<String, String> internalState;

    public void addDevice(Object d){
        System.out.println("adding Device");
    }

    public void addCommand(Object c){
        // Obviously fix this
        System.out.println("adding Command");
        commandRoots.add((CommandNode) c);
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