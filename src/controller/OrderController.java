package controller;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import model.OrderModel;
import model.OrderItemModel;
import java.util.List;

public class OrderController {
    
    private final OrderDAO orderDAO;

    public OrderController() {
        this.orderDAO = new OrderDAOImpl();
    }

    public List<OrderModel> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public OrderModel getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public List<OrderItemModel> getOrderItems(int orderId) {
        return orderDAO.getOrderItems(orderId);
    }

    public List<OrderModel> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    public boolean updateOrderStatus(int orderId, String status) {
        return orderDAO.updateOrderStatus(orderId, status);
    }

    public int getTotalOrderCount() {
        return orderDAO.getTotalOrderCount();
    }
}