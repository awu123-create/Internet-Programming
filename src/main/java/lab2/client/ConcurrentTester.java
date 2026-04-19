package lab2.client;

public class ConcurrentTester {

    public static void main(String[] args) {
        int numThreads = 20;
        Thread[] threads = new Thread[numThreads];
        long start = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            final int clientID = i + 1;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    TcpClient client = new TcpClient();
                    client.sendRequest(clientID, "127.0.0.1", 8000);
                }
            };
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.nanoTime();
        double totalTime= (end - start) / 1_000_000.0;
        System.out.println("Total completion time: " + totalTime + " ms");
    }
}
