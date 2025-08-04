package com.rickynj.functions;

import com.rickynj.domain.CommandContext;

public class Assign {
    public String source;
    public String target;

    // TODO probably use functional interfaces here
    // TODO find less error prone way, probably just add ispresents
    public void execute(CommandContext ctx) {
        ctx.setValueForKey(ctx.getValueForKey(source).get(), ctx.getValueForKey(target).get());
    }
}
