package dao;

import database.MySqlConnection;
import model.UserData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userDAOImpl implements userDAO {
    MySqlConnection db = new MySqlConnection();

    @Override
    public boolean createUser(UserData user) {
        String query = "INSERT INTO users (email, password_hash, username, full_name, address, phone) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public UserData getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public UserData getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public boolean updateUser(UserData user) {
        String query = "UPDATE users SET email = ?, username = ?, full_name = ?, " +
                      "address = ?, phone = ?, is_active = ? WHERE user_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPhone());
            ps.setBoolean(6, user.isActive());
            ps.setInt(7, user.getUserId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean authenticateUser(String email, String password) {
        String query = "SELECT password_hash FROM users WHERE email = ? AND is_active = TRUE";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                // In production, use proper password hashing like BCrypt
                return storedHash.equals(password); // Simple comparison for demo
            }
            
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean userExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking if user exists: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public List<UserData> getAllUsers() {
        List<UserData> users = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY created_at DESC";
        
        try (
             Statement stmt = db.openConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        
        return users;
    }
    
    @Override
    public boolean updatePassword(int userId, String newPasswordHash) {
        String query = "UPDATE users SET password_hash = ? WHERE user_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, newPasswordHash);
            ps.setInt(2, userId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean updateProfile(UserData user) {
        String query = "UPDATE users SET full_name = ?, address = ?, phone = ? WHERE user_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getAddress());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getUserId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating profile: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deactivateUser(int userId) {
        String query = "UPDATE users SET is_active = FALSE WHERE user_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deactivating user: " + e.getMessage());
            return false;
        }
    }
    
    private UserData mapResultSetToUser(ResultSet rs) throws SQLException {
        UserData user = new UserData();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setAddress(rs.getString("address"));
        user.setPhone(rs.getString("phone"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setActive(rs.getBoolean("is_active"));
        return user;
    }
}