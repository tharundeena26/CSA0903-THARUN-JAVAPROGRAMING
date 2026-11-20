import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class NamedInnerConverter extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");

    private class ConvertHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            convertAmount();
        }
    }

    public NamedInnerConverter() {
        super("Converter - Named Inner Class");
        setLayout(new FlowLayout());
        JButton convert = new JButton("Convert");
        add(amount);
        add(target);
        add(convert);
        add(result);
        ConvertHandler handler = new ConvertHandler();
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
        SwingUtilities.invokeLater(() -> new NamedInnerConverter().setVisible(true));
    }
}