/*
 * TASK 10 – ENCAPSULATION
 * Step 1: Keep the slot number and registration private.
 * Step 2: Use a constructor to validate and initialize the slot number.
 * Step 3: Use getters to read values without exposing the fields.
 * Step 4: park() validates the registration and rejects occupied slots.
 * Step 5: release() clears the registration to make the slot available.
 * Step 6: main() tests normal parking, duplicate occupancy and release.
 */
public class Task10 {
    static class Slot {
        private final int number;
        private String registration;

        Slot(int number) {
            if (number < 0) throw new IllegalArgumentException("Invalid slot");
            this.number = number;
        }

        public int getNumber() { return number; }
        public String getRegistration() { return registration; }

        public void park(String registration) {
            if (registration == null || registration.isBlank() || this.registration != null) {
                throw new IllegalArgumentException("Invalid or occupied");
            }
            this.registration = registration;
        }

        public void release() {
            registration = null;
        }
    }

    public static void main(String[] args) {
        // Step 7: Create a slot and park the first vehicle.
        Slot slot = new Slot(3);
        slot.park("TN01AB1111");
        System.out.println("Slot: " + slot.getNumber());
        System.out.println("Vehicle: " + slot.getRegistration());

        // Step 8: Attempting to park another vehicle must be rejected.
        try {
            slot.park("TN02CD2222");
        } catch (IllegalArgumentException exception) {
            System.out.println("Duplicate occupancy rejected");
        }

        // Step 9: Release the slot and confirm it is empty.
        slot.release();
        System.out.println("Free: " + (slot.getRegistration() == null));
    }
}
