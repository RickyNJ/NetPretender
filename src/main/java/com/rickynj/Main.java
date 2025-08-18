package com.rickynj;

import com.rickynj.domain.DevicesWrapper;
import com.rickynj.organisation.Organisation;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;
import com.rickynj.repository.valkey.ValkeyClient;

import static com.rickynj.config.Constants.COMMANDSFILE;


public class Main {
    public static void main(String[] args) {
        // TODO: think about sshmockserver interface, add getters to make commandctx creation possible
        // TODO: regex based operator matching.
        // TODO: make enabling caching a bit more explicit.
        // Caching will be automatically enabled when a redisson config file is enabled
        ValkeyClient client = ValkeyClient.getValkeyClient();
        DevicesWrapper dataWrapper = YAMLParser.parseFile();
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

//        Repl repl  = new Repl(organisation.getDevice(22));
        Repl repl = new Repl(organisation.getDevice(Integer.parseInt(args[0])));
        repl.start();
        }
}