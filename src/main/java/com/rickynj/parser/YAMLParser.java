package com.rickynj.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.device.DeviceManager;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.POJO.DevicePojo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class YAMLParser implements Parser {
    private static final String commandFilesPath =  "src/main/resources/commands.yml";
//    private static final String commandFilesPath =  "/opt/configs/commands.yml";

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final DeviceManager deviceManager;

    public YAMLParser(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    private DevicesWrapper readFile() throws IOException {
        File yamlFile = new File(commandFilesPath);
        return mapper.readValue(yamlFile, DevicesWrapper.class);
    }

    private byte[] getFileHash() throws NoSuchAlgorithmException, IOException {
        return MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of(commandFilesPath)));
    }

    @Override
    public void parse() {
        DevicesWrapper data;
        try {
            byte[] hash = getFileHash();
            data = readFile();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        for (DevicePojo d : data.devices) {
            deviceManager.addDevice(d);
        }
    }
}