package com.rickynj.actions.execute;

import com.rickynj.domain.CommandContext;

public class Reset implements Execute {
    // TODO: Reset valkey state
    @Override
    public void execute(CommandContext ctx) {
        ctx.device.defaultState.forEach(ctx::setDeviceVar);
    }
}
