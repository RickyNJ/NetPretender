package com.rickynj.parser;

import static com.rickynj.parser.YAMLParser.getTextOrNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.rickynj.domain.pojo.ResponsePojo;

public class ResponseDeserializer {
    private ResponseDeserializer() {}

    public static ResponsePojo deserialize(JsonNode node) {
        ResponsePojo responsePojo = new ResponsePojo();
        responsePojo.response = getTextOrNull(node, "response");
        responsePojo.responseFile = getTextOrNull(node, "responseFile");
        responsePojo.multiPartResponse = node.findValuesAsText("multiPartResponse");
        responsePojo.allowed_values = node.findValuesAsText("allowed_values");
        responsePojo.operation = getTextOrNull(node, "operation");
        responsePojo.delay = node.has("delay") ? node.get("delay").asInt() : 0;
        return responsePojo;
    }
}
