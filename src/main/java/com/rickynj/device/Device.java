package com.rickynj.device;

import com.rickynj.commands.CommandNode;

import java.util.List;
import java.util.Map;

// THIS IS A WIP
public class Device {
    public List<CommandNode> commandRoots;
    public Map<String, String> internalState;

    public void addDevice(Object d){
        System.out.println("adding Command");
    }
    public void addCommand(Object c){
        System.out.println("adding Object");
    }

}