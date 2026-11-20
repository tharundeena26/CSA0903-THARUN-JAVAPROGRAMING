import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PassLoader {
    /** Loads all passes in one transaction, in batches of 1,000 rows. */
    public static int loadAll(Connection connection, List<String[]> passes)
            throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) "
                + "VALUES (?, ?, ?, ?, ?)";
        boolean oldAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int count = 0;
            for (String[] pass : passes) {
                if (pass.length != 5) {
                    throw new IllegalArgumentException("Each pass must contain five fields");
                }
                statement.setString(1, pass[0]);
                statement.setString(2, pass[1]);
                statement.setString(3, pass[2]);
                statement.setInt(4, Integer.parseInt(pass[3]));
                statement.setDouble(5, Double.parseDouble(pass[4]));
                statement.addBatch();
                count++;

                if (count % 1000 == 0) {
                    statement.executeBatch();
                }
            }
            if (count % 1000 != 0) {
                statement.executeBatch();
            }
            connection.commit();
            return count;
        } catch (SQLException | RuntimeException exception) {
            connection.rollback();
            throw exception;
        } finally {
            connection.setAutoCommit(oldAutoCommit);
        }
    }
}