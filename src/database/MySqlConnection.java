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
       try{
           String username = "root";
           String password = "Samridha19";
           String database = "sneakers";
           Connection connection;
           connection = DriverManager.getConnection(
           "jdbc:mysql://localhost:3306/" +database, username, password);
           if (connection == null){
               System.out.print("Connection unsuccessfull");
           }else{
               System.out.println("Connection successful");
           }
           
           return connection;        
       } catch (SQLException e){
           System.out.println(e);
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
