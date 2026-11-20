import java.util.Comparator;
import java.util.PriorityQueue;

class Patient {
    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Triage {
    private long counter = 0;
    private final PriorityQueue<Patient> queue = new PriorityQueue<>(
            Comparator.comparingInt((Patient patient) -> patient.severity)
                    .thenComparingLong(patient -> patient.arrival));

    void admit(String name, int severity) {
        if (severity < 1 || severity > 5) {
            throw new IllegalArgumentException("Severity must be from 1 to 5");
        }
        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    public static void main(String[] args) {
        Triage emergencyRoom = new Triage();
        emergencyRoom.admit("Ravi", 3);
        emergencyRoom.admit("Meena", 1);
        emergencyRoom.admit("John", 3);
        emergencyRoom.admit("Asha", 5);
        emergencyRoom.admit("Kumar", 1);
        emergencyRoom.admit("Lata", 2);
        emergencyRoom.admit("Priya", 4);
        emergencyRoom.admit("Sanjay", 2);

        System.out.println("Q6 - Hospital emergency triage");
        StringBuilder order = new StringBuilder();
        Patient patient;
        while ((patient = emergencyRoom.next()) != null) {
            order.append(patient).append(' ');
        }
        System.out.println(order.toString().trim());
        System.out.println("Lower severity number is treated first; arrival counter preserves FIFO ties.");
    }
}