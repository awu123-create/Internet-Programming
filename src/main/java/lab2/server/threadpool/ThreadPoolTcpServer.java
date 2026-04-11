package lab2.server.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lab2.common.log.ServerLogger;
import lab2.server.ClientHandler;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolTcpServer {
    public static void main(String[] args) {
        System.out.println("Starting ThreadPool TCP Server...");

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                10,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10));

        int numClients = 5;
        int count = 0;
        try (ServerLogger logger = new ServerLogger("logs/threadpool-server.log");
                ServerSocket serverSocket = new ServerSocket(8000)) {
            while (count++ < numClients) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket, logger));
            }
            threadPool.shutdown();
            threadPool.awaitTermination(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
