# A3 – Discover the Data-Access Design Pattern

## A3.1 Repeated pattern
Common steps: obtain a connection, prepare SQL, bind parameters, execute, map ResultSet rows to objects, handle SQL exceptions and close ResultSet/statement/connection. Differences: table and column names, SQL operation, key type, object mapping and domain validation.

## A3.2 Generic DAO
```java
public interface Dao<T, K> {
    T insert(T value) throws SQLException;
    Optional<T> findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean update(T value) throws SQLException;
    boolean delete(K id) throws SQLException;
}
```
A StudentDao implements Dao<Student, String>, owns Student-specific SQL and row mapping, and receives a DataSource/connection provider through its constructor. Services call the DAO rather than embedding SQL in a GUI.

## A3.3 Object-to-table mapping
Student.registerNumber -> student.reg_no (PRIMARY KEY); Student.name -> student.name; Student.email -> student.email; Student.department -> student.department. Use PreparedStatement for values and try-with-resources for JDBC resources.

## Alternative: SQL in service/GUI
A column rename requires edits in every service or GUI location containing SQL, increasing coupling and regression risk. A DAO centralizes SQL/mapping. Both approaches have essentially the same database-operation asymptotic time and network round trips; DAO adds a small constant method/abstraction overhead and does not change database query complexity or stored data size.
