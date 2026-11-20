/*
 * TASK 2 - DATA REPRESENTATION
 *
 * Step 1: Create a jagged array for floors with different slot counts.
 * Step 2: Store a vehicle registration in one of the slots.
 * Step 3: Calculate the billable hours for 130 minutes.
 * Step 4: Use (minutes + 59) / 60 to round started hours upward.
 */
public class Task02 {

    public static void main(String[] args) {

        String[][] slots = {
            new String[2],
            new String[3],
            new String[1]
        };

        slots[1][2] = "TN01AB1111";

        int minutes = 130;
        int billableHours = (minutes + 59) / 60;

        System.out.println("Floors: " + slots.length);
        System.out.println("Vehicle: " + slots[1][2]);

        System.out.println("Incorrect integer hours: "
                + (minutes / 60));

        System.out.println("Billable hours: " + billableHours);
        System.out.println("Fee: Rs " + billableHours * 30.0);
    }
}
