package com.rickynj.responses;

import java.util.function.Supplier;

public abstract class ResponseBase implements Response {
    protected int delay;
    protected Supplier<Long> delayMs = () -> delay * 1000L;
}
