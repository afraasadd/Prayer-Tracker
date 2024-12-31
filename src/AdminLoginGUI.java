import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (User.ADMIN_USERNAME.equals(username) && User.ADMIN_PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(this, "Admin login successful!");
            new AdminDashboardGUI();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Access denied.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminLoginGUI());
    }
}