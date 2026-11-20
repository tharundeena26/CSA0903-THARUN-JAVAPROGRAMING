import java.util.*;

public class StringComparison {
    private static volatile int sink;

    private static String buildWithPlus(String[] fields) {
        String ticket = "";
        for (String field : fields) {
            ticket = ticket + field + " | ";
        }
        return ticket;
    }

    private static String buildWithBuilder(String[] fields) {
        StringBuilder ticket = new StringBuilder();
        for (String field : fields) {
            ticket.append(field).append(" | ");
        }
        return ticket.toString();
    }

    private static String buildWithBuffer(String[] fields) {
        StringBuffer ticket = new StringBuffer();
        for (String field : fields) {
            ticket.append(field).append(" | ");
        }
        return ticket.toString();
    }

    private static long measure(int method, String[][] workload, int repetitions) {
        long start = System.nanoTime();
        for (int r = 0; r < repetitions; r++) {
            for (String[] fields : workload) {
                String result;
                if (method == 0) result = buildWithPlus(fields);
                else if (method == 1) result = buildWithBuilder(fields);
                else result = buildWithBuffer(fields);
                sink += result.length();
            }
        }
        return (System.nanoTime() - start) / repetitions;
    }

    private static void runWorkload(String name, int ticketCount, int fieldCount) {
        String[][] workload = new String[ticketCount][fieldCount];
        for (int i = 0; i < ticketCount; i++) {
            for (int j = 0; j < fieldCount; j++) {
                workload[i][j] = "F" + i + "_" + j;
            }
        }
        System.out.println("\n" + name + " (tickets=" + ticketCount + ", fields=" + fieldCount + ")");
        measure(0, workload, 2);
        measure(1, workload, 2);
        measure(2, workload, 2);
        System.out.println("String + average ns: " + measure(0, workload, 5));
        System.out.println("StringBuilder average ns: " + measure(1, workload, 5));
        System.out.println("StringBuffer average ns: " + measure(2, workload, 5));
    }

    public static void main(String[] args) {
        System.out.println("Q1 - Railway e-ticket text generation");
        runWorkload("Short ticket", 1, 6);
        runWorkload("Large booking batch", 1000, 50);
        runWorkload("Custom workload", 100, 100);
        System.out.println("Result checksum: " + sink);
        System.out.println("Note: timings vary by machine and JVM; run several times.");
    }
}