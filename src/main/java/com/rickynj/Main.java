package com.rickynj;

import com.rickynj.device.DeviceManager;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyConnector;
import org.redisson.api.RBucket;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        DeviceManager deviceManager = new DeviceManager();

        // TODO: make this into a static at some point
//        Parser parser = new YAMLParser(deviceManager);
//        parser.parse();
        ValkeyConnector conn = new ValkeyConnector();
        RBucket<DeviceManager> dm = conn.client.getBucket("dm");
//        dm.set(deviceManager);
        deviceManager = dm.get();

        Repl repl = new Repl(deviceManager.getDeviceByPort(22));
//        Repl repl = new Repl(deviceManager.getDeviceByPort(Integer.parseInt(args[0])));
        repl.start();
    }
}