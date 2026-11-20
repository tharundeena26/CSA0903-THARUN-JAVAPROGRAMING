# Programming in Java – Assignment 5 (CO5)
**Student:** THARUN D  
**Register Number:** 192524399  
**Java version:** OpenJDK 21  
**Source:** Uploaded SIMATS Engineering Assignment 5 – CO5, Data Persistence Engineering – Files and Databases.

## Folder map
- `A1` – Bus-pass file-format design, record layouts, operation pseudocode and damaged-data strategy.
- `A2` – Buffered I/O cost prediction and redesign.
- `A3` – Generic DAO design and Student-to-table mapping.
- `A4` – Serialization/transient-field design and versioning.
- `A5` – JDBC transaction and PreparedStatement injection defence.
- `B1` – PassStore implementation, damaged-line tests, buffered/unbuffered line counter and benchmark.
- `B2` – Unsafe versus parameterized login demonstration and adversarial input cases.
- `B3` – JDBC Statement, PreparedStatement and batched PassLoader implementation, transaction rollback.
- `B4` – File-to-database migration and DatabaseMetaData inspection.

## Compile and run (from each folder)
`javac *.java` then run the class named in that folder, for example `java PassStoreDemo`.
For JDBC tasks, add MySQL Connector/J to the classpath and configure:
- `DB_URL` (example `jdbc:mysql://localhost:3306/bus_pass_db`)
- `DB_USER`
- `DB_PASSWORD`

On Windows, a typical command is:
`javac -cp ".;mysql-connector-j.jar" *.java`
`java -cp ".;mysql-connector-j.jar" PassLoaderDemo`

On macOS/Linux, replace the classpath separator `;` with `:`.

## Important execution/evidence note
The committed `output.txt` files are expected-output/test guides and the SVG files are illustrative terminal/UI previews. They are **not screenshots of a live execution**. Run the programs on your computer, record actual timings for 1 MB, 10 MB and 50 MB (B1), and replace/add genuine screenshots and measured CSV evidence before final submission. Database tasks require a locally configured MySQL instance and Connector/J; timings and metadata depend on your machine/server.

## CO5 assignment requirements
Part A (A1–A5) is provided as design/pseudocode in Markdown because the PDF explicitly says Java code is not accepted in place of Part A pseudocode. Part B (B1–B4) contains Java implementation files, SQL schema, test data and evidence guides.
