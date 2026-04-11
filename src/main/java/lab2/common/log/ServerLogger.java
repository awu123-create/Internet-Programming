package lab2.common.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLogger implements AutoCloseable {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private BufferedWriter writer;

    public ServerLogger(String filePath) {
        try {
            Path logPath = Path.of(filePath);
            Path parent = logPath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void writeLog(String str) {
        try {
            writer.write(str);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectionMsg(int clientID, LocalDateTime time, InetAddress address) {
        String logMessage = String.format("[%s] Client %d connects to %s", time.format(FORMATTER), clientID,
                address.getHostAddress());
        writeLog(logMessage);
    }

    public void requestInfo(int clientID, String message) {
        String logMessage = String.format("[%s] Client %d send %s",
                LocalDateTime.now().format(FORMATTER), clientID, message);
        writeLog(logMessage);
    }

    public void responseInfo(int clientID, String message) {
        String logMessage = String.format("[%s] Server send %s to Client %d",
                LocalDateTime.now().format(FORMATTER), message, clientID);
        writeLog(logMessage);
    }

    public void closeConnection(int clientID) {
        String logMessage = String.format("[%s] Client %d disconnected",
                LocalDateTime.now().format(FORMATTER), clientID);
        writeLog(logMessage);
    }

    public void errorInfo(int clientID, String message) {
        String logMessage = String.format("[%s] Client %d error: %s",
                LocalDateTime.now().format(FORMATTER), clientID, message);
        writeLog(logMessage);
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
