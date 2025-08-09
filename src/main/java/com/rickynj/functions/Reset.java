package com.rickynj.functions;

import com.rickynj.domain.CommandContext;

public class Reset implements Operation{
    @Override
    public void execute(CommandContext ctx) {
        ctx.device.state = ctx.device.defaultState;
    }
}
