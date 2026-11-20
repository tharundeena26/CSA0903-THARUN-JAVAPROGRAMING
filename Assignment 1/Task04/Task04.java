/*
 * TASK 4 - CLASSES, OBJECTS AND CONSTRUCTORS
 *
 * Step 1: Ticket stores an ID, registration and parking duration.
 * Step 2: Constructors demonstrate overloading and constructor chaining.
 * Step 3: The copy constructor creates a copy of an existing ticket.
 */
public class Task04 {

    static class Ticket {
        final long id;
        final String registration;
        final int minutes;

        Ticket() {
            this(1, "UNKNOWN", 0);
        }

        Ticket(long id, String registration) {
            this(id, registration, 0);
        }

        Ticket(long id, String registration, int minutes) {

            if (id < 1 || registration == null
                    || registration.isBlank() || minutes < 0) {
                throw new IllegalArgumentException();
            }

            this.id = id;
            this.registration = registration;
            this.minutes = minutes;
        }

        Ticket(Ticket ticket) {
            this(ticket.id, ticket.registration, ticket.minutes);
        }

        public String toString() {
            return id + " " + registration + " " + minutes + " min";
        }
    }

    public static void main(String[] args) {

        Ticket original =
                new Ticket(101, "TN01AB1111", 90);

        Ticket copy = new Ticket(original);

        System.out.println("Original: " + original);
        System.out.println("Copy: " + copy);
    }
}
