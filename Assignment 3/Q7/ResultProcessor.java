import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ResultProcessor {
    record Result(String fileName, int score) { }
    record Failure(String fileName, String reason) { }

    static class ScoreTask implements Callable<Result> {
        private final Path file;
        ScoreTask(Path file) { this.file = file; }

        @Override
        public Result call() throws Exception {
            if (!Files.exists(file)) throw new IOException("File is missing");
            String text = Files.readString(file).trim();
            int score;
            try {
                score = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                throw new IOException("Corrupt score data", e);
            }
            if (score < 0 || score > 100) throw new IOException("Score outside 0-100 range");
            return new Result(file.getFileName().toString(), score);
        }
    }

    private static List<Path> prepareInput(Path dir, int count) throws IOException {
        Files.createDirectories(dir);
        List<Path> files = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Path file = dir.resolve(String.format("score-%05d.txt", i));
            if (i % 100 == 0) {
                Files.deleteIfExists(file);
            } else if (i % 100 == 1) {
                Files.writeString(file, "NOT_A_SCORE");
            } else {
                Files.writeString(file, Integer.toString((i * 37) % 101));
            }
            files.add(file);
        }
        return files;
    }

    private static void process(List<Path> files, int poolSize) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        ConcurrentLinkedQueue<Result> successes = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Failure> failures = new ConcurrentLinkedQueue<>();
        List<Future<Result>> futures = new ArrayList<>(files.size());
        long start = System.nanoTime();
        try {
            for (Path file : files) futures.add(executor.submit(new ScoreTask(file)));
            for (int i = 0; i < futures.size(); i++) {
                try {
                    successes.add(futures.get(i).get());
                } catch (Exception e) {
                    Throwable cause = e.getCause() == null ? e : e.getCause();
                    failures.add(new Failure(files.get(i).getFileName().toString(),
                            cause.getMessage()));
                }
            }
        } finally {
            executor.shutdown();
        }
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        int total = successes.stream().mapToInt(Result::score).sum();
        System.out.println("Pool " + poolSize + " -> success=" + successes.size()
                + ", failed=" + failures.size() + ", score total=" + total
                + ", elapsed=" + elapsedMs + " ms");
    }

    public static void main(String[] args) throws Exception {
        Path input = Path.of("score-input");
        List<Path> files = prepareInput(input, 10000);
        System.out.println("Q7 - Concurrent fault-tolerant result processor");
        System.out.println("Files submitted: " + files.size() + " (100 missing and 100 corrupt)");
        for (int poolSize : new int[]{1, 2, 4, 8}) process(files, poolSize);
        System.out.println("Expected per run: 9800 successful, 200 failed");
    }
}