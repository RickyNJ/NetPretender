package com.rickynj;

import com.rickynj.config.Config;
import com.rickynj.device.DeviceManager;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;
import io.netty.channel.unix.Errors;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // TODO: make caching optional.
        ValkeyClient client = ValkeyClient.getValkeyClient();
        DeviceManager deviceManager = client.getCachedDeviceManagerIfExists(Config.commandsFile);
        if (deviceManager == null) {
            deviceManager = new DeviceManager();
            YAMLParser parser = new YAMLParser(deviceManager, Config.commandsFile);
            parser.parse();
            client.cacheNewDeviceManager(Config.commandsFile, deviceManager);
        }

        Repl repl  = new Repl(deviceManager.getDeviceByPort(22));
//        Repl repl = new Repl(deviceManager.getDeviceByPort(Integer.parseInt(args[0])));
        repl.start();
    }
}