import java.util.ArrayList;
import java.util.List;

class ScannerStation extends Thread {
    private final int scripts;
    private int scanned;

    ScannerStation(String name, int scripts) {
        super(name);
        this.scripts = scripts;
    }

    @Override
    public void run() {
        scanBatch();
    }

    private void scanBatch() {
        try {
            for (int i = 0; i < scripts; i++) {
                Thread.sleep(15);
                scanned++;
            }
            System.out.println(getName() + " scanned " + scanned + " scripts");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(getName() + " was interrupted");
        }
    }

    int getScanned() { return scanned; }
}

class RunnableStation implements Runnable {
    private final String stationName;
    private final int scripts;
    private int scanned;

    RunnableStation(String stationName, int scripts) {
        this.stationName = stationName;
        this.scripts = scripts;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < scripts; i++) {
                Thread.sleep(10);
                scanned++;
            }
            System.out.println(stationName + " scanned " + scanned + " scripts (Runnable)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    int getScanned() { return scanned; }
}

public class ScannerStations {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Q4 - Scanner station thread life cycle");
        ScannerStation[] stations = new ScannerStation[4];
        for (int i = 0; i < stations.length; i++) {
            stations[i] = new ScannerStation("Station-" + (i + 1), 5 + i);
            stations[i].start();
            System.out.println(stations[i].getName() + " alive after start: " + stations[i].isAlive());
        }
        int total = 0;
        for (ScannerStation station : stations) {
            station.join();
            total += station.getScanned();
        }
        System.out.println("Total from Thread subclasses: " + total);

        List<Thread> runnableThreads = new ArrayList<>();
        List<RunnableStation> jobs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            RunnableStation job = new RunnableStation("Runnable-" + (i + 1), 3);
            Thread thread = new Thread(job);
            jobs.add(job);
            runnableThreads.add(thread);
            thread.start();
        }
        for (Thread thread : runnableThreads) thread.join();
        int runnableTotal = jobs.stream().mapToInt(RunnableStation::getScanned).sum();
        System.out.println("Total from Runnable stations: " + runnableTotal);
        System.out.println("All scanner threads finished: " + runnableThreads.stream().noneMatch(Thread::isAlive));
    }
}