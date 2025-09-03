package com.rickynj.parser;

import static com.rickynj.parser.YAMLParser.getTextOrNull;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.domain.POJO.CasePojo;
import com.rickynj.domain.POJO.ConditionPojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConditionDeserializer extends StdDeserializer<ConditionPojo> {

  public ConditionDeserializer() {
    this(null);
  }

  public  ConditionDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public ConditionPojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    ConditionPojo conditionPojo = new ConditionPojo();
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    conditionPojo.ifStatement = getTextOrNull(node, "if");
    if (node.has("else")) {
      conditionPojo.elseStatement = "PRESENT";
    }
    conditionPojo.switchStatement = getTextOrNull(node, "switch");
    conditionPojo.operation = getTextOrNull(node, "operation");

    conditionPojo.response = ResponseDeserializer.deserialize(node);

    if (node.has("cases")) {
      ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
      List<CasePojo> caseList = new ArrayList<>();
      for (JsonNode caseNode : node.get("cases")) {
        CasePojo casePojo = objectMapper.treeToValue(caseNode, CasePojo.class);
        caseList.add(casePojo);
      }
      conditionPojo.cases = caseList;
    }

    return conditionPojo;
  }
}
