package com.rickynj;


import com.rickynj.commands.CommandNode;
import com.rickynj.device.Device;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.responses.MultipartResponse;
import com.rickynj.responses.Response;


/**
 * flow will be:
 * new parser -> yaml parser for new (snakeyaml)
 * parser.parse() -> new Device();
 *
 * new REPL(device);
 *
 */
public class Main {
    public static void main(String[] args) {
        Parser parser = new YAMLParser();
        Device device = new Device();
        parser.parse();
        System.out.printf("Hello and welcome!");
    }
}