import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSignUpGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserSignUpGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Sign-Up");
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

        JButton signUpButton = new JButton("Sign-Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
        panel.add(signUpButton);

        add(panel);
        setVisible(true);
    }

    private void handleSignUp() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (User.isUsernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Try a different one.");
        } else {
            User.userSignUp(username, password);
            JOptionPane.showMessageDialog(this, "Sign-up request submitted. Please wait for admin approval.");
            dispose();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserSignUpGUI());
    }
}