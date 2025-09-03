package com.rickynj.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.organisation.Device;

import com.rickynj.responses.Response;
import java.io.IOException;

public class ResponseDeserializer extends StdDeserializer<Response> {
    protected ResponseDeserializer() {
        super(Response.class);
    }

    @Override
    public Response deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return null;
    }
}
