package com.rickynj.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.domain.POJO.CasePojo;
import java.io.IOException;

public class CaseDeserializer extends StdDeserializer<CasePojo> {

  protected CaseDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public CasePojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    CasePojo casePojo = new CasePojo();
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    casePojo.caseStatement = node.get("case").asText();
    casePojo.response = ResponseDeserializer.deserialize(node);
    return casePojo;
  }
}
