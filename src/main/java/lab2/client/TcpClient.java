package lab2.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    public void sendRequest(int clientID) {
        Path path = Path.of("src/main/resources/lab2/testcases/testcase_" + clientID + ".txt");
        List<String> requests = sourceReader(path);
        String filepath = "logs/client-" + clientID + ".log";

        ClientLogger logger = null;
        try {
            logger = new ClientLogger(filepath);

            try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8000);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                logger.connectionMsg(clientID, socket.getInetAddress());
                writer.write(String.valueOf(clientID));
                writer.newLine();
                writer.flush();

                for (String request : requests) {
                    logger.requestInfo(clientID, request);
                    writer.write(request);
                    writer.newLine();
                    writer.flush();

                    String response = reader.readLine();
                    logger.responseInfo(clientID, response);
                }
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
