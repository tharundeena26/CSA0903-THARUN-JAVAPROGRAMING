class Wallet {
    final int id;
    private int balance;

    Wallet(int id, int balance) { this.id = id; this.balance = balance; }
    int getBalance() { return balance; }
    void debit(int amount) { balance -= amount; }
    void credit(int amount) { balance += amount; }
}

class WalletTransfers {
    // Opposite-direction calls to this method can deadlock if threads acquire locks in reverse order.
    static void transferWithoutOrdering(Wallet from, Wallet to, int amount) {
        synchronized (from) {
            synchronized (to) {
                from.debit(amount);
                to.credit(amount);
            }
        }
    }

    // Always acquire the lower-ID wallet first to prevent circular wait.
    static void transferSafely(Wallet from, Wallet to, int amount) {
        Wallet first = from.id < to.id ? from : to;
        Wallet second = from.id < to.id ? to : from;
        synchronized (first) {
            synchronized (second) {
                if (from.getBalance() < amount) {
                    throw new IllegalArgumentException("Insufficient wallet balance");
                }
                from.debit(amount);
                to.credit(amount);
            }
        }
    }
}

public class TokenCounter {
    private final int[] buffer = new int[5];
    private int count = 0, in = 0, out = 0;

    public synchronized void put(int token) throws InterruptedException {
        while (count == buffer.length) wait();
        buffer[in] = token;
        in = (in + 1) % buffer.length;
        count++;
        notifyAll();
    }

    public synchronized int take() throws InterruptedException {
        while (count == 0) wait();
        int token = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        notifyAll();
        return token;
    }

    public static void main(String[] args) throws InterruptedException {
        TokenCounter counter = new TokenCounter();
        int[] collected = new int[2];
        Thread cook = new Thread(() -> {
            try {
                for (int token = 1; token <= 20; token++) counter.put(token);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread[] students = new Thread[2];
        for (int s = 0; s < 2; s++) {
            final int id = s;
            students[s] = new Thread(() -> {
                try {
                    for (int k = 0; k < 10; k++) collected[id] += counter.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        cook.start();
        for (Thread student : students) student.start();
        cook.join();
        for (Thread student : students) student.join();
        System.out.println("Sum of tokens collected: "
                + (collected[0] + collected[1]) + " (expected 210)");

        Wallet first = new Wallet(1, 1000);
        Wallet second = new Wallet(2, 500);
        WalletTransfers.transferSafely(first, second, 200);
        System.out.println("Wallet balances after ordered transfer: "
                + first.getBalance() + " and " + second.getBalance());
    }
}