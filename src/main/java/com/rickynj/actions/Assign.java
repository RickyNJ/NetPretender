package com.rickynj.actions;

import com.rickynj.domain.CommandContext;

public class Assign implements Action {
    public String source;
    public String target;

    public Assign(String source, String target) {
        this.source = source;
        this.target = target;
    }

    // TODO probably use functional interfaces here
    // TODO find less error prone way, probably just add ispresents
    public void execute(CommandContext ctx) {
        String newValue = ctx.getValueForKey(source);
        // If newvalue is not a key in device or valkey, it is a string literal defined in yaml.
        // In this case the key is the value.
        if (newValue == null) {
            newValue = source;
        }
        ctx.setDeviceVar(target, newValue);
    }
}
