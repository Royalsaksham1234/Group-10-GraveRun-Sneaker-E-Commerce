package controller;

import view.Order_Tracking;
import dao.OrderTrackingDao;
import model.Order;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class OrderTrackingController {
    private final Order_Tracking view;
    private final OrderTrackingDao orderDAO;
    private Order currentOrder;
    private final Color RED_ACTIVE = new Color(255, 50, 50);
    private final Color GRAY_INACTIVE = new Color(150, 150, 150);
    private final Color GRAY_CANCELLED = new Color(100, 100, 100);
    
    private Timer refreshTimer;
    private JFrame previousPage;
    
    public OrderTrackingController(Order_Tracking view, JFrame previousPage) {
        this.view = view;
        this.orderDAO = new OrderTrackingDao();
        this.previousPage = previousPage;
        setupEventListeners();
        startAutoRefresh();
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
        
        // Logo click - navigate to Dashboard1
        view.getLogo().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navigateToDashboard();
            }
        });
    }
    
    /**
     * Load most recent order for user
     */
    public void loadMostRecentOrder(int userId) {
        currentOrder = orderDAO.getMostRecentOrderByUserId(userId);
        if (currentOrder != null) {
            updateUI();
        } else {
            JOptionPane.showMessageDialog(view, 
                "No orders found for your account!", 
                "No Orders", 
                JOptionPane.INFORMATION_MESSAGE);
            handleGoBack();
        }
    }
    
    /**
     * Start auto-refresh timer (polls database every 10 seconds)
     */
    private void startAutoRefresh() {
        refreshTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentOrder != null && !currentOrder.getStatus().equalsIgnoreCase("Cancelled") 
                    && !currentOrder.getStatus().equalsIgnoreCase("Delivered")) {
                    refreshOrderStatus();
                }
            }
        });
        refreshTimer.start();
    }
    
    /**
     * Refresh order status from database
     */
    private void refreshOrderStatus() {
        Order updatedOrder = orderDAO.getOrderById(currentOrder.getId());
        if (updatedOrder != null && !updatedOrder.getStatus().equals(currentOrder.getStatus())) {
            currentOrder = updatedOrder;
            updateUI();
            
            // Show notification of status change
            JOptionPane.showMessageDialog(view,
                "Order status updated to: " + mapDatabaseStatusToDisplay(currentOrder.getStatus()),
                "Status Update",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Update UI with current order data
     */
    public void updateUI() {
        // Update order information
        view.getOrderLabel().setText("ORDER: #" + currentOrder.getId());
        view.getEstimatedTimeLabel().setText("ESTIMATED TIME: " + currentOrder.getEstimatedTime());
        view.getPaymentLabel().setText("PAYMENT: " + currentOrder.getPaymentMethod());
        view.getDeliveryLabel().setText("DELIVERY: " + currentOrder.getDeliveryType());
        
        // Update timeline based on status
        updateTimeline(currentOrder.getStatus());
    }
    
    /**
     * Update visual timeline based on order status
     * Maps database enum to tracking UI statuses
     */
    public void updateTimeline(String dbStatus) {
        // Reset all steps to inactive
        resetTimelineToGray();
        
        // Map database status to tracking steps
        // Database: Pending, Processing, Shipped, Delivered, Cancelled
        // Tracking: PLACED, CONFIRMED, PROCESSED, READY
        switch (dbStatus.toLowerCase()) {
            case "pending":
                // Show as "PLACED"
                activateStep(1);
                break;
            case "processing":
                // Show as "CONFIRMED" and "PROCESSED"
                activateStep(1);
                activateStep(2);
                activateStep(3);
                break;
            case "shipped":
                // Show as "READY"
                activateStep(1);
                activateStep(2);
                activateStep(3);
                activateStep(4);
                view.getCancelButton().setEnabled(false);
                break;
            case "delivered":
                // All steps complete
                activateStep(1);
                activateStep(2);
                activateStep(3);
                activateStep(4);
                view.getCancelButton().setEnabled(false);
                stopAutoRefresh();
                break;
            case "cancelled":
                // Cancelled state
                deactivateAllSteps();
                view.getCancelButton().setEnabled(false);
                view.getCancelButton().setText("CANCELLED");
                stopAutoRefresh();
                break;
        }
    }
    
    /**
     * Map database status to display name
     */
    private String mapDatabaseStatusToDisplay(String dbStatus) {
        switch (dbStatus.toLowerCase()) {
            case "pending": return "Order Placed";
            case "processing": return "Processing";
            case "shipped": return "Ready to Pickup";
            case "delivered": return "Delivered";
            case "cancelled": return "Cancelled";
            default: return dbStatus;
        }
    }
    
    private void activateStep(int stepNumber) {
        switch (stepNumber) {
            case 1:
                view.getOOneLabel().setText("●");
                view.getOOneLabel().setForeground(RED_ACTIVE);
                view.getOrderPlacedLabel().setForeground(RED_ACTIVE);
                view.getWeHaveReceivedYourOrderLabel().setForeground(RED_ACTIVE);
                break;
            case 2:
                view.getOTwoLabel().setText("●");
                view.getOTwoLabel().setForeground(RED_ACTIVE);
                view.getOrderConfirmedLabel().setForeground(RED_ACTIVE);
                view.getYourOrderHasBeenConfirmedLabel().setForeground(RED_ACTIVE);
                break;
            case 3:
                view.getOThreeLabel().setText("●");
                view.getOThreeLabel().setForeground(RED_ACTIVE);
                view.getOrderProcessedLabel().setForeground(RED_ACTIVE);
                view.getWeArePreparingYourOrderLabel().setForeground(RED_ACTIVE);
                break;
            case 4:
                view.getOFourLabel().setText("●");
                view.getOFourLabel().setForeground(RED_ACTIVE);
                view.getReadyToPickupLabel().setForeground(RED_ACTIVE);
                view.getYourOrderIsReadyForPickupLabel().setForeground(RED_ACTIVE);
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
        
        view.getWeHaveReceivedYourOrderLabel().setForeground(GRAY_INACTIVE);
        view.getYourOrderHasBeenConfirmedLabel().setForeground(GRAY_INACTIVE);
        view.getWeArePreparingYourOrderLabel().setForeground(GRAY_INACTIVE);
        view.getYourOrderIsReadyForPickupLabel().setForeground(GRAY_INACTIVE);
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
        
        view.getWeHaveReceivedYourOrderLabel().setForeground(GRAY_CANCELLED);
        view.getYourOrderHasBeenConfirmedLabel().setForeground(GRAY_CANCELLED);
        view.getWeArePreparingYourOrderLabel().setForeground(GRAY_CANCELLED);
        view.getYourOrderIsReadyForPickupLabel().setForeground(GRAY_CANCELLED);
    }
    
    /**
     * Handle order cancellation with confirmation dialog
     */
    private void handleCancelOrder() {
        if (currentOrder.getStatus().equalsIgnoreCase("Cancelled")) {
            JOptionPane.showMessageDialog(view,
                "This order is already cancelled!",
                "Already Cancelled",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (currentOrder.getStatus().equalsIgnoreCase("Delivered")) {
            JOptionPane.showMessageDialog(view,
                "Cannot cancel a delivered order!",
                "Cannot Cancel",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Show confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(view,
            "Are you sure you want to cancel Order #" + currentOrder.getId() + "?\n" +
            "Total Amount: Rs " + String.format("%.2f", currentOrder.getTotalAmount()),
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = orderDAO.cancelOrder(currentOrder.getId());
            if (success) {
                currentOrder.setStatus("Cancelled");
                updateTimeline("Cancelled");
                
                JOptionPane.showMessageDialog(view,
                    "Order #" + currentOrder.getId() + " has been cancelled successfully!",
                    "Cancellation Complete",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Navigate to Dashboard1
                navigateToDashboard();
            } else {
                JOptionPane.showMessageDialog(view,
                    "Failed to cancel order. Please try again!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Handle back button - navigate to previous screen
     */
    private void handleGoBack() {
        stopAutoRefresh();
        view.dispose();
        
        // Show previous page
        if (previousPage != null) {
            previousPage.setVisible(true);
        } else {
            // Fallback to Dashboard1 if no previous page
            navigateToDashboard();
        }
    }
    
    /**
     * Navigate to Dashboard1
     */
    private void navigateToDashboard() {
        stopAutoRefresh();
        view.dispose();
        new view.Dashboard1().setVisible(true);
    }
    
    /**
     * Stop auto-refresh timer
     */
    private void stopAutoRefresh() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
    }
    
    public void showScreen() {
        view.setVisible(true);
    }
    
    public Order getCurrentOrder() {
        return currentOrder;
    }
}