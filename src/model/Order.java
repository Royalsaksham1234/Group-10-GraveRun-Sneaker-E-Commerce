/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Order {
    private String orderId;
    private String estimatedTime;
    private String paymentMethod;
    private String deliveryType;
    private String status; // PLACED, CONFIRMED, PROCESSED, READY, CANCELLED
    
    public Order(String orderId, String estimatedTime, String paymentMethod, 
                 String deliveryType, String status) {
        this.orderId = orderId;
        this.estimatedTime = estimatedTime;
        this.paymentMethod = paymentMethod;
        this.deliveryType = deliveryType;
        this.status = status;
    }
    
    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getDeliveryType() { return deliveryType; }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}