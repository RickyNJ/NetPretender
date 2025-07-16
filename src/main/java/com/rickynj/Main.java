package com.rickynj;


import com.rickynj.commands.CommandNode;
import com.rickynj.responses.BasicResponse;
import com.rickynj.responses.Response;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");
        Response r = new BasicResponse();
        CommandNode c = new CommandNode();
    }
}