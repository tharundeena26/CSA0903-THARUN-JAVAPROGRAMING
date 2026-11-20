import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeeService {
    private static final String URL = "jdbc:mysql://localhost:3306/college";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public String payFee(String registrationNumber, String amountText)
            throws IllegalArgumentException, SQLException {
        String regNo = registrationNumber == null ? "" : registrationNumber.trim();
        if (regNo.isEmpty()) {
            throw new IllegalArgumentException("Registration number is required");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a valid amount", e);
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        String sql = "INSERT INTO fees (reg_no, amount, paid_at) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, regNo);
            statement.setDouble(2, amount);
            statement.executeUpdate();
            return String.format("Payment successful: Rs. %.2f", amount);
        }
    }
}