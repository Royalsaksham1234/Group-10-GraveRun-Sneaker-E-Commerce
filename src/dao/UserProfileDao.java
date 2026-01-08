package dao;

import model.UserProfileData;
import database.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class UserProfileDao {

    private MySqlConnection db = new MySqlConnection();

    /**
     * Fetch user profile info and order history by userId
     */
    public UserProfileData getUserProfile(int userId) {

        // Query for basic user info
        String userSql = "SELECT username, email FROM users WHERE id = ?";

        // Query for user's orders
        String orderSql = """
            SELECT 
                p.name AS product_name,
                oi.quantity,
                oi.price,
                o.status,
                o.created_at
            FROM orders o
            LEFT JOIN order_items oi ON o.id = oi.order_id
            LEFT JOIN products p ON oi.product_id = p.id
            WHERE o.user_id = ?
            ORDER BY o.created_at DESC
        """;

        UserProfileData profile = null;

        try (Connection con = db.openConnection()) {

            // 1️⃣ Fetch user info
            try (PreparedStatement ps = con.prepareStatement(userSql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    profile = new UserProfileData(
                        userId,
                        rs.getString("username"),
                        rs.getString("email")
                    );
                }
            }

            if (profile == null) {
                // User not found
                return null;
            }

            // 2️⃣ Fetch user orders
            try (PreparedStatement ps = con.prepareStatement(orderSql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String productName = rs.getString("product_name");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String status = rs.getString("status");
                    Timestamp createdAt = rs.getTimestamp("created_at");

                    if (productName != null) {
                        profile.addOrder(productName, quantity, price, status, createdAt);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return profile;
    }
}
