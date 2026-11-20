import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawPad extends JFrame {
    private final List<Point> points = new ArrayList<>();
    private final JLabel status = new JLabel("Clicks: 0");
    private final JPanel canvas = new JPanel() {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            for (Point point : points) {
                graphics.fillOval(point.x - 3, point.y - 3, 6, 6);
            }
        }
    };

    public DrawPad() {
        super("Draw Pad");
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                points.add(event.getPoint());
                status.setText("Clicks: " + points.size());
                canvas.repaint();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                dispose();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 360);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawPad().setVisible(true));
    }
}