package com.rickynj.organisation;

import com.rickynj.domain.CommandContext;

public interface SSHMockServer {
    void respondToCommand(CommandContext ctx) throws InterruptedException;
}
