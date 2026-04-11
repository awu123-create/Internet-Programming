package lab2.common.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.InetAddress;

public class ClientLogger implements AutoCloseable {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private BufferedWriter writer;

    public ClientLogger(String filePath) {
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

    public void connectionMsg(int clientID, InetAddress address) {
        String logMessage = String.format("[%s] Client %d connected to %s",
                LocalDateTime.now().format(FORMATTER), clientID, address.getHostAddress());
        writeLog(logMessage);
    }

    public void requestInfo(int clientID, String message) {
        String logMessage = String.format("[%s] Client %d send %s",
                LocalDateTime.now().format(FORMATTER), clientID, message);
        writeLog(logMessage);
    }

    public void responseInfo(int clientID, String message) {
        String logMessage = String.format("[%s] Client %d receive %s",
                LocalDateTime.now().format(FORMATTER), clientID, message);
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
    public synchronized void close() {
        try {
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to close client logger", e);
        }
    }
}
