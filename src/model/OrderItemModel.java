package model;

import java.math.BigDecimal;

public class OrderItemModel {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
    
    // Additional fields for displaying product info
    private String productName;
    private String productBrand;
    private String productImageUrl;

    // Default constructor
    public OrderItemModel() {
    }

    // Constructor
    public OrderItemModel(int orderId, int productId, int quantity, 
                         BigDecimal price, BigDecimal subtotal) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    @Override
    public String toString() {
        return "OrderItemModel{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", subtotal=" + subtotal +
                '}';
    }
}