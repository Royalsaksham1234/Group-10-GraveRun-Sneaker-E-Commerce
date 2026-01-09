package controller;

import dao.CartDAO;
import dao.FavoriteDAO;
import model.AdminProductModel;
import util.SessionManager;
import view.*;
import javax.swing.*;

public class ProductDetailController {
    private final ProductDetail view;
    private final AdminProductModel product;
    private final CartDAO cartDAO;
    private final FavoriteDAO favoriteDAO;
    
    public ProductDetailController(ProductDetail view, AdminProductModel product,
                                   CartDAO cartDAO, FavoriteDAO favoriteDAO) {
        this.view = view;
        this.product = product;
        this.cartDAO = cartDAO;
        this.favoriteDAO = favoriteDAO;
        attachListeners();
    }
    
    /**
     * ATTACH ALL BUTTON LISTENERS
     */
    private void attachListeners() {
        view.getAddToCartButton().addActionListener(e -> addToCart());
        view.getBuyNowButton().addActionListener(e -> handleBuyNow());
        view.getFavoriteButton().addActionListener(e -> addToFavorite());
    }
    
    /**
     * ENSURE USER IS LOGGED IN
     * @return true if logged in, false otherwise
     */
    private boolean ensureLoggedIn() {
        if (SessionManager.getCurrentUser() != null) {
            return true;
        }
        
        int choice = JOptionPane.showConfirmDialog(view,
                "You need to login/signup first. Signup now?",
                "Login Required", 
                JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            GraveRunSignup signup = new GraveRunSignup(null, true);
            signup.setLocationRelativeTo(null);
            signup.setVisible(true);
        }
        
        return SessionManager.getCurrentUser() != null;
    }
    
    /**
     * ADD PRODUCT TO CART
     */
    private void addToCart() {
        if (!ensureLoggedIn()) return;
        
        String size = view.getSelectedSize();
        if (size == null || size.equals("Select Size")) {
            JOptionPane.showMessageDialog(view, 
                "Please select a size", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        product.setSize(size);
        cartDAO.addProduct(product);
        JOptionPane.showMessageDialog(view, 
            product.getName() + " added to cart!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * ADD PRODUCT TO FAVORITES
     */
    private void addToFavorite() {
        if (!ensureLoggedIn()) return;
        
        try {
            int productId = product.getProductId();
            
            // Check if already in favorites
            if (favoriteDAO.isFavourite(productId)) {
                JOptionPane.showMessageDialog(view, 
                    product.getName() + " is already in your favorites!",
                    "Already Favorited",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Add to favorites
            favoriteDAO.add(product);
            JOptionPane.showMessageDialog(view, 
                product.getName() + " added to favorites!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Error adding to favorites: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * HANDLE BUY NOW BUTTON
     */
    private void handleBuyNow() {
        if (!ensureLoggedIn()) return;
        
        String size = view.getSelectedSize();
        if (size == null || size.equals("Select Size")) {
            JOptionPane.showMessageDialog(view,
                "Please select a shoe size",
                "Size Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        product.setSize(size);
        
        // Get parent dashboard and its controller
        Dashboard1 dashboard = (Dashboard1) SwingUtilities.getWindowAncestor(view);
        if (dashboard != null) {
            dashboard.getController().showOrderConfirm(product);
        } else {
            JOptionPane.showMessageDialog(view,
                "Unable to proceed with order. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}