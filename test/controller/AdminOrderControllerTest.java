package controller;

import dao.AdminOrderDAO;
import dao.AdminOrderDAOImpl;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import model.AdminOrderItemModel;
import model.AdminOrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AdminOrderControllerTest {
    
    // This is the object we are testing
    private AdminOrderController instance;

    public AdminOrderControllerTest() {
    }

   @Before
public void setUp() {
    // 1. Create the DAO first
    // Replace 'AdminOrderDAOImpl' with the actual name of your implementation class
    AdminOrderDAO dao = new AdminOrderDAOImpl(); 
    
    // 2. Pass the DAO into the controller constructor
    instance = new AdminOrderController(dao); 
}
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of formatDate method.
     * Checks if a null date returns an empty string or expected format.
     */
    @Test
    public void testFormatDate() {
        System.out.println("Testing: formatDate");
        Date date = new Date(1704067200000L); // Jan 1, 2024
        String result = instance.formatDate(date);
        assertNotNull("Formatted date should not be null", result);
    }

    /**
     * Test of formatAmount method.
     */
    @Test
    public void testFormatAmount() {
        System.out.println("Testing: formatAmount");
        BigDecimal amount = new BigDecimal("150.50");
        String result = instance.formatAmount(amount);
        // Adjust the expected string based on your actual currency formatting
        assertTrue(result.contains("150.50")); 
    }

    /**
     * Test of getStatusColor method.
     */
    @Test
    public void testGetStatusColor() {
        System.out.println("Testing: getStatusColor");
        
        Color pendingColor = instance.getStatusColor("Pending");
        Color completedColor = instance.getStatusColor("Completed");
        
        assertNotNull(pendingColor);
        assertNotNull(completedColor);
        assertNotEquals(pendingColor, completedColor);
    }

    /**
     * Test of isValidStatusTransition method.
     */
    @Test
    public void testIsValidStatusTransition() {
        System.out.println("Testing: isValidStatusTransition");
        
        // Example: Transitioning from Pending to Shipped should be true
        boolean result = instance.isValidStatusTransition("Pending", "Shipped");
        assertTrue("Transition from Pending to Shipped should be valid", result);
        
        // Example: Transitioning from Completed to Pending should be false
        boolean invalidResult = instance.isValidStatusTransition("Completed", "Pending");
        assertFalse("Should not be able to go from Completed back to Pending", invalidResult);
    }

    /**
     * Test of getAllowedStatuses method.
     */
    @Test
    public void testGetAllowedStatuses() {
        System.out.println("Testing: getAllowedStatuses");
        String[] result = instance.getAllowedStatuses("Pending");
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    /**
     * Test of updateOrderStatus with dummy data.
     */
    @Test
    public void testUpdateOrderStatus() {
        System.out.println("Testing: updateOrderStatus");
        int orderId = 9999; // Non-existent ID
        String newStatus = "Shipped";
        
        // This should return false because the order ID doesn't exist in DB
        boolean result = instance.updateOrderStatus(orderId, newStatus);
        assertFalse(result);
    }

    /**
     * Test of searchOrders with empty string.
     */
    @Test
    public void testSearchOrders() {
        System.out.println("Testing: searchOrders");
        List<AdminOrderModel> result = instance.searchOrders("");
        assertNotNull(result);
    }

    @Test
    public void testGetTotalOrderCount() {
        System.out.println("Testing: getTotalOrderCount");
        int count = instance.getTotalOrderCount();
        assertTrue(count >= 0);
    }
}