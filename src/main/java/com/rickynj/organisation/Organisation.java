package com.rickynj.organisation;

import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.domain.POJO.DevicePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Organisation {
    transient private final Logger logger = LoggerFactory.getLogger(Organisation.class);
    private final HashMap<Integer, Device> devices = new HashMap<>();

    public void addDevice(String org, DevicePojo d){
        logger.info("adding device to devicemanager, {}, {}", this, d);
        Device device = new Device();
        device.state = d.vars;
        device.defaultState = d.vars;
        device.name = org + "." + d.name;
        for (CommandPojo c : d.commands) {
            device.addCommand(c);
        }

        devices.put(d.port, device);
        // create device, check if port is defined. check if port is taken, return device.
    }
    public Device getDeviceByPort(int port) {
        //TODO check if device exists
        return devices.get(port);
    }
}
