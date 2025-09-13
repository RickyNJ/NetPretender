package com.rickynj.organisation;

import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.pojo.CommandPojo;
import com.rickynj.domain.pojo.DevicePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Organisation {
    private final HashMap<Integer, Device> devicesByPort = new HashMap<>();

  public Organisation(DevicesWrapper devicesWrapper) {
        for (DevicePojo dp : devicesWrapper.devices) {
            addDevice(devicesWrapper.org, devicesWrapper.caching, dp);
        }
    }

    public void addDevice(String org, boolean caching, DevicePojo d){
        for (int port : d.port) {
          Device device = new Device(d, caching, org, port);
          for (CommandPojo c : d.commands) {
            device.addCommand(c);
          }
          devicesByPort.put(port, device);
        }
    }

    // The program stops when no device is found, because the port the user used to connect.
    // Did not have a device configured.
    // In theory this should not trigger as an unconfigured port should not be exposed on the container.
    public Device getDevice(int port) {
      Device device = devicesByPort.get(port);
      if (device == null) {
          System.exit(1);
      }
      return device;
    }
}
