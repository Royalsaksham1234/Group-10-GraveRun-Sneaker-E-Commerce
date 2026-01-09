package controller;

import dao.CartDAO;
import model.CartItem;
import model.ProductModel;
import view.CartView;
import view.ProductCartPanel;

import javax.swing.*;

public class CartController {

    private final CartView view;
    private final CartDAO cartDAO;

    // Only one constructor needed
    public CartController(CartView view) {
        this.view = view;
        this.cartDAO = new CartDAO(); // ✅ create DAO internally
        loadCart();
    }

    // ---------------- LOAD CART ----------------
    private void loadCart() {
        JPanel container = view.getProductContainer();
        container.removeAll();

        for (CartItem item : cartDAO.getAllItems()) {
            ProductCartPanel panel = createPanel(item);
            container.add(panel);
        }

        container.revalidate();
        container.repaint();
    }

    // ---------------- PANEL FACTORY ----------------
    private ProductCartPanel createPanel(CartItem item) {
        ProductModel product = item.getProduct();

        ProductCartPanel panel = new ProductCartPanel();
        panel.setProductId(product.getProductId());
        panel.setProductName(product.getName());
        panel.setProductImage(product.getImageUrl());
        panel.setUnitPrice(product.getPrice());
        panel.setQuantity(item.getQuantity());

        // quantity + / -
        panel.addQuantityListeners(() -> {
            cartDAO.updateQuantity(product, panel.getQuantity());
        });

        // delete product
        panel.addDeleteListener(() -> {
            cartDAO.removeProduct(product.getProductId());
            loadCart();
        });

        return panel;
    }

    // ---------------- EXTERNAL API ----------------
    public void addProductToCart(ProductModel product) {
        cartDAO.addProduct(product);
        loadCart();
    }
}
