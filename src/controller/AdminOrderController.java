package controller;

import model.AdminOrderModel;
import model.AdminOrderItemModel;
import dao.AdminOrderDAO;
import dao.AdminOrderDAOImpl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * Controller for Order Management
 * Handles all business logic for order operations
 */
public class AdminOrderController {
    
    private final AdminOrderDAO orderDAO;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm");
    
    // CHANGE THIS: Accept the DAO as a parameter
    public AdminOrderController(AdminOrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
    // ==================== Data Retrieval Methods ====================
    
    /**
     * Get all orders from database
     */
    public List<AdminOrderModel> getAllOrders() {
        try {
            return orderDAO.getAllOrders();
        } catch (Exception e) {
            System.err.println("Error retrieving orders: " + e.getMessage());
            return List.of(); // Return empty list instead of null
        }
    }
    
    /**
     * Get order by ID
     */
    public AdminOrderModel getOrderById(int orderId) {
        try {
            return orderDAO.getOrderById(orderId);
        } catch (Exception e) {
            System.err.println("Error retrieving order: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get order items for specific order
     */
    public List<AdminOrderItemModel> getOrderItems(int orderId) {
        try {
            return orderDAO.getOrderItems(orderId);
        } catch (Exception e) {
            System.err.println("Error retrieving order items: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Get orders for specific user
     */
    public List<AdminOrderModel> getOrdersByUserId(int userId) {
        try {
            return orderDAO.getOrdersByUserId(userId);
        } catch (Exception e) {
            System.err.println("Error retrieving user orders: " + e.getMessage());
            return List.of();
        }
    }
    
    // ==================== Order Operations ====================
    
    /**
     * Update order status with validation
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        // Validate status
        if (!isValidStatus(newStatus)) {
            JOptionPane.showMessageDialog(null, 
                "Invalid order status.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check if order exists
        AdminOrderModel order = getOrderById(orderId);
        if (order == null) {
            JOptionPane.showMessageDialog(null, 
                "Order not found.", 
                "Update Failed", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            boolean success = orderDAO.updateOrderStatus(orderId, newStatus);
            if (!success) {
                JOptionPane.showMessageDialog(null, 
                    "Failed to update order status.", 
                    "Update Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error updating order status: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // ==================== Search and Filter Methods ====================
    
    /**
     * Search orders by order ID, user ID, or shipping address
     */
    public List<AdminOrderModel> searchOrders(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return getAllOrders();
        }
        
        String search = searchText.toLowerCase().trim();
        List<AdminOrderModel> allOrders = getAllOrders();
        
        return allOrders.stream()
            .filter(order -> matchesSearchCriteria(order, search))
            .collect(Collectors.toList());
    }

    /**
     * Check if order matches search criteria
     */
    private boolean matchesSearchCriteria(AdminOrderModel order, String searchText) {
        String orderId = String.valueOf(order.getId());
        String userId = String.valueOf(order.getUserId());
        String address = order.getShippingAddress() != null ? 
                        order.getShippingAddress().toLowerCase() : "";
        
        return orderId.contains(searchText) ||
               userId.contains(searchText) ||
               address.contains(searchText);
    }

    /**
     * Filter orders by status
     */
    public List<AdminOrderModel> filterByStatus(String status) {
        if (status == null || status.trim().isEmpty() || "All".equals(status)) {
            return getAllOrders();
        }
        
        List<AdminOrderModel> allOrders = getAllOrders();
        
        return allOrders.stream()
            .filter(order -> order.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
    }

    // ==================== Data Formatting Methods ====================
    
    /**
     * Format order data for table display
     */
    public Object[] formatOrderForTable(AdminOrderModel order) {
        return new Object[]{
            order.getId(),
            order.getUserId(),
            formatDate(order.getCreatedAt()),
            formatAmount(order.getTotalAmount()),
            order.getStatus(),
            order.getShippingAddress() != null ? order.getShippingAddress() : "N/A"
        };
    }

    /**
     * Format date for display
     */
    public String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        return DATE_FORMAT.format(date);
    }

    /**
     * Format amount with currency
     */
    public String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "Rs 0.00";
        }
        return String.format("Rs %.2f", amount);
    }

    /**
     * Get status display color
     */
    public java.awt.Color getStatusColor(String status) {
        if (status == null) {
            return java.awt.Color.GRAY;
        }
        
        switch (status.toLowerCase()) {
            case "pending":
                return new java.awt.Color(255, 152, 0); // Orange
            case "processing":
                return new java.awt.Color(33, 150, 243); // Blue
            case "shipped":
                return new java.awt.Color(156, 39, 176); // Purple
            case "delivered":
                return new java.awt.Color(76, 175, 80); // Green
            case "cancelled":
                return new java.awt.Color(244, 67, 54); // Red
            default:
                return java.awt.Color.GRAY;
        }
    }

    // ==================== Statistics Methods ====================
    
    /**
     * Get total order count
     */
    public int getTotalOrderCount() {
        try {
            return orderDAO.getTotalOrderCount();
        } catch (Exception e) {
            System.err.println("Error getting order count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get total revenue from all orders
     */
    public BigDecimal getTotalRevenue() {
        List<AdminOrderModel> orders = getAllOrders();
        return orders.stream()
            .map(AdminOrderModel::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get order count by status
     */
    public int getOrderCountByStatus(String status) {
        return (int) getAllOrders().stream()
            .filter(order -> order.getStatus().equalsIgnoreCase(status))
            .count();
    }

    /**
     * Get total revenue for specific user
     */
    public BigDecimal getUserTotalSpent(int userId) {
        List<AdminOrderModel> userOrders = getOrdersByUserId(userId);
        return userOrders.stream()
            .map(AdminOrderModel::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get order count for specific user
     */
    public int getUserOrderCount(int userId) {
        return getOrdersByUserId(userId).size();
    }

    // ==================== Validation Methods ====================
    
    /**
     * Validate order status
     */
    private boolean isValidStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        
        String[] validStatuses = {"pending", "processing", "shipped", "delivered", "cancelled"};
        String statusLower = status.toLowerCase();
        
        for (String validStatus : validStatuses) {
            if (validStatus.equals(statusLower)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Check if status transition is valid
     */
    public boolean isValidStatusTransition(String currentStatus, String newStatus) {
        if (currentStatus == null || newStatus == null) {
            return false;
        }
        
        // Delivered and cancelled are terminal states
        if ("delivered".equalsIgnoreCase(currentStatus) || 
            "cancelled".equalsIgnoreCase(currentStatus)) {
            return false;
        }
        
        return !currentStatus.equalsIgnoreCase(newStatus);
    }

    /**
     * Get allowed status transitions for current status
     */
    public String[] getAllowedStatuses(String currentStatus) {
        if (currentStatus == null) {
            return new String[]{"pending", "processing", "shipped", "delivered", "cancelled"};
        }
        
        switch (currentStatus.toLowerCase()) {
            case "pending":
                return new String[]{"processing", "cancelled"};
            case "processing":
                return new String[]{"shipped", "cancelled"};
            case "shipped":
                return new String[]{"delivered"};
            default:
                return new String[]{};
        }
    }

    // ==================== State Check Methods ====================
    
    /**
     * Check if orders list is empty
     */
    public boolean hasOrders() {
        return !getAllOrders().isEmpty();
    }

    /**
     * Check if user has orders
     */
    public boolean userHasOrders(int userId) {
        return !getOrdersByUserId(userId).isEmpty();
    }
}