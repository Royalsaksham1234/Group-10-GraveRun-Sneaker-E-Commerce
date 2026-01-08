package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class MySqlConnection implements Database {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "graverun"; // 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234SAH";

    @Override
    public Connection openConnection() {
        try {
            ensureDatabaseExists();

            String url = String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC",
                HOST, PORT, DATABASE
            );

            Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);

            // ✅ Debug print
            System.out.println("Connected to database: " + DATABASE);

            return connection;

        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
            return null;
        }
    }

    private void ensureDatabaseExists() throws SQLException {
        String rootUrl = String.format("jdbc:mysql://%s:%d/?serverTimezone=UTC", HOST, PORT);

        try (Connection conn = DriverManager.getConnection(rootUrl, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + DATABASE + "`");
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
