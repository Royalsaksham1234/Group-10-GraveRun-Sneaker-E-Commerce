package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalesStatisticsDao {

    private Connection conn;

    public SalesStatisticsDao(Connection conn) {
        this.conn = conn;
    }

    // ================= PIE CHART =================
    // Top 5 categories by sales
    public Map<String, Double> getSalesByCategory() {
        Map<String, Double> data = new LinkedHashMap<>();
        String sql =
            "SELECT p.category, SUM(oi.subtotal) AS total_sales " +
            "FROM order_items oi " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "GROUP BY p.category " +        // <-- added space at end
            "ORDER BY total_sales DESC " +  // <-- added space at end
            "LIMIT 5";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String category = rs.getString("category");
                if (category != null && !category.isEmpty()) {
                    data.put(category, rs.getDouble("total_sales"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // ================= BAR CHART =================
    // Top 10 products by revenue
    public Map<String, Double> getTopProductsByRevenue() {
        Map<String, Double> data = new LinkedHashMap<>();
        String sql =
            "SELECT p.name AS product_name, SUM(oi.subtotal) AS total_sales " +
            "FROM order_items oi " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "GROUP BY p.product_id, p.name " +
            "ORDER BY total_sales DESC " +
            "LIMIT 10";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String productName = rs.getString("product_name");
                double revenue = rs.getDouble("total_sales");
                data.put(productName, revenue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // ================= LINE CHART =================
    // Monthly sales trend
    public Map<String, Double> getMonthlySales() {
        Map<String, Double> data = new LinkedHashMap<>();
        String sql =
            "SELECT DATE_FORMAT(o.created_at, '%Y-%m') AS month, SUM(o.total_amount) AS monthly_sales " +
            "FROM orders o " +
            "GROUP BY month " +  // use alias 'month' from SELECT
            "ORDER BY month";    // same alias

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String month = rs.getString("month"); // YYYY-MM
                double total = rs.getDouble("monthly_sales");
                data.put(month, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
