package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import com.rickynj.domain.POJO.ResponsePojo;
import java.util.ArrayList;
import java.util.List;

public class ResponseUtility {

    public static Response getResponseType(ResponsePojo responsePojo) {
        Response r = null;
        if (responsePojo.response != null) {
            r = new BasicResponse(responsePojo.response);
        } else if (responsePojo.multiPartResponse != null) {
            r = new MultipartResponse(responsePojo.multiPartResponse);
        } else if (responsePojo.responseFile != null ) {
            r = new FileResponse(responsePojo.responseFile);
        }

        if (r != null) {
            r.setDelay(responsePojo.delay);
        }
        return r;
    }

    public static String replaceVariablesWithValues(String input, CommandContext ctx) {
        StringBuilder sb = new StringBuilder();
        List<String> splitInput = List.of(input.split(" "));
        for (String tok : splitInput) {
            if (ctx.vars.containsKey(tok)) {
                sb.append(ctx.getValueForKey(tok));
                sb.append(" ");
            } else {
                sb.append(tok);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static List<String> replaceVariablesWithValues(List<String> input, CommandContext ctx) {
        List<String> parts = new ArrayList<>();
        for (String line : input) {
            parts.add(replaceVariablesWithValues(line, ctx));
        }
        return parts;
    }
}
