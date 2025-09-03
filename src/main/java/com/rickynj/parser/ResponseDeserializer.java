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

public class ResponseDeserializer extends StdDeserializer<ResponsePojo> {
    protected ResponseDeserializer() {
        super(ResponsePojo.class);
    }

    @Override
    public ResponsePojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String response = node.get("response").asText();
        String responseFile = node.get("responseFile").asText();

        List<String> multiPartResponse = node.findValuesAsText("multiPartResponse");
        List<String> allowed_values = node.findValuesAsText("allowed_values");

        String operation = node.get("operation").asText();
        int delay = node.get("delay").asInt();
        return new ResponsePojo(response, multiPartResponse, responseFile, allowed_values, operation, delay);
    }
}
