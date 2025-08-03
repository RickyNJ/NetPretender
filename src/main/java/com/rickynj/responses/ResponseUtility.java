package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.util.List;

public class ResponseUtility {
    public static String replaceVariablesWithValues(String input, CommandContext ctx) {
        StringBuilder sb = new StringBuilder();
        List<String> splitInput = List.of(input.split(" "));
        for (String tok : splitInput) {
            sb.append(ctx.getValueForKey(tok).orElse(tok));
            sb.append(" ");
        }
        return sb.toString();
    }
    public static String replaceVariablesWithValues(List<String> input, CommandContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (String tok : input) {
            sb.append(ctx.getValueForKey(tok).orElse(tok));
            sb.append(" ");
        }
        return sb.toString();
    }
}
