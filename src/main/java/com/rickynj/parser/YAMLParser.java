package com.rickynj.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.device.DeviceManager;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.POJO.DevicePojo;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class YAMLParser implements Parser {
    private String commandFilesPath =  "src/main/resources/commands.yml";
//    private static final String commandFilesPath =  "/opt/configs/commands.yml";

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final DeviceManager deviceManager;

    public YAMLParser(DeviceManager deviceManager, String commandFilesPath) {
        this.deviceManager = deviceManager;
        this.commandFilesPath = commandFilesPath;
    }

    private DevicesWrapper readFile() throws IOException {
        File yamlFile = new File(commandFilesPath);
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