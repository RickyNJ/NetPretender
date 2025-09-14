package com.rickynj.parser;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.MapEntryDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.domain.pojo.CommandPojo;
import com.rickynj.domain.pojo.DevicePojo;
import com.rickynj.exception.ParserException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.rickynj.config.Constants.*;

public class DeviceDeserializer extends StdDeserializer<DevicePojo> {
  public DeviceDeserializer() {
    this(null);
  }
  public DeviceDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public DevicePojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    DevicePojo devicePojo = new DevicePojo();
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    if (node.has(NAME)) {
      devicePojo.name = node.get(NAME).asText();
    } else {
      throw new ParserException("Missing 'name' field in Device configuration");
    }
    if (node.has(PROMPT)) {
      devicePojo.prompt = node.get(PROMPT).asText();
    } else {
      devicePojo.prompt = "> ";
    }

    JsonNode portNode = node.get(PORT);
    if (portNode.isInt()) {
      devicePojo.port.add(portNode.asInt());
    } else if (portNode.isArray()) {
      for (JsonNode port : portNode) {
        devicePojo.port.add(port.asInt());
      }
    }

    JsonNode varsNode = node.get(VARS);
    if (varsNode != null && varsNode.isObject()) {
      Iterator<Map.Entry<String, JsonNode>> fields = varsNode.fields();
      while (fields.hasNext()) {
        Map.Entry<String, JsonNode> field = fields.next();
        devicePojo.vars.put(field.getKey(), field.getValue().asText());
      }
    }

    JsonNode commandsNode = node.get(COMMANDS);
    ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
    for (JsonNode command : commandsNode) {
      CommandPojo commandPojo = objectMapper.treeToValue(command, CommandPojo.class);
      devicePojo.commands.add(commandPojo);
    }

    return devicePojo;
  }
}
