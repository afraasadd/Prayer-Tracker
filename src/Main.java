/*
This is the main/entry class of our project.
 */
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PrayerHandler handler = new PrayerHandler();
        while (true) {
            System.out.println("\nWelcome to the Prayer Tracker System!");
            System.out.println("1. Admin Login");
            System.out.println("2. User Sign-Up");
            System.out.println("3. User Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    User.adminLogin(scanner);
                    break;
                case 2:
                    userSignUp(scanner);
                    break;
                case 3:
                    User user = userLogin(scanner);
                    if (user != null) {
                        userDashboard(scanner, user);
                    }
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void userSignUp(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User.userSignUp(username, password);
    }

    private static User userLogin(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return User.userLogin(username, password);
    }

    private static void userDashboard(Scanner scanner, User user) {
        PrayerHandler prayerHandler = new PrayerHandler();
        while (true) {
            System.out.println("\nUser Dashboard:");
            System.out.println("1. Add Prayer Record");
            System.out.println("2. View Prayer Records");
            System.out.println("3. View Missed Prayers");
            System.out.println("4. View Prayer Streak");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    addPrayerRecord(scanner, user, prayerHandler);
                    break;
                case 2:
                    prayerHandler.printUserPrayerRecords(user.getId());
                    break;
                case 3:
                    prayerHandler.printMissedPrayers(user.getId());
                    break;
                case 4:
                    int streak = prayerHandler.calculatePrayerStreak(user.getId());
                    System.out.println("Your current prayer streak: " + streak + " days.");
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addPrayerRecord(Scanner scanner, User user, PrayerHandler prayerHandler) {
        System.out.print("Enter prayer name (e.g., Fajr, Dhuhr, etc.): ");
        String prayerName = scanner.nextLine();
        System.out.print("Enter prayer date (yyyy-MM-dd): ");
        String prayerDate = scanner.nextLine();
        System.out.print("Enter prayer status (e.g., Prayed on Time, Not Prayed): ");
        String status = scanner.nextLine();
        System.out.print("Enter khushu rating (1-5): ");
        int khushuRating = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.print("Enter any notes (optional): ");
        String notes = scanner.nextLine();

        prayerHandler.addPrayerRecord(user.getId(), prayerName, prayerDate, status, khushuRating, notes);
    }
}