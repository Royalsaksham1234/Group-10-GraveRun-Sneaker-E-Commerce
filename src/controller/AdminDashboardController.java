package controller;

import dao.AdminOrderDAO;
import dao.AdminUserDAO;
import dao.AdminProductDAO;
import model.DashboardStats;
import java.math.BigDecimal;

/**
 * Controller for Admin Dashboard
 * Handles business logic for dashboard statistics
 * Follows strict MVC pattern - no direct database access
 * All DAOs injected via constructor (Dependency Injection)
 */
public class AdminDashboardController {
    
    private final AdminProductDAO productDAO;
    private final AdminUserDAO userDAO;
    private final AdminOrderDAO orderDAO;
    
    /**
     * Constructor with full dependency injection
     * All DAOs must be provided by the caller (View layer)
     */
    public AdminDashboardController(AdminProductDAO productDAO, 
                                   AdminUserDAO userDAO, 
                                   AdminOrderDAO orderDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
    }
    
    // ==================== Main Dashboard Data Method ====================
    
    /**
     * Get complete dashboard statistics
     * Returns a model object containing all dashboard data
     */
    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        try {
            stats.setTotalProducts(getTotalProducts());
            stats.setTotalUsers(getTotalUsers());
            stats.setTotalOrders(getTotalOrders());
            stats.setTotalRevenue(getTotalRevenue());
            stats.setActiveUsers(getActiveUsersCount());
            stats.setPendingOrders(getPendingOrdersCount());
            stats.setLowStockProducts(getLowStockProductsCount());
        } catch (Exception e) {
            System.err.println("Error loading dashboard stats: " + e.getMessage());
            e.printStackTrace();
        }
        
        return stats;
    }
    
    // ==================== Statistics Methods ====================
    
    /**
     * Get total number of products
     */
    public int getTotalProducts() {
        try {
            return productDAO.getAllProducts().size();
        } catch (Exception e) {
            System.err.println("Error getting total products: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get total number of users
     */
    public int getTotalUsers() {
        try {
            return userDAO.getAllUsers().size();
        } catch (Exception e) {
            System.err.println("Error getting total users: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get total number of orders
     */
    public int getTotalOrders() {
        try {
            return orderDAO.getTotalOrderCount();
        } catch (Exception e) {
            System.err.println("Error getting total orders: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get total revenue from all orders
     * Uses DAO method instead of direct database access
     */
    public BigDecimal getTotalRevenue() {
        try {
            return orderDAO.getTotalRevenue();
        } catch (Exception e) {
            System.err.println("Error getting total revenue: " + e.getMessage());
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
    
    /**
     * Get active users count
     */
    public int getActiveUsersCount() {
        try {
            return (int) userDAO.getAllUsers().stream()
                    .filter(user -> user.isActive())
                    .count();
        } catch (Exception e) {
            System.err.println("Error getting active users: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get pending orders count
     */
    public int getPendingOrdersCount() {
        try {
            return orderDAO.getOrderCountByStatus("pending");
        } catch (Exception e) {
            System.err.println("Error getting pending orders: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get low stock products count (products with stock < 10)
     */
    public int getLowStockProductsCount() {
        try {
            return (int) productDAO.getAllProducts().stream()
                    .filter(product -> product.getStockQuantity() < 10)
                    .count();
        } catch (Exception e) {
            System.err.println("Error getting low stock products: " + e.getMessage());
            return 0;
        }
    }
    
    // ==================== Formatting Methods (Business Logic) ====================
    
    /**
     * Format revenue for display (converts to K/M format)
     * Examples: 1500 -> "2k", 1500000 -> "1.5M"
     */
    public String formatRevenue(BigDecimal revenue) {
        if (revenue == null) {
            return "0";
        }
        
        double amount = revenue.doubleValue();
        
        if (amount >= 1000000) {
            return String.format("%.1fM", amount / 1000000);
        } else if (amount >= 1000) {
            return String.format("%.0fk", amount / 1000);
        } else {
            return String.format("%.0f", amount);
        }
    }
    
    /**
     * Format revenue with currency symbol
     */
    public String formatRevenueWithCurrency(BigDecimal revenue) {
        if (revenue == null) {
            return "Rs 0";
        }
        
        double amount = revenue.doubleValue();
        
        if (amount >= 1000000) {
            return String.format("Rs %.1fM", amount / 1000000);
        } else if (amount >= 1000) {
            return String.format("Rs %.0fk", amount / 1000);
        } else {
            return String.format("Rs %.2f", amount);
        }
    }
    
    /**
     * Format any number for display
     */
    public String formatNumber(int number) {
        return String.valueOf(number);
    }
}