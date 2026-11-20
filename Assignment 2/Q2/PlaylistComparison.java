import java.util.*;

public class PlaylistComparison {
    private static volatile long checksum;

    private static long timeAddEnd(List<Integer> list, int n) {
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) list.add(i);
        return System.nanoTime() - start;
    }

    private static long timeInsertFront(List<Integer> list, int n) {
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) list.add(0, i);
        return System.nanoTime() - start;
    }

    private static long timeMiddleInsert(List<Integer> list, int n) {
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) list.add(list.size() / 2, i);
        return System.nanoTime() - start;
    }

    private static long timeRandomAccess(List<Integer> list, int n) {
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) checksum += list.get((i * 31) % list.size());
        return System.nanoTime() - start;
    }

    private static long timeIteration(List<Integer> list) {
        long start = System.nanoTime();
        for (int value : list) checksum += value;
        return System.nanoTime() - start;
    }

    private static void testSize(int n) {
        System.out.println("\nPlaylist size: " + n);
        List<Integer> arrayEnd = new ArrayList<>();
        List<Integer> linkedEnd = new LinkedList<>();
        System.out.println("ArrayList add end ns: " + timeAddEnd(arrayEnd, n));
        System.out.println("LinkedList add end ns: " + timeAddEnd(linkedEnd, n));

        List<Integer> arrayFront = new ArrayList<>();
        List<Integer> linkedFront = new LinkedList<>();
        System.out.println("ArrayList insert front ns: " + timeInsertFront(arrayFront, n));
        System.out.println("LinkedList insert front ns: " + timeInsertFront(linkedFront, n));

        List<Integer> arrayMiddle = new ArrayList<>();
        List<Integer> linkedMiddle = new LinkedList<>();
        System.out.println("ArrayList insert middle ns: " + timeMiddleInsert(arrayMiddle, n));
        System.out.println("LinkedList insert middle ns: " + timeMiddleInsert(linkedMiddle, n));

        List<Integer> arrayAccess = new ArrayList<>(arrayEnd);
        List<Integer> linkedAccess = new LinkedList<>(linkedEnd);
        System.out.println("ArrayList indexed access ns: " + timeRandomAccess(arrayAccess, n));
        System.out.println("LinkedList indexed access ns: " + timeRandomAccess(linkedAccess, n));
        System.out.println("ArrayList iteration ns: " + timeIteration(arrayEnd));
        System.out.println("LinkedList iteration ns: " + timeIteration(linkedEnd));

        ArrayDeque<Integer> upNext = new ArrayDeque<>();
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) upNext.addLast(i);
        for (int i = 0; i < n; i++) upNext.removeFirst();
        System.out.println("ArrayDeque addLast/removeFirst ns: " + (System.nanoTime() - start));
    }

    public static void main(String[] args) {
        System.out.println("Q2 - Playlist collection experiment");
        testSize(1000);
        testSize(5000);
        testSize(10000);
        System.out.println("Checksum: " + checksum);
        System.out.println("ArrayList suits append, indexed access and iteration; use ArrayDeque for a two-ended queue.");
    }
}