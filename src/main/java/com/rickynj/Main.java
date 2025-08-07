package com.rickynj;

import com.rickynj.device.DeviceManager;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;
import org.redisson.api.RBucket;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        String valkeyConfigFile = "src/main/resources/redisson.yaml";
//        String valkeyConfigFile = "/opt/configs/redisson.yaml";

        String commandFilesPath =  "src/main/resources/commands.yml";
//        String commandFilesPath =  "/opt/configs/commands.yml";

        ValkeyClient client = new ValkeyClient(valkeyConfigFile, commandFilesPath);
        DeviceManager deviceManager;

        // TODO, the flow goes, get valkey hash from file name -> then hash file and check if it is the same -> if it is the same get the devicemanager using the hash as key
        // TODO, if not the same, delete previous device manager, using old hash, store new devicemanager.

        deviceManager = client.getCachedDeviceManagerIfExists(commandFilesPath);
        if (deviceManager == null) {
            deviceManager = new DeviceManager();
            YAMLParser parser = new YAMLParser(deviceManager, commandFilesPath);
            parser.parse();
            client.cacheNewDeviceManager(commandFilesPath, deviceManager);
        }

        Repl repl = new Repl(deviceManager.getDeviceByPort(22));
//        Repl repl = new Repl(deviceManager.getDeviceByPort(Integer.parseInt(args[0])));
        repl.start();
    }
}