package com.rickynj.actions.common;

import com.rickynj.domain.CommandContext;
import com.rickynj.domain.DevicesWrapper;
import com.rickynj.organisation.Device;
import com.rickynj.organisation.Organisation;
import com.rickynj.parser.YAMLParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestBase {

    private final PrintStream standardOut = System.out;
    protected final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeAll
    static void createContext() {

    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    public Device getDeviceFromFile(String path, int port) {
        DevicesWrapper dw = YAMLParser.parseFile(path);
        Organisation org = new Organisation(dw);
        return org.getDevice(port);
    }

    public void testCommandOnDevice(Device device, String command, String expectedResponse) throws InterruptedException {
        CommandContext ctx = new CommandContext(command, device, null);
        try {
            device.respondToCommand(ctx);
        } catch (InterruptedException e) {
            throw new InterruptedException("Exception while executing command: " + command);
        }
        assertEquals(expectedResponse, outputStreamCaptor.toString()
                .trim());
    }

    public void testCommandOnDevice(Device device, String command, List<String> expectedResponse) throws InterruptedException {
        CommandContext ctx = new CommandContext(command, device, null);
        try {
            device.respondToCommand(ctx);
        } catch (InterruptedException e) {
            throw new InterruptedException("Exception while executing command: " + command);
        }
        List<String> actualLines = Arrays.stream(outputStreamCaptor.toString().split("\\R"))
                .map(String::trim)
                .toList();
        assertEquals(expectedResponse, actualLines);
    }
}
