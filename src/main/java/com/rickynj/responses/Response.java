package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

public interface Response {
    void respond(CommandContext ctx) throws InterruptedException;
    void setDelay(int delay);
}
