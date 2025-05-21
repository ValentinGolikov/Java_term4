package lab2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java Main <количество_потоков>");
            System.exit(1);
        }

        int threadCount = Integer.parseInt(args[0]);
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        ThreadFactory writerThreadFactory = new CustomThreadFactory("WriterThread");
        ThreadFactory readerThreadFactory = new CustomThreadFactory("ReaderThread");

        ExecutorService writerExecutor = Executors.newFixedThreadPool(threadCount, writerThreadFactory);
        ExecutorService readerExecutor = Executors.newFixedThreadPool(threadCount, readerThreadFactory);

        for (int i = 0; i < threadCount; i++) {
            writerExecutor.submit(new MessageWriter(queue));
        }

        for (int i = 0; i < threadCount; i++) {
            readerExecutor.submit(new MessageReader(queue));
        }

        writerExecutor.shutdown();
        readerExecutor.shutdown();
    }
}