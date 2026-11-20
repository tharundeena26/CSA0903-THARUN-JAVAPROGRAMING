/*
 * TASK 2 – DATA REPRESENTATION
 *
 * Step 1: Create a jagged String array. Each inner array represents one
 *         floor, and floors may have different slot counts.
 * Step 2: Put a vehicle registration number in a chosen floor and slot.
 * Step 3: Set the parking duration to 130 minutes.
 * Step 4: Show why minutes / 60 is not enough: integer division truncates
 *         130 / 60 to 2 and does not round a partial hour upward.
 * Step 5: Calculate started hours with (minutes + 59) / 60.
 * Step 6: Multiply the billable hours by the illustrative hourly rate.
 */
public class Task02{public static void main(String[]x){String[][]slots={new String[2],new String[3],new String[1]};slots[1][2]="TN01AB1111";int m=130,h=(m+59)/60;System.out.println("Floors: "+slots.length);System.out.println("Vehicle: "+slots[1][2]);System.out.println("Incorrect integer hours: "+(m/60));System.out.println("Billable hours: "+h);System.out.println("Fee: Rs "+h*30.0);}}