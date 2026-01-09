package dao;

import model.Order;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import database.MySqlConnection;

public class OrderTrackingDaoTest {
    
    private static OrderTrackingDao dao;
    private static MySqlConnection mysql;
    private static int testUserId = -1;
    private static int testOrderId = -1;
    
    @BeforeClass
    public static void setUpClass() {
        dao = new OrderTrackingDao();
        mysql = new MySqlConnection();
        System.out.println("Starting OrderTrackingDao tests...");
        
        // Create a test user for order testing
        Connection conn = mysql.openConnection();
        try {
            String createUserSql = "INSERT INTO users (email, password_hash, username, full_name, address, phone) " +
                                  "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(createUserSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "ordertest" + System.currentTimeMillis() + "@example.com");
            pstmt.setString(2, "testpass123");
            pstmt.setString(3, "ordertestuser" + System.currentTimeMillis());
            pstmt.setString(4, "Order Test User");
            pstmt.setString(5, "123 Test Street");
            pstmt.setString(6, "9876543210");
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                testUserId = rs.getInt(1);
                System.out.println("Created test user with ID: " + testUserId);
            }
        } catch (SQLException e) {
            System.err.println("Error creating test user: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        // Clean up test order and user
        Connection conn = mysql.openConnection();
        try {
            if (testOrderId > 0) {
                String deleteOrderSql = "DELETE FROM orders WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(deleteOrderSql);
                pstmt.setInt(1, testOrderId);
                pstmt.executeUpdate();
            }
            
            if (testUserId > 0) {
                String deleteUserSql = "DELETE FROM users WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(deleteUserSql);
                pstmt.setInt(1, testUserId);
                pstmt.executeUpdate();
            }
            System.out.println("Finished OrderTrackingDao tests and cleaned up test data.");
        } catch (SQLException e) {
            System.err.println("Error cleaning up test data: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    @Before
    public void setUp() {
        // Create a test order before each test
        Connection conn = mysql.openConnection();
        try {
            String createOrderSql = "INSERT INTO orders (user_id, total_amount, shipping_address, " +
                                   "payment_method, status, estimated_time, delivery_type) " +
                                   "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(createOrderSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, testUserId);
            pstmt.setDouble(2, 150.50);
            pstmt.setString(3, "456 Test Avenue, Test City");
            pstmt.setString(4, "Credit Card");
            pstmt.setString(5, "Pending");
            pstmt.setString(6, "2-3 days");
            pstmt.setString(7, "Standard");
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                testOrderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error creating test order: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
    }
    
    @After
    public void tearDown() {
        // Clean up test order after each test
        if (testOrderId > 0) {
            Connection conn = mysql.openConnection();
            try {
                String deleteSql = "DELETE FROM orders WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(deleteSql);
                pstmt.setInt(1, testOrderId);
                pstmt.executeUpdate();
                testOrderId = -1;
            } catch (SQLException e) {
                System.err.println("Error deleting test order: " + e.getMessage());
            } finally {
                mysql.closeConnection(conn);
            }
        }
    }

    @Test
    public void testGetMostRecentOrderByUserId() {
        System.out.println("Testing getMostRecentOrderByUserId...");
        
        // Create additional older order
        Connection conn = mysql.openConnection();
        int oldOrderId = -1;
        try {
            String createOrderSql = "INSERT INTO orders (user_id, total_amount, shipping_address, " +
                                   "payment_method, status, estimated_time, delivery_type, created_at) " +
                                   "VALUES (?, ?, ?, ?, ?, ?, ?, DATE_SUB(NOW(), INTERVAL 1 DAY))";
            PreparedStatement pstmt = conn.prepareStatement(createOrderSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, testUserId);
            pstmt.setDouble(2, 100.00);
            pstmt.setString(3, "Old Address");
            pstmt.setString(4, "Credit Card");
            pstmt.setString(5, "Delivered");
            pstmt.setString(6, "1 day");
            pstmt.setString(7, "Express");
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                oldOrderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error creating old order: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        
        // Test getting most recent order
        Order result = dao.getMostRecentOrderByUserId(testUserId);
        
        assertNotNull("Most recent order should be found", result);
        assertEquals("Should return the most recent order", testOrderId, result.getId());
        assertEquals("User ID should match", testUserId, result.getUserId());
        assertEquals("Total amount should match", 150.50, result.getTotalAmount(), 0.01);
        
        // Clean up old order
        if (oldOrderId > 0) {
            conn = mysql.openConnection();
            try {
                String deleteSql = "DELETE FROM orders WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(deleteSql);
                pstmt.setInt(1, oldOrderId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error deleting old order: " + e.getMessage());
            } finally {
                mysql.closeConnection(conn);
            }
        }
    }

    @Test
    public void testGetMostRecentOrderByUserId_NoOrders() {
        System.out.println("Testing getMostRecentOrderByUserId with no orders...");
        
        // Use a non-existent user ID
        Order result = dao.getMostRecentOrderByUserId(99999);
        
        assertNull("Should return null when no orders exist for user", result);
    }

    @Test
    public void testGetOrderById() {
        System.out.println("Testing getOrderById...");
        
        Order result = dao.getOrderById(testOrderId);
        
        assertNotNull("Order should be found by ID", result);
        assertEquals("Order ID should match", testOrderId, result.getId());
        assertEquals("User ID should match", testUserId, result.getUserId());
        assertEquals("Total amount should match", 150.50, result.getTotalAmount(), 0.01);
        assertEquals("Shipping address should match", "456 Test Avenue, Test City", result.getShippingAddress());
        assertEquals("Payment method should match", "Credit Card", result.getPaymentMethod());
        assertEquals("Status should match", "Pending", result.getStatus());
        assertEquals("Estimated time should match", "2-3 days", result.getEstimatedTime());
        assertEquals("Delivery type should match", "Standard", result.getDeliveryType());
    }

    @Test
    public void testGetOrderById_NotFound() {
        System.out.println("Testing getOrderById with non-existent ID...");
        
        Order result = dao.getOrderById(99999);
        
        assertNull("Should return null when order doesn't exist", result);
    }

    @Test
    public void testUpdateOrderStatus() {
        System.out.println("Testing updateOrderStatus...");
        
        // Update order status
        boolean result = dao.updateOrderStatus(testOrderId, "Processing");
        assertTrue("Order status should be updated successfully", result);
        
        // Verify the update
        Order updatedOrder = dao.getOrderById(testOrderId);
        assertNotNull("Order should still exist", updatedOrder);
        assertEquals("Status should be updated to Processing", "Processing", updatedOrder.getStatus());
    }

    @Test
    public void testUpdateOrderStatus_MultipleUpdates() {
        System.out.println("Testing multiple order status updates...");
        
        // Update through multiple statuses
        assertTrue("Should update to Processing", dao.updateOrderStatus(testOrderId, "Processing"));
        assertEquals("Status should be Processing", "Processing", dao.getOrderById(testOrderId).getStatus());
        
        assertTrue("Should update to Shipped", dao.updateOrderStatus(testOrderId, "Shipped"));
        assertEquals("Status should be Shipped", "Shipped", dao.getOrderById(testOrderId).getStatus());
        
        assertTrue("Should update to Delivered", dao.updateOrderStatus(testOrderId, "Delivered"));
        assertEquals("Status should be Delivered", "Delivered", dao.getOrderById(testOrderId).getStatus());
    }

    @Test
    public void testUpdateOrderStatus_InvalidOrderId() {
        System.out.println("Testing updateOrderStatus with invalid order ID...");
        
        boolean result = dao.updateOrderStatus(99999, "Processing");
        assertFalse("Should return false when order doesn't exist", result);
    }

    @Test
    public void testCancelOrder() {
        System.out.println("Testing cancelOrder...");
        
        // Cancel the order
        boolean result = dao.cancelOrder(testOrderId);
        assertTrue("Order should be cancelled successfully", result);
        
        // Verify cancellation
        Order cancelledOrder = dao.getOrderById(testOrderId);
        assertNotNull("Order should still exist", cancelledOrder);
        assertEquals("Status should be Cancelled", "Cancelled", cancelledOrder.getStatus());
    }

    @Test
    public void testCancelOrder_InvalidOrderId() {
        System.out.println("Testing cancelOrder with invalid order ID...");
        
        boolean result = dao.cancelOrder(99999);
        assertFalse("Should return false when order doesn't exist", result);
    }

    @Test
    public void testCancelOrder_AlreadyCancelled() {
        System.out.println("Testing cancelOrder on already cancelled order...");
        
        // Cancel the order first time
        assertTrue("First cancellation should succeed", dao.cancelOrder(testOrderId));
        
        // Try to cancel again
        boolean result = dao.cancelOrder(testOrderId);
        assertTrue("Should still return true (idempotent operation)", result);
        
        // Verify it's still cancelled
        Order order = dao.getOrderById(testOrderId);
        assertEquals("Status should remain Cancelled", "Cancelled", order.getStatus());
    }

    @Test
    public void testOrderFieldsIntegrity() {
        System.out.println("Testing order fields integrity...");
        
        Order order = dao.getOrderById(testOrderId);
        
        assertNotNull("Order should exist", order);
        assertTrue("Order ID should be positive", order.getId() > 0);
        assertTrue("User ID should be positive", order.getUserId() > 0);
        assertTrue("Total amount should be positive", order.getTotalAmount() > 0);
        assertNotNull("Shipping address should not be null", order.getShippingAddress());
        assertNotNull("Payment method should not be null", order.getPaymentMethod());
        assertNotNull("Status should not be null", order.getStatus());
        assertNotNull("Created at should not be null", order.getCreatedAt());
    }
}