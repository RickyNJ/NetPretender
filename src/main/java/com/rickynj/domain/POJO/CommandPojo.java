package com.rickynj.domain.POJO;

import java.util.List;

public class CommandPojo {
    public String command;
    public int delay;
    public List<ConditionPojo> condition;
    public String response;
    public List<String> multiPartResponse;
    public String responseFile;
    public List<String> allowed_values;
    public String operation;
}
