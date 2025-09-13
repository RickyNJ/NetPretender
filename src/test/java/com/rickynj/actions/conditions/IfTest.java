package com.rickynj.actions.conditions;

import com.rickynj.actions.common.TestBase;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rickynj.actions.condition.If;
import org.junit.jupiter.api.Test;

public class IfTest extends TestBase {
    private final If ifstatement = new If("$deviceId == modem1");
    @Test
    void testIf() {
    }
}
