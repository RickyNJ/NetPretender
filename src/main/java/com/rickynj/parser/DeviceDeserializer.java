package com.rickynj.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.organisation.Device;

import java.io.IOException;

public class DeviceDeserializer extends StdDeserializer<Device> {
    protected DeviceDeserializer() {
        super(Device.class);
    }

    @Override
    public Device deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return null;
    }
}
