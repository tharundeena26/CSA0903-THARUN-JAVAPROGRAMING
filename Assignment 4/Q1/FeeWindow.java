import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class FeeWindow extends JFrame {
    private final JTextField regNo = new JTextField(16);
    private final JTextField amount = new JTextField(16);
    private final JLabel status = new JLabel("Enter fee details");
    private final FeeService service = new FeeService();

    public FeeWindow() {
        super("Student Fee Payment");
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.add(new JLabel("Registration number"));
        form.add(regNo);
        form.add(new JLabel("Amount (Rs.)"));
        form.add(amount);

        JButton pay = new JButton("Pay");
        JButton clear = new JButton("Clear");
        JButton receipt = new JButton("Receipt");
        JPanel actions = new JPanel();
        actions.add(pay);
        actions.add(clear);
        actions.add(receipt);

        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
        add(status, BorderLayout.NORTH);

        pay.addActionListener(event -> processPayment());
        clear.addActionListener(event -> clearForm());
        receipt.addActionListener(event ->
                JOptionPane.showMessageDialog(this, status.getText(), "Receipt",
                        JOptionPane.INFORMATION_MESSAGE));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(440, 220);
        setLocationRelativeTo(null);
    }

    private void processPayment() {
        String registration = regNo.getText();
        String paymentAmount = amount.getText();
        setActionsEnabled(false);
        status.setText("Processing payment...");

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return service.payFee(registration, paymentAmount);
            }

            @Override
            protected void done() {
                setActionsEnabled(true);
                try {
                    status.setText(get());
                    JOptionPane.showMessageDialog(FeeWindow.this, status.getText());
                } catch (Exception e) {
                    status.setText("Payment failed");
                    JOptionPane.showMessageDialog(FeeWindow.this,
                            e.getCause() == null ? e.getMessage() : e.getCause().getMessage(),
                            "Payment error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void clearForm() {
        regNo.setText("");
        amount.setText("");
        status.setText("Form cleared");
    }

    private void setActionsEnabled(boolean enabled) {
        for (java.awt.Component component : getContentPane().getComponents()) {
            if (component instanceof JPanel panel) {
                for (java.awt.Component child : panel.getComponents()) {
                    if (child instanceof JButton button) button.setEnabled(enabled);
                }
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() ->
                new FeeWindow().setVisible(true));
    }
}