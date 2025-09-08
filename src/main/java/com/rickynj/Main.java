package com.rickynj;

import com.rickynj.domain.DevicesWrapper;
import com.rickynj.organisation.Organisation;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;

public class Main {
    public static void main(String[] args) {
        // TODO: regex based operator matching.
        // TODO: Stop valkeyclient from initializing when caching is disabled.
        // TODO: refactor device class, god object antipattern
        // TODO: make operationPOJO to enable multiple operations.

        DevicesWrapper dataWrapper = YAMLParser.parseFile();
        Organisation organisation;
        ValkeyClient valkeyClient = null;

        if (dataWrapper.caching) {
            valkeyClient = ValkeyClient.getValkeyClient();
            organisation = valkeyClient.getCachedDeviceManagerIfExists();
            if (organisation == null) {
                organisation = new Organisation(dataWrapper);
                valkeyClient.cacheNewDeviceManager(organisation);
            }
        } else {
            organisation = new Organisation(dataWrapper);
        }

        Repl repl = new Repl(organisation.getDevice(Integer.parseInt(args[0])), valkeyClient);
        repl.start();
        }
}