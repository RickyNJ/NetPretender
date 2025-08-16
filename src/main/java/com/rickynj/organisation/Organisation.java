package com.rickynj.organisation;

import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.domain.POJO.DevicePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Organisation {
    transient private final Logger logger = LoggerFactory.getLogger(Organisation.class);
    private final HashMap<Integer, Device> devicesByPort = new HashMap<>();
    private final HashMap<String, Device> devicesByName = new HashMap<>();

    private String org;

    public void addDevice(String org, DevicePojo d, boolean caching){
        logger.info("adding device to devicemanager, {}, {}", this, d);

        this.org = org;

        Device device = new Device();
        device.caching = caching;
        device.state = d.vars;
        device.defaultState = d.vars;
        device.name = org + "." + d.name;

        for (CommandPojo c : d.commands) {
            device.addCommand(c);
        }

        devicesByPort.put(d.port, device);
        devicesByName.put(d.name, device);
    }
    public Device getDevice(int port) {
        //TODO check if device exists
        return devicesByPort.get(port);
    }

    public Device getDevice(String name) {
        String fullName = org + "." + name;
        return devicesByName.get(fullName);
    }

}
