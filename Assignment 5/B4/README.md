# B4 – Final Engineering Decision

Run `schema.sql` from B3 first, then set DB_URL, DB_USER and DB_PASSWORD. Compile with MySQL Connector/J and run:

`java MigratePasses passes.csv`

The migration validates each row, batches up to 1,000 valid records, inserts within one transaction and reports rejected line numbers. Database metadata is printed after migration.

**Production recommendation:** use a relational database for the 12-campus deployment. It supports concurrent counters, unique constraints, transactions, indexed searches and centralized backup/recovery. Retain a versioned text-file import/export format for interchange and migration, not as the authoritative multi-campus store. Java serialization is not recommended as the shared production data store because it is class-version coupled and poorly suited to concurrent multi-user queries.
