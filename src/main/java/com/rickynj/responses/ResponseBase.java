package com.rickynj.responses;

import java.util.function.Supplier;

public abstract class ResponseBase implements Response {
    private int delay;
    transient public Supplier<Long> delayMs = () -> delay * 1000L;

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
