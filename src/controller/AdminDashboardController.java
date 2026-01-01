package controller;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.productDAO;
import dao.UserDao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import database.MySqlConnection;

public class AdminDashboardController {
    
    private final productDAO productDAO;
    private final UserDao userDAO;
    private final OrderDAO orderDAO;
    private final MySqlConnection db; 

    public AdminDashboardController(productDAO productDAO, UserDao userDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.orderDAO = new OrderDAOImpl();
        this.db = new MySqlConnection();
    }

    public int getTotalProducts() {
        try {
            return productDAO.getAllProducts().size();
        } catch (Exception e) {
            System.err.println("Error getting total products: " + e.getMessage());
            return 0;
        }
    }

    public int getTotalUsers() {
        try {
            return userDAO.getAllUsers().size();
        } catch (Exception e) {
            System.err.println("Error getting total users: " + e.getMessage());
            return 0;
        }
    }

    public int getTotalOrders() {
        try {
            return orderDAO.getTotalOrderCount();
        } catch (Exception e) {
            System.err.println("Error getting total orders: " + e.getMessage());
            return 0;
        }
    }

    public BigDecimal getTotalRevenue() {
        String query = "SELECT SUM(total_amount) as revenue FROM orders";
        Connection conn = null;
        
        try {
            conn = db.openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                BigDecimal revenue = rs.getBigDecimal("revenue");
                return revenue != null ? revenue : BigDecimal.ZERO;
            }
        } catch (Exception e) {
            System.err.println("Error getting total revenue: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return BigDecimal.ZERO;
    }

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
}