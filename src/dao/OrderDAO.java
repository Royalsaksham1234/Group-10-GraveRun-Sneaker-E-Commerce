/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Order;
import database.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final MySqlConnection mysql = new MySqlConnection();
    
    public Order getOrderById(String orderId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Order(
                    rs.getString("order_id"),
                    rs.getString("estimated_time"),
                    rs.getString("payment_method"),
                    rs.getString("delivery_type"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            System.out.println("Error fetching order: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }
    
    public boolean updateOrderStatus(String orderId, String newStatus) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, orderId);
            
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.out.println("Error updating order status: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    public boolean cancelOrder(String orderId) {
        return updateOrderStatus(orderId, "CANCELLED");
    }
    
    public List<String> getAllStatuses() {
        List<String> statuses = new ArrayList<>();
        statuses.add("Pending");
        statuses.add("Processing");
        statuses.add("Shipped");
        statuses.add("Delivered");
        statuses.add("Cancelled");
        return statuses;
    }
}