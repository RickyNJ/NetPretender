package com.rickynj;

import com.rickynj.device.Device;
import com.rickynj.parser.Parser;
import com.rickynj.parser.YAMLParser;
import com.rickynj.repl.Repl;


public class Main {
    public static void main(String[] args) {
        Device device = new Device();

        Parser parser = new YAMLParser(device);
        parser.parse();

        Repl repl= new Repl(device);
        repl.start();
    }
}