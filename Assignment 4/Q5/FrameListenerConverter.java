import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FrameListenerConverter extends JFrame implements ActionListener {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");
    private final JButton convert = new JButton("Convert");

    public FrameListenerConverter() {
        super("Converter - Frame implements ActionListener");
        setLayout(new FlowLayout());
        add(amount);
        add(target);
        add(convert);
        add(result);
        convert.addActionListener(this);
        amount.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
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
        SwingUtilities.invokeLater(() -> new FrameListenerConverter().setVisible(true));
    }
}