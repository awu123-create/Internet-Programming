package lab2.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import lab2.common.log.ClientLogger;

public class TcpClient {
    public List<String> sourceReader(Path path) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void sendRequest(int clientID,String serverIP, int serverPort) {
        Path path = Path.of("src/main/resources/lab2/testcases/testcase_" + clientID + ".txt");
        List<String> requests = sourceReader(path);
        String filepath = "logs/client-" + clientID + ".log";

        ClientLogger logger = null;
        try {
            logger = new ClientLogger(filepath);

            try (Socket socket = new Socket(InetAddress.getByName(serverIP), serverPort);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                logger.connectionMsg(clientID, socket.getInetAddress());
                writer.write(String.valueOf(clientID));
                writer.newLine();
                writer.flush();

                long totalRequestTime =0;
                int requestCount=0;

                for (String request : requests) {
                    logger.requestInfo(clientID, request);
                    long reqStart= System.nanoTime();
                    writer.write(request);
                    writer.newLine();
                    writer.flush();

                    String response = reader.readLine();
                    long reqEnd = System.nanoTime();
                    totalRequestTime += (reqEnd - reqStart);
                    requestCount++;
                    logger.responseInfo(clientID, response);
                }

                double avgTime = totalRequestTime / (requestCount * 1_000_000.0);
                System.out.println("Client " + clientID + " average response time: " + avgTime + " ms");
            } catch (Exception e) {
                logger.errorInfo(clientID, e.toString());
                e.printStackTrace();
            } finally {
                logger.closeConnection(clientID);
                logger.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
