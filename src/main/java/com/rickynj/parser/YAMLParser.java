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
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final DeviceManager deviceManager;

    public YAMLParser(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    private DevicesWrapper readFile() throws IOException {
        File yamlFile = new File("/opt/configs/commands.yml");
//        File yamlFile = new File("src/main/resources/commands.yml");
        return mapper.readValue(yamlFile, DevicesWrapper.class);
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
        }
    }
}