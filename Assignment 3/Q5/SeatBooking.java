import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;

    SeatBooking(int seats) { booked = new boolean[seats]; }

    public boolean bookUnsafe(int seat, CyclicBarrier barrier)
            throws InterruptedException, BrokenBarrierException {
        if (booked[seat]) return false;
        barrier.await();
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    public synchronized boolean book(int seat) {
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    public boolean bookWithBlock(int seat) {
        synchronized (this) {
            if (booked[seat]) return false;
            booked[seat] = true;
            bookingsMade++;
            return true;
        }
    }

    public int getBookingsMade() {
        synchronized (this) { return bookingsMade; }
    }

    private static int runUnsafeCompetition() throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        CountDownLatch ready = new CountDownLatch(100);
        CountDownLatch start = new CountDownLatch(1);
        CyclicBarrier[] barriers = new CyclicBarrier[10];
        for (int i = 0; i < barriers.length; i++) barriers[i] = new CyclicBarrier(10);
        Thread[] users = new Thread[100];
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> {
                ready.countDown();
                try {
                    start.await();
                    screen.bookUnsafe(seat, barriers[seat]);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new IllegalStateException(e);
                }
            });
            users[i].start();
        }
        ready.await();
        start.countDown();
        for (Thread user : users) user.join();
        return screen.getBookingsMade();
    }

    private static int runCompetition(boolean useBlock) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        CountDownLatch ready = new CountDownLatch(100);
        CountDownLatch start = new CountDownLatch(1);
        Thread[] users = new Thread[100];
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> {
                ready.countDown();
                try { start.await(); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                if (useBlock) screen.bookWithBlock(seat);
                else screen.book(seat);
            });
            users[i].start();
        }
        ready.await();
        start.countDown();
        for (Thread user : users) user.join();
        return screen.getBookingsMade();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Q5 - Cinema seat booking synchronization");
        System.out.println("Unsynchronized seats sold (race demonstration): " + runUnsafeCompetition());
        System.out.println("Synchronized method seats sold: " + runCompetition(false));
        System.out.println("Synchronized block seats sold: " + runCompetition(true));
        System.out.println("Expected maximum seats sold: 10");
    }
}