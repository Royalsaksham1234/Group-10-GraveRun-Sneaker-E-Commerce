package dao;

import model.UserProfileData;
import database.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserProfileDao {

    private MySqlConnection db = new MySqlConnection();

    public UserProfileData getUserProfile(int userId) {
        String username = "";
        String email = "";

        // Query to get username and email
        String userSql = "SELECT username, email FROM users WHERE id = ?";

        // Query to get orders (joins orders -> order_items -> products)
        String orderSql = """
            SELECT p.name AS product_name, oi.quantity, oi.subtotal, o.status, o.created_at
            FROM orders o
            LEFT JOIN order_items oi ON o.id = oi.order_id
            LEFT JOIN products p ON oi.product_id = p.product_id
            WHERE o.user_id = ?
            ORDER BY o.created_at DESC
        """;

        UserProfileData profile = null;

        try (Connection con = db.openConnection()) {

            // 1️⃣ Get user info
            try (PreparedStatement ps = con.prepareStatement(userSql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    username = rs.getString("username");
                    email = rs.getString("email");
                }
            }

            profile = new UserProfileData(userId, username, email);

            // 2️⃣ Get orders
            try (PreparedStatement ps = con.prepareStatement(orderSql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    // Handle nulls if user has no orders
                    String productName = rs.getString("product_name");
                    int qty = rs.getInt("quantity");
                    double subtotal = rs.getDouble("subtotal");
                    String status = rs.getString("status");
                    java.sql.Timestamp orderDate = rs.getTimestamp("created_at");

                    if (productName != null) {  // only add if order exists
                        profile.addOrder(productName, qty, subtotal, status, orderDate);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return profile;
    }
}
