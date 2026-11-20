/*
 * TASK 3 – CONTROL LOGIC AND PARKING ALLOCATION
 *
 * Step 1: The constructor receives the number of slots on every floor.
 * Step 2: Create one inner array per floor, allowing unequal capacities.
 * Step 3: allocate() scans floors with the outer loop and slots with the
 *         inner loop.
 * Step 4: When a null slot is found, store the registration number and
 *         immediately return the floor/slot coordinates.
 * Step 5: If every slot is occupied, return null to indicate a full facility.
 * Step 6: release() validates both indexes and confirms that the slot is
 *         occupied before clearing it.
 * Step 7: main() fills the facility, tests the full condition, tests invalid
 *         and repeated releases, and allocates the freed slot again.
 */
public class ParkingFacility{private final String[][]slots;public ParkingFacility(int[]n){slots=new String[n.length][];for(int f=0;f<n.length;f++)slots[f]=new String[n[f]];}public int[]allocate(String v){for(int f=0;f<slots.length;f++)for(int s=0;s<slots[f].length;s++)if(slots[f][s]==null){slots[f][s]=v;return new int[]{f,s};}return null;}public boolean release(int f,int s){if(f<0||f>=slots.length||s<0||s>=slots[f].length||slots[f][s]==null)return false;slots[f][s]=null;return true;}public static void main(String[]x){ParkingFacility p=new ParkingFacility(new int[]{2,1});System.out.println(java.util.Arrays.toString(p.allocate("TN01AB1111")));System.out.println(java.util.Arrays.toString(p.allocate("TN02CD2222")));System.out.println(java.util.Arrays.toString(p.allocate("TN03EF3333")));System.out.println(java.util.Arrays.toString(p.allocate("TN04GH4444")));System.out.println(p.release(0,1)+" "+p.release(0,1)+" "+p.release(5,0));System.out.println(java.util.Arrays.toString(p.allocate("TN04GH4444")));}}