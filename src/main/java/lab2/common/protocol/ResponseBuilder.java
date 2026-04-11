package lab2.common.protocol;

import lab2.common.model.Response;

public class ResponseBuilder {
    public static String buildResponse(Response response) {
        return "Status:" + response.getStatus() + "; Message:" + response.getMessage();
    }
}
