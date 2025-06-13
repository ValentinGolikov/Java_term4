package lab2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class MessageQueue {
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

    public void put(String message) throws InterruptedException {
        queue.put(message);
    }

    public String take() throws InterruptedException {
        return queue.take();
    }
}