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
        ResponsePojo responsePojo = new ResponsePojo();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        responsePojo.setResponse(node.get("response").asText());
        responsePojo.setResponseFile(node.get("responseFile").asText());
        responsePojo.setMultiPartResponse(node.findValuesAsText("multiPartResponse"));
        responsePojo.setAllowed_values(node.findValuesAsText("allowed_values"));

        responsePojo.setOperation(node.get("operation").asText());
        responsePojo.setDelay(node.get("delay").asInt());
        return responsePojo;
    }
}
