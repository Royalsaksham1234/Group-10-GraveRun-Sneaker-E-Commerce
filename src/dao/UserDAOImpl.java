package dao;

import database.MySqlConnection;
import model.UserModel;
import util.UserData;  // Import your session user class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAOImpl implements UserDAO {

    private final MySqlConnection db = new MySqlConnection();

    public int registerUser(UserModel user) {  // Changed return type to int (userId)
        String sql = "INSERT INTO users (email, username, password_hash, role) VALUES (?, ?, ?, 'customer')";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String email = user.getEmail();
            String username = email.split("@")[0];  // simple username from email
            String passwordHash = user.getPassword();  // TODO: Hash this in production!

            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, passwordHash);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                // Get the auto-generated user ID
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Signup error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;  // failed
    }

    @Override
    public boolean authenticate(String email, String password) {
        String sql = "SELECT password_hash FROM users WHERE email = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    return password.equals(storedHash);  // TODO: Use proper hashing comparison
                }
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isEmailTaken(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Check email error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(String email, String newPasswordHash) {
        String sql = "UPDATE users SET password_hash = ? WHERE email = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPasswordHash);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Password update error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public String getUserRole(String email) {
        String sql = "SELECT role FROM users WHERE email = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // New method: Get full user data for session
    public UserData getUserByEmail(String email) {
        String sql = "SELECT id, username, email, role, created_at FROM users WHERE email = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserData(
                        rs.getInt("id"),
                        "",  // fullName - add column later if needed
                        rs.getString("username"),
                        rs.getString("email"),
                        "",  // phone
                        "",  // address
                        rs.getString("role"),
                        true,
                        rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserId(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}