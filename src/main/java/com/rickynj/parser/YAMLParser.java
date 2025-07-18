package com.rickynj.parser;

import com.rickynj.device.Device;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class YAMLParser implements Parser {
    @Override
    public Device parse() {
        Yaml yaml = new Yaml();
        Object commands;
        try {
            InputStream input  = new FileInputStream("src/main/resources/commands.yml");
            commands = yaml.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(commands);
        return null;
    }
}
