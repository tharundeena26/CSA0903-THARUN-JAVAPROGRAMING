import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class PassStoreDemo {
    public static void main(String[] args) throws Exception {
        Path file = Files.createTempFile("passes", ".csv");
        PassStore store = new PassStore(file);
        store.add("P1001", "192311001", "Arun", 12, 4500);
        Files.writeString(file, "P1002,DAMAGED LINE\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, 3800);

        System.out.println(Arrays.toString(store.find("P1003")));
        System.out.println(Arrays.toString(store.find("P1002")));
        System.out.println("Route 12 count: " + store.listByRoute(12).size());
        Files.deleteIfExists(file);
    }
}