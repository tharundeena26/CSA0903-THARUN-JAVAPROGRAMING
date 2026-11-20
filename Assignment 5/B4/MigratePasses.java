import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MigratePasses {
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/bus_pass_db");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    public static void main(String[] args) throws Exception {
        Path input = Path.of(args.length == 0 ? "passes.csv" : args[0]);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            migrate(connection, input);
            inspectDatabase(connection);
        }
    }

    public static void migrate(Connection connection, Path file) throws Exception {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) "
                + "VALUES (?, ?, ?, ?, ?)";
        List<String> rejected = new ArrayList<>();
        boolean previousAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        int inserted = 0;
        int batchCount = 0;

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] fields = parse(line);
                    if (fields.length != 5) {
                        throw new IllegalArgumentException("Expected five fields");
                    }
                    if (fields[0].isBlank() || fields[1].isBlank() || fields[2].isBlank()) {
                        throw new IllegalArgumentException("Required text field is empty");
                    }
                    int route = Integer.parseInt(fields[3]);
                    double amount = Double.parseDouble(fields[4]);
                    if (route <= 0 || !Double.isFinite(amount) || amount < 0) {
                        throw new IllegalArgumentException("Route or amount out of range");
                    }

                    statement.setString(1, fields[0]);
                    statement.setString(2, fields[1]);
                    statement.setString(3, fields[2]);
                    statement.setInt(4, route);
                    statement.setDouble(5, amount);
                    statement.addBatch();
                    batchCount++;
                    if (batchCount == 1000) {
                        int[] counts = statement.executeBatch();
                        inserted += countSuccessful(counts);
                        batchCount = 0;
                    }
                } catch (RuntimeException exception) {
                    rejected.add("Line " + lineNumber + ": " + exception.getMessage()
                            + " | " + line);
                }
            }

            if (batchCount > 0) {
                inserted += countSuccessful(statement.executeBatch());
            }
            connection.commit();
            System.out.println("Inserted records: " + inserted);
            System.out.println("Rejected records: " + rejected.size());
            for (String reason : rejected) System.out.println(reason);
        } catch (Exception exception) {
            connection.rollback();
            throw exception;
        } finally {
            connection.setAutoCommit(previousAutoCommit);
        }
    }

    private static int countSuccessful(int[] counts) {
        int total = 0;
        for (int count : counts) {
            if (count == PreparedStatement.SUCCESS_NO_INFO) total++;
            else if (count > 0) total += count;
        }
        return total;
    }

    private static String[] parse(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean escaped = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (escaped) {
                if (ch == 'n') value.append('\n');
                else if (ch == 'r') value.append('\r');
                else value.append(ch);
                escaped = false;
            } else if (ch == '\\') {
                escaped = true;
            } else if (ch == ',') {
                fields.add(value.toString());
                value.setLength(0);
            } else {
                value.append(ch);
            }
        }
        if (escaped) throw new IllegalArgumentException("Dangling escape");
        fields.add(value.toString());
        return fields.toArray(new String[0]);
    }

    public static void inspectDatabase(Connection connection) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        System.out.println("Database product: " + metadata.getDatabaseProductName());
        System.out.println("Database version: " + metadata.getDatabaseProductVersion());
        System.out.println("JDBC driver: " + metadata.getDriverName());
        System.out.println("Driver version: " + metadata.getDriverVersion());
        try (ResultSet tables = metadata.getTables(connection.getCatalog(), null,
                "%", new String[]{"TABLE"})) {
            System.out.println("Tables:");
            while (tables.next()) System.out.println("- " + tables.getString("TABLE_NAME"));
        }
    }
}