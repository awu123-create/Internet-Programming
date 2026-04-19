package lab2.common.model;

public class Response {
    private String status;
    private String message;

    public static final String STATUSOK = "OK";
    public static final String STATUSERR = "ERROR";

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
