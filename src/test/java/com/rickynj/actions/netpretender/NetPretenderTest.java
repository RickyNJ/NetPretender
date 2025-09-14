package com.rickynj.actions.netpretender;

import com.rickynj.actions.common.TestBase;
import com.rickynj.organisation.Device;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class NetPretenderTest extends TestBase {
    @Test
    void singleCommandResponseTest() throws InterruptedException {
        Device device = getDeviceFromFile("src/test/resources/singleBasicCommand.yaml", 22);
        testCommandOnDevice(device, "show name", "my name is modem1");
    }

    @Test
    void singleVarCommandResponseTest() throws InterruptedException {
        Device device = getDeviceFromFile("src/test/resources/singleBasicVarCommand.yaml", 22);
        testCommandOnDevice(device, "show name", "my name is router");
    }

    @Test
    void singleCommandMultiResponseTest() throws InterruptedException {
        Device device = getDeviceFromFile("src/test/resources/singleMultiResponseCommand.yaml", 22);
        testCommandOnDevice(device, "show interfaces", Arrays.asList("SHOWING INTERFACES","INTERFACE 1", "INTERFACE 2"));
    }
}
