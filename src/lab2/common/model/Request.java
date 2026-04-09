package lab2.common.model;

public class Request {
    private String command;
    private String content;

    public Request(String command, String content) {
        this.command = command;
        this.content = content;
    }

    public String getCommand() {
        return command;
    }

    public String getContent() {
        return content;
    }
}
