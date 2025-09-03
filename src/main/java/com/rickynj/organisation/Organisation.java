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

  public Organisation(DevicesWrapper devicesWrapper) {
        for (DevicePojo dp : devicesWrapper.devices) {
            addDevice(devicesWrapper.org, devicesWrapper.caching, dp);
        }
    }

    public void addDevice(String org, boolean caching, DevicePojo d){
        logger.info("adding device to devicemanager, {}, {}", this, d);
        Device device = new Device();
        device.caching = caching;
        device.state = d.vars;
        device.defaultState = new HashMap<>(d.vars);
        device.name = org + "." + d.name;

        for (CommandPojo c : d.commands) {
            device.addCommand(c);
        }

        devicesByPort.put(d.port, device);
        devicesByName.put(d.name, device);
    }
    public Device getDevice(int port) {
        return devicesByPort.get(port);
    }

}
