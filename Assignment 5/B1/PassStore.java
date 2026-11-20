import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PassStore {
    private final Path file;

    public PassStore(Path file) {
        this.file = file;
    }

    public void add(String passId, String regNo, String name, int route, double amount)
            throws IOException {
        if (passId == null || passId.isBlank() || regNo == null || regNo.isBlank()
                || name == null || name.isBlank() || route <= 0
                || !Double.isFinite(amount) || amount < 0) {
            throw new IllegalArgumentException("Invalid bus pass fields");
        }
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(String.join(",", escape(passId), escape(regNo), escape(name),
                    String.valueOf(route), String.valueOf(amount)));
            writer.newLine();
        }
    }

    /** Returns the five fields for a matching pass, or null when not found. */
    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields;
                try {
                    fields = parse(line);
                    if (fields.length != 5) continue;
                    Integer.parseInt(fields[3]);
                    Double.parseDouble(fields[4]);
                } catch (IllegalArgumentException exception) {
                    continue;
                }
                if (fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public List<String[]> listByRoute(int route) throws IOException {
        List<String[]> matches = new ArrayList<>();
        if (!Files.exists(file)) return matches;
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] fields = parse(line);
                    if (fields.length == 5 && Integer.parseInt(fields[3]) == route) {
                        Double.parseDouble(fields[4]);
                        matches.add(fields);
                    }
                } catch (IllegalArgumentException ignored) {
                    // Ignore a malformed record and continue scanning.
                }
            }
        }
        return matches;
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace(",", "\\,")
                .replace("\n", "\\n").replace("\r", "\\r");
    }

    private static String[] parse(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean escaped = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (escaped) {
                if (ch == 'n') current.append('\n');
                else if (ch == 'r') current.append('\r');
                else current.append(ch);
                escaped = false;
            } else if (ch == '\\') {
                escaped = true;
            } else if (ch == ',') {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }
        if (escaped) throw new IllegalArgumentException("Dangling escape");
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }
}