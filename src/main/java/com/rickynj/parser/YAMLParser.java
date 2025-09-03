package com.rickynj.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.domain.POJO.CasePojo;
import com.rickynj.domain.POJO.CommandPojo;
import com.rickynj.domain.POJO.ConditionPojo;
import com.rickynj.domain.DevicesWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.rickynj.config.Constants.COMMANDSFILE;

public class YAMLParser implements Parser {
    private static final Logger logger = LoggerFactory.getLogger(YAMLParser.class);
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static DevicesWrapper parseFile() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CasePojo.class, new CaseDeserializer());
        module.addDeserializer(ConditionPojo.class, new ConditionDeserializer());
        module.addDeserializer(CommandPojo.class, new CommandDeserializer());
        mapper.registerModule(module);
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