/*
 * TASK 1 - PROBLEM ANALYSIS AND OBJECTS
 *
 * Step 1: Vehicle stores the registration number and vehicle type.
 * Step 2: Slot represents one parking space.
 * Step 3: park() checks whether the slot is already occupied.
 * Step 4: If free, the vehicle is stored and true is returned.
 * Step 5: release() clears the slot.
 */
public class Task01 {

    static class Vehicle {
        String registration;
        String type;

        Vehicle(String registration, String type) {
            this.registration = registration;
            this.type = type;
        }
    }

    static class Slot {
        Vehicle occupant;

        boolean park(Vehicle vehicle) {
            if (occupant != null) {
                return false;
            }

            occupant = vehicle;
            return true;
        }

        void release() {
            occupant = null;
        }
    }

    public static void main(String[] args) {
        Slot slot = new Slot();

        System.out.println("First vehicle: "
                + slot.park(new Vehicle("TN01AB1111", "Car")));

        System.out.println("Second vehicle: "
                + slot.park(new Vehicle("TN02CD2222", "Bike")));

        slot.release();

        System.out.println("Slot free: " + (slot.occupant == null));
    }
}
