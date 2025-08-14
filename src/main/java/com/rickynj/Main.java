package com.rickynj;

import com.rickynj.config.Config;
import com.rickynj.organisation.Organisation;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;

public class Main {
    public static void main(String[] args) {
        // TODO: caching is a yaml config now? 2 step parsing, first get settings
        // TODO: make caching optional.
        // TODO: regex based operator matching.
        ValkeyClient client = ValkeyClient.getValkeyClient();
        Organisation organisation = client.getCachedDeviceManagerIfExists(Config.commandsFile);
        if (organisation == null) {
            organisation = new Organisation();
            Parser parser = new YAMLParser(organisation, Config.commandsFile);
            parser.parse();
            client.cacheNewDeviceManager(Config.commandsFile, organisation);
        }

        Repl repl  = new Repl(organisation.getDeviceByPort(22));
//        Repl repl = new Repl(organisation.getDeviceByPort(Integer.parseInt(args[0])));
        repl.start();
    }
}