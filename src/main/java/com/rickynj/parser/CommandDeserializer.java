package com.rickynj.parser;

import static com.rickynj.parser.YAMLParser.getTextOrNull;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.domain.pojo.CommandPojo;
import com.rickynj.domain.pojo.ConditionPojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandDeserializer extends StdDeserializer<CommandPojo> {
  public CommandDeserializer() {
    this(null);
  }

  public CommandDeserializer(Class<?> vc) {
    super(vc);
  }
  @Override
  public CommandPojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    CommandPojo commandPojo = new CommandPojo();
    commandPojo.command = getTextOrNull(node, "command");
    commandPojo.operation = getTextOrNull(node, "operation");
    commandPojo.response = ResponseDeserializer.deserialize(node);

    if (node.has("condition")) {
      ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
      List<ConditionPojo> conditionList = new ArrayList<>();
      for (JsonNode conditionNode : node.get("condition")) {
        ConditionPojo conditionPojo = objectMapper.treeToValue(conditionNode, ConditionPojo.class);
        conditionList.add(conditionPojo);
      }
      commandPojo.condition = conditionList;
    }
    return commandPojo;
  }
}
