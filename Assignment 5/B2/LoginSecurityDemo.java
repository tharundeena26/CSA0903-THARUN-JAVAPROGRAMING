import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginSecurityDemo {
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/bus_pass_db");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    public static boolean unsafeLogin(Connection connection, String id, String password)
            throws SQLException {
        String sql = "SELECT * FROM login_test WHERE user_id='" + id
                + "' AND password_value='" + password + "'";
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            return result.next();
        }
    }

    public static boolean safeLogin(Connection connection, String id, String password)
            throws SQLException {
        String sql = "SELECT * FROM login_test WHERE user_id=? AND password_value=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, password);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String normalId = "student01";
            String wrongPassword = "wrong-password";
            String craftedId = "' OR '1'='1' -- ";

            System.out.println("Unsafe normal invalid login: "
                    + unsafeLogin(connection, normalId, wrongPassword));
            System.out.println("Safe normal invalid login: "
                    + safeLogin(connection, normalId, wrongPassword));
            System.out.println("Unsafe crafted test input: "
                    + unsafeLogin(connection, craftedId, "anything"));
            System.out.println("Safe crafted test input: "
                    + safeLogin(connection, craftedId, "anything"));
        }
    }
}