package com.rickynj.repl;


import com.rickynj.organisation.Device;
import com.rickynj.domain.CommandContext;
import com.rickynj.exception.CommandNotMockedException;

import com.rickynj.repository.valkey.ValkeyClient;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Repl {
    private final Device device;
    private final ValkeyClient valkeyClient;

    // Each command check if first word appears as val in any root
    public Repl(Device device, ValkeyClient valkeyClient) {
        this.valkeyClient = valkeyClient;
        this.device = device;
    }

    public void start() {
        Scanner scanner  = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("> ");
                CommandContext ctx = new CommandContext(scanner.nextLine(), device, valkeyClient);
                device.respondToCommand(ctx);
            } catch (CommandNotMockedException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NoSuchElementException e) {
                System.out.println("no such command");
            }
        }
    }
}