package com.rickynj.domain.POJO;

import java.util.List;
import java.util.Map;

public class DevicePojo {
    public String name;
    public int port;
    public Map<String, String> vars;
    public List<CommandPojo> commands;
}
