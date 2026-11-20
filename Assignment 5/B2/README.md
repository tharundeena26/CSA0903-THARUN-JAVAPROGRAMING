# B2 – Adversarial Input Investigation

1. Create the local test database with `mysql -u root -p < schema.sql`.
2. Set DB_URL, DB_USER and DB_PASSWORD for the local MySQL instance.
3. Compile with MySQL Connector/J on the classpath.
4. Run `LoginSecurityDemo`. The crafted test string is only for this isolated local demo. The unsafe concatenated query may accept it; PreparedStatement treats it as data and rejects it.
5. Inspect `damaged-passes.csv`: it includes a wrong field count, a non-numeric route, a blank line and a non-numeric amount. PassStore skips invalid records during lookup/list.

Never run the unsafe example against a real account or production database.
