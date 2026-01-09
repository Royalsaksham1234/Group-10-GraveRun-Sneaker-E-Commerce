package model;

import java.math.BigDecimal;

public class CartItem {
    private ProductModel product;
    private int quantity;

    public CartItem(ProductModel product, int quantity) {
        this.product = product;
        this.quantity = Math.max(1, quantity);
    }

    public ProductModel getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = Math.max(1, quantity); }
    public void increase() { quantity++; }
    public void decrease() { if (quantity > 1) quantity--; }

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}