package com.rickynj;

import com.rickynj.device.Device;
import com.rickynj.device.DeviceManager;
import com.rickynj.domain.CommandContext;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        DeviceManager deviceManager = new DeviceManager();
        Parser parser = new YAMLParser(deviceManager);
        parser.parse();
        /*TODO: application starts on new connection. find a way to pass the port
        * TODO: then match port with device manager. start REPL with matching device.
        */

        Repl repl= new Repl(deviceManager.getDeviceByPort(22));
        repl.start();
    }
}