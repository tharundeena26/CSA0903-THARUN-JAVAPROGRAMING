/*
 * TASK 8 - RUNTIME POLYMORPHISM AND INTERFACES
 *
 * Step 1: Chargeable defines the charging fee method.
 * Step 2: Vehicle stores common registration information.
 * Step 3: TwoWheeler and Car provide different hourly rates.
 * Step 4: ElectricCar extends Car and implements Chargeable.
 * Step 5: Store different vehicle objects in one Vehicle array.
 * Step 6: Call calculateFee() and allow runtime polymorphism to work.
 */
interface Chargeable {
    double chargingFee(int units);
}

abstract class Vehicle {

    private final String registration;

    protected Vehicle(String registration) {
        this.registration = registration;
    }

    public String getRegistration() {
        return registration;
    }

    protected abstract double ratePerHour();

    public double calculateFee(int minutes) {

        if (minutes < 0) {
            throw new IllegalArgumentException("Negative duration");
        }

        return ((minutes + 59) / 60) * ratePerHour();
    }

    public boolean equals(Object object) {
        return object instanceof Vehicle
                && registration.equals(
                        ((Vehicle) object).registration);
    }

    public int hashCode() {
        return registration.hashCode();
    }
}

class TwoWheeler extends Vehicle {

    TwoWheeler(String registration) {
        super(registration);
    }

    protected double ratePerHour() {
        return 10;
    }
}

class Car extends Vehicle {

    Car(String registration) {
        super(registration);
    }

    protected double ratePerHour() {
        return 30;
    }
}

class ElectricCar extends Car implements Chargeable {

    ElectricCar(String registration) {
        super(registration);
    }

    public double chargingFee(int units) {
        return 12.0 * units;
    }
}

public class FeeDemo {

    public static void main(String[] args) {

        Vehicle[] vehicles = {
            new TwoWheeler("TN01A1"),
            new Car("TN02B2"),
            new ElectricCar("TN03C3")
        };

        for (Vehicle vehicle : vehicles) {

            double fee = vehicle.calculateFee(130);

            if (vehicle instanceof Chargeable) {
                Chargeable chargeable = (Chargeable) vehicle;
                fee += chargeable.chargingFee(10);
            }

            System.out.println(
                    vehicle.getClass().getSimpleName()
                    + " " + vehicle.getRegistration()
                    + " -> Rs " + fee);
        }

        System.out.println(
                new Car("TN02B2").equals(
                        new ElectricCar("TN02B2")));
    }
}
