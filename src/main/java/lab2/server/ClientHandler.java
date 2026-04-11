package lab2.server;

import java.net.Socket;
import java.time.LocalDateTime;

import lab2.common.log.ServerLogger;
import lab2.common.model.Request;
import lab2.common.model.Response;
import lab2.common.protocol.*;
import lab2.common.service.CommandService;
import java.io.*;

public class ClientHandler implements Runnable {
    // 处理单个客户端的通信流程
    private Socket socket;
    private int clientID;
    private ServerLogger logger;

    public ClientHandler(Socket socket, ServerLogger logger) {
        this.socket = socket;
        this.logger = logger;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            int ID = Integer.parseInt(reader.readLine());
            setClientID(ID);
            logger.connectionMsg(clientID, LocalDateTime.now(), socket.getInetAddress());
            String operation;
            while ((operation = reader.readLine()) != null) {
                logger.requestInfo(clientID, operation);
                Request request = RequestParser.parser(operation);
                if (request == null) {
                    Response errorResponse = new Response("ERROR", "Invalid command format");
                    String errorResponseStr = ResponseBuilder.buildResponse(errorResponse);
                    writer.write(errorResponseStr);
                    writer.newLine();
                    writer.flush();
                    logger.responseInfo(clientID, errorResponseStr);
                    continue;
                }

                Response response = CommandService.executeCommand(request);
                String responseStr = ResponseBuilder.buildResponse(response);

                writer.write(responseStr);
                writer.newLine();
                writer.flush();
                logger.responseInfo(clientID, responseStr);

                if (request.getCommand().equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.errorInfo(clientID, e.toString());
            e.printStackTrace();
        } finally {
            logger.closeConnection(clientID);
        }
    }
}
