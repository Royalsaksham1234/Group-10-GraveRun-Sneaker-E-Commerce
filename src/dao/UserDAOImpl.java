package dao;

import model.UserModel;
import util.UserData;
import util.PasswordHasher;
import database.MySqlConnection;
import java.sql.*;

/**
 * Implementation of UserDAO with Password Hashing
 * Database field: 'id' (not 'user_id')
 */
public class UserDAOImpl implements UserDAO {
    
    private final MySqlConnection db;
    
    public UserDAOImpl() {
        this.db = new MySqlConnection();
    }
    
    @Override
    public int registerUser(UserModel user) {
        String query = "INSERT INTO users (email, full_name, password_hash, username, role, is_active, created_at) " +
                      "VALUES (?, ?, ?, ?, 'customer', TRUE, NOW())";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            // Extract username from email
            String username = user.getEmail().split("@")[0];
            
            // Hash the password before storing
            String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
            
            // Set all 4 parameters correctly
            pstmt.setString(1, user.getEmail());           // email
            pstmt.setString(2, user.getFullName());        // full_name
            pstmt.setString(3, hashedPassword);            // password_hash (HASHED)
            pstmt.setString(4, username);                  // username
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return generated id
                }
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return 0; // Failed
    }
    
    @Override
    public boolean authenticate(String email, String password) {
        String query = "SELECT password_hash FROM users WHERE email = ? AND is_active = TRUE";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                // Verify password using BCrypt
                return PasswordHasher.verifyPassword(password, storedHash);
            }
            
            return false; // User not found
            
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }
    
    @Override
    public UserData getUserByEmail(String email) {
        String query = "SELECT id, full_name, username, email, phone, address, role, is_active, created_at " +
                      "FROM users WHERE email = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Create UserData with all fields
                return new UserData(
                    rs.getInt("id"),                    // id field
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("role"),
                    rs.getBoolean("is_active"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return null;
    }
    
    public UserData getUserById(int id) {
        String query = "SELECT id, full_name, username, email, phone, address, role, is_active, created_at " +
                      "FROM users WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new UserData(
                    rs.getInt("id"),                    // id field
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("role"),
                    rs.getBoolean("is_active"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return null;
    }
    
    @Override
    public boolean isEmailTaken(String email) {
        String query = "SELECT id FROM users WHERE email = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if email exists
            
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }
    
    @Override
    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE users SET password_hash = ? WHERE email = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            // Hash the new password before storing
            String hashedPassword = PasswordHasher.hashPassword(newPassword);
            
            pstmt.setString(1, hashedPassword);  // HASHED password
            pstmt.setString(2, email);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }
    
    public boolean updateUserProfile(UserData userData) {
        String query = "UPDATE users SET full_name = ?, username = ?, phone = ?, address = ? WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, userData.getFullName());
            pstmt.setString(2, userData.getUsername());
            pstmt.setString(3, userData.getPhone());
            pstmt.setString(4, userData.getAddress());
            pstmt.setInt(5, userData.getid());  // id field
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }
    
    public boolean deactivateUser(int id) {
        String query = "UPDATE users SET is_active = FALSE WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deactivating user: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }
    
    public boolean activateUser(int id) {
        String query = "UPDATE users SET is_active = TRUE WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error activating user: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }

    public String getUserRole(String email) {
        String query = "SELECT role FROM users WHERE email = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            System.err.println("Error getting user role: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return null;
    }

    public int getUserId(String email) {
        String query = "SELECT id FROM users WHERE email = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error getting user ID: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return 0;
    }
}