import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Attendance {
    static class BrokenStudent {
        final String regNo;
        final String name;

        BrokenStudent(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof BrokenStudent)) return false;
            BrokenStudent other = (BrokenStudent) object;
            return regNo.equals(other.regNo);
        }
        // Deliberately missing hashCode(): this class violates the contract.
    }

    static class Student {
        final String regNo;
        final String name;

        Student(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Student)) return false;
            Student other = (Student) object;
            return regNo.equals(other.regNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(regNo);
        }

        @Override
        public String toString() {
            return regNo + " " + name;
        }
    }

    public static void main(String[] args) {
        System.out.println("Q3 - hashCode and equals contract");
        Set<BrokenStudent> broken = new HashSet<>();
        broken.add(new BrokenStudent("192311001", "Arun"));
        broken.add(new BrokenStudent("192311002", "Divya"));
        broken.add(new BrokenStudent("192311001", "Arun"));
        System.out.println("Broken class set size (may show duplicate): " + broken.size());

        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));
        System.out.println("Corrected unique students: " + present.size());
        System.out.println("Students: " + present);
        System.out.println("Use immutable fields in equals/hashCode while an object is stored in a hash set.");
    }
}