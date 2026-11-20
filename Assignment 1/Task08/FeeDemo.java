/*
 * TASK 8 – RUNTIME POLYMORPHISM AND INTERFACES
 *
 * Step 1: Chargeable defines the chargingFee() contract.
 * Step 2: Abstract Vehicle stores a registration number and defines the
 *         common calculateFee() method.
 * Step 3: TwoWheeler and Car override ratePerHour() with different rates.
 * Step 4: ElectricCar extends Car and implements Chargeable.
 * Step 5: Put different concrete vehicles into one Vehicle array.
 * Step 6: Loop through the array and call calculateFee() on each reference.
 *         Runtime polymorphism selects the correct ratePerHour() method.
 * Step 7: Use instanceof to apply the additional charging fee only to EVs.
 * Step 8: Compare two vehicles using their registration numbers.
 */
interface Chargeable{double chargingFee(int kWh);}abstract class Vehicle{private final String regNo;protected Vehicle(String r){regNo=r;}public String getRegNo(){return regNo;}protected abstract double ratePerHour();public double calculateFee(int m){if(m<0)throw new IllegalArgumentException("negative duration");return ((m+59)/60)*ratePerHour();}public boolean equals(Object o){return o instanceof Vehicle&&regNo.equals(((Vehicle)o).regNo);}public int hashCode(){return regNo.hashCode();}}class TwoWheeler extends Vehicle{TwoWheeler(String r){super(r);}protected double ratePerHour(){return 10;}}class Car extends Vehicle{Car(String r){super(r);}protected double ratePerHour(){return 30;}}class ElectricCar extends Car implements Chargeable{ElectricCar(String r){super(r);}public double chargingFee(int k){return 12.0*k;}}public class FeeDemo{public static void main(String[]x){Vehicle[]a={new TwoWheeler("TN01A1"),new Car("TN02B2"),new ElectricCar("TN03C3")};for(Vehicle v:a){double f=v.calculateFee(130);if(v instanceof Chargeable c)f+=c.chargingFee(10);System.out.println(v.getClass().getSimpleName()+" "+v.getRegNo()+" -> Rs "+f);}System.out.println(new Car("TN02B2").equals(new ElectricCar("TN02B2")));}}