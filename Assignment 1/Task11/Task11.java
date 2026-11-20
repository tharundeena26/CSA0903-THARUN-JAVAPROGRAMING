/*
 * TASK 11 – IMPLEMENTATION, TESTING AND DEFENCE
 * Step 1: Ticket records the vehicle, type and allocated floor/slot.
 * Step 2: Parking creates a jagged array for floors of unequal sizes.
 * Step 3: A HashMap stores active tickets by registration for quick lookup.
 * Step 4: enter() validates the vehicle and finds a free slot.
 * Step 5: exit() calculates the fee, releases the slot and removes the ticket.
 * Step 6: main() tests full capacity, normal exits and an unsupported type.
 */
import java.util.HashMap;
import java.util.Map;

public class Task11 {
    static class Ticket {
        final String registration, type;
        final int floor, slot;

        Ticket(String registration, String type, int floor, int slot) {
            this.registration = registration;
            this.type = type;
            this.floor = floor;
            this.slot = slot;
        }
    }

    static class Parking {
        final String[][] slots;
        final Map<String, Ticket> active = new HashMap<>();
        double total;

        Parking(int... capacity) {
            slots = new String[capacity.length][];
            for (int floor = 0; floor < capacity.length; floor++) {
                slots[floor] = new String[capacity[floor]];
            }
        }

        Ticket enter(String registration, String type) {
            if (active.containsKey(registration)) {
                throw new IllegalArgumentException("Duplicate registration");
            }
            if (!type.equals("Bike") && !type.equals("Car") && !type.equals("EV")) {
                throw new IllegalArgumentException("Unknown type");
            }

            // Search every floor and slot for an empty position.
            for (int floor = 0; floor < slots.length; floor++) {
                for (int slot = 0; slot < slots[floor].length; slot++) {
                    if (slots[floor][slot] == null) {
                        slots[floor][slot] = registration;
                        Ticket ticket = new Ticket(registration, type, floor, slot);
                        active.put(registration, ticket);
                        return ticket;
                    }
                }
            }
            throw new IllegalStateException("Full");
        }

        double exit(String registration, int minutes, boolean monthlyPass, int chargingUnits) {
            Ticket ticket = active.get(registration);
            if (ticket == null || minutes < 0 || chargingUnits < 0) {
                throw new IllegalArgumentException("Invalid exit");
            }

            // Calculate parking fee. A valid pass makes the parking fee zero.
            double rate = ticket.type.equals("Bike") ? 10 : 30;
            double fee = monthlyPass ? 0 : ((minutes + 59) / 60) * rate;

            // Add charging cost only for an electric vehicle.
            if (ticket.type.equals("EV")) fee += chargingUnits * 12;

            // Release the slot and remove the active ticket.
            slots[ticket.floor][ticket.slot] = null;
            active.remove(registration);
            total += fee;
            return fee;
        }
    }

    public static void main(String[] args) {
        // Create a small facility with 2 slots on floor 1 and 1 on floor 2.
        Parking parking = new Parking(2, 1);
        parking.enter("TN01", "Bike");
        parking.enter("TN02", "Car");
        parking.enter("TN03", "EV");
        System.out.println("Full occupancy: " + parking.active.size());

        // The fourth vehicle cannot enter because all three slots are occupied.
        try {
            parking.enter("TN04", "Car");
        } catch (Exception exception) {
            System.out.println("Last slot boundary: full");
        }

        // Exit each vehicle and display the collected amount.
        System.out.println("Bike 0 min: Rs " + parking.exit("TN01", 0, false, 0));
        System.out.println("Car 60 min: Rs " + parking.exit("TN02", 60, false, 0));
        System.out.println("EV 61 min plus 5 kWh: Rs " + parking.exit("TN03", 61, false, 5));
        System.out.println("Total: Rs " + parking.total);

        // Unsupported vehicle categories are rejected.
        try {
            parking.enter("TN05", "Truck");
        } catch (Exception exception) {
            System.out.println("Unknown type rejected");
        }
    }
}
