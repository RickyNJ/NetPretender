package com.rickynj.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.organisation.Organisation;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.POJO.DevicePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.rickynj.config.Constants.COMMANDSFILE;

public class YAMLParser implements Parser {
    private static final Logger logger = LoggerFactory.getLogger(YAMLParser.class);

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private final Organisation organisation;
    private final boolean caching;

    public YAMLParser(Organisation organisation,  boolean caching) {
        this.organisation = organisation;
        this.caching = caching;
    }

    public static DevicesWrapper parseFile() {
        logger.info("parsing yaml. {}", COMMANDSFILE);
        DevicesWrapper data;
        try {
            File yamlFile = new File(COMMANDSFILE);
            data = mapper.readValue(yamlFile, DevicesWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }


    @Override
    public DevicesWrapper parse() {
        logger.info("parsing yaml. {}", COMMANDSFILE);
        DevicesWrapper data;
        try {
            File yamlFile = new File(COMMANDSFILE);
            data = mapper.readValue(yamlFile, DevicesWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;

//        for (DevicePojo d : data.devices) {
//            organisation.addDevice(data.org, d, caching);
//        }
//        logger.info("Device created successfully.");
    }
}