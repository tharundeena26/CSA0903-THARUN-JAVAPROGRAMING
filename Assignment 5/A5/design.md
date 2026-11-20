# A5 – Design for Correctness and Safety

## A5.1 JDBC transfer transaction
1. Obtain one Connection and setAutoCommit(false).
2. Debit hostel_wallet by 500 only if balance >= 500; verify exactly one row updated.
3. Credit canteen_wallet by 500; verify exactly one row updated.
4. Commit only after both updates succeed.
5. On any SQLException or runtime failure, rollback and rethrow; restore the prior auto-commit state in finally.
Use row locks or an atomic conditional update and a consistent lock order to avoid lost updates/deadlocks. Use DECIMAL, not floating point, for money.

## A5.2 SQL injection
String concatenation lets attacker-controlled input alter SQL syntax. A crafted ID/password can close a quoted literal and append a condition that makes the WHERE clause true. Never test with a real account or production system. Use:
```java
String sql = "SELECT password_hash FROM users WHERE id = ?";
try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, id);
    try (ResultSet rs = ps.executeQuery()) {
        // Compare supplied password with the stored salted hash using a password-hashing library.
    }
}
```
Bind all user values; use a password-hashing algorithm such as Argon2id/bcrypt/PBKDF2 rather than comparing plaintext passwords.

## Alternative: two auto-commit updates
If the first update commits and the process fails before the second, money is lost from the source wallet. If the credit happens first, money may be created. A transaction has coordination/logging overhead but protects the all-or-nothing invariant; two independent updates may be slightly cheaper but are incorrect for this transfer.
