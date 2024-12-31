/**
 * <h3>Purpose of this class:</h3>
 * <p>This class provides functionality for tracking prayer records. The following operations are supported:</p>
 * <ul>
 *     <li><strong>Add a New Prayer Record:</strong> Allows users to input details such as prayer name, date, status, khushu rating, and optional notes, and insert them into the database.</li>
 *     <li><strong>View Prayer Records:</strong> Fetches and displays weekly or monthly prayer records for a specific user.</li>
 *     <li><strong>Update a Prayer Record:</strong> Allows users to modify existing prayer details, such as status or khushu rating.</li>
 *     <li><strong>Delete a Prayer Record (optional):</strong> Enables the deletion of an existing prayer record if needed.</li>
 * </ul>
 */

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class PrayerHandler {

    private static final String GREEN = "\u001B[32m";
    private static final String BROWN = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    // Method to add prayer record
    public void addPrayerRecord(int userId, String prayerName, String prayerDate, String status, int khushuRating, String notes) {
        if (khushuRating < 1 || khushuRating > 5) {           // Validate khushuRating
            System.out.println("Error: Khushu rating must be between 1 and 5.");
            return;
        }
        if (isPrayerRecordExists(userId, prayerName, prayerDate)) //avoiding duplicate entry
            System.out.println("Error: A record with this prayer name and date already exists.");
            return;
        }

        String query = "INSERT INTO PrayerRecords (user_id, prayer_name, prayer_date, status, khushu_rating, notes) VALUES (?, ?, ?, ?, ?, ?)";


    // Check if a prayer record already exists
    private boolean isPrayerRecordExists(int userId, String prayerName, String prayerDate) {
        String query = "SELECT COUNT(*) FROM PrayerRecords WHERE user_id = ? AND prayer_name = ? AND prayer_date = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setString(2, prayerName);
            stmt.setDate(3, java.sql.Date.valueOf(prayerDate));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Record exists
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while checking prayer record: " + e.getMessage());
        }
        return false;
    }

    public void printAllPrayerRecords() {
        String query = "SELECT * FROM PrayerRecords ORDER BY prayer_date DESC, "+ "CASE " +
                "WHEN prayer_name = 'Fajr' THEN 1 " +
                "WHEN prayer_name = 'Dhuhr' THEN 2 " +
                "WHEN prayer_name = 'Asr' THEN 3 " +
                "WHEN prayer_name = 'Maghrib' THEN 4 " +
                "WHEN prayer_name = 'Isha' THEN 5 END";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n =========== All Prayer Records ===========");

            while (rs.next()) {
                printTableHeader();

                String currentDate = "";
                while (rs.next()) {
                    String prayerName = rs.getString("prayer_name");
                    Date date = rs.getDate("prayer_date");
                    String dateStr = date.toString();
                    String status = rs.getString("status");
                    int khushuRating = rs.getInt("khushu_rating");
                    String notes = rs.getString("notes");

                    if (notes == null || notes.isEmpty()) {
                        notes = "-";
                    }
                    if (notes.length() > 30) {
                        notes = notes.substring(0, 27) + "...";
                    }

                    // Print separator between different dates
                    if (!dateStr.equals(currentDate) && !currentDate.isEmpty()) {
                        printTableSeparator();
                    }
                    currentDate = dateStr;

                    printTableRow(prayerName, dateStr, status, khushuRating, notes);
                }
                printTableSeparator();
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching records: " + e.getMessage());
        }
    }
    // Updated method to print user prayer records
    public void printUserPrayerRecords(int userId) {
        String query = "SELECT * FROM PrayerRecords WHERE user_id = ? ORDER BY prayer_date DESC, CASE " +
                "WHEN prayer_name = 'Fajr' THEN 1 " +
                "WHEN prayer_name = 'Dhuhr' THEN 2 " +
                "WHEN prayer_name = 'Asr' THEN 3 " +
                "WHEN prayer_name = 'Maghrib' THEN 4 " +
                "WHEN prayer_name = 'Isha' THEN 5 END";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n=== Prayer Records for User ID: " + userId + " ===");
                printTableHeader();

                String currentDate = "";
                while (rs.next()) {
                    String prayerName = rs.getString("prayer_name");
                    Date date = rs.getDate("prayer_date");
                    String dateStr = date.toString();
                    String status = rs.getString("status");
                    int khushuRating = rs.getInt("khushu_rating");
                    String notes = rs.getString("notes");

                    if (notes == null || notes.isEmpty()) {
                        notes = "-";
                    }
                    if (notes.length() > 30) {
                        notes = notes.substring(0, 27) + "...";
                    }

                    // Print separator between different dates
                    if (!dateStr.equals(currentDate) && !currentDate.isEmpty()) {
                        printTableSeparator();
                    }
                    currentDate = dateStr;

                    printTableRow(prayerName, dateStr, status, khushuRating, notes);
                }
                printTableSeparator();
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching user's prayer records: " + e.getMessage());
        }
    }

    // Method to update a prayer record
    public void updatePrayerRecord(int userId, String prayerName, String prayerDate, String status, int khushuRating, String notes) {
        // Convert the String prayerDate to a java.sql.Date
        Date sqlDate = Date.valueOf(prayerDate);  // Convert String to Date

        // Update query with placeholders
        String query = "UPDATE prayerrecords SET prayer_name = ?, prayer_date = ?, status = ?, khushu_rating = ?, notes = ? WHERE user_id = ? AND prayer_name = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters for the PreparedStatement
            preparedStatement.setString(1, prayerName);
            preparedStatement.setDate(2, sqlDate);  // Set the SQL Date
            preparedStatement.setString(3, status);
            preparedStatement.setInt(4, khushuRating);
            preparedStatement.setString(5, notes);
            preparedStatement.setInt(6, userId);
            preparedStatement.setString(7, prayerName);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prayer record updated successfully!");
            } else {
                System.out.println("No record found for the specified user and prayer name.");
            }
        } catch (SQLException e) {
            System.out.println("Error while updating prayer record: " + e.getMessage());
        }
    }

    public void deletePrayerRecord(int userId, String prayerName, String prayerDate) {
        String query = "DELETE FROM PrayerRecords WHERE user_id = ? AND prayer_name = ? AND prayer_date = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set values for placeholders
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, prayerName);
            preparedStatement.setDate(3, java.sql.Date.valueOf(prayerDate)); // Convert String to java.sql.Date

            // Execute the delete
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Prayer record deleted successfully!");
            } else {
                System.out.println("No matching record found to delete.");
            }

        } catch (SQLException e) {
            System.out.println("Error while deleting prayer record: " + e.getMessage());
        }
    }

    // Updated method to print missed prayers
    public void printMissedPrayers(int userId) {
        System.out.println("\n=== Missed Prayers for User " + userId + " ===");
        String query = "SELECT * FROM PrayerRecords WHERE user_id = ? AND status = 'Not Prayed' ORDER BY prayer_date DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                printTableHeader();

                String format = "| %-12s | %-12s | %-12s | %-13d | %-30s |%n";

                while (rs.next()) {
                    String prayerName = rs.getString("prayer_name");
                    Date date = rs.getDate("prayer_date");
                    String status = rs.getString("status");
                    int khushuRating = rs.getInt("khushu_rating");
                    String notes = rs.getString("notes");
                    if (notes == null || notes.isEmpty()) {
                        notes = "-";
                    }
                    if (notes.length() > 30) {
                        notes = notes.substring(0, 27) + "...";
                    }

                    System.out.printf(format,
                            prayerName,
                            date.toString(),
                            status,
                            khushuRating,
                            notes);
                }

                printTableFooter();
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching missed prayers: " + e.getMessage());
        }
    }
    // Method to calculate streaks
    public int calculatePrayerStreak(int userId) {
        String query = "SELECT prayer_date FROM PrayerRecords WHERE user_id = ? AND status = 'Prayed on Time' ORDER BY prayer_date DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                int streak = 0;
                java.sql.Date previousDate = null;

                while (rs.next()) {
                    java.sql.Date currentDate = rs.getDate("prayer_date");

                    // If this is the first record, initialize the streak
                    if (previousDate == null) {
                        streak = 1;
                    } else {
                        // Check if the current date is exactly one day before the previous date
                        long diffInMillies = previousDate.getTime() - currentDate.getTime();
                        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

                        if (diffInDays == 1) {
                            streak++;
                        } else {
                            break; // Break the streak
                        }
                    }
                    previousDate = currentDate;
                }

                return streak;
            }

        } catch (SQLException e) {
            System.out.println("Error while calculating streak: " + e.getMessage());
        }
        return 0;
    }
    // Helper method to print table header
    private void printTableHeader() {
        System.out.println(GREEN + "+--------------+--------------+--------------+----------------+--------------------------------+" + RESET);
        System.out.println(GREEN + "| Prayer Name  |     Date     |    Status    | Khushu Rating  |              Notes             |" + RESET);
        System.out.println(GREEN + "+--------------+--------------+--------------+----------------+--------------------------------+" + RESET);
    }
    // Helper method to print table separator
    private void printTableSeparator() {
        System.out.println(GREEN + "+--------------+--------------+--------------+----------------+--------------------------------+" + RESET);
    }
    // Helper method to print a table row
    private void printTableRow(String prayerName, String date, String status, int khushuRating, String notes) {
        String statusColor = status.equals("Not Prayed") ? BROWN : GREEN;

        System.out.printf(GREEN + "| %-12s" + RESET + " | " +
                        BROWN + "%-12s" + RESET + " | " +
                        statusColor + "%-12s" + RESET + " | " +
                        "%14d | %-30s |\n",
                prayerName, date, status, khushuRating, notes);
    }
    // Helper method to print table footer
    private void printTableFooter() {
        System.out.println("+==============+==============+==============+===============+================================+");
    }
    // Updated method to print user prayer records for GUI
    public void printUserPrayerRecords(int userId, StringBuilder recordsText) {
        String query = "SELECT * FROM PrayerRecords WHERE user_id = ? ORDER BY prayer_date DESC, CASE " +
                "WHEN prayer_name = 'Fajr' THEN 1 " +
                "WHEN prayer_name = 'Dhuhr' THEN 2 " +
                "WHEN prayer_name = 'Asr' THEN 3 " +
                "WHEN prayer_name = 'Maghrib' THEN 4 " +
                "WHEN prayer_name = 'Isha' THEN 5 END";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                recordsText.append("=== Prayer Records for User ID: ").append(userId).append(" ===\n");

                while (rs.next()) {
                    String prayerName = rs.getString("prayer_name");
                    Date date = rs.getDate("prayer_date");
                    String dateStr = date.toString();
                    String status = rs.getString("status");
                    int khushuRating = rs.getInt("khushu_rating");
                    String notes = rs.getString("notes");

                    if (notes == null || notes.isEmpty()) {
                        notes = "-";
                    }
                    if (notes.length() > 30) {
                        notes = notes.substring(0, 27) + "...";
                    }

                    recordsText.append(String.format("%s | %s | %s | %d | %s\n", prayerName, dateStr, status, khushuRating, notes));
                }
            }

        } catch (SQLException e) {
            recordsText.append("Error while fetching user's prayer records: ").append(e.getMessage()).append("\n");
        }
    }
    public List<Object[]> getUserPrayerRecords(int userId) {
        List<Object[]> records = new ArrayList<>();
        String query = "SELECT * FROM PrayerRecords WHERE user_id = ? ORDER BY prayer_date DESC, CASE " +
                "WHEN prayer_name = 'Fajr' THEN 1 " +
                "WHEN prayer_name = 'Dhuhr' THEN 2 " +
                "WHEN prayer_name = 'Asr' THEN 3 " +
                "WHEN prayer_name = 'Maghrib' THEN 4 " +
                "WHEN prayer_name = 'Isha' THEN 5 END";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String prayerName = rs.getString("prayer_name");
                    java.sql.Date date = rs.getDate("prayer_date"); // Use java.sql.Date here
                    String status = rs.getString("status");
                    int khushuRating = rs.getInt("khushu_rating");
                    String notes = rs.getString("notes");

                    records.add(new Object[]{prayerName, date, status, khushuRating, notes});
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user prayer records: " + e.getMessage());
        }
        return records;
    }
    public List<Object[]> getMissedPrayers(int userId) {
        List<Object[]> records = new ArrayList<>();
        String query = "SELECT * FROM PrayerRecords WHERE user_id = ? AND status = 'Not Prayed' ORDER BY prayer_date DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String prayerName = rs.getString("prayer_name");
                    java.sql.Date date = rs.getDate("prayer_date");
                    String status = rs.getString("status");
                    int khushuRating = rs.getInt("khushu_rating");
                    String notes = rs.getString("notes");

                    records.add(new Object[]{prayerName, date, status, khushuRating, notes});
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching missed prayers: " + e.getMessage());
        }
        return records;
    }
    }



