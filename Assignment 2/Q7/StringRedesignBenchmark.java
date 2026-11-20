public class StringRedesignBenchmark {
    private static volatile int checksum;

    private static String originalRepeatedConcatenation(String[] fields) {
        String text = "";
        for (String field : fields) {
            text = text + field + "|";
        }
        return text;
    }

    private static String redesignedStringBuilder(String[] fields) {
        StringBuilder text = new StringBuilder();
        for (String field : fields) {
            text.append(field).append('|');
        }
        return text.toString();
    }

    private static long measure(boolean redesigned, String[][] data, int repeats) {
        long start = System.nanoTime();
        for (int repeat = 0; repeat < repeats; repeat++) {
            for (String[] fields : data) {
                String result = redesigned
                        ? redesignedStringBuilder(fields)
                        : originalRepeatedConcatenation(fields);
                checksum += result.length();
            }
        }
        return (System.nanoTime() - start) / repeats;
    }

    public static void main(String[] args) {
        int ticketCount = 500;
        int fieldCount = 80;
        String[][] data = new String[ticketCount][fieldCount];

        for (int i = 0; i < ticketCount; i++) {
            for (int j = 0; j < fieldCount; j++) {
                data[i][j] = "T" + i + "F" + j;
            }
        }

        measure(false, data, 2);
        measure(true, data, 2);

        System.out.println("Q7 - Diagnose and validate a StringBuilder redesign");
        System.out.println("Workload: " + ticketCount + " tickets x " + fieldCount + " fields");
        System.out.println("Original repeated + average ns: " + measure(false, data, 5));
        System.out.println("Redesigned StringBuilder average ns: " + measure(true, data, 5));
        System.out.println("Checksum: " + checksum);
        System.out.println("Objective: reduce repeated temporary String creation and character copying.");
        System.out.println("Complexity: repeated concatenation can copy O(n^2) characters; StringBuilder is O(n) amortized.");
        System.out.println("Measurements vary by JVM and hardware; repeat runs before drawing conclusions.");
    }
}