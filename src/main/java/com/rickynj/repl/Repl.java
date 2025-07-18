package com.rickynj.repl;


import com.rickynj.device.Device;

import java.util.Scanner;

public class Repl {
    private Device device;

    // Probably input an array of command tree roots?
    // Each command check if first word appears as val in any root
    public Repl(Device device) {
        this.device = device;
    }

    public void start() {
        while (true) {
            device.countcommands();
        }
    }
}
