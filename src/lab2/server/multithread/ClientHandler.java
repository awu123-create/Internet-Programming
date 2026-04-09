package lab2.server.multithread;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import lab2.common.service.CommandService;

public class ClientHandler implements Runnable {
    // 处理单个客户端的通信流程
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String operation;
            while((operation = reader.readLine())!=null){
                System.out.println("Received operation: " + operation);
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
