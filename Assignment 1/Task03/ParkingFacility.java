/*
 * TASK 3 - CONTROL LOGIC AND PARKING ALLOCATION
 *
 * Step 1: The constructor receives the capacity of each floor.
 * Step 2: allocate() searches for the first empty slot.
 * Step 3: It returns the floor and slot coordinates after allocation.
 * Step 4: release() validates the coordinates before freeing a slot.
 */
import java.util.Arrays;

public class ParkingFacility {

    private final String[][] slots;

    public ParkingFacility(int[] capacity) {
        slots = new String[capacity.length][];

        for (int floor = 0; floor < capacity.length; floor++) {
            slots[floor] = new String[capacity[floor]];
        }
    }

    public int[] allocate(String vehicleNumber) {

        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {

                if (slots[floor][slot] == null) {
                    slots[floor][slot] = vehicleNumber;
                    return new int[] {floor, slot};
                }
            }
        }

        return null;
    }

    public boolean release(int floor, int slot) {

        if (floor < 0 || floor >= slots.length) {
            return false;
        }

        if (slot < 0 || slot >= slots[floor].length) {
            return false;
        }

        if (slots[floor][slot] == null) {
            return false;
        }

        slots[floor][slot] = null;
        return true;
    }

    public static void main(String[] args) {

        ParkingFacility parking =
                new ParkingFacility(new int[] {2, 1});

        System.out.println(Arrays.toString(
                parking.allocate("TN01AB1111")));

        System.out.println(Arrays.toString(
                parking.allocate("TN02CD2222")));

        System.out.println(Arrays.toString(
                parking.allocate("TN03EF3333")));

        System.out.println(Arrays.toString(
                parking.allocate("TN04GH4444")));

        System.out.println(
                parking.release(0, 1) + " "
                + parking.release(0, 1) + " "
                + parking.release(5, 0));

        System.out.println(Arrays.toString(
                parking.allocate("TN04GH4444")));
    }
}
