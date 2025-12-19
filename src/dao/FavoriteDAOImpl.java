package dao;

import database.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class favoriteDAOImpl implements favoriteDAO {
    MySqlConnection db = new MySqlConnection();

    @Override
    public boolean addToFavorites(int userId, int productId) {
        String query = "INSERT IGNORE INTO favorites (user_id, product_id) VALUES (?, ?)";
        
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding to favorites: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean removeFromFavorites(int userId, int productId) {
        String query = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing from favorites: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean isFavorite(int userId, int productId) {
        String query = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND product_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking favorite: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public List<Integer> getUserFavorites(int userId) {
        List<Integer> favoriteProductIds = new ArrayList<>();
        String query = "SELECT product_id FROM favorites WHERE user_id = ? ORDER BY added_at DESC";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                favoriteProductIds.add(rs.getInt("product_id"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user favorites: " + e.getMessage());
        }
        
        return favoriteProductIds;
    }
    
    @Override
    public int getFavoriteCount(int productId) {
        String query = "SELECT COUNT(*) FROM favorites WHERE product_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting favorite count: " + e.getMessage());
        }
        
        return 0;
    }
}