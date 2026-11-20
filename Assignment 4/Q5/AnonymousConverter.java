import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AnonymousConverter extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");

    public AnonymousConverter() {
        super("Converter - Anonymous Inner Class");
        setLayout(new FlowLayout());
        JButton convert = new JButton("Convert");
        add(amount);
        add(target);
        add(convert);
        add(result);

        ActionListener handler = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                convertAmount();
            }
        };
        convert.addActionListener(handler);
        amount.addActionListener(handler);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent event) {
                dispose();
            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

    private void convertAmount() {
        try {
            double value = Double.parseDouble(amount.getText().trim());
            String currency = (String) target.getSelectedItem();
            double rate = currency.equals("USD") ? 0.012 : 0.011;
            result.setText(String.format("%.2f %s", value * rate, currency));
        } catch (NumberFormatException exception) {
            result.setText("Invalid amount");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnonymousConverter().setVisible(true));
    }
}