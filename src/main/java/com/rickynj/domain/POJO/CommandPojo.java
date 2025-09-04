package com.rickynj.domain.POJO;

import com.rickynj.actions.operation.HoldsOperation;
import java.util.List;

public class CommandPojo implements HoldsOperation {
    public String command;
    public List<ConditionPojo> condition;
    public String operation;
    public ResponsePojo response;

    @Override
    public String getOperation() {
        return operation;
    }
}
