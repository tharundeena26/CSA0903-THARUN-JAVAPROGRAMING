/*
 * TASK 7 – METHOD OVERRIDING
 *
 * Step 1: Vehicle declares rate() as an abstract method and provides the
 *         common fee calculation.
 * Step 2: Bike overrides rate() with the two-wheeler hourly rate.
 * Step 3: Car overrides rate() with the car hourly rate.
 * Step 4: Create both objects using Vehicle references (upcasting).
 * Step 5: Call fee() through each reference. Java dynamically invokes the
 *         correct overridden rate() for the actual object.
 * Step 6: equals() compares registration numbers; hashCode() follows the
 *         same identity rule, and toString() prints the concrete class name.
 */
import java.util.Objects;public class Task07{static abstract class Vehicle{final String reg;Vehicle(String r){reg=r;}abstract double rate();double fee(int m){return ((m+59)/60)*rate();}public final String registration(){return reg;}public boolean equals(Object o){return o instanceof Vehicle&&reg.equals(((Vehicle)o).reg);}public int hashCode(){return Objects.hash(reg);}public String toString(){return getClass().getSimpleName()+"("+reg+")";}}static class Bike extends Vehicle{Bike(String r){super(r);}double rate(){return 10;}}static class Car extends Vehicle{Car(String r){super(r);}double rate(){return 30;}}public static void main(String[]x){Vehicle a=new Bike("TN01A"),b=new Car("TN01A");System.out.println(a+" fee: "+a.fee(90));System.out.println(b+" fee: "+b.fee(90));System.out.println("Same registration: "+a.equals(b));}}