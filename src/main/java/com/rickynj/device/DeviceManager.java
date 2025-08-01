package com.rickynj.device;

import java.util.HashMap;
import java.util.List;

public class DeviceManager {
    private HashMap<Integer, Device> devices;

    public void addDevice(Object d){
        // create device, check if port is defined. check if port is taken, return device.
        devices.put(22, new Device());
    }
    public Device getDeviceByPort(int port) {
        return devices.get(port);
    }
}
