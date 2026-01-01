package dao;

import model.OrderModel;
import model.OrderItemModel;
import java.util.List;

/**
 * DAO interface for Order operations
 * @author srsro
 */
public interface OrderDAO {
    
    List<OrderModel> getAllOrders();
    
    OrderModel getOrderById(int orderId);
    
    List<OrderItemModel> getOrderItems(int orderId);
    
    int getTotalOrderCount();
    
    List<OrderModel> getOrdersByUserId(int userId);
    
    List<OrderModel> getOrdersByStatus(String status);
    
    boolean updateOrderStatus(int orderId, String status);
}