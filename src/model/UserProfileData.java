package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserProfileData {

    private int userId;
    private String username;
    private String email;

    public static class Order {
        private String productName;
        private int quantity;
        private double totalAmount;
        private String status;
        private Timestamp orderDate;

        public Order(String productName, int quantity, double totalAmount, String status, Timestamp orderDate) {
            this.productName = productName;
            this.quantity = quantity;
            this.totalAmount = totalAmount;
            this.status = status;
            this.orderDate = orderDate;
        }

        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public double getTotalAmount() { return totalAmount; }
        public String getStatus() { return status; }
        public Timestamp getOrderDate() { return orderDate; }
    }

    private List<Order> orders;

    public UserProfileData(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.orders = new ArrayList<>();
    }

    public void addOrder(String productName, int quantity, double totalAmount, String status, Timestamp orderDate) {
        orders.add(new Order(productName, quantity, totalAmount, status, orderDate));
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public List<Order> getOrders() { return orders; }
}
