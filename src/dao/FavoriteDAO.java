package dao;

import database.MySqlConnection;
import model.FavouriteItem;
import model.ProductModel;
import util.SessionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    private final MySqlConnection db;

    public FavoriteDAO() {
        this.db = new MySqlConnection();
    }

    // Get current logged-in user ID
    private int getCurrentUserId() {
        if (SessionManager.getCurrentUser() == null) {
            throw new IllegalStateException("No user logged in");
        }
        return SessionManager.getCurrentUser().getUserId();
    }

    // ---------------- GET ALL FAVORITES ----------------
    public List<FavouriteItem> getItems() {
        List<FavouriteItem> items = new ArrayList<>();
        String query = "SELECT f.product_id, p.name, p.price, p.image_url " +
                       "FROM favorites f JOIN products p ON f.product_id = p.id " +
                       "WHERE f.user_id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // ---------------- ADD FAVORITE ----------------
    public void add(ProductModel product) {
        String query = "INSERT INTO favorites (user_id, product_id, added_at) VALUES (?, ?, NOW()) " +
                       "ON DUPLICATE KEY UPDATE added_at = NOW()";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, product.getProductId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- REMOVE FAVORITE ----------------
    public void remove(int productId) {
        String query = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- CHECK IF FAVORITE ----------------
    public boolean isFavourite(int productId) {
        String query = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND product_id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, productId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
