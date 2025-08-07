package com.rickynj;

import com.rickynj.device.DeviceManager;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;



public class Main {
    public static void main(String[] args) {
        String valkeyConfigFile = "src/main/resources/redisson.yaml";
//        String valkeyConfigFile = "/opt/configs/redisson.yaml";

        String commandFilesPath =  "src/main/resources/commands.yml";
//        String commandFilesPath =  "/opt/configs/commands.yml";

        DeviceManager deviceManager = getDeviceManager(valkeyConfigFile, commandFilesPath);

        Repl repl = new Repl(deviceManager.getDeviceByPort(22));
//        Repl repl = new Repl(deviceManager.getDeviceByPort(Integer.parseInt(args[0])));
        repl.start();
    }

    private static DeviceManager getDeviceManager(String valkeyConfigFile, String commandFilesPath) {
        ValkeyClient client = new ValkeyClient(valkeyConfigFile, commandFilesPath);
        DeviceManager deviceManager;

        // TODO: make caching optional.
        deviceManager = client.getCachedDeviceManagerIfExists(commandFilesPath);
        if (deviceManager == null) {
            deviceManager = new DeviceManager();
            YAMLParser parser = new YAMLParser(deviceManager, commandFilesPath);
            parser.parse();
            client.cacheNewDeviceManager(commandFilesPath, deviceManager);
        }
        return deviceManager;
    }
}