import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class InsertBenchmark {
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/bus_pass_db");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    private static void clear(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM bus_pass");
        }
    }

    private static long runStatement(Connection connection, int size) throws Exception {
        clear(connection);
        long start = System.nanoTime();
        try (Statement statement = connection.createStatement()) {
            for (int i = 1; i <= size; i++) {
                statement.executeUpdate("INSERT INTO bus_pass VALUES ('P" + i
                        + "','R" + i + "','Student" + i + "',12,4500.00)");
            }
        }
        return System.nanoTime() - start;
    }

    private static long runPrepared(Connection connection, int size) throws Exception {
        clear(connection);
        long start = System.nanoTime();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO bus_pass VALUES (?,?,?,?,?)")) {
            for (int i = 1; i <= size; i++) {
                statement.setString(1, "P" + i);
                statement.setString(2, "R" + i);
                statement.setString(3, "Student" + i);
                statement.setInt(4, 12);
                statement.setDouble(5, 4500.00);
                statement.executeUpdate();
            }
        }
        return System.nanoTime() - start;
    }

    private static long runBatch(Connection connection, int size) throws Exception {
        clear(connection);
        boolean previous = connection.getAutoCommit();
        connection.setAutoCommit(false);
        long start = System.nanoTime();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO bus_pass VALUES (?,?,?,?,?)")) {
            for (int i = 1; i <= size; i++) {
                statement.setString(1, "P" + i);
                statement.setString(2, "R" + i);
                statement.setString(3, "Student" + i);
                statement.setInt(4, 12);
                statement.setDouble(5, 4500.00);
                statement.addBatch();
                if (i % 1000 == 0) statement.executeBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception exception) {
            connection.rollback();
            throw exception;
        } finally {
            connection.setAutoCommit(previous);
        }
        return System.nanoTime() - start;
    }

    public static void main(String[] args) throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            for (int size : new int[]{1000, 10000, 100000}) {
                System.out.println("Rows: " + size);
                System.out.println("Statement ms: "
                        + runStatement(connection, size) / 1_000_000.0);
                System.out.println("PreparedStatement ms: "
                        + runPrepared(connection, size) / 1_000_000.0);
                System.out.println("Prepared batch ms: "
                        + runBatch(connection, size) / 1_000_000.0);
            }
        }
    }
}