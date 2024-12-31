/*
This class connects the database to our project.
 */

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class DatabaseConnection {

    public static Connection connect() {
        // JDBC URL and credentials
        String url = "jdbc:postgresql://localhost:5432/prayer_tracker";
        String user = "postgres";
        String password = "admin";

        // Create a connection object
        Connection connection = null;

        // Loading PostgreSQL driver
        try {
            Class.forName("org.postgresql.Driver");  // Loading the PostgreSQL driver
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver: " + e.getMessage());
            return null;
        }

        // Establishing the connection
        try {
            connection = DriverManager.getConnection(url, user, password);
            //  System.out.println("Successfully connected to the database");
        } catch (SQLException e) {
            System.out.println("Error in establishing connection with database");
            System.out.println(e.getMessage());
        }

        return connection;  // Returning the connection object
    }

    public static void main(String[] args) {
        // Testing the connection
        Connection connection = connect();  // Calling the connect method
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed!");
        }
    }
}