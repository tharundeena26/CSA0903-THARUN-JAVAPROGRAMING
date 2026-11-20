/*
 * TASK 5 – STATIC MEMBERS AND OBJECT LIFE CYCLE
 *
 * Step 1: next is static, so all Ticket objects share one ticket sequence.
 * Step 2: active is static, so it counts all currently open tickets.
 * Step 3: Each Ticket constructor takes the next ID and increments active.
 * Step 4: close() checks open before decrementing, preventing a double count.
 * Step 5: activeCount() is static because it reads class-wide data.
 * Step 6: main() creates two tickets, closes one, and prints the new count.
 */
public class Task05{static class Ticket{private static long next=1;private static int active=0;final long id;boolean open=true;Ticket(){id=next++;active++;}void close(){if(open){open=false;active--;}}static int activeCount(){return active;}}public static void main(String[]x){Ticket a=new Ticket(),b=new Ticket();System.out.println("IDs: "+a.id+", "+b.id);System.out.println("Active: "+Ticket.activeCount());a.close();System.out.println("After exit: "+Ticket.activeCount());}}