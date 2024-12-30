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

public class PrayerHandler {

    // Method to add prayer record
    public void addPrayerRecord(int userId, String prayerName, String prayerDate, String status, int khushuRating, String notes) {
        String query = "INSERT INTO PrayerRecords (user_id, prayer_name, prayer_date, status, khushu_rating, notes) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Convert the prayerDate string to a java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Set the date format
            java.util.Date utilDate = sdf.parse(prayerDate);  // Parse string to util.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Convert to sql.Date

            // Set the values for the PreparedStatement
            stmt.setInt(1, userId);
            stmt.setString(2, prayerName);
            stmt.setDate(3, sqlDate);  // Set the prayer_date as a java.sql.Date
            stmt.setString(4, status);
            stmt.setInt(5, khushuRating);
            stmt.setString(6, notes);

            // Execute the query
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prayer record inserted successfully!");
            } else {
                System.out.println("Failed to insert prayer record.");
            }

        } catch (SQLException | ParseException e) {
            System.out.println("Error while inserting prayer record: " + e.getMessage());
        }
    }

    // Method to print all prayer records for all users
    public void printAllPrayerRecords() {
        String query = "SELECT * FROM PrayerRecords";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String prayerName = rs.getString("prayer_name");
                String prayerDate = rs.getString("prayer_date");
                String status = rs.getString("status");
                int khushuRating = rs.getInt("khushu_rating");
                String notes = rs.getString("notes");

                System.out.println("User ID: " + userId + ", Prayer: " + prayerName + ", Date: " + prayerDate
                        + ", Status: " + status + ", Rating: " + khushuRating + ", Notes: " + notes);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching prayer records: " + e.getMessage());
        }
    }

    // Method to print prayer records for a specific user
    public void printUserPrayerRecords(int userId) {
        System.out.println("DATA FOR USER "+ userId);
        String query = "SELECT * FROM PrayerRecords WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);  // Set the user ID in the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String prayerName = rs.getString("prayer_name");
                    String prayerDate = rs.getString("prayer_date");
                    String status = rs.getString("status");
                    int khushuRating = rs.getInt("khushu_rating");
                    String notes = rs.getString("notes");

                    System.out.println("Prayer: " + prayerName + ", Date: " + prayerDate
                            + ", Status: " + status + ", Rating: " + khushuRating + ", Notes: " + notes);
                }
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


}



