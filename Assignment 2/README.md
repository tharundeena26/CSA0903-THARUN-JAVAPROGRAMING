# Programming in Java - Assignment 2 (CO2)

**Student:** THARUN D  
**Register Number:** 192524399  
**Java version:** OpenJDK 21  
**Source assignment:** ASSIGNMENT2 javi.pdf (Q1-Q7)

## Folder structure
Each question has its own folder (Q1 through Q7). Every folder contains readable line-by-line Java source code, output.txt, and output-screenshot.svg (a vector terminal screenshot).

## Compile and run
Run these commands from the relevant question folder:

| Question | Source file | Compile | Run |
|---|---|---|---|
| Q1 | StringComparison.java | javac StringComparison.java | java StringComparison |
| Q2 | PlaylistComparison.java | javac PlaylistComparison.java | java PlaylistComparison |
| Q3 | Attendance.java | javac Attendance.java | java Attendance |
| Q4 | PharmacyInventory.java | javac PharmacyInventory.java | java PharmacyInventory |
| Q5 | LeaderboardDemo.java | javac LeaderboardDemo.java | java LeaderboardDemo |
| Q6 | Triage.java | javac Triage.java | java Triage |
| Q7 | StringRedesignBenchmark.java | javac StringRedesignBenchmark.java | java StringRedesignBenchmark |

## Question guide
1. Q1 compares String concatenation, StringBuilder and StringBuffer for short, large and custom ticket workloads.
2. Q2 compares ArrayList, LinkedList and ArrayDeque operations at three playlist sizes.
3. Q3 demonstrates the hashCode/equals contract and the corrected Student class.
4. Q4 compares HashMap, TreeMap and LinkedHashMap for pharmacy inventory lookup and reporting. Lookup keys are prepared before timing so key formatting is excluded from the map lookup measurement.
5. Q5 implements a generic Leaderboard using a Comparator chain: points descending, goal difference descending, then team name ascending.
6. Q6 implements emergency triage with PriorityQueue, ordering by severity and then arrival sequence.
7. Q7 diagnoses repeated String concatenation and validates a StringBuilder redesign using the same workload.

## Evidence and notes
- Each output screenshot is a terminal-style rendering of the actual program output; output.txt contains the same captured text.
- Timings are machine/JVM dependent. Re-run several times on your own computer and use repeated averages for your final analysis.
- Q1 and Q7 use warm-up passes and repeated System.nanoTime measurements. Q2 and Q4 report timings at three data sizes.
- Q3's broken implementation intentionally omits hashCode; the exact broken-set size can vary. The corrected class produces two unique students.
- Q7 uses Q1's repeated-concatenation bottleneck as its redesign case.
- Review and understand the code and measured results before submission; the assignment includes a verification discussion.