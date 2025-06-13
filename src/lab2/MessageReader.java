package lab2;

class MessageReader implements Runnable {
    private final MessageQueue queue;
    private final String readerName;

    public MessageReader(MessageQueue queue, String readerName) {
        this.queue = queue;
        this.readerName = readerName;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = queue.take();
                if ("[STOP]".equals(message)) {
                    System.out.println(readerName + " получил сигнал завершения");
                    break;
                }
                System.out.println(readerName + " получил: " + message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}