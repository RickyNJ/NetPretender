package com.rickynj.actions;

import com.rickynj.domain.CommandContext;


//TODO: defininely rename this file. maybe operations?
public interface Action {
    void execute(CommandContext ctx);
}