package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class UserDao {

    private final MySqlConnection mysql = new MySqlConnection();

    // --- Signup ---
    public boolean registerUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); // ⚠️ In production, hash the password
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error during signup: " + e.getMessage());
            return false;
        }
    }

    // --- Check if user already exists ---
    public boolean checkUserExists(String username, String email) {
        String sql = "SELECT 1 FROM users WHERE email = ? OR username = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            stmt.setString(1, email);
            stmt.setString(2, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true if user exists
            }

        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }

    // --- Login ---
    public boolean validateLogin(String email, String password) {
        String sql = "SELECT 1 FROM users WHERE email = ? AND password = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            stmt.setString(1, email);
            stmt.setString(2, password); // ⚠️ Hash comparison in real apps

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true if valid
            }

        } catch (SQLException e) {
            System.err.println("Error validating login: " + e.getMessage());
            return false;
        }
    }

    // --- Generate Reset Code ---
    public String generateResetCode() {
        int code = (int)(Math.random() * 900000) + 100000; // 6-digit code
        return String.valueOf(code);
    }

    // --- Save Reset Code ---
    public boolean saveResetCode(String email, String resetCode) {
        String sql = "UPDATE users SET reset_code = ?, reset_code_expiry = ? WHERE email = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setString(1, resetCode);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis() + 15 * 60 * 1000)); // 15 min expiry
            ps.setString(3, email);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saving reset code: " + e.getMessage());
            return false;
        }
    }

    // --- Validate Reset Code ---
    public boolean validateResetCode(String email, String code) {
        String sql = "SELECT reset_code, reset_code_expiry FROM users WHERE email = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedCode = rs.getString("reset_code");
                    Timestamp expiry = rs.getTimestamp("reset_code_expiry");
                    return storedCode != null
                            && storedCode.equals(code)
                            && expiry != null
                            && expiry.after(new Date());
                }
            }

        } catch (SQLException e) {
            System.err.println("Error validating reset code: " + e.getMessage());
        }
        return false;
    }

    // --- Update Password ---
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ?, reset_code = NULL, reset_code_expiry = NULL WHERE email = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return false;

            ps.setString(1, newPassword); // ⚠️ Hash in production
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }
}
