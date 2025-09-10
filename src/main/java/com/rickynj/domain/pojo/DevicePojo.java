package com.rickynj.domain.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevicePojo {
    public String name;
    public String prompt;
    public List<Integer> port = new ArrayList<>();
    public Map<String, String> vars = new HashMap<>();
    public List<CommandPojo> commands = new ArrayList<>();
}
