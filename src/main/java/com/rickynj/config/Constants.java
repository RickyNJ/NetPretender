package com.rickynj.config;

import java.util.Properties;

public class Constants {

    public static final String REDISSONCONFIGFILEPATH = Config.get("redissonConfigFile");
    public static final String COMMANDSFILE = Config.get("commandsFile");
    public static final String responsesDirectory = Config.get("responsesDir");

//    public static final String REDISSONCONFIGFILEPATH = "/opt/configs/redisson.yaml";
//    public static final String COMMANDSFILE = "/opt/configs/commands.yml";
//    public static final String responsesDirectory = "/opt/configs/responses/";

    private Constants(){};
}
