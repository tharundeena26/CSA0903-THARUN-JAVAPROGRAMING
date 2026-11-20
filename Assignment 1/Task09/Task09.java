/*
 * TASK 9 - ABSTRACTION AND MULTIPLE INTERFACES
 *
 * Step 1: Chargeable defines charging behaviour.
 * Step 2: Payable defines the parking fee contract.
 * Step 3: Vehicle is an abstract class for shared vehicle data.
 * Step 4: EV extends Vehicle and implements both interfaces.
 */
public class Task09 {

    interface Chargeable {

        double chargingFee(int units);

        default boolean supportsCharging() {
            return true;
        }

        static double rate() {
            return 12;
        }
    }

    interface Payable {
        double fee(int minutes);
    }

    static abstract class Vehicle {

        final String registration;

        Vehicle(String registration) {
            this.registration = registration;
        }

        abstract String type();
    }

    static class EV extends Vehicle implements Chargeable, Payable {

        EV(String registration) {
            super(registration);
        }

        String type() {
            return "ElectricCar";
        }

        public double chargingFee(int units) {
            return units * Chargeable.rate();
        }

        public double fee(int minutes) {
            return ((minutes + 59) / 60) * 30;
        }
    }

    public static void main(String[] args) {

        EV vehicle = new EV("TN01EV");

        System.out.println(vehicle.type());
        System.out.println("Parking: " + vehicle.fee(130));
        System.out.println("Charging: " + vehicle.chargingFee(10));
        System.out.println(
                "Supports charging: " + vehicle.supportsCharging());
    }
}
