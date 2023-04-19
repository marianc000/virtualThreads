package sample;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Measure {

    List<Callable<String>> tasks;
    int repeats;

    public Measure(String[] args) {

        tasks = IntStream.range(0, Integer.parseInt(args[0]))
                .mapToObj(i -> (Callable<String>) () -> {
            Thread.sleep(1000);
            return "" + i;
        }).toList();

        repeats = Integer.parseInt(args[1]);
    }

    void execute(ExecutorService executor, String title) throws Exception {
        System.out.println(">>>" + title);
        try (executor) {
            for (int i = 0; i < repeats; i++) {
                long s = System.currentTimeMillis();
                executor.invokeAll(tasks).stream().map(f->f.resultNow()).toList();
                System.out.println("attempt/time " + i + " " + (System.currentTimeMillis() - s));
            }
        }
    }

    void assessExecutors() throws Exception {
        System.out.println("CPU count " + Runtime.getRuntime().availableProcessors());

        execute(Executors.newCachedThreadPool(), "Cached");
        execute(Executors.newVirtualThreadPerTaskExecutor(), "Virtual");
    }

    public static void main(String[] args) throws Exception {
        new Measure(args).assessExecutors();
    }
}
