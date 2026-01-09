package dao;

import database.MySqlConnection;
import model.CartItem;

import util.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.AdminProductModel;

public class CartDAO {

    private final MySqlConnection db;

    // Default constructor — uses your existing database helper
    public CartDAO() {
        this.db = new MySqlConnection();
    }

    // Get current logged-in user ID
    private int getCurrentUserId() {
        if (SessionManager.getCurrentUser() == null) {
            throw new IllegalStateException("No user logged in");
        }
        return SessionManager.getCurrentUser().getid();
    }

    // ---------------- GET ALL ITEMS ----------------
    public List<CartItem> getAllItems() {
        List<CartItem> items = new ArrayList<>();
        String query = "SELECT c.quantity, p.product_id, p.name, p.price, p.image_url " +
                       "FROM cart_items c JOIN products p ON c.product_id = p.product_id " +
                       "WHERE c.user_id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, getCurrentUserId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AdminProductModel product = new AdminProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImageUrl(rs.getString("image_url"));

                CartItem item = new CartItem(product, rs.getInt("quantity"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // ---------------- ADD PRODUCT ----------------
    public void addProduct(AdminProductModel product) {
        String query = "INSERT INTO cart_items (user_id, product_id, quantity) " +
                       "VALUES (?, ?, 1) " +
                       "ON DUPLICATE KEY UPDATE quantity = quantity + 1";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, product.getProductId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- UPDATE QUANTITY ----------------
    public void updateQuantity(AdminProductModel product, int quantity) {
        if (quantity < 1) return;

        String query = "UPDATE cart_items SET quantity = ? WHERE id = ? AND product_id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, quantity);
            ps.setInt(2, getCurrentUserId());
            ps.setInt(3, product.getProductId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- REMOVE PRODUCT ----------------
    public void removeProduct(int productId) {
        String query = "DELETE FROM cart_items WHERE id = ? AND product_id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, getCurrentUserId());
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- CLEAR CART ----------------
    public void clearCart() {
        String query = "DELETE FROM cart_items WHERE id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, getCurrentUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
