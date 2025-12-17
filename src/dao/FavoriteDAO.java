package dao;

import model.ProductModel;
import java.util.List;

/**
 * FavoriteDAO interface for favorite-related database operations
 */
public interface FavoriteDAO {
    
    /**
     * Add product to user's favorites
     * @param userId User ID
     * @param productId Product ID
     * @return true if successful, false otherwise
     */
    boolean addToFavorites(int userId, int productId);
    
    /**
     * Remove product from user's favorites
     * @param userId User ID
     * @param productId Product ID
     * @return true if successful, false otherwise
     */
    boolean removeFromFavorites(int userId, int productId);
    
    /**
     * Check if product is in user's favorites
     * @param userId User ID
     * @param productId Product ID
     * @return true if product is favorited, false otherwise
     */
    boolean isFavorite(int userId, int productId);
    
    /**
     * Get all favorite products for a user
     * @param userId User ID
     * @return List of favorite products
     */
    List<ProductModel> getUserFavorites(int userId);
    
    /**
     * Get count of favorites for a user
     * @param userId User ID
     * @return Number of favorite products
     */
    int getFavoriteCount(int userId);
    
    /**
     * Clear all favorites for a user
     * @param userId User ID
     * @return true if successful, false otherwise
     */
    boolean clearAllFavorites(int userId);
}