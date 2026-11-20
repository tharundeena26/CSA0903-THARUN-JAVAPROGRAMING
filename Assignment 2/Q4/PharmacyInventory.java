import java.util.*;

public class PharmacyInventory {
    private static volatile int checksum;

    private static <M extends Map<String, Integer>> long lookupTime(M map, int lookups) {
        long start = System.nanoTime();
        for (int i = 0; i < lookups; i++) {
            checksum += map.get(String.format("MED%06d", i % map.size()));
        }
        return System.nanoTime() - start;
    }

    private static long reportTime(Map<String, Integer> map) {
        long start = System.nanoTime();
        int count = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            count += entry.getValue();
        }
        checksum += count;
        return System.nanoTime() - start;
    }

    private static void test(int n) {
        Map<String, Integer> hash = new HashMap<>();
        Map<String, Integer> tree = new TreeMap<>();
        Map<String, Integer> linked = new LinkedHashMap<>();

        for (int i = n - 1; i >= 0; i--) {
            String code = String.format("MED%06d", i);
            hash.put(code, i + 10);
            tree.put(code, i + 10);
            linked.put(code, i + 10);
        }

        System.out.println("\nMedicine count: " + n);
        int lookups = 100000;
        System.out.println("HashMap lookup ns: " + lookupTime(hash, lookups));
        System.out.println("TreeMap lookup ns: " + lookupTime(tree, lookups));
        System.out.println("LinkedHashMap lookup ns: " + lookupTime(linked, lookups));
        System.out.println("HashMap report iteration ns: " + reportTime(hash));
        System.out.println("TreeMap code-order report ns: " + reportTime(tree));
        System.out.println("LinkedHashMap insertion-order report ns: " + reportTime(linked));
        System.out.println("TreeMap first key: " + tree.keySet().iterator().next());
        System.out.println("LinkedHashMap first key: " + linked.keySet().iterator().next());
    }

    public static void main(String[] args) {
        System.out.println("Q4 - Pharmacy inventory map comparison");
        test(1000);
        test(5000);
        test(10000);
        System.out.println("Checksum: " + checksum);
        System.out.println("HashMap: average O(1) lookup; TreeMap: sorted keys and O(log n); LinkedHashMap: insertion order.");
    }
}