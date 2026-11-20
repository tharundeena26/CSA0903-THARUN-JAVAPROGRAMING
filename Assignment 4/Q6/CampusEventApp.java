import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CampusEventApp extends JFrame {
    private final JTextField studentId = new JTextField(12);
    private final JTextField studentName = new JTextField(12);
    private final JComboBox<EventItem> eventChoice = new JComboBox<>();
    private final JComboBox<EventItem> filterChoice = new JComboBox<>();
    private final DefaultListModel<String[]> rows = new DefaultListModel<>();
    private final JTable table = new JTable(new javax.swing.table.DefaultTableModel(
            new Object[]{"Student ID", "Student Name", "Event", "Registered At"}, 0));
    private final JLabel status = new JLabel("Ready");
    private final String url = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/campus_events");
    private final String user = System.getenv().getOrDefault("DB_USER", "root");
    private final String password = System.getenv().getOrDefault("DB_PASSWORD", "");

    private record EventItem(int id, String name) {
        @Override public String toString() { return name; }
    }

    public CampusEventApp() {
        super("Campus Event Registration");
        setLayout(new BorderLayout(8, 8));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem refreshItem = new JMenuItem("Refresh");
        JMenuItem exportItem = new JMenuItem("Export CSV");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(refreshItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel form = new JPanel(new GridLayout(2, 4, 6, 6));
        form.add(new JLabel("Student ID"));
        form.add(studentId);
        form.add(new JLabel("Student name"));
        form.add(studentName);
        form.add(new JLabel("Event"));
        form.add(eventChoice);
        JButton register = new JButton("Register");
        form.add(register);
        form.add(new JLabel("Filter event"));
        form.add(filterChoice);

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.add(form, BorderLayout.CENTER);
        JButton applyFilter = new JButton("Apply filter");
        top.add(applyFilter, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        register.addActionListener(event -> registerStudent());
        applyFilter.addActionListener(event -> loadRegistrations());
        refreshItem.addActionListener(event -> loadEvents());
        exportItem.addActionListener(event -> exportCsv());
        exitItem.addActionListener(event -> dispose());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 480);
        setLocationRelativeTo(null);
        loadEvents();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void loadEvents() {
        status.setText("Loading events...");
        new SwingWorker<List<EventItem>, Void>() {
            @Override protected List<EventItem> doInBackground() throws Exception {
                List<EventItem> events = new ArrayList<>();
                try (Connection connection = connect();
                     Statement statement = connection.createStatement();
                     ResultSet result = statement.executeQuery(
                             "SELECT event_id, event_name FROM events ORDER BY event_name")) {
                    while (result.next()) {
                        events.add(new EventItem(result.getInt(1), result.getString(2)));
                    }
                }
                return events;
            }

            @Override protected void done() {
                try {
                    eventChoice.removeAllItems();
                    filterChoice.removeAllItems();
                    filterChoice.addItem(new EventItem(0, "All events"));
                    for (EventItem item : get()) {
                        eventChoice.addItem(item);
                        filterChoice.addItem(item);
                    }
                    status.setText("Events loaded");
                    loadRegistrations();
                } catch (Exception exception) {
                    showError(exception);
                }
            }
        }.execute();
    }

    private void registerStudent() {
        String id = studentId.getText().trim();
        String name = studentName.getText().trim();
        EventItem selected = (EventItem) eventChoice.getSelectedItem();
        if (id.isEmpty() || name.isEmpty() || selected == null) {
            JOptionPane.showMessageDialog(this, "Enter student ID, name and event.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        setBusy(true, "Registering...");
        new SwingWorker<Void, Void>() {
            @Override protected Void doInBackground() throws Exception {
                String sql = "INSERT INTO registrations (student_id, student_name, event_id) VALUES (?, ?, ?)";
                try (Connection connection = connect();
                     PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, id);
                    statement.setString(2, name);
                    statement.setInt(3, selected.id());
                    statement.executeUpdate();
                }
                return null;
            }

            @Override protected void done() {
                setBusy(false, "Registration request completed");
                try {
                    get();
                    JOptionPane.showMessageDialog(CampusEventApp.this,
                            "Registration successful.");
                    loadRegistrations();
                } catch (Exception exception) {
                    Throwable cause = exception.getCause();
                    String message = cause == null ? exception.getMessage() : cause.getMessage();
                    if (message != null && message.contains("Duplicate")) {
                        message = "This student is already registered for this event.";
                    }
                    JOptionPane.showMessageDialog(CampusEventApp.this, message,
                            "Registration error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void loadRegistrations() {
        EventItem selected = (EventItem) filterChoice.getSelectedItem();
        setBusy(true, "Loading registrations...");
        new SwingWorker<List<Object[]>, Void>() {
            @Override protected List<Object[]> doInBackground() throws Exception {
                List<Object[]> data = new ArrayList<>();
                String sql = "SELECT r.student_id, r.student_name, e.event_name, r.registered_at "
                        + "FROM registrations r JOIN events e ON r.event_id=e.event_id "
                        + (selected == null || selected.id() == 0 ? "" : "WHERE e.event_id=? ")
                        + "ORDER BY r.registered_at DESC LIMIT 500";
                try (Connection connection = connect();
                     PreparedStatement statement = connection.prepareStatement(sql)) {
                    if (selected != null && selected.id() != 0) {
                        statement.setInt(1, selected.id());
                    }
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            data.add(new Object[]{result.getString(1), result.getString(2),
                                    result.getString(3), result.getTimestamp(4).toString()});
                        }
                    }
                }
                return data;
            }

            @Override protected void done() {
                setBusy(false, "Registration list updated");
                try {
                    javax.swing.table.DefaultTableModel model =
                            (javax.swing.table.DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (Object[] row : get()) model.addRow(row);
                } catch (Exception exception) {
                    showError(exception);
                }
            }
        }.execute();
    }

    private void exportCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("registrations.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (FileWriter writer = new FileWriter(chooser.getSelectedFile())) {
            for (int column = 0; column < table.getColumnCount(); column++) {
                if (column > 0) writer.write(",");
                writer.write(table.getColumnName(column));
            }
            writer.write("\n");
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int column = 0; column < table.getColumnCount(); column++) {
                    if (column > 0) writer.write(",");
                    String value = String.valueOf(table.getValueAt(row, column));
                    writer.write("\"" + value.replace("\"", "\"\"") + "\"");
                }
                writer.write("\n");
            }
            status.setText("CSV exported");
        } catch (IOException exception) {
            showError(exception);
        }
    }

    private void setBusy(boolean busy, String message) {
        status.setText(message);
    }

    private void showError(Exception exception) {
        Throwable cause = exception.getCause();
        JOptionPane.showMessageDialog(this,
                cause == null ? exception.getMessage() : cause.getMessage(),
                "Database error", JOptionPane.ERROR_MESSAGE);
        status.setText("Operation failed");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CampusEventApp().setVisible(true));
    }
}