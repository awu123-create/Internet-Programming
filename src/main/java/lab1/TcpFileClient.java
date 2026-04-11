package lab1;

import java.io.*;
import java.net.*;

public class TcpFileClient {
    public static void main(String[] args) {
        try {
            System.out.println("Client connecting to server...");
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            int fileNum = dis.readInt();
            System.out.println("Number of files to receive: " + fileNum);

            for (int i = 0; i < fileNum; i++) {
                String fileName = dis.readUTF();
                long fileSize = dis.readLong();
                System.out.println("Receiving file: " + fileName + " (" + fileSize + " bytes)");

                String[] parts = fileName.split("\\.");
                File file = new File("src/lab1/receive/received_file" + (i + 1) + "." + parts[parts.length - 1]);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

                byte[] buffer = new byte[4096];
                long remaining = fileSize;

                while (remaining > 0) {
                    int read = (int) Math.min(remaining, buffer.length);
                    int bytesRead = dis.read(buffer, 0, read);
                    bos.write(buffer, 0, bytesRead);
                    remaining -= bytesRead;
                }
                bos.close();
                System.out.println("Finished receiving file: " + fileName);
            }
            dis.close();
            socket.close();
            System.out.println("Client finished receiving files and closed connection.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
