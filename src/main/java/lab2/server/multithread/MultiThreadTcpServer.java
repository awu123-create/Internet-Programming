package lab2.server.multithread;

import java.net.*;
import lab2.common.log.ServerLogger;
import lab2.server.ClientHandler;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadTcpServer {
    public static void main(String[] args) {
        System.out.println("Starting MultiThread TCP Server...");

        int numClients = 5;
        int count = 0;

        try (ServerLogger logger = new ServerLogger("logs/multithread-server.log");
                ServerSocket serverSocket = new ServerSocket(8000)) {
            List<Thread> threads = new ArrayList<>();
            while (count++ < numClients) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(socket, logger));
                threads.add(thread);
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
