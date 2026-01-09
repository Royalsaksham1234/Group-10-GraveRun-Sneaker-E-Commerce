package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import model.AdminOrderItemModel;
import model.AdminOrderModel;
import database.MySqlConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AdminOrderDAOImpl using Mockito
 */
public class AdminOrderDAOImplTest {
    
    private AdminOrderDAOImpl dao;
    
    @Mock
    private MySqlConnection mockDb;
    
    @Mock
    private Connection mockConnection;
    
    @Mock
    private Statement mockStatement;
    
    @Mock
    private PreparedStatement mockPreparedStatement;
    
    @Mock
    private ResultSet mockResultSet;
    
    private AutoCloseable closeable;
    
    @Before
    public void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
        
        // Create DAO with mock database connection
        dao = new AdminOrderDAOImpl(mockDb);
        
        // Default mock behaviors
        when(mockDb.openConnection()).thenReturn(mockConnection);
        doNothing().when(mockDb).closeConnection(any(Connection.class));
    }
    
    @After
    public void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    public void testGetTotalRevenue_WithOrders() throws SQLException {
        System.out.println("getTotalRevenue - with orders");
        
        BigDecimal expectedRevenue = new BigDecimal("5000.00");
        
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBigDecimal("revenue")).thenReturn(expectedRevenue);
        
        BigDecimal result = dao.getTotalRevenue();
        
        assertEquals(expectedRevenue, result);
        verify(mockDb).openConnection();
        verify(mockDb).closeConnection(mockConnection);
    }
    
    @Test
    public void testGetTotalRevenue_NoOrders() throws SQLException {
        System.out.println("getTotalRevenue - no orders");
        
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBigDecimal("revenue")).thenReturn(null);
        
        BigDecimal result = dao.getTotalRevenue();
        
        assertEquals(BigDecimal.ZERO, result);
    }
    
    @Test
    public void testGetTotalRevenue_SQLException() throws SQLException {
        System.out.println("getTotalRevenue - SQLException");
        
        when(mockConnection.createStatement()).thenThrow(new SQLException("Database error"));
        
        BigDecimal result = dao.getTotalRevenue();
        
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void testGetOrderCountByStatus() throws SQLException {
        System.out.println("getOrderCountByStatus");
        
        String status = "pending";
        int expectedCount = 5;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("count")).thenReturn(expectedCount);
        
        int result = dao.getOrderCountByStatus(status);
        
        assertEquals(expectedCount, result);
        verify(mockPreparedStatement).setString(1, status);
    }

    @Test
    public void testGetAllOrders() throws SQLException {
        System.out.println("getAllOrders");
        
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getInt("user_id")).thenReturn(10, 11);
        when(mockResultSet.getBigDecimal("total_amount")).thenReturn(
            new BigDecimal("100.00"), new BigDecimal("200.00")
        );
        when(mockResultSet.getString("status")).thenReturn("pending", "shipped");
        when(mockResultSet.getString("shipping_address")).thenReturn("Address 1", "Address 2");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(
            new Timestamp(System.currentTimeMillis())
        );
        when(mockResultSet.getTimestamp("updated_at")).thenReturn(
            new Timestamp(System.currentTimeMillis())
        );
        when(mockResultSet.getString("username")).thenReturn("user1", "user2");
        when(mockResultSet.getString("email")).thenReturn("user1@test.com", "user2@test.com");
        
        List<AdminOrderModel> result = dao.getAllOrders();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("user1", result.get(0).getUsername());
    }

    @Test
    public void testGetOrderById() throws SQLException {
        System.out.println("getOrderById");
        
        int orderId = 1;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(orderId);
        when(mockResultSet.getInt("user_id")).thenReturn(10);
        when(mockResultSet.getBigDecimal("total_amount")).thenReturn(new BigDecimal("150.00"));
        when(mockResultSet.getString("status")).thenReturn("pending");
        when(mockResultSet.getString("shipping_address")).thenReturn("Test Address");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getTimestamp("updated_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getString("username")).thenReturn("testuser");
        when(mockResultSet.getString("email")).thenReturn("test@test.com");
        
        AdminOrderModel result = dao.getOrderById(orderId);
        
        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals("testuser", result.getUsername());
        verify(mockPreparedStatement).setInt(1, orderId);
    }

    @Test
    public void testGetOrderItems() throws SQLException {
        System.out.println("getOrderItems");
        
        int orderId = 1;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getInt("order_id")).thenReturn(orderId, orderId);
        when(mockResultSet.getInt("product_id")).thenReturn(100, 101);
        when(mockResultSet.getInt("quantity")).thenReturn(2, 3);
        when(mockResultSet.getBigDecimal("price")).thenReturn(
            new BigDecimal("50.00"), new BigDecimal("75.00")
        );
        when(mockResultSet.getBigDecimal("subtotal")).thenReturn(
            new BigDecimal("100.00"), new BigDecimal("225.00")
        );
        when(mockResultSet.getString("name")).thenReturn("Product 1", "Product 2");
        when(mockResultSet.getString("brand")).thenReturn("Brand A", "Brand B");
        when(mockResultSet.getString("image_url")).thenReturn("url1.jpg", "url2.jpg");
        
        List<AdminOrderItemModel> result = dao.getOrderItems(orderId);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getProductId());
        assertEquals("Product 1", result.get(0).getProductName());
    }

    @Test
    public void testGetTotalOrderCount() throws SQLException {
        System.out.println("getTotalOrderCount");
        
        int expectedCount = 25;
        
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("count")).thenReturn(expectedCount);
        
        int result = dao.getTotalOrderCount();
        
        assertEquals(expectedCount, result);
    }

    @Test
    public void testGetOrdersByUserId() throws SQLException {
        System.out.println("getOrdersByUserId");
        
        int userId = 10;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("user_id")).thenReturn(userId);
        when(mockResultSet.getBigDecimal("total_amount")).thenReturn(new BigDecimal("100.00"));
        when(mockResultSet.getString("status")).thenReturn("pending");
        when(mockResultSet.getString("shipping_address")).thenReturn("Address");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getTimestamp("updated_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getString("username")).thenReturn("testuser");
        when(mockResultSet.getString("email")).thenReturn("test@test.com");
        
        List<AdminOrderModel> result = dao.getOrdersByUserId(userId);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
        verify(mockPreparedStatement).setInt(1, userId);
    }

    @Test
    public void testGetOrdersByStatus() throws SQLException {
        System.out.println("getOrdersByStatus");
        
        String status = "shipped";
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("user_id")).thenReturn(10);
        when(mockResultSet.getBigDecimal("total_amount")).thenReturn(new BigDecimal("100.00"));
        when(mockResultSet.getString("status")).thenReturn(status);
        when(mockResultSet.getString("shipping_address")).thenReturn("Address");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getTimestamp("updated_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getString("username")).thenReturn("testuser");
        when(mockResultSet.getString("email")).thenReturn("test@test.com");
        
        List<AdminOrderModel> result = dao.getOrdersByStatus(status);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(status, result.get(0).getStatus());
        verify(mockPreparedStatement).setString(1, status);
    }

    @Test
    public void testUpdateOrderStatus() throws SQLException {
        System.out.println("updateOrderStatus");
        
        int orderId = 1;
        String newStatus = "shipped";
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        boolean result = dao.updateOrderStatus(orderId, newStatus);
        
        assertTrue(result);
        verify(mockPreparedStatement).setString(1, newStatus);
        verify(mockPreparedStatement).setInt(2, orderId);
    }
}