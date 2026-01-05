package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDao {

    private Connection conn;

    public OrderDao(Connection conn) {
        this.conn = conn ;// your existing DB connection class
    }

    /**
     * Get all order items of a specific user
     * Columns:
     * Product Name | Order Date | Quantity | Total Amount | Status
     */
    public ResultSet getOrdersByUserId(int userId) {

        ResultSet rs = null;

        String sql =
            "SELECT " +
            "p.name AS product_name, " +
            "o.created_at AS order_date, " +
            "oi.quantity AS quantity, " +
            "oi.subtotal AS total_amount, " +
            "o.status AS status " +
            "FROM orders o " +
            "JOIN order_items oi ON o.id = oi.order_id " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "WHERE o.user_id = 1 " +
            "ORDER BY o.created_at DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);   // bind user id
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
}
