package com.rickynj.parser;

import static com.rickynj.config.Constants.*;
import static com.rickynj.parser.YAMLParser.getTextOrNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.rickynj.domain.pojo.ResponsePojo;

import java.util.ArrayList;
import java.util.List;

public class ResponseDeserializer {
    private ResponseDeserializer() {}

    public static ResponsePojo deserialize(JsonNode node) {
        ResponsePojo responsePojo = new ResponsePojo();
        responsePojo.response = getTextOrNull(node, RESPONSE);
        responsePojo.responseFile = getTextOrNull(node, RESPONSE_FILE);

        if (node.has(MULTI_PART_RESPONSE)) {
            ArrayNode arrayNode = (ArrayNode) node.get(MULTI_PART_RESPONSE);
            List<String> values = new ArrayList<>();
            arrayNode.forEach(n -> values.add(n.asText()));
            responsePojo.multiPartResponse = values;
        }

        if (node.has(ALLOWED_VALUES)) {
            ArrayNode arrayNode = (ArrayNode) node.get(ALLOWED_VALUES);
            List<String> values = new ArrayList<>();
            arrayNode.forEach(n -> values.add(n.asText()));
            responsePojo.multiPartResponse = values;
        }

        responsePojo.operation = getTextOrNull(node, OPERATION);

        responsePojo.delay = node.has(DELAY) ? node.get(DELAY).asInt() : 0;
        return responsePojo;
    }
}
