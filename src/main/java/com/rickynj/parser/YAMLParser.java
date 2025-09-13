package com.rickynj.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rickynj.domain.pojo.CasePojo;
import com.rickynj.domain.pojo.CommandPojo;
import com.rickynj.domain.pojo.ConditionPojo;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.domain.pojo.DevicePojo;
import com.rickynj.exception.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class YAMLParser  {
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());


    public static DevicesWrapper parseFile() {
        File yamlFile;
        try {
            URL url = YAMLParser.class.getClassLoader().getResource("commands.yml");
            if (url == null) {
                throw new FileNotFoundException("commands.yml not found in classpath");
            }
            yamlFile = new File(url.toURI());
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new ParserException("Failed to read command.yaml file");
        }
        addModules();
        DevicesWrapper data;
        try {
            data = mapper.readValue(yamlFile, DevicesWrapper.class);
        } catch (IOException e) {
            throw new ParserException("Exception while parsing commands file: ", e);
        }
        return data;
    }

    public static String getTextOrNull(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private static void addModules() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CasePojo.class, new CaseDeserializer());
        module.addDeserializer(ConditionPojo.class, new ConditionDeserializer());
        module.addDeserializer(CommandPojo.class, new CommandDeserializer());
        module.addDeserializer(DevicePojo.class, new DeviceDeserializer());
        mapper.registerModule(module);
    }

    private YAMLParser() {}
}