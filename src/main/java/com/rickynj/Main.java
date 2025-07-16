package com.rickynj;


import com.rickynj.commands.CommandNode;
import com.rickynj.responses.BasicResponse;
import com.rickynj.responses.MultipartResponse;
import com.rickynj.responses.Response;
import com.rickynj.responses.ResponseBase;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");
        Response r = new MultipartResponse();

        CommandNode c = new CommandNode();

        c.setResponse(r);
    }
}