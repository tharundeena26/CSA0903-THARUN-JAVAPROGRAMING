import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LineCounter {
    public static long countUnbuffered(Path file) throws IOException {
        long lines = 0;
        try (FileInputStream input = new FileInputStream(file.toFile())) {
            int value;
            while ((value = input.read()) != -1) {
                if (value == '\n') lines++;
            }
        }
        return lines;
    }

    public static long countBuffered(Path file, int bufferSize) throws IOException {
        long lines = 0;
        byte[] buffer = new byte[bufferSize];
        try (BufferedInputStream input = new BufferedInputStream(
                new FileInputStream(file.toFile()), bufferSize)) {
            int count;
            while ((count = input.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    if (buffer[i] == '\n') lines++;
                }
            }
        }
        return lines;
    }

    public static void main(String[] args) throws IOException {
        Path file = Path.of(args.length == 0 ? "attendance-1mb.log" : args[0]);
        long start = System.nanoTime();
        long unbuffered = countUnbuffered(file);
        long unbufferedNanos = System.nanoTime() - start;

        start = System.nanoTime();
        long buffered = countBuffered(file, 64 * 1024);
        long bufferedNanos = System.nanoTime() - start;

        System.out.println("File: " + file);
        System.out.println("Unbuffered lines: " + unbuffered);
        System.out.println("Unbuffered ms: " + unbufferedNanos / 1_000_000.0);
        System.out.println("Buffered lines: " + buffered);
        System.out.println("Buffered ms: " + bufferedNanos / 1_000_000.0);
    }
}