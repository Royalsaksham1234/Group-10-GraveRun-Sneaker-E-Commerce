package dao;

import database.MySqlConnection;
import model.FavouriteItem;
import model.AdminProductModel;
import util.SessionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing user favorites
 * Handles all database operations for the favorites table
 */
public class FavoriteDAO {
    
    private final MySqlConnection db;
    
    public FavoriteDAO() {
        this.db = new MySqlConnection();
    }
    
    /**
     * Get current logged-in user ID
     * @return user ID
     * @throws IllegalStateException if no user is logged in
     */
    private int getCurrentUserId() {
        if (!SessionManager.isLoggedIn()) {
            throw new IllegalStateException("No user logged in");
        }
        return SessionManager.getCurrentUser().getid();
    }
    
    /**
     * Get all favorite items for the current user
     * @return List of FavouriteItem
     */
    public List<FavouriteItem> getItems() {
        List<FavouriteItem> items = new ArrayList<>();
        
        String query = "SELECT f.product_id, p.name, p.price, p.image_url " +
                       "FROM favorites f JOIN products p ON f.product_id = p.product_id " +
                       "WHERE f.user_id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, getCurrentUserId());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                items.add(new FavouriteItem(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getBigDecimal("price"),
                    rs.getString("image_url")
                ));
            }
            
            System.out.println("✓ Loaded " + items.size() + " favorites for user " + getCurrentUserId());
            
        } catch (SQLException e) {
            System.err.println("Error loading favorites: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return items;
    }
    
    /**
     * Add a product to favorites
     * @param product Product to add
     */
    public void add(AdminProductModel product) {
        String query = "INSERT INTO favorites (user_id, product_id, added_at) " +
                       "VALUES (?, ?, NOW()) " +
                       "ON DUPLICATE KEY UPDATE added_at = NOW()";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, product.getProductId());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Added product " + product.getProductId() + 
                                 " to favorites for user " + getCurrentUserId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding to favorites: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
    }
    
    /**
     * Remove a product from favorites
     * @param productId Product ID to remove
     */
    public void remove(int productId) {
        String query = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, productId);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Removed product " + productId + 
                                 " from favorites for user " + getCurrentUserId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error removing from favorites: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
    }
    
    /**
     * Check if a product is in favorites
     * @param productId Product ID to check
     * @return true if product is in favorites, false otherwise
     */
    public boolean isFavourite(int productId) {
        String query = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND product_id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, productId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking favorite status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }
    
    /**
     * Check if a product is in favorites for a specific user
     * @param userId User ID
     * @param productId Product ID to check
     * @return true if product is in favorites, false otherwise
     */
    public boolean isFavorite(int userId, int productId) {
        String query = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND product_id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking favorite status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }
    
    /**
     * Add a product to favorites for a specific user
     * @param userId User ID
     * @param productId Product ID to add
     */
    public void addFavorite(int userId, int productId) {
        String query = "INSERT INTO favorites (user_id, product_id, added_at) " +
                       "VALUES (?, ?, NOW()) " +
                       "ON DUPLICATE KEY UPDATE added_at = NOW()";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Added product " + productId + 
                                 " to favorites for user " + userId);
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding to favorites: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
    }
    
    /**
     * Get count of favorites for current user
     * @return number of favorites
     */
    public int getFavoritesCount() {
        String query = "SELECT COUNT(*) FROM favorites WHERE user_id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, getCurrentUserId());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting favorites count: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return 0;
    }
    
    /**
     * Clear all favorites for current user
     */
    public void clearAllFavorites() {
        String query = "DELETE FROM favorites WHERE user_id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, getCurrentUserId());
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("✓ Cleared " + rowsAffected + " favorites");
            
        } catch (SQLException e) {
            System.err.println("Error clearing favorites: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
    }
}