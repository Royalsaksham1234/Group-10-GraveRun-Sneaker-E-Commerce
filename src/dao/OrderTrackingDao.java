package dao;

import model.Order;
import database.MySqlConnection;
import java.sql.*;

public class OrderTrackingDao {
    private final MySqlConnection mysql = new MySqlConnection();
    
    /**
     * Get the most recent order for a specific user
     */
    public Order getMostRecentOrderByUserId(int userId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC LIMIT 1";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractOrderFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("Error fetching most recent order: " + e.getMessage());
            e.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }
    
    /**
     * Get order by ID
     */
    public Order getOrderById(int orderId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM orders WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractOrderFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("Error fetching order by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }
    
    /**
     * Update order status
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderId);
            
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.out.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    /**
     * Cancel order
     */
    public boolean cancelOrder(int orderId) {
        return updateOrderStatus(orderId, "Cancelled");
    }
    
    /**
     * Helper method to extract Order from ResultSet
     */
    private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setStatus(rs.getString("status"));
        order.setEstimatedTime(rs.getString("estimated_time"));
        order.setDeliveryType(rs.getString("delivery_type"));
        order.setCreatedAt(rs.getString("created_at"));
        
        // Handle nullable columns
        try {
            order.setUpdatedAt(rs.getString("updated_at"));
            order.setStripeSessionId(rs.getString("stripe_session_id"));
        } catch (SQLException e) {
            // Columns might not exist in older schemas
        }
        
        return order;
    }
}