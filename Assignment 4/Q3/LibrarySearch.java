import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class LibrarySearch extends JFrame {
    private final JTextField query = new JTextField(22);
    private final JComboBox<String> category = new JComboBox<>(
            new String[]{"All", "Books", "Journals", "Theses"});
    private final DefaultListModel<String> results = new DefaultListModel<>();
    private final JList<String> resultList = new JList<>(results);
    private final JLabel status = new JLabel("Enter a title and press Search");

    public LibrarySearch() {
        super("Campus Library Search");
        setLayout(new BorderLayout(8, 8));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search"));
        searchPanel.add(query);
        searchPanel.add(category);
        JButton search = new JButton("Search");
        searchPanel.add(search);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(new JScrollPane(resultList), BorderLayout.CENTER);

        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton previous = new JButton("Prev");
        JButton next = new JButton("Next");
        JButton reserve = new JButton("Reserve");
        navigation.add(previous);
        navigation.add(next);
        navigation.add(reserve);

        JPanel bottom = new JPanel(new GridLayout(2, 1));
        bottom.add(navigation);
        bottom.add(status);

        add(searchPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        search.addActionListener(event -> searchBooks());
        previous.addActionListener(event -> status.setText("Previous page requested"));
        next.addActionListener(event -> status.setText("Next page requested"));
        reserve.addActionListener(event -> {
            String selected = resultList.getSelectedValue();
            status.setText(selected == null ? "Select a result first"
                    : "Reservation requested: " + selected);
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 480);
        setLocationRelativeTo(null);
    }

    private void searchBooks() {
        results.clear();
        String text = query.getText().trim();
        String type = (String) category.getSelectedItem();
        if (text.isEmpty()) {
            status.setText("Enter a search term");
            return;
        }
        results.addElement(type + ": " + text + " - Java Programming");
        results.addElement(type + ": " + text + " - Data Structures");
        results.addElement(type + ": " + text + " - Database Systems");
        status.setText("3 sample results (connect library catalogue for live data)");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearch().setVisible(true));
    }
}