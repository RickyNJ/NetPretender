package com.rickynj.responses;


public abstract class ResponseBase implements Response {
    private int delay;
    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Long getDelay() {
        return delay * 1000L;
    }
}
