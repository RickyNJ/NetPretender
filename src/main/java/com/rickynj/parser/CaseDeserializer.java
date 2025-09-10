package com.rickynj.parser;

import static com.rickynj.parser.YAMLParser.getTextOrNull;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rickynj.domain.pojo.CasePojo;
import java.io.IOException;

public class CaseDeserializer extends StdDeserializer<CasePojo> {
  public CaseDeserializer() {
    this(null);
  }
  public CaseDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public CasePojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    CasePojo casePojo = new CasePojo();
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    casePojo.caseStatement = getTextOrNull(node, "case");
    casePojo.operation = getTextOrNull(node, "operation");
    casePojo.response = ResponseDeserializer.deserialize(node);
    return casePojo;
  }
}
