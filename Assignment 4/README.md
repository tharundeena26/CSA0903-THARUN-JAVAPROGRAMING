# Programming in Java - Assignment 4 (CO4): Event-Driven GUI Design

**Student:** THARUN D  
**Register Number:** 192524399  
**Java:** OpenJDK 21  
**Source:** SIMATS Engineering, Programming in Java, Assignment 4 - CO4 (6 tasks).

Each question folder contains line-by-line Java source, a test/evidence note and an SVG interface/evidence illustration. GUI screenshots must be captured on a desktop after running the programs; SVG files here are labelled design/evidence illustrations, not photographs of a live desktop.

## Compile and run
Run each command inside its question folder:
- Q1: `javac FeeWindow.java FeeService.java` then `java FeeWindow`
- Q2: `javac AppointmentDesk.java` then `java AppointmentDesk`
- Q3: `javac LibrarySearch.java` then `java LibrarySearch`
- Q4: `javac DrawPad.java` then `java DrawPad`
- Q5: `javac Converter.java` then `java Converter`
- Q6: Set JDBC environment variables, run `schema.sql` in MySQL, then compile/run `CampusEventApp.java` with MySQL Connector/J on the classpath.

## Question guide
1. Q1 repairs the fee-payment design: view collects input, FeeService validates, prepared SQL is used, and database work runs in SwingWorker. Separate listeners/actions and user feedback are provided.
2. Q2 creates an outpatient appointment desk with patient ID, department, dependent doctor list, slot, booking validation, confirmation and a close-while-booking guard.
3. Q3 implements the library search layout using nested BorderLayout and FlowLayout panels so the centre results table expands on resize.
4. Q4 fixes the mouseClicked spelling, stores points, updates the click label and repaints from the stored model so dots survive minimize/restore.
5. Q5 demonstrates four event styles: frame listener, named inner class, anonymous class and lambda. Convert button and Enter key both trigger conversion; invalid input is shown in a dialog.
6. Q6 provides a layered Swing/JDBC event registration app with form validation, duplicate prevention, table filtering, export, menu, and SwingWorker for background database access. The MySQL schema is in Q6/schema.sql.

## Notes
- Use a desktop environment with a graphical display to run AWT/Swing applications.
- Q1 and Q6 require MySQL Connector/J and a configured MySQL database.
- Q6 environment variables: `DB_URL` (for example jdbc:mysql://localhost:3306/campus_events), `DB_USER`, `DB_PASSWORD`.
- The assignment PDF asks for screenshots of actual tested interactions and SQL. The committed SVG evidence files are illustrative mockups; replace them with real desktop screenshots after testing on your machine.
