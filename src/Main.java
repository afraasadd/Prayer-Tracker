import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Welcome to the Prayer Tracker System!");
            System.out.println("Choose your role:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleAdminRole(scanner);
                    break;
                case 2:
                    handleUserRole(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void handleAdminRole(Scanner scanner) {
        User userHandler = new User();
        System.out.println("\nAdmin Section:");
        User.handleAdminLogin(scanner);
    }

    public static void handleUserRole(Scanner scanner) {
        User userHandler = new User();
        PrayerHandler prayerHandler = new PrayerHandler();

        while (true) {
            System.out.println("\nUser Section:");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (userChoice) {
                case 1:
                    User.handleUserSignUp(scanner);
                    break;
                case 2:
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    if (User.userCredentials.containsKey(username)
                            && User.userCredentials.get(username).equals(password)) {
                        System.out.println("Login successful. Welcome, " + username + "!");
                        userDashboard(scanner, username, prayerHandler);
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void userDashboard(Scanner scanner, String username, PrayerHandler prayerHandler) {
        while (true) {
            System.out.println("\nUser Dashboard:");
            System.out.println("1. Add Prayer Record");
            System.out.println("2. View All Prayer Records");
            System.out.println("3. View Prayer Records by User");
            System.out.println("4. Update Prayer Record");
            System.out.println("5. Delete Prayer Record");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int dashboardChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (dashboardChoice) {
                case 1:
                    System.out.print("Enter prayer name: ");
                    String prayerName = scanner.nextLine();
                    System.out.print("Enter prayer date (yyyy-MM-dd): ");
                    String prayerDate = scanner.nextLine();
                    System.out.print("Enter status: ");
                    String status = scanner.nextLine();
                    System.out.print("Enter Khushu rating (1-10): ");
                    int khushuRating = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter any notes (optional): ");
                    String notes = scanner.nextLine();

                    prayerHandler.addPrayerRecord(
                            username.hashCode(), // Simulate a userId using the hashCode of the username
                            prayerName,
                            prayerDate,
                            status,
                            khushuRating,
                            notes
                    );
                    break;

                case 2:
                    prayerHandler.printAllPrayerRecords();
                    break;

                case 3:
                    System.out.println("Fetching prayer records for " + username + "...");
                    prayerHandler.printUserPrayerRecords(username.hashCode());
                    break;

                case 4:
                    System.out.print("Enter prayer name to update: ");
                    String updatePrayerName = scanner.nextLine();
                    System.out.print("Enter new prayer date (yyyy-MM-dd): ");
                    String updatePrayerDate = scanner.nextLine();
                    System.out.print("Enter new status: ");
                    String updateStatus = scanner.nextLine();
                    System.out.print("Enter new Khushu rating (1-10): ");
                    int updateKhushuRating = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter new notes (optional): ");
                    String updateNotes = scanner.nextLine();

                    prayerHandler.updatePrayerRecord(
                            username.hashCode(),
                            updatePrayerName,
                            updatePrayerDate,
                            updateStatus,
                            updateKhushuRating,
                            updateNotes
                    );
                    break;

                case 5:
                    System.out.print("Enter prayer name to delete: ");
                    String deletePrayerName = scanner.nextLine();
                    System.out.print("Enter prayer date (yyyy-MM-dd): ");
                    String deletePrayerDate = scanner.nextLine();

                    prayerHandler.deletePrayerRecord(username.hashCode(),deletePrayerName, deletePrayerDate);
                    break;

                case 6:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
