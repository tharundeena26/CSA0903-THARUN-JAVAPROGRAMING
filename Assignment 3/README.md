# Programming in Java - Assignment 3 (CO3)

**Student:** THARUN D  
**Register Number:** 192524399  
**Java version:** OpenJDK 21  
**Assignment:** CO3 - Exception Handling and Multithreading

## Repository layout
Each question has its own folder (Q1 to Q7). Each folder contains its Java source, captured program output (output.txt) and a terminal-style output screenshot (output-screenshot.svg). Supporting input files are included where applicable. All Java source is formatted in normal horizontal, line-by-line Java syntax with indentation.

## Compile and run

| Folder | Compile | Run |
|---|---|---|
| Q1 | javac ATMSimulation.java | java ATMSimulation |
| Q2 | javac StorageDemo.java | java StorageDemo |
| Q3 | javac SalesReport.java | java SalesReport |
| Q4 | javac ScannerStations.java | java ScannerStations |
| Q5 | javac SeatBooking.java | java SeatBooking |
| Q6 | javac TokenCounter.java | java TokenCounter |
| Q7 | javac ResultProcessor.java | java ResultProcessor |

## Question summary

### Q1 - ATM exception handling
ATMSimulation reads an account number and withdrawal amount, checks card-reader.txt, and handles malformed numbers, invalid/negative amounts, insufficient funds and card-reader I/O errors. It uses multi-catch and finally.
Test cases: 1500 (success), letters (NumberFormatException), -50 (invalid amount), 7000 (insufficient balance), and temporarily renaming card-reader.txt (IOException). The finally message is printed on every path. Catch blocks are ordered from specific to broader failures. A single catch(Exception) is appropriate only at a safe application boundary with logging; it loses precise recovery handling.
Alternative: Scanner.hasNextDouble() and error codes avoid exception allocation. Normal validation is O(1) time and O(1) extra space. Exception creation includes stack-trace capture and additional object memory, so exceptions should not be routine control flow.

### Q2 - User-defined exceptions and propagation
StorageDemo defines checked portal exceptions. Storage.save catches IOException and wraps it in SubmissionStorageException while preserving the original cause. Output includes the expected Could not store answer02.pdf and Disk quota exceeded messages. Checked exceptions require callers to catch or declare them; RuntimeException does not. A chained exception retains the cause and its stack trace, increasing memory with the cause chain.

### Q3 - Resource safety and try-with-resources
SalesReport reads two store files and attempts one missing file. It compares manual try/catch/finally closing with try-with-resources. Both versions produce the same three valid rows; the missing file is logged and the reader count returns to zero. Try-with-resources closes resources in reverse declaration order and preserves close-time failures as suppressed exceptions.
For f store files, processing is O(total input bytes). Correct sequential versions hold O(1) open handles; failure to close can let open handles grow to O(f).

### Q4 - Thread creation and life cycle
ScannerStations demonstrates Thread subclass and Runnable, with start(), sleep(), join() and isAlive(). The Thread stations process 26 scripts; Runnable stations process 12. Completion order depends on the scheduler. Calling run() directly does not start a new thread.
An ExecutorService fixed pool reuses p workers for n tasks. Idealized work time is approximately total work divided by p for balanced tasks; each worker uses stack space.

### Q5 - Cinema seat-booking race condition
SeatBooking uses a barrier to demonstrate a race deterministically: 100 competing attempts can increment the unsynchronized counter for 10 seats. The synchronized method and synchronized block each sell exactly 10 seats. The critical section is the check-then-mark-and-increment operation. Each attempt is O(1); the seat array uses O(s) space.

### Q6 - Deadlock and inter-thread communication
TokenCounter completes both producer-consumer TODOs with while, wait() and notifyAll() on a capacity-five circular buffer. The collected sum is 210. WalletTransfers includes an unsafe nested-lock method for discussion and a safe method that always acquires wallet locks in ascending ID order, preventing circular wait. ArrayBlockingQueue is a less error-prone alternative for the bounded buffer.

### Q7 - Concurrent fault-tolerant result processor
ResultProcessor creates 10,000 score files, including 100 missing and 100 corrupt inputs, then processes them using ExecutorService, Callable, Future and ConcurrentLinkedQueue. One bad file does not stop the run. Each pool size (1, 2, 4 and 8) should report 9,800 successes and 200 failures. Timings are machine-dependent and should be measured again on the submission computer.

## Execution note
All seven programs were compiled and run with OpenJDK 21. Each output screenshot is a terminal-style vector capture generated from its output.txt. Timing values and thread completion order can vary between runs.
