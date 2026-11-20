/*
 * TASK 5 - STATIC MEMBERS AND OBJECT LIFE CYCLE
 *
 * Step 1: nextId is shared by all Ticket objects.
 * Step 2: activeCount stores the number of open tickets.
 * Step 3: Creating a ticket increments the active count.
 * Step 4: Closing a ticket decrements the count only once.
 */
public class Task05 {

    static class Ticket {

        private static long nextId = 1;
        private static int activeCount = 0;

        final long id;
        boolean open = true;

        Ticket() {
            id = nextId++;
            activeCount++;
        }

        void close() {
            if (open) {
                open = false;
                activeCount--;
            }
        }

        static int activeCount() {
            return activeCount;
        }
    }

    public static void main(String[] args) {

        Ticket first = new Ticket();
        Ticket second = new Ticket();

        System.out.println("IDs: " + first.id + ", " + second.id);
        System.out.println("Active: " + Ticket.activeCount());

        first.close();

        System.out.println("After exit: " + Ticket.activeCount());
    }
}
