package com.rickynj.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.domain.POJO.ConditionPojo;
import java.io.IOException;

public class ConditionDeserializer extends StdDeserializer<ConditionPojo> {

  protected ConditionDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public ConditionPojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    ConditionPojo conditionPojo = new ConditionPojo();
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    conditionPojo.response = ResponseDeserializer.deserialize(node);
    return conditionPojo;
  }
}
