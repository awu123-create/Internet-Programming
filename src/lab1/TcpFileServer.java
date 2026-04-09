package lab1;

import java.net.*;
import java.util.List;
import java.io.*;

public class TcpFileServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("Server is listening on port 9000...");

            // 等待客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            List<String> files = List.of(
                    "src/lab1/send/file1.txt",
                    "src/lab1/send/file2.jpg",
                    "src/lab1/send/file3.md");

            // 先发送文件数量
            dos.writeInt(files.size());
            for (String file : files) {
                // 发送文件名和大小
                System.out.println("Server is sending file: " + file);
                dos.writeUTF(file);
                File f = new File(file);
                dos.writeLong(f.length());

                // 发送文件内容
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bis.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                }
                System.out.println("Finished sending file: " + file);
                bis.close();
            }

            // 关闭连接
            dos.close();
            socket.close();
            serverSocket.close();
            System.out.println("Server finished sending files and closed connection.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
