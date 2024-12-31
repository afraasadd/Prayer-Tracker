import java.sql.*;
import java.util.Scanner;public class User {
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password123";

    private String username;
    private String password;
    private int userId;

    // Constructor
    public User(String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }
    PrayerHandler handler= new PrayerHandler();
    // Getters
    public String getUsername() { return username; }
    public int getId() { return userId; }

    // Admin login
    public static void adminLogin(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Admin login successful!");
            adminMenu(scanner);
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }
    }

    // User sign-up

    // User sign-up
    public static void userSignUp(String username, String password) {
        // Check if username exists in database
        if (isUsernameExists(username)) {
            System.out.println("Username already exists. Try a different one.");
            return;
        }

        // Insert new user into database
        String query = "INSERT INTO users (username, password, pending) VALUES (?, ?, true)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("Sign-up request submitted. Please wait for admin approval.");
            } else {
                System.out.println("Error creating user account.");
            }
        } catch (SQLException e) {
            System.out.println("Error during sign-up: " + e.getMessage());
        }
    }

    // User login with username and password
    public static User userLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE LOWER(username) = LOWER(?) AND password = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getBoolean("pending")) {
                        System.out.println("Your account is still pending approval. Please wait for admin approval.");
                        return null;
                    }
                    System.out.println("Login successful. Welcome, " + username + "!");
                    return new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("id")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }

        System.out.println("Invalid username or password.");
        return null;
    }



    private static void adminMenu(Scanner scanner) {
        PrayerHandler handler = new PrayerHandler(); // Create instance here

        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Pending Sign-Ups");
            System.out.println("2. Approve/Reject Sign-Ups");
            System.out.println("3. View All Active Users");
            System.out.println("4. View Record for All Users ");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewPendingSignUps();
                    break;
                case 2:
                    approveRejectSignUps(scanner);
                    break;
                case 3:
                    viewActiveUsers();
                    break;
                case 4:
                    handler.printAllPrayerRecords();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewPendingSignUps() {
        String query = "SELECT username FROM users WHERE pending = true";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            boolean hasPending = false;
            System.out.println("Pending Sign-Ups:");
            while (rs.next()) {
                hasPending = true;
                System.out.println("- " + rs.getString("username"));
            }

            if (!hasPending) {
                System.out.println("No pending sign-ups.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching pending sign-ups: " + e.getMessage());
        }
    }

    private static void approveRejectSignUps(Scanner scanner) {
        String selectQuery = "SELECT id, username FROM users WHERE pending = true";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            ResultSet rs = selectStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No pending sign-ups to process.");
                return;
            }

            do {
                int userId = rs.getInt("id");
                String username = rs.getString("username");

                System.out.println("Approve or reject the following sign-up: " + username);
                System.out.print("Enter 'approve' or 'reject': ");
                String decision = scanner.nextLine();

                if ("approve".equalsIgnoreCase(decision)) {
                    updateUserStatus(userId, false);
                    System.out.println(username + " has been approved.");
                } else if ("reject".equalsIgnoreCase(decision)) {
                    deleteUser(userId);
                    System.out.println(username + " has been rejected.");
                } else {
                    System.out.println("Invalid input. Skipping...");
                }
            } while (rs.next());

        } catch (SQLException e) {
            System.out.println("Error processing sign-ups: " + e.getMessage());
        }
    }

    public static boolean isUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
        }
        return false;
    }

    private static void updateUserStatus(int userId, boolean pending) {
        String query = "UPDATE users SET pending = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, pending);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating user status: " + e.getMessage());
        }
    }

    private static void deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
    // Add new method to view active users
    private static void viewActiveUsers() {
        String query = "SELECT username FROM users WHERE pending = false ORDER BY username";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nActive Users:");
            boolean hasActiveUsers = false;

            while (rs.next()) {
                hasActiveUsers = true;
                System.out.println("- " + rs.getString("username"));
            }

            if (!hasActiveUsers) {
                System.out.println("No active users found.");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching active users: " + e.getMessage());
        }
    }
}