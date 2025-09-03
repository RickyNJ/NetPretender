package com.rickynj.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.domain.POJO.ResponsePojo;
import com.rickynj.organisation.Device;

import com.rickynj.responses.Response;
import java.io.IOException;
import java.util.List;

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

    private static String getTextOrNull(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }
}
