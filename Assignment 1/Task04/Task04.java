/*
 * TASK 4 – CLASSES, OBJECTS AND CONSTRUCTORS
 *
 * Step 1: Ticket stores an ID, registration number and parking duration.
 * Step 2: The no-argument constructor chains to the main constructor using
 *         this(...), supplying default values.
 * Step 3: The two-argument constructor also chains to the main constructor.
 * Step 4: The main constructor validates all values before assigning fields.
 * Step 5: The copy constructor accepts an existing Ticket and copies its
 *         data by calling another constructor.
 * Step 6: main() creates an original ticket and a copy, then prints both.
 */
public class Task04{static class Ticket{final long id;final String reg;final int minutes;Ticket(){this(1,"UNKNOWN",0);}Ticket(long i,String r){this(i,r,0);}Ticket(long i,String r,int m){if(i<1||r==null||r.isBlank()||m<0)throw new IllegalArgumentException();id=i;reg=r;minutes=m;}Ticket(Ticket t){this(t.id,t.reg,t.minutes);}public String toString(){return id+" "+reg+" "+minutes+" min";}}public static void main(String[]x){Ticket a=new Ticket(101,"TN01AB1111",90);System.out.println("Original: "+a);System.out.println("Copy: "+new Ticket(a));}}