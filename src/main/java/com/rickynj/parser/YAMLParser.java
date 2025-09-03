package com.rickynj.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.domain.POJO.CasePojo;
import com.rickynj.domain.POJO.ResponsePojo;
import com.rickynj.organisation.Organisation;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.POJO.DevicePojo;
import com.rickynj.responses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.rickynj.config.Constants.COMMANDSFILE;

public class YAMLParser implements Parser {
    private static final Logger logger = LoggerFactory.getLogger(YAMLParser.class);

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public YAMLParser() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CasePojo.class, new CaseDeserializer());
        mapper.registerModule(module);
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
    }

    public static String getTextOrNull(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }
}