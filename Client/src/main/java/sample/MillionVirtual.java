package sample;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MillionVirtual {

    public static void main(String[] args) throws Exception {

        var tasks = IntStream.range(0, 1_000_000)
                .mapToObj(i -> (Callable<String>) () -> {
            Thread.sleep(1000);
            return "ok";
        }).toList();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.invokeAll(tasks);
        }
    }
}
