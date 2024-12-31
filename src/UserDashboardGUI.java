import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserDashboardGUI extends JFrame {
    private JTable recordsTable;
    private PrayerHandler prayerHandler;
    private User user;

    public UserDashboardGUI(User user) {
        this.user = user;
        this.prayerHandler = new PrayerHandler();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addRecordButton = new JButton("Add Prayer Record");
        JButton viewRecordsButton = new JButton("View Prayer Records");
        JButton viewMissedPrayersButton = new JButton("View Missed Prayers");
        JButton viewStreakButton = new JButton("View Prayer Streak");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(addRecordButton);
        buttonPanel.add(viewRecordsButton);
        buttonPanel.add(viewMissedPrayersButton);
        buttonPanel.add(viewStreakButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Create table for displaying records
        String[] columnNames = {"Prayer Name", "Date", "Status", "Khushu Rating", "Notes"};
        recordsTable = new JTable(new Object[][]{}, columnNames);
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Add action listeners
        addRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPrayerRecord();
            }
        });

        viewRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPrayerRecords();
            }
        });

        viewMissedPrayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMissedPrayers();
            }
        });

        viewStreakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPrayerStreak();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenuGUI();
            }
        });
    }

    private void addPrayerRecord() {
        JTextField prayerNameField = new JTextField();
        JTextField prayerDateField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField khushuRatingField = new JTextField();
        JTextField notesField = new JTextField();

        Object[] message = {
                "Prayer Name:", prayerNameField,
                "Prayer Date (yyyy-MM-dd):", prayerDateField,
                "Status:", statusField,
                "Khushu Rating (1-5):", khushuRatingField,
                "Notes:", notesField,
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Prayer Record", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String prayerName = prayerNameField.getText();
            String prayerDate = prayerDateField.getText();
            String status = statusField.getText();
            int khushuRating = Integer.parseInt(khushuRatingField.getText());
            String notes = notesField.getText();

            prayerHandler.addPrayerRecord(user.getId(), prayerName, prayerDate, status, khushuRating, notes);
            JOptionPane.showMessageDialog(this, "Prayer record added successfully.");
            viewPrayerRecords(); // Refresh the table to show the new record
        }
    }

    private void viewPrayerRecords() {
        List<Object[]> records = prayerHandler.getUserPrayerRecords(user.getId());
        Object[][] data = records.toArray(new Object[0][]);
        recordsTable.setModel(new javax.swing.table.DefaultTableModel(data,
                new String[]{"Prayer Name", "Date", "Status", "Khushu Rating", "Notes"}));
    }

    private void viewMissedPrayers() {
        List<Object[]> records = prayerHandler.getMissedPrayers(user.getId());
        Object[][] data = records.toArray(new Object[0][]);
        recordsTable.setModel(new javax.swing.table.DefaultTableModel(data,
                new String[]{"Prayer Name", "Date", "Status", "Khushu Rating", "Notes"}));
    }

    private void viewPrayerStreak() {
        int streak = prayerHandler.calculatePrayerStreak(user.getId());
        JOptionPane.showMessageDialog(this, "Your current prayer streak: " + streak + " days.");
    }
}