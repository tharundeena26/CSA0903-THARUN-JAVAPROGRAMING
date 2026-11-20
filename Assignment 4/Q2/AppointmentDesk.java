import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class AppointmentDesk extends JFrame {
    private final JTextField patientId = new JTextField(12);
    private final JComboBox<String> department = new JComboBox<>(
            new String[]{"Cardiology", "Neurology", "Orthopedics"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> slot = new JComboBox<>(
            new String[]{"09:00", "10:00", "11:00", "14:00"});
    private final JButton book = new JButton("Book appointment");
    private boolean bookingInProgress = false;

    private final Map<String, List<String>> doctors = Map.of(
            "Cardiology", List.of("Dr. Arun", "Dr. Meena"),
            "Neurology", List.of("Dr. Kumar", "Dr. Priya"),
            "Orthopedics", List.of("Dr. Ravi", "Dr. Latha"));

    public AppointmentDesk() {
        super("Hospital Outpatient Appointment Desk");
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.add(new JLabel("Patient ID"));
        form.add(patientId);
        form.add(new JLabel("Department"));
        form.add(department);
        form.add(new JLabel("Doctor"));
        form.add(doctor);
        form.add(new JLabel("Time slot"));
        form.add(slot);
        form.add(new JLabel("Action"));
        form.add(book);
        add(form, BorderLayout.CENTER);

        department.addActionListener(event -> refreshDoctors());
        book.addActionListener(event -> bookAppointment());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                if (bookingInProgress) {
                    JOptionPane.showMessageDialog(AppointmentDesk.this,
                            "A booking is in progress. Please wait.");
                    return;
                }
                int answer = JOptionPane.showConfirmDialog(AppointmentDesk.this,
                        "Close the appointment desk?", "Confirm close",
                        JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) dispose();
            }
        });

        refreshDoctors();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(480, 300);
        setLocationRelativeTo(null);
    }

    private void refreshDoctors() {
        doctor.removeAllItems();
        String selectedDepartment = (String) department.getSelectedItem();
        if (selectedDepartment != null) {
            for (String name : doctors.get(selectedDepartment)) doctor.addItem(name);
        }
    }

    private void bookAppointment() {
        String id = patientId.getText().trim();
        if (id.isEmpty() || doctor.getSelectedItem() == null
                || slot.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Complete all appointment fields.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        bookingInProgress = true;
        book.setEnabled(false);
        String message = "Patient " + id + " booked with " + doctor.getSelectedItem()
                + " at " + slot.getSelectedItem();
        JOptionPane.showMessageDialog(this, message, "Appointment confirmed",
                JOptionPane.INFORMATION_MESSAGE);
        bookingInProgress = false;
        book.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentDesk().setVisible(true));
    }
}