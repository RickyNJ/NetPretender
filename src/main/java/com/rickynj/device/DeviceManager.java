package com.rickynj.device;

import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.domain.POJO.DevicePojo;

import java.util.HashMap;

public class DeviceManager {
    private final HashMap<Integer, Device> devices = new HashMap<>();

    public void addDevice(DevicePojo d){
        Device device = new Device();
        for (CommandPojo c : d.commands) {
            device.addCommand(c);
        }
        // create device, check if port is defined. check if port is taken, return device.
        devices.put(d.port, device);
    }
    public Device getDeviceByPort(int port) {
        return devices.get(port);
    }
}
