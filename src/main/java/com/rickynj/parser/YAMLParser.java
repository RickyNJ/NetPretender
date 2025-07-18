package com.rickynj.parser;

import com.rickynj.device.Device;
import com.rickynj.exception.ParserError;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class YAMLParser implements Parser {
    private static final Yaml yaml = new Yaml();
    private Device device;

    public YAMLParser(Device device) {
        this.device = device;
    }

    private Map<String, ?> readFile() {
        Map<String, ?> data;
        try {
            InputStream input  = new FileInputStream("src/main/resources/commands.yml");
            data = yaml.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    public void parse() {
        Map<String, ?> data = readFile();

        Object commands = data.get("commands");
        if (commands instanceof List<?> commandsList) {
            for (Object c : commandsList) {
                device.addCommand(c);
            }
        } else {
            throw new ParserError("no commands found.");
        }

        Object devices = data.get("devices");
        if (devices instanceof List<?> deviceList) {
            for (Object d : deviceList) {
                device.addDevice(d);
            }
        } else {
            throw new ParserError("no devices found.");
        }
    }
}