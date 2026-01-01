package dao;

import model.OrderModel;
import model.OrderItemModel;
import database.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of OrderDAO interface
 * @author srsro
 */
public class OrderDAOImpl implements OrderDAO {
    
    MySqlConnection db = new MySqlConnection();


 
    @Override
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.*, u.username, u.email FROM orders o " +
                      "LEFT JOIN users u ON o.user_id = u.id " +
                      "ORDER BY o.created_at DESC";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setUsername(rs.getString("username"));
                order.setUserEmail(rs.getString("email"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all orders: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return orders;
    }

    @Override
    public OrderModel getOrderById(int orderId) {
        String query = "SELECT o.*, u.username, u.email FROM orders o " +
                      "LEFT JOIN users u ON o.user_id = u.id " +
                      "WHERE o.id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                OrderModel order = new OrderModel();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setUsername(rs.getString("username"));
                order.setUserEmail(rs.getString("email"));
                return order;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching order by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return null;
    }

    @Override
    public List<OrderItemModel> getOrderItems(int orderId) {
        List<OrderItemModel> items = new ArrayList<>();
        String query = "SELECT oi.*, p.name, p.brand, p.image_url " +
              "FROM `order_items` oi " +  
              "LEFT JOIN products p ON oi.product_id = p.product_id " +
              "WHERE oi.order_id = ?";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrderItemModel item = new OrderItemModel();
                item.setId(rs.getInt("id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setSubtotal(rs.getBigDecimal("subtotal"));
                item.setProductName(rs.getString("name"));
                item.setProductBrand(rs.getString("brand"));
                item.setProductImageUrl(rs.getString("image_url"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching order items: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return items;
    }

    @Override
    public int getTotalOrderCount() {
        String query = "SELECT COUNT(*) as count FROM orders";
        Connection conn = null;
        
        try {
            conn = db.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting order count: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return 0;
    }

    @Override
    public List<OrderModel> getOrdersByUserId(int userId) {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.*, u.username, u.email FROM orders o " +
                      "LEFT JOIN users u ON o.user_id = u.id " +
                      "WHERE o.user_id = ? " +
                      "ORDER BY o.created_at DESC";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setUsername(rs.getString("username"));
                order.setUserEmail(rs.getString("email"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders by user ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return orders;
    }

    @Override
    public List<OrderModel> getOrdersByStatus(String status) {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.*, u.username, u.email FROM orders o " +
                      "LEFT JOIN users u ON o.user_id = u.id " +
                      "WHERE o.status = ? " +
                      "ORDER BY o.created_at DESC";
        
        Connection conn = null;
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setUsername(rs.getString("username"));
                order.setUserEmail(rs.getString("email"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders by status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        Connection conn = null;
        
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            db.closeConnection(conn);
        }
    }
}