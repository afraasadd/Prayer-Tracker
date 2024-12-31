import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {
    public MainMenuGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Prayer Tracker Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome to the Prayer Tracker", JLabel.CENTER);
        mainPanel.add(welcomeLabel);

        JButton adminDashboardButton = new JButton("Admin Dashboard");
        adminDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLoginGUI();
                dispose(); // Close the main menu
            }
        });

        JButton userSignUpButton = new JButton("User Sign-Up");
        userSignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserSignUpGUI();
                dispose(); // Close the main menu
            }
        });

        JButton userLoginButton = new JButton("User Login");
        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserLoginGUI();
                dispose(); // Close the main menu
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainPanel.add(adminDashboardButton);
        mainPanel.add(userSignUpButton);
        mainPanel.add(userLoginButton);
        mainPanel.add(exitButton);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI());
    }
}
