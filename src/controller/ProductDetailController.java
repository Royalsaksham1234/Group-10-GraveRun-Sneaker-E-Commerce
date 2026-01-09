package controller;

import dao.CartDAO;
import dao.FavoriteDAO;
import java.awt.BorderLayout;
import model.ProductModel;
import util.SessionManager;
import view.*;

import javax.swing.*;

public class ProductDetailController {

    private final ProductDetail view;
    private final ProductModel product;
    private final CartDAO cartDAO;
    private final FavoriteDAO favoriteDAO;

    public ProductDetailController(ProductDetail view, ProductModel product,
                                   CartDAO cartDAO, FavoriteDAO favoriteDAO) {
        this.view = view;
        this.product = product;
        this.cartDAO = cartDAO;
        this.favoriteDAO = favoriteDAO;

        attachListeners(); // Attach all button listeners
    }

    private void attachListeners() {
        view.getAddToCartButton().addActionListener(e -> addToCart());
        view.getBuyNowButton().addActionListener(e -> handleBuyNow());
        view.getFavoriteButton().addActionListener(e -> addToFavorite());
    }

    private boolean ensureLoggedIn() {
        if (SessionManager.getCurrentUser() != null) return true;

        int choice = JOptionPane.showConfirmDialog(view,
                "You need to login/signup first. Signup now?",
                "Login Required", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            GraveRunSignup signup = new GraveRunSignup(null, true);
            signup.setLocationRelativeTo(null);
            signup.setVisible(true);
        }
        return SessionManager.getCurrentUser() != null;
    }

    private void addToCart() {
        if (!ensureLoggedIn()) return;

        String size = view.getSelectedSize();
        if (size == null || size.equals("Select Size")) {
            JOptionPane.showMessageDialog(view, "Please select a size", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        product.setSize(size);
        cartDAO.addProduct(product);
        JOptionPane.showMessageDialog(view, product.getName() + " added to cart!");
    }

private void handleBuyNow() {
    if (!ensureLoggedIn()) return;

    String size = view.getSelectedSize();
    if (size == null || size.equals("Select Size")) {
        JOptionPane.showMessageDialog(
                view,
                "Please select a shoe size",
                "Size Required",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    product.setSize(size);

    // Get parent dashboard container
    Dashboard1 dashboard = (Dashboard1) SwingUtilities.getWindowAncestor(view);

    // Use dashboard method to show OrderConfirm panel
    dashboard.showOrderConfirm(product);  // ✅ pass the correct product
}



    private void addToFavorite() {
        if (!ensureLoggedIn()) return;
        favoriteDAO.add(product);
        JOptionPane.showMessageDialog(view, product.getName() + " added to favorites!");
    }
}
