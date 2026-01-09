package model;

import java.math.BigDecimal;

/**
 * Model class for Dashboard Statistics
 * Contains all data needed for the dashboard display
 * Pure data object - no business logic
 */
public class DashboardStats {
    
    private int totalProducts;
    private int totalUsers;
    private int totalOrders;
    private BigDecimal totalRevenue;
    private int activeUsers;
    private int pendingOrders;
    private int lowStockProducts;
    
    // ==================== Constructors ====================
    
    public DashboardStats() {
        this.totalRevenue = BigDecimal.ZERO;
    }
    
    public DashboardStats(int totalProducts, int totalUsers, int totalOrders, 
                         BigDecimal totalRevenue) {
        this.totalProducts = totalProducts;
        this.totalUsers = totalUsers;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
    }
    
    // ==================== Getters and Setters ====================
    
    public int getTotalProducts() {
        return totalProducts;
    }
    
    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }
    
    public int getTotalUsers() {
        return totalUsers;
    }
    
    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
    
    public int getTotalOrders() {
        return totalOrders;
    }
    
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
    
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
    }
    
    public int getActiveUsers() {
        return activeUsers;
    }
    
    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }
    
    public int getPendingOrders() {
        return pendingOrders;
    }
    
    public void setPendingOrders(int pendingOrders) {
        this.pendingOrders = pendingOrders;
    }
    
    public int getLowStockProducts() {
        return lowStockProducts;
    }
    
    public void setLowStockProducts(int lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }
    
    // ==================== Utility Methods ====================
    
    @Override
    public String toString() {
        return "DashboardStats{" +
                "totalProducts=" + totalProducts +
                ", totalUsers=" + totalUsers +
                ", totalOrders=" + totalOrders +
                ", totalRevenue=" + totalRevenue +
                ", activeUsers=" + activeUsers +
                ", pendingOrders=" + pendingOrders +
                ", lowStockProducts=" + lowStockProducts +
                '}';
    }
}