package com.rickynj;

import com.rickynj.config.Constants;
import com.rickynj.organisation.Organisation;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;


public class Main {
    public static void main(String[] args) {
        // TODO: regex based operator matching.
        // TODO: make enabling caching a bit more explicit.
        // Caching will be automatically enabled when a redisson config file is enabled
        Organisation organisation = new Organisation();
        ValkeyClient client = ValkeyClient.getValkeyClient();
        if (client != null) {
            organisation = client.getCachedDeviceManagerIfExists();
            if (organisation == null) {
                organisation = new Organisation();
                Parser parser = new YAMLParser(organisation, true);
                parser.parse();
                client.cacheNewDeviceManager(organisation);
            }
        } else {
            Parser parser = new YAMLParser(organisation, false);
            parser.parse();
        }

        Repl repl  = new Repl(organisation.getDevice(22));
//        Repl repl = new Repl(organisation.getDeviceByPort(Integer.parseInt(args[0])));
        repl.start();
        }
}