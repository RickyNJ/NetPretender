package com.rickynj.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.device.DeviceManager;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.POJO.DevicePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class YAMLParser implements Parser {
    private final Logger logger = LoggerFactory.getLogger(YAMLParser.class);

    private final String commandFilesPath;
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private final DeviceManager deviceManager;

    public YAMLParser(DeviceManager deviceManager, String commandFilesPath) {
        this.deviceManager = deviceManager;
        this.commandFilesPath = commandFilesPath;
    }

    @Override
    public void parse() {
        logger.info("parsing yaml. {}", commandFilesPath);
        DevicesWrapper data;
        try {
            File yamlFile = new File(commandFilesPath);
            data = mapper.readValue(yamlFile, DevicesWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (DevicePojo d : data.devices) {
            deviceManager.addDevice(data.org, d);
        }
        logger.info("Device created successfully.");
    }
}