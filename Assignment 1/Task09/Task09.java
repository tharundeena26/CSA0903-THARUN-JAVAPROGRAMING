/*
 * TASK 9 – ABSTRACTION AND MULTIPLE INTERFACES
 *
 * Step 1: Chargeable specifies the chargingFee() method and supplies a
 *         default supportsCharging() implementation.
 * Step 2: Its static rate() method provides a shared charging-rate value.
 * Step 3: Payable defines the parking fee contract.
 * Step 4: Abstract Vehicle stores common registration data and requires
 *         concrete vehicles to provide a type.
 * Step 5: EV extends Vehicle and implements both Chargeable and Payable.
 * Step 6: Implement every required interface method in EV.
 * Step 7: main() calls the parking, charging and default interface methods.
 */
public class Task09{interface Chargeable{double chargingFee(int k);default boolean supportsCharging(){return true;}static double rate(){return 12;}}interface Payable{double fee(int m);}static abstract class Vehicle{final String reg;Vehicle(String r){reg=r;}abstract String type();}static class EV extends Vehicle implements Chargeable,Payable{EV(String r){super(r);}String type(){return "ElectricCar";}public double chargingFee(int k){return k*Chargeable.rate();}public double fee(int m){return ((m+59)/60)*30;}}public static void main(String[]x){EV e=new EV("TN01EV");System.out.println(e.type());System.out.println("Parking: "+e.fee(130));System.out.println("Charging: "+e.chargingFee(10));System.out.println("Supports charging: "+e.supportsCharging());}}