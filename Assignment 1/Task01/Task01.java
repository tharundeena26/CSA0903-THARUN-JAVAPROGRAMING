/*
 * TASK 1 – PROBLEM ANALYSIS AND OBJECTS
 *
 * Step 1: Define a Vehicle class so a vehicle's registration and type
 *         are stored together in one object.
 * Step 2: Define a Slot class to represent one parking space.
 * Step 3: In park(), check whether the slot is already occupied.
 * Step 4: If it is free, store the Vehicle object and return true.
 *         Otherwise return false without replacing the first vehicle.
 * Step 5: release() clears the occupant reference and makes the slot free.
 * Step 6: main() tests successful parking, rejection of a second vehicle,
 *         and release of the slot.
 */
public class Task01 { static class Vehicle{String reg,type;Vehicle(String r,String t){reg=r;type=t;}} static class Slot{Vehicle occupant;boolean park(Vehicle v){if(occupant!=null)return false;occupant=v;return true;}void release(){occupant=null;}} public static void main(String[]x){Slot s=new Slot();System.out.println("First vehicle: "+s.park(new Vehicle("TN01AB1111","Car")));System.out.println("Second vehicle: "+s.park(new Vehicle("TN02CD2222","Bike")));s.release();System.out.println("Slot free: "+(s.occupant==null));}}