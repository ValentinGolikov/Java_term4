package lab2;

import java.util.concurrent.ArrayBlockingQueue;

class MessageReader implements Runnable {
    private final ArrayBlockingQueue<String> queue;

    public MessageReader(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String message = queue.take();
            System.out.println(Thread.currentThread().getName() + " get: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}