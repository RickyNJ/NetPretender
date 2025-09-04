package com.rickynj.actions.operation;

import com.rickynj.domain.CommandContext;


//TODO: defininely rename this file. maybe operations?
public interface Execute {
    void execute(CommandContext ctx);
}