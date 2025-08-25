package com.rickynj;

import com.rickynj.domain.DevicesWrapper;
import com.rickynj.organisation.Organisation;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;

public class Main {
    public static void main(String[] args) {
        // TODO: regex based operator matching.
        // TODO: refactor device class, god object antipattern
        DevicesWrapper dataWrapper = YAMLParser.parseFile();
        ValkeyClient client = ValkeyClient.getValkeyClient();
        Organisation organisation;

        if (dataWrapper.caching && client != null) {
            organisation = client.getCachedDeviceManagerIfExists();
            if (organisation == null) {
                organisation = new Organisation(dataWrapper);
                client.cacheNewDeviceManager(organisation);
            }
        } else {
            organisation = new Organisation(dataWrapper);
        }

        Repl repl = new Repl(organisation.getDevice(Integer.parseInt(args[0])));
        repl.start();
        }
}