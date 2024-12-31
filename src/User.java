import java.util.*;

public class User {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    private static Map<String, List<String>> userPrayerRecords = new HashMap<>();
    private static Map<String, Integer> userStreaks = new HashMap<>();
    static Map<String, String> userCredentials = new HashMap<>();
    private static Map<String, String> pendingUserRequests = new HashMap<>();

    static void handleAdminLogin(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();

        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Login successful. Welcome, Admin!");
            handleAdminMenu(scanner);
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }
    }

    private static void handleAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Pending Sign-Ups");
            System.out.println("2. Approve/Reject Sign-Ups");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewPendingSignUps();
                    break;
                case 2:
                    approveOrRejectSignUps(scanner);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewPendingSignUps() {
        System.out.println("Pending Sign-Ups:");
        if (pendingUserRequests.isEmpty()) {
            System.out.println("No pending sign-ups.");
        } else {
            for (String username : pendingUserRequests.keySet()) {
                System.out.println("- " + username);
            }
        }
    }

    private static void approveOrRejectSignUps(Scanner scanner) {
        if (pendingUserRequests.isEmpty()) {
            System.out.println("No pending sign-ups to approve or reject.");
            return;
        }

        for (Map.Entry<String, String> entry : pendingUserRequests.entrySet()) {
            String username = entry.getKey();
            String password = entry.getValue();

            System.out.println("Approve or reject the following sign-up:");
            System.out.println("Username: " + username);
            System.out.print("Enter 'approve' or 'reject': ");
            String decision = scanner.nextLine();

            if ("approve".equalsIgnoreCase(decision)) {
                userCredentials.put(username, password);
                userPrayerRecords.put(username, new ArrayList<>());
                userStreaks.put(username, 0);
                System.out.println(username + " has been approved.");
            } else if ("reject".equalsIgnoreCase(decision)) {
                System.out.println(username + " has been rejected.");
            } else {
                System.out.println("Invalid input. Skipping...");
            }
        }
        pendingUserRequests.clear();
    }

    private static void handleUserAccess(Scanner scanner) {
        System.out.println("\nUser Menu:");
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                handleUserSignUp(scanner);
                break;
            case 2:
                handleUserLogin(scanner);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    static void handleUserSignUp(Scanner scanner) {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();

        if (userCredentials.containsKey(username) || pendingUserRequests.containsKey(username)) {
            System.out.println("Username already exists. Please try a different username.");
            return;
        }

        System.out.print("Enter a new password: ");
        String password = scanner.nextLine();

        pendingUserRequests.put(username, password);
        System.out.println("Sign-up request submitted. Please wait for admin approval.");
    }

    private static void handleUserLogin(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            handleUserMenu(scanner, username);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void handleUserMenu(Scanner scanner, String username) {

    }
    }

