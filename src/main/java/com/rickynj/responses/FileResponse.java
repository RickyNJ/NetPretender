package com.rickynj.responses;

import com.rickynj.domain.CommandContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileResponse extends ResponseBase {
    final private String response;

    public FileResponse(String path) {
        path = "/opt/configs/responses/" + path;
//        path = "src/main/resources/responses/" + path;
        try {
            response = Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void respond(CommandContext ctx) {
        System.out.println(ResponseUtility.replaceVariablesWithValues(response, ctx));
    }
}
