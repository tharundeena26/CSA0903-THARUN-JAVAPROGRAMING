/*
 * TASK 12 – RE-EVALUATE AND ADAPT THE DESIGN
 * Step 1: PricingRule defines a common contract for fee calculations.
 * Step 2: Hourly applies an hourly rate; Flat applies one fixed fee.
 * Step 3: Van applies the delivery-van rate based on entry time.
 * Step 4: Vehicle combines a registration with its selected pricing rule.
 * Step 5: Facility uses a jagged array to support unequal floor capacities.
 * Step 6: bill() applies a valid visitor pass as a zero-parking-fee rule.
 * Step 7: main() demonstrates the new categories and five-floor layout.
 */
import java.time.LocalTime;
import java.util.Arrays;

public class Task12 {
    interface PricingRule {
        double fee(int minutes, LocalTime entryTime);
    }

    static class Hourly implements PricingRule {
        final double rate;
        Hourly(double rate) { this.rate = rate; }
        public double fee(int minutes, LocalTime entryTime) {
            return ((minutes + 59) / 60) * rate;
        }
    }

    static class Flat implements PricingRule {
        final double amount;
        Flat(double amount) { this.amount = amount; }
        public double fee(int minutes, LocalTime entryTime) {
            return amount;
        }
    }

    static class Van implements PricingRule {
        public double fee(int minutes, LocalTime entryTime) {
            // Simplified assignment rule: double rate for entry between 9 AM and 5 PM.
            double rate = (!entryTime.isBefore(LocalTime.of(9, 0))
                    && entryTime.isBefore(LocalTime.of(17, 0))) ? 60 : 30;
            return ((minutes + 59) / 60) * rate;
        }
    }

    static class Vehicle {
        final String registration;
        final PricingRule pricingRule;
        Vehicle(String registration, PricingRule pricingRule) {
            this.registration = registration;
            this.pricingRule = pricingRule;
        }
    }

    static class Facility {
        final String[][] slots;
        Facility(int... capacityPerFloor) {
            slots = new String[capacityPerFloor.length][];
            for (int floor = 0; floor < capacityPerFloor.length; floor++) {
                slots[floor] = new String[capacityPerFloor[floor]];
            }
        }

        int[] allocate(String registration) {
            for (int floor = 0; floor < slots.length; floor++) {
                for (int slot = 0; slot < slots[floor].length; slot++) {
                    if (slots[floor][slot] == null) {
                        slots[floor][slot] = registration;
                        return new int[] {floor, slot};
                    }
                }
            }
            return null;
        }
    }

    // A valid pass makes the parking fee zero; otherwise use the vehicle's rule.
    static double bill(Vehicle vehicle, int minutes, LocalTime entryTime, boolean validPass) {
        return validPass ? 0 : vehicle.pricingRule.fee(minutes, entryTime);
    }

    public static void main(String[] args) {
        // Step 8: Create five floors with different slot counts.
        Facility facility = new Facility(2, 3, 1, 4, 2);

        // Step 9: Create an e-bike with flat pricing and a van with time-based pricing.
        Vehicle eBike = new Vehicle("EB01", new Flat(25));
        Vehicle van = new Vehicle("VAN01", new Van());

        // Step 10: Allocate a slot and compare the pricing scenarios.
        System.out.println("Floors: " + facility.slots.length);
        System.out.println("E-bike slot: " + Arrays.toString(facility.allocate(eBike.registration)));
        System.out.println("E-bike flat: Rs " + bill(eBike, 130, LocalTime.of(10, 0), false));
        System.out.println("Van peak: Rs " + bill(van, 120, LocalTime.of(10, 0), false));
        System.out.println("Van off-peak: Rs " + bill(van, 120, LocalTime.of(18, 0), false));
        System.out.println("Visitor day pass: Rs " + bill(van, 120, LocalTime.of(10, 0), true));
    }
}
