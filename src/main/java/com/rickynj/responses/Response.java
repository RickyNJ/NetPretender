package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.util.Map;

public interface Response {
    void respond(CommandContext ctx) throws InterruptedException;
}
