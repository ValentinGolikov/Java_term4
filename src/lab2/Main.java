package lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java Main <количество_потоков>");
            System.exit(1);
        }

        int threadCount = Integer.parseInt(args[0]);
        MessageQueue queue = new MessageQueue();
        AtomicInteger activeWriters = new AtomicInteger(threadCount);

        ThreadFactory writerThreadFactory = new CustomThreadFactory("WriterThreadFromIlyuha");
        ThreadFactory readerThreadFactory = new CustomThreadFactory("ReaderThreadFromIlyuha");

        ExecutorService writerExecutor = Executors.newFixedThreadPool(threadCount, writerThreadFactory);
        ExecutorService readerExecutor = Executors.newFixedThreadPool(threadCount, readerThreadFactory);

        for (int i = 0; i < threadCount; i++) {
            writerExecutor.submit(new MessageWriter(queue, "Writer-" + i, activeWriters, threadCount));
        }

        for (int i = 0; i < threadCount; i++) {
            readerExecutor.submit(new MessageReader(queue, "Reader-" + i));
        }

        writerExecutor.shutdown();
        readerExecutor.shutdown();
    }
}