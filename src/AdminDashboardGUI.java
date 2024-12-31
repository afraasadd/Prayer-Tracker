import javax.swing.*;
import java.awt.*;

public class AdminDashboardGUI extends JFrame {

    public AdminDashboardGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Welcome to the Admin Dashboard", JLabel.CENTER);
        panel.add(label, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboardGUI());
    }
}