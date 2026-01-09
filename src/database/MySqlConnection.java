package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * MySQL Connection Helper for GraveRun Sneaker E-Commerce
 */
public class MySqlConnection implements Database {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "graverun";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Jibesh@16";  // Your password

    @Override
    public Connection openConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            if (connection != null) {
                System.out.println("Database connection successful!");
            }
            
            return connection;
            
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            
            // Optional: Show popup so you know immediately
            javax.swing.JOptionPane.showMessageDialog(null,
                "Cannot connect to database!\n\n" +
                "Error: " + e.getMessage() + "\n\n" +
                "Check:\n" +
                "- MySQL server is running\n" +
                "- Username: root\n" +
                "- Password: Jibesh@16\n" +
                "- Database 'graverun' exists",
                "Connection Failed",
                javax.swing.JOptionPane.ERROR_MESSAGE);
                
            return null;
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            if (conn == null || conn.isClosed()) {
                System.err.println("Connection is null or closed!");
                return null;
            }
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Query error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int excecuteUpdate(Connection conn, String query) {  // Fixed typo: executeUpdate
        try {
            if (conn == null || conn.isClosed()) {
                System.err.println("Connection is null or closed!");
                return -1;
            }
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Update error: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}
