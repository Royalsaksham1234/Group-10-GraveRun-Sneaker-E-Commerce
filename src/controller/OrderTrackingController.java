/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.Order_Tracking;
import dao.OrderDAO;
import model.Order;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class OrderTrackingController {
    private final Order_Tracking view;
    private final OrderDAO orderDAO;
    private Order currentOrder;
    private final Color RED_ACTIVE = new Color(255, 50, 50);
    private final Color GRAY_INACTIVE = new Color(150, 150, 150);
    private final Color GRAY_CANCELLED = new Color(100, 100, 100);
    
    public OrderTrackingController(Order_Tracking view) {
        this.view = view;
        this.orderDAO = new OrderDAO();
        setupEventListeners();
    }
    
    private void setupEventListeners() {
        // Cancel button action
        view.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancelOrder();
            }
        });
        
        // Back button action
        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGoBack();
            }
        });
    }
    
    public void loadOrder(String orderId) {
        currentOrder = orderDAO.getOrderById(orderId);
        if (currentOrder != null) {
            updateUI();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Order #" + orderId + " not found!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void updateUI() {
        // Update order information
        view.getOrderLabel().setText("ORDER: #" + currentOrder.getOrderId());
        view.getEstimatedTimeLabel().setText("ESTIMATED TIME: " + currentOrder.getEstimatedTime());
        view.getPaymentLabel().setText("PAYMENT: " + currentOrder.getPaymentMethod());
        view.getDeliveryLabel().setText("DELIVERY: " + currentOrder.getDeliveryType());
        
        // Update timeline based on status
        updateTimeline(currentOrder.getStatus());
    }
    
    public void updateTimeline(String status) {
        // Reset all steps to inactive
        resetTimelineToGray();
        
        // Activate steps based on current status
        switch (status.toUpperCase()) {
            case "PLACED":
                activateStep(1);
                break;
            case "CONFIRMED":
                activateStep(1);
                activateStep(2);
                break;
            case "PROCESSED":
                activateStep(1);
                activateStep(2);
                activateStep(3);
                break;
            case "READY":
                activateStep(1);
                activateStep(2);
                activateStep(3);
                activateStep(4);
                view.getCancelButton().setEnabled(false);
                break;
            case "CANCELLED":
                deactivateAllSteps();
                view.getCancelButton().setEnabled(false);
                view.getCancelButton().setText("CANCELLED");
                break;
        }
    }
    
    private void activateStep(int stepNumber) {
        switch (stepNumber) {
            case 1:
                view.getOOneLabel().setText("●");
                view.getOOneLabel().setForeground(RED_ACTIVE);
                view.getOrderPlacedLabel().setForeground(RED_ACTIVE);
                break;
            case 2:
                view.getOTwoLabel().setText("●");
                view.getOTwoLabel().setForeground(RED_ACTIVE);
                view.getOrderConfirmedLabel().setForeground(RED_ACTIVE);
                break;
            case 3:
                view.getOThreeLabel().setText("●");
                view.getOThreeLabel().setForeground(RED_ACTIVE);
                view.getOrderProcessedLabel().setForeground(RED_ACTIVE);
                break;
            case 4:
                view.getOFourLabel().setText("●");
                view.getOFourLabel().setForeground(RED_ACTIVE);
                view.getReadyToPickupLabel().setForeground(RED_ACTIVE);
                break;
        }
    }
    
    private void resetTimelineToGray() {
        // Reset icons to ○ and set to gray
        view.getOOneLabel().setText("○");
        view.getOTwoLabel().setText("○");
        view.getOThreeLabel().setText("○");
        view.getOFourLabel().setText("○");
        
        view.getOOneLabel().setForeground(GRAY_INACTIVE);
        view.getOTwoLabel().setForeground(GRAY_INACTIVE);
        view.getOThreeLabel().setForeground(GRAY_INACTIVE);
        view.getOFourLabel().setForeground(GRAY_INACTIVE);
        
        view.getOrderPlacedLabel().setForeground(GRAY_INACTIVE);
        view.getOrderConfirmedLabel().setForeground(GRAY_INACTIVE);
        view.getOrderProcessedLabel().setForeground(GRAY_INACTIVE);
        view.getReadyToPickupLabel().setForeground(GRAY_INACTIVE);
    }
    
    private void deactivateAllSteps() {
        resetTimelineToGray();
        
        // Set all to cancelled color
        view.getOOneLabel().setForeground(GRAY_CANCELLED);
        view.getOTwoLabel().setForeground(GRAY_CANCELLED);
        view.getOThreeLabel().setForeground(GRAY_CANCELLED);
        view.getOFourLabel().setForeground(GRAY_CANCELLED);
        
        view.getOrderPlacedLabel().setForeground(GRAY_CANCELLED);
        view.getOrderConfirmedLabel().setForeground(GRAY_CANCELLED);
        view.getOrderProcessedLabel().setForeground(GRAY_CANCELLED);
        view.getReadyToPickupLabel().setForeground(GRAY_CANCELLED);
    }
    
    private void handleCancelOrder() {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Are you sure you want to cancel Order #" + currentOrder.getOrderId() + "?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION && currentOrder != null) {
            boolean success = orderDAO.cancelOrder(currentOrder.getOrderId());
            if (success) {
                currentOrder.setStatus("CANCELLED");
                updateTimeline("CANCELLED");
                JOptionPane.showMessageDialog(view,
                    "Order #" + currentOrder.getOrderId() + " has been cancelled!",
                    "Cancellation Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view,
                    "Failed to cancel order. Please try again!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleGoBack() {
        view.dispose();
        // Add navigation logic to return to previous screen
    }
    
    public void simulateStatusUpdate(String newStatus) {
        if (currentOrder != null && orderDAO.updateOrderStatus(currentOrder.getOrderId(), newStatus)) {
            currentOrder.setStatus(newStatus);
            updateTimeline(newStatus);
            JOptionPane.showMessageDialog(view,
                "Order status updated to: " + newStatus,
                "Status Updated",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void showScreen() {
        view.setVisible(true);
    }
    
    public Order getCurrentOrder() {
        return currentOrder;
    }
}