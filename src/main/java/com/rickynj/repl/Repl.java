package com.rickynj.repl;


import com.rickynj.organisation.Device;
import com.rickynj.domain.CommandContext;
import com.rickynj.exception.CommandNotMockedException;
import com.rickynj.organisation.SSHMockServer;

import java.util.Scanner;

public class Repl {
    private final Device device;

    // Each command check if first word appears as val in any root
    public Repl(Device device) {
        this.device = device;
    }

    public void start() {
        Scanner scanner  = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            CommandContext ctx = new CommandContext(scanner.nextLine(), device);
            try {
                device.respondToCommand(ctx);
            } catch (CommandNotMockedException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
