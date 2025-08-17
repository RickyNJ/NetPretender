package com.rickynj.organisation;

import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.domain.POJO.DevicePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Organisation {
    private static final Logger logger = LoggerFactory.getLogger(Organisation.class);
    private final HashMap<Integer, Device> devicesByPort = new HashMap<>();
    private final HashMap<String, Device> devicesByName = new HashMap<>();

    private String org;

    public Organisation(DevicesWrapper devicesWrapper) {
        for (DevicePojo dp : devicesWrapper.devices) {
            addDevice(org, dp, devicesWrapper.caching);
        }
    }

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
    public SSHMockServer getDevice(int port) {
        //TODO check if device exists
        return devicesByPort.get(port);
    }

    public Device getDevice(String name) {
        String fullName = org + "." + name;
        return devicesByName.get(fullName);
    }

}
