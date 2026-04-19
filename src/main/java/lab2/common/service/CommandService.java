package lab2.common.service;

import lab2.common.model.Request;
import lab2.common.model.Response;

public class CommandService {
    public static Response executeCommand(Request request) {
        String command = request.getCommand();
        switch (command) {
            case "ECHO":
                return new Response(Response.STATUSOK, request.getContent());
            case "REVERSE":
                String reversedString = new StringBuilder(request.getContent()).reverse().toString();
                return new Response(Response.STATUSOK, reversedString);
            case "UPPER":
                String upperString = request.getContent().toUpperCase();
                return new Response(Response.STATUSOK, upperString);
            case "LOWER":
                String lowerString = request.getContent().toLowerCase();
                return new Response(Response.STATUSOK, lowerString);
            case "EXIT":
                return new Response(Response.STATUSOK, "Connection closed.");
            default:
                return new Response(Response.STATUSERR, "Unknown command: " + command);
        }
    }
}
