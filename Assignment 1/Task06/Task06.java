/*
 * TASK 6 - INHERITANCE HIERARCHY
 *
 * Step 1: Vehicle is an abstract parent class.
 * Step 2: Car extends Vehicle and defines the parking rate.
 * Step 3: ElectricCar extends Car and adds a charging fee method.
 * Step 4: Observe the constructor execution order.
 */
public class Task06 {

    static abstract class Vehicle {

        final String registration;

        Vehicle(String registration) {
            this.registration = registration;
            System.out.println("Vehicle constructor");
        }

        abstract double rate();
    }

    static class Car extends Vehicle {

        Car(String registration) {
            super(registration);
            System.out.println("Car constructor");
        }

        double rate() {
            return 30;
        }
    }

    static class ElectricCar extends Car {

        ElectricCar(String registration) {
            super(registration);
            System.out.println("ElectricCar constructor");
        }

        double chargingFee(int units) {
            return units * 12;
        }
    }

    public static void main(String[] args) {

        ElectricCar vehicle =
                new ElectricCar("TN01EV1234");

        System.out.println("Rate: " + vehicle.rate());
        System.out.println("Charging: " + vehicle.chargingFee(5));
    }
}
