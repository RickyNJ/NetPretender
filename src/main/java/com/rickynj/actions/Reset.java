package com.rickynj.actions;

import com.rickynj.domain.CommandContext;

public class Reset implements Action {
    // TODO: Reset valkey state
    @Override
    public void execute(CommandContext ctx) {
        ctx.device.defaultState.forEach(ctx::setDeviceVar);
    }
}
