package lab2.common.service;

import lab2.common.model.Request;
import lab2.common.model.Response;

public class CommandService {
    public Response executeCommand(Request request) {
        String command = request.getCommand();
        switch (command) {
            case "ECHO":
                return new Response("OK", request.getContent());
            case "REVERSE":
                String reversedString = new StringBuilder(request.getContent()).reverse().toString();
                return new Response("OK", reversedString);
            case "UPPER":
                String upperString = request.getContent().toUpperCase();
                return new Response("OK", upperString);
            case "LOWER":
                String lowerString = request.getContent().toLowerCase();
                return new Response("OK", lowerString);
            default:
                return new Response("ERROR", "Unknown command: " + command);
        }
    }
}
