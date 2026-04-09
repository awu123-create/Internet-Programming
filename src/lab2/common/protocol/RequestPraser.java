package lab2.common.protocol;

import lab2.common.model.Request;

public class RequestPraser {
    public Request parser(String operation){
        String []contents = operation.split(":");
        if(contents.length != 2){
            return null;
        }

        String command= contents[0];
        String content= contents[1];
        return new Request(command,content);
    }
}
