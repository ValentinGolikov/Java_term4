package lab2;

import java.util.concurrent.ArrayBlockingQueue;

class MessageWriter implements Runnable {
    private final ArrayBlockingQueue<String> queue;

    public MessageWriter(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String message = "[" + Thread.currentThread().getName() + "]: message";
            System.out.println("Send: " + message);
            queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}