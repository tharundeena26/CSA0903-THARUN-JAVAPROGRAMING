import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PassLoaderDemo {
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/bus_pass_db");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    private static List<String[]> createPasses(int size, boolean includeInvalid) {
        List<String[]> passes = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            String amount = includeInvalid && i == size / 2 ? "not-a-number" : "4500.00";
            passes.add(new String[]{"P" + i, "REG" + i, "Student" + i, "12", amount});
        }
        return passes;
    }

    public static void main(String[] args) throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM bus_pass");
            try {
                int loaded = PassLoader.loadAll(connection, createPasses(10, false));
                System.out.println("Rows loaded: " + loaded);
            } catch (Exception exception) {
                System.out.println("Unexpected load error: " + exception.getMessage());
            }

            statement.executeUpdate("DELETE FROM bus_pass");
            try {
                PassLoader.loadAll(connection, createPasses(10, true));
            } catch (Exception exception) {
                System.out.println("Invalid amount rejected: " + exception.getClass().getSimpleName());
            }

            try (ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM bus_pass")) {
                result.next();
                System.out.println("Rows after failed transaction: " + result.getInt(1));
            }
        }
    }
}