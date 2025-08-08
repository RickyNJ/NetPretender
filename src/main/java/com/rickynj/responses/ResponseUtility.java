package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.util.List;

public class ResponseUtility {
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
    public static String replaceVariablesWithValues(List<String> input, CommandContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (String line : input) {
            for (String tok : line.split(" ")) {
                if (ctx.vars.containsKey(tok)) {
                    sb.append(ctx.getValueForKey(tok));
                    sb.append(" ");
                } else {
                    sb.append(tok);
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}
