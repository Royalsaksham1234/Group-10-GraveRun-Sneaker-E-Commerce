package dao;

import database.MySqlConnection;
import model.ProductModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FavoriteDAO implementation
 */
public class FavoriteDAOImpl implements FavoriteDAO {
   private final MySqlConnection mysql = new MySqlConnection(); 
    @Override
    public boolean addToFavorites(int userId, int productId) {
        // Check if already exists
        if (isFavorite(userId, productId)) {
            return false;
        }
        
        String query = "INSERT INTO favorites (user_id, product_id) VALUES (?, ?)";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding to favorites: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean removeFromFavorites(int userId, int productId) {
        String query = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing from favorites: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean isFavorite(int userId, int productId) {
        String query = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND product_id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking favorite status: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<ProductModel> getUserFavorites(int userId) {
        List<ProductModel> favorites = new ArrayList<>();
        String query = "SELECT p.* FROM products p " +
                      "INNER JOIN favorites f ON p.id = f.product_id " +
                      "WHERE f.user_id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                favorites.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user favorites: " + e.getMessage());
            e.printStackTrace();
        }
        return favorites;
    }
    
    @Override
    public int getFavoriteCount(int userId) {
        String query = "SELECT COUNT(*) FROM favorites WHERE user_id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting favorite count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public boolean clearAllFavorites(int userId) {
        String query = "DELETE FROM favorites WHERE user_id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() >= 0;
        } catch (SQLException e) {
            System.err.println("Error clearing favorites: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Helper method to extract Product object from ResultSet
     */
    private ProductModel extractProductFromResultSet(ResultSet rs) throws SQLException {
        return new ProductModel(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("brand"),
            rs.getString("category"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getString("image_path"),
            rs.getInt("stock"),
            rs.getInt("sales_count")
        );
    }
}