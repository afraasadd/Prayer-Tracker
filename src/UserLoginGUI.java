import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class UserLoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserLoginGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Login");
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

        User user = User.userLogin(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful. Welcome, " + username + "!");
            new UserDashboardGUI(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserLoginGUI());
    }
}