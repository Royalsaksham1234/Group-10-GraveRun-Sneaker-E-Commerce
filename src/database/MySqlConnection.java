/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * MySQL configuration helper.
 * - Ensures database exists before returning a connection.
 * - Provides basic query/update helpers used by DAO layer.
 */
public class MySqlConnection implements Database {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "sneakers";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234SAH";

    @Override
    public Connection openConnection() {
        try {
            // Make sure the database exists; creates it if missing.
            ensureDatabaseExists();

            String url = String.format(
                "jdbc:mysql://%s:%d/%s?serverTimezone=UTC&createDatabaseIfNotExist=true",
                HOST, PORT, DATABASE
            );

            Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            if (connection == null) {
                System.out.print("Connection unsuccessful");
            } else {
                System.out.println("Connection successful");
                ensureTablesExist(connection);
            }

            return connection;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Creates required tables if they do not already exist.
     * Currently includes the users table used across the app.
     */
    private void ensureTablesExist(Connection conn) throws SQLException {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                full_name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                phone VARCHAR(50),
                username VARCHAR(255) NOT NULL UNIQUE,
                password_hash VARCHAR(255) NOT NULL,
                role VARCHAR(20) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createUsersTable);
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
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
                System.out.println("Connection close");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            Statement stmp = conn.createStatement();
            return stmp.executeQuery(query);

        } catch (Exception e) {
            System.out.print(e);
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
        try {
            Statement stamp = conn.createStatement();
            return stamp.executeUpdate(query);

        } catch (Exception e) {
            System.out.print(e);
            return -1;
        }
    }

}