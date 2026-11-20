/*
 * TASK 6 – INHERITANCE HIERARCHY
 *
 * Step 1: Vehicle is abstract because it defines common vehicle state and
 *         requires each concrete vehicle to provide a rate().
 * Step 2: Car extends Vehicle and calls super(r) to initialize registration.
 * Step 3: ElectricCar extends Car, reusing the car's parking rate.
 * Step 4: ElectricCar adds chargingFee() as extra EV-specific behaviour.
 * Step 5: Create an ElectricCar in main().
 * Step 6: Observe constructor order: Vehicle, then Car, then ElectricCar.
 * Step 7: Print its inherited hourly rate and its charging cost.
 */
public class Task06{static abstract class Vehicle{final String reg;Vehicle(String r){reg=r;System.out.println("Vehicle constructor");}abstract double rate();}static class Car extends Vehicle{Car(String r){super(r);System.out.println("Car constructor");}double rate(){return 30;}}static class ElectricCar extends Car{ElectricCar(String r){super(r);System.out.println("ElectricCar constructor");}double chargingFee(int k){return k*12;}}public static void main(String[]x){ElectricCar e=new ElectricCar("TN01EV1234");System.out.println("Rate: "+e.rate());System.out.println("Charging: "+e.chargingFee(5));}}