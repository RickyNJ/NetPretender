package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.util.List;
import java.util.Optional;

public class VariableResponse extends ResponseBase{
    private final String responseTemplate;

    public VariableResponse(String responseTemplate) {
        this.responseTemplate = responseTemplate;
    }

    @Override
    public void respond(CommandContext ctx) {
        StringBuilder sb = new StringBuilder();
        List<String> reponseTemplateList = List.of(responseTemplate.split(" "));
        for (String tok : reponseTemplateList) {
            sb.append(ctx.getValueForKey(tok).orElse(tok));
            sb.append(" ");
        }
        System.out.println(sb);
    }
}
