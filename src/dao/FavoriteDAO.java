package dao;

import java.util.List;

public interface favoriteDAO {
    boolean addToFavorites(int userId, int productId);
    boolean removeFromFavorites(int userId, int productId);
    boolean isFavorite(int userId, int productId);
    List<Integer> getUserFavorites(int userId);
    int getFavoriteCount(int productId);
}