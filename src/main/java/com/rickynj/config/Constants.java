package com.rickynj.config;

import java.util.Properties;

public class Constants {

//    public static final String REDISSONCONFIGFILEPATH = Config.get("redissonConfigFile");
    public static final String COMMANDSFILE = Config.get("commandsFile");
    public static final String responsesDirectory = Config.get("responsesDir");

    public static final String REDISSONCONFIGFILEPATH = "src/main/resources/redisson.yaml";
//    public static final String COMMANDSFILE = "/opt/configs/commands.yml";
//    public static final String responsesDirectory = "/opt/configs/responses/";


    // Commandfile YAML keys
    public static final String NAME = "name";
    public static final String PROMPT = "prompt";
    public static final String PORT = "port";

    public static final String COMMANDS = "commands";
    public static final String COMMAND = "command";
    public static final String VARS = "vars";
    public static final String RESPONSE = "response";
    public static final String RESPONSE_FILE = "responseFile";
    public static final String MULTI_PART_RESPONSE = "multiPartResponse";
    public static final String DELAY = "delay";
    public static final String ALLOWED_VALUES = "allowed_values";

    public static final String OPERATION = "operation";
    public static final String RESET = "reset";
    public static final String IF = "if";
    public static final String ELSE = "else";
    public static final String SWITCH = "switch";
    public static final String CASES = "cases";
    public static final String CASE = "case";

    public static final String CONDITION = "condition";
    public static final String FALSE = "false";
    public static final String TRUE = "true";

    private Constants(){};
}
