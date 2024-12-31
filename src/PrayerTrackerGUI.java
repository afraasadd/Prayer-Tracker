import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrayerTrackerGUI extends JFrame {
    private JTextField prayerNameField;
    private JTextField prayerDateField;
    private JTextField statusField;
    private JTextField khushuRatingField;
    private JTextArea notesArea;
    private PrayerHandler prayerHandler;
    private User user;

    public PrayerTrackerGUI(User user) {
        this.user = user;
        this.prayerHandler = new PrayerHandler();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Prayer Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form fields
        formPanel.add(new JLabel("Prayer Name:"));
        prayerNameField = new JTextField();
        formPanel.add(prayerNameField);

        formPanel.add(new JLabel("Prayer Date (yyyy-MM-dd):"));
        prayerDateField = new JTextField();
        formPanel.add(prayerDateField);

        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        formPanel.add(new JLabel("Khushu Rating:"));
        khushuRatingField = new JTextField();
        formPanel.add(khushuRatingField);

        formPanel.add(new JLabel("Notes:"));
        notesArea = new JTextArea();
        formPanel.add(new JScrollPane(notesArea));

        // Buttons
        JButton addButton = new JButton("Add Prayer Record");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPrayerRecord();
            }
        });

        JButton viewButton = new JButton("View Prayer Records");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPrayerRecords();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void addPrayerRecord() {
        String prayerName = prayerNameField.getText();
        String prayerDate = prayerDateField.getText();
        String status = statusField.getText();
        int khushuRating = Integer.parseInt(khushuRatingField.getText());
        String notes = notesArea.getText();

        prayerHandler.addPrayerRecord(user.getId(), prayerName, prayerDate, status, khushuRating, notes);

        // Clear fields after adding
        prayerNameField.setText("");
        prayerDateField.setText("");
        statusField.setText("");
        khushuRatingField.setText("");
        notesArea.setText("");
    }

    private void viewPrayerRecords() {
        JFrame viewFrame = new JFrame("Prayer Records");
        viewFrame.setSize(600, 400);
        viewFrame.setLocationRelativeTo(null);

        JTextArea recordsArea = new JTextArea();
        recordsArea.setEditable(false);
        viewFrame.add(new JScrollPane(recordsArea));

        // Fetch and display prayer records
        StringBuilder recordsText = new StringBuilder();
        prayerHandler.printUserPrayerRecords(user.getId(), recordsText);
        recordsArea.setText(recordsText.toString());

        viewFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrayerTrackerGUI(new User("Aiesha", "aiesha123", 100)));
    }
}