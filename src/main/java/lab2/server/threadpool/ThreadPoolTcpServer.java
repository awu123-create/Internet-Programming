package lab2.server.threadpool;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lab2.common.log.ServerLogger;
import lab2.server.ClientHandler;

public class ThreadPoolTcpServer {
    public static void main(String[] args) {
        System.out.println("Starting ThreadPool TCP Server...");

        try (ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                10,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());
                ServerLogger logger = new ServerLogger("logs/threadpool-server.log");
                ServerSocket serverSocket = new ServerSocket(8000)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                try {
                    threadPool.execute(new ClientHandler(socket, logger));
                } catch (RejectedExecutionException ex) {
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
