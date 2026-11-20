/*
 * TASK 7 - METHOD OVERRIDING
 *
 * Step 1: Vehicle declares the abstract rate() method.
 * Step 2: Bike and Car override rate() with their own values.
 * Step 3: A Vehicle reference can refer to either object.
 * Step 4: Java selects the correct overridden method at runtime.
 */
import java.util.Objects;

public class Task07 {

    static abstract class Vehicle {

        final String registration;

        Vehicle(String registration) {
            this.registration = registration;
        }

        abstract double rate();

        double fee(int minutes) {
            return ((minutes + 59) / 60) * rate();
        }

        public final String registration() {
            return registration;
        }

        public boolean equals(Object object) {
            return object instanceof Vehicle
                    && registration.equals(
                            ((Vehicle) object).registration);
        }

        public int hashCode() {
            return Objects.hash(registration);
        }

        public String toString() {
            return getClass().getSimpleName()
                    + "(" + registration + ")";
        }
    }

    static class Bike extends Vehicle {

        Bike(String registration) {
            super(registration);
        }

        double rate() {
            return 10;
        }
    }

    static class Car extends Vehicle {

        Car(String registration) {
            super(registration);
        }

        double rate() {
            return 30;
        }
    }

    public static void main(String[] args) {

        Vehicle bike = new Bike("TN01A");
        Vehicle car = new Car("TN01A");

        System.out.println(bike + " fee: " + bike.fee(90));
        System.out.println(car + " fee: " + car.fee(90));

        System.out.println(
                "Same registration: " + bike.equals(car));
    }
}
