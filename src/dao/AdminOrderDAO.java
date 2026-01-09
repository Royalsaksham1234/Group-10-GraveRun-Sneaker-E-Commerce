// Add these methods to your AdminOrderDAO interface:

package dao;

import model.AdminOrderModel;
import model.AdminOrderItemModel;
import java.math.BigDecimal;
import java.util.List;

public interface AdminOrderDAO {
    
    // Existing methods...
    List<AdminOrderModel> getAllOrders();
    AdminOrderModel getOrderById(int orderId);
    List<AdminOrderItemModel> getOrderItems(int orderId);
    List<AdminOrderModel> getOrdersByUserId(int userId);
    boolean updateOrderStatus(int orderId, String newStatus);
    int getTotalOrderCount();
    
    // NEW METHODS TO ADD:
    
    /**
     * Get total revenue from all orders
     */
    BigDecimal getTotalRevenue();
    
    /**
     * Get order count by status
     */
    int getOrderCountByStatus(String status);
}

// ============================================================================
// Add these implementations to your AdminOrderDAOImpl class:
// ============================================================================

