package com.rickynj.organisation;

import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.pojo.CommandPojo;
import com.rickynj.domain.pojo.DevicePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Organisation {
    private static final Logger logger = LoggerFactory.getLogger(Organisation.class);
    private final HashMap<Integer, Device> devicesByPort = new HashMap<>();

  public Organisation(DevicesWrapper devicesWrapper) {
        for (DevicePojo dp : devicesWrapper.devices) {
            addDevice(devicesWrapper.org, devicesWrapper.caching, dp);
        }
    }

    public void addDevice(String org, boolean caching, DevicePojo d){
        for (int port : d.port) {
          logger.info("adding device to devicemanager, {}, {}", this, d);
          Device device = new Device(d, caching, org, port);
          for (CommandPojo c : d.commands) {
            device.addCommand(c);
          }
          devicesByPort.put(port, device);
        }
    }
    public Device getDevice(int port) {
        return devicesByPort.get(port);
    }
}
