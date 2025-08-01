package com.rickynj.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.device.DeviceManager;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.domain.POJO.DevicePojo;
import com.rickynj.exception.ParserException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

public class YAMLParser implements Parser {
    private static final Yaml yaml = new Yaml();
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final DeviceManager deviceManager;

    public YAMLParser(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    private DevicesWrapper readFile() throws IOException {
        File yamlFile = new File("src/main/resources/commands.yml");
        return mapper.readValue(yamlFile, DevicesWrapper.class);
//        Map<String, ?> data;
//        try {
//            InputStream input  = new FileInputStream("src/main/resources/commands.yml");
//            data = yaml.load(input);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return data;
    }
//    TODO: implement
    private boolean validateCommand(CommandPojo c) {
        return true;
    }
    //    TODO: implement
    private boolean validateDevice(DevicePojo d) {
        return true;
    }

    @Override
    public void parse() {
        DevicesWrapper data;
        try {
            data = readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (DevicePojo d : data.devices) {
            deviceManager.addDevice(d);
            System.out.println(d.getClass());
        }
//        Object devices = data.get("devices");

//        if (devices instanceof List<?> deviceList) {
//            for (Object d : deviceList) {
//                DevicePojo dp = mapper.convertValue(d, DevicePojo.class);
//                deviceManager.addDevice(dp);
//            }
//        } else {
//            throw new ParserException("no devices found.");
//        }

//        Object commands = data.get("commands");
//        if (commands instanceof List<?> commandsList) {
//            for (Object c : commandsList) {
//                CommandPojo cm = mapper.convertValue(c, CommandPojo.class);
//                device.addCommand(cm);
//            }
//        } else {
//            throw new ParserException("no commands found.");
//        }

    }
}