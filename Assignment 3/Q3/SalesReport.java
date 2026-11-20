import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SalesReport {
    private static int manualOpenResources;

    private static List<String> readManual(Path file) throws IOException {
        BufferedReader reader = Files.newBufferedReader(file);
        manualOpenResources++;
        try {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
            return lines;
        } finally {
            reader.close();
            manualOpenResources--;
        }
    }

    private static List<String> readSafely(Path file) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
            return lines;
        }
    }

    private static void writeReport(Path output, List<String> rows) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            writer.write("DAILY SALES SUMMARY");
            writer.newLine();
            for (String row : rows) {
                writer.write(row);
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Path.of("stores");
        Files.createDirectories(inputDir);
        Files.writeString(inputDir.resolve("store-a.txt"), "Store A,1200\nStore A,800\n");
        Files.writeString(inputDir.resolve("store-b.txt"), "Store B,950\n");
        List<Path> files = List.of(inputDir.resolve("store-a.txt"),
                inputDir.resolve("store-b.txt"), inputDir.resolve("missing-store.txt"));

        List<String> manualRows = new ArrayList<>();
        for (Path file : files) {
            try {
                manualRows.addAll(readManual(file));
            } catch (IOException e) {
                System.out.println("Manual version: " + file.getFileName() + " -> " + e.getMessage());
            }
        }
        writeReport(Path.of("manual-report.txt"), manualRows);
        System.out.println("Manual report rows: " + manualRows.size());
        System.out.println("Manual open readers after processing: " + manualOpenResources);

        List<String> safeRows = new ArrayList<>();
        for (Path file : files) {
            try {
                safeRows.addAll(readSafely(file));
            } catch (IOException e) {
                System.out.println("Try-with-resources: " + file.getFileName() + " -> " + e.getMessage());
            }
        }
        writeReport(Path.of("safe-report.txt"), safeRows);
        System.out.println("Try-with-resources report rows: " + safeRows.size());
        System.out.println("Reports contain the same valid rows: " + manualRows.equals(safeRows));
    }
}