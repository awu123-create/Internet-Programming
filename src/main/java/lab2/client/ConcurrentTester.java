package lab2.client;

public class ConcurrentTester {

    public static void main(String[] args) {
        int numThreads = 5;

        for (int i = 0; i < numThreads; i++) {
            final int clientID = i + 1;
            new Thread() {
                @Override
                public void run() {
                    TcpClient client = new TcpClient();
                    client.sendRequest(clientID, "10.42.0.1", 8000);
                }
            }.start();
        }
    }
}
