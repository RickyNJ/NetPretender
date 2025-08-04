package com.rickynj.functions;

import com.rickynj.domain.CommandContext;

public class Assign implements Operation {
    public String source;
    public String target;

    public Assign(String source, String target) {
        this.source = source;
        this.target = target;
    }

    // TODO probably use functional interfaces here
    // TODO find less error prone way, probably just add ispresents
    public void execute(CommandContext ctx) {
        String newValue = ctx.getValueForKey(source).orElse("nothing");
        ctx.setDeviceVar(target, newValue);
    }
}
