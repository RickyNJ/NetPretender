package com.rickynj.responses;

import com.rickynj.config.Constants;
import com.rickynj.domain.CommandContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileResponse extends ResponseBase {
    private final String response;

    public FileResponse(String path) {
        path = Constants.responsesDirectory + path;
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