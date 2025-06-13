package lab2;

import java.util.concurrent.atomic.AtomicInteger;

class MessageWriter implements Runnable {
    private final MessageQueue queue;
    private final String writerName;
    private final AtomicInteger activeWriters;
    private final int readersCount;

    public MessageWriter(MessageQueue queue, String writerName, AtomicInteger activeWriters, int readersCount) {
        this.queue = queue;
        this.writerName = writerName;
        this.activeWriters = activeWriters;
        this.readersCount = readersCount;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                String message = "[" + writerName + "]: Сообщение #" + i;
                System.out.println("Отправлено: " + message);
                queue.put(message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (activeWriters.decrementAndGet() == 0) {
                for (int i = 0; i < readersCount; i++) {
                    try {
                        queue.put("[STOP]");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}