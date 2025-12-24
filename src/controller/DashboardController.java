package controller;

import view.dashboard;
import dao.productDAO;
import dao.productDAOImpl;
import dao.favoriteDAO;
import dao.favoriteDAOImpl;
import dao.userDAO;
import dao.userDAOImpl;
import java.awt.HeadlessException;
import model.ProductModel;
import model.UserData;
import util.SessionManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DashboardController {
    private dashboard view;
    private productDAO productDao;
    private favoriteDAO favoriteDao;
    private userDAO userDao;
    
    // Store product data for buttons
    private ProductModel product1, product2, product3, product4;
    
    public DashboardController() {
        this.productDao = new productDAOImpl();
        this.favoriteDao = new favoriteDAOImpl();
        this.userDao = new userDAOImpl();
        this.view = new dashboard();
        initController();
        loadProducts();
    }
    
    private void initController() {
        // Remove existing login/signup button texts since user is already logged in
        updateAuthButtons();
        
        // Set welcome message
        UserData currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            view.getLogin().setText("Profile");
            view.getSignup().setText("Logout");
        }
        
        // Setup event listeners
        setupEventListeners();
        
        // Make product panels clickable
        setupProductPanelListeners();
    }
    
    private void updateAuthButtons() {
        if (SessionManager.isLoggedIn()) {
            UserData currentUser = SessionManager.getCurrentUser();
            view.getLogin().setText("Profile");
            view.getSignup().setText("Logout");
        }
    }
    
    private void loadProducts() {
        try {
            // Get featured products
            List<ProductModel> featuredProducts = productDao.getFeaturedProducts();
            
            // Display first 4 products
            if (featuredProducts.size() >= 4) {
                product1 = featuredProducts.get(0);
                product2 = featuredProducts.get(1);
                product3 = featuredProducts.get(2);
                product4 = featuredProducts.get(3);
                
                // Update product 1
                if (view.getShoeImg() != null) {
                    // Set image if available
                    // view.getShoeImg().setIcon(new ImageIcon(product1.getImageUrl()));
                }
                if (view.getShoeName() != null) {
                    view.getShoeName().setText(product1.getName());
                }
                if (view.getPrice() != null) {
                    view.getPrice().setText("$" + product1.getPrice().toString());
                }
                
                // Update product 2
                if (view.getjLabel6() != null) {
                    // view.getjLabel6().setIcon(new ImageIcon(product2.getImageUrl()));
                }
                if (view.getjLabel9() != null) {
                    view.getjLabel9().setText(product2.getName());
                }
                if (view.getjLabel10() != null) {
                    view.getjLabel10().setText("$" + product2.getPrice().toString());
                }
                
                // Update product 3
                if (view.getjLabel11() != null) {
                    // view.getjLabel11().setIcon(new ImageIcon(product3.getImageUrl()));
                }
                if (view.getjLabel12() != null) {
                    view.getjLabel12().setText(product3.getName());
                }
                if (view.getjLabel13() != null) {
                    view.getjLabel13().setText("$" + product3.getPrice().toString());
                }
                
                // Update product 4
                if (view.getjLabel14() != null) {
                    // view.getjLabel14().setIcon(new ImageIcon(product4.getImageUrl()));
                }
                if (view.getjLabel15() != null) {
                    view.getjLabel15().setText(product4.getName());
                }
                if (view.getjLabel16() != null) {
                    view.getjLabel16().setText("$" + product4.getPrice().toString());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                "Error loading products: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void setupEventListeners() {
        // Search button listener
        view.AddbtnSearchListener((ActionEvent e) -> {
            handleSearch();
        });
        
        // Login/Profile button
        view.AddloginListener((ActionEvent e) -> {
            handleProfile();
        });
        
        // Signup/Logout button
        view.AddSignupListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
        
        // Banner Buy Now button
        view.AddgetBuynowListener((ActionEvent e) -> {
            handleBannerBuyNow();
        });
        
        // Categories button
        view.getCategoriesListener((ActionEvent e) -> {
            showCategories();
        });
        
        // Logo button
        view.getLogoListener((ActionEvent e) -> {
            refreshDashboard();
        });
        
        // Product Buy Now buttons
        view.getBuyNow1Listener((ActionEvent e) -> {
            handleBuyNow(product1);
        });
        
        view.getBuyNow2Listener((ActionEvent e) -> {
            handleBuyNow(product2);
        });
        
        view.getBuyNow3Listener((ActionEvent e) -> {
            handleBuyNow(product3);
        });
        
        view.getBuyNow4Listener((ActionEvent e) -> {
            handleBuyNow(product4);
        });
        
        // Favorite buttons
        view.getFavourite1Listener((ActionEvent e) -> {
            handleFavorite(product1);
        });
        
        view.getFavourite2Listener((ActionEvent e) -> {
            handleFavorite(product2);
        });
        
        view.getFavourite3Listener((ActionEvent e) -> {
            handleFavorite(product3);
        });
        
        view.getFavourite4Listener((ActionEvent e) -> {
            handleFavorite(product4);
        });
    }
    
    private void setupProductPanelListeners() {
        // Make product panels clickable for details
        if (view.getProductLabel1() != null) {
            view.getProductLabel1().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showProductDetails(product1);
                }
            });
        }
        
        if (view.getProductlabel2() != null) {
            view.getProductlabel2().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showProductDetails(product2);
                }
            });
        }
        
        if (view.getProductlabel3() != null) {
            view.getProductlabel3().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showProductDetails(product3);
                }
            });
        }
        
        if (view.getProductlevel4() != null) {
            view.getProductlevel4().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showProductDetails(product4);
                }
            });
        }
    }
    
    // === IMPLEMENTED METHODS ===
    
    private void handleSearch() {
        String searchTerm = view.getTxtSearch().getText().trim();
        
        if (searchTerm.isEmpty() || searchTerm.equals("Search sneakers, brands or collections…")) {
            JOptionPane.showMessageDialog(view,
                "Please enter a search term!",
                "Search Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            List<ProductModel> searchResults = productDao.searchProducts(searchTerm);
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                    "No products found for: " + searchTerm,
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                // You could implement a search results view here
                JOptionPane.showMessageDialog(view,
                    "Found " + searchResults.size() + " products for: " + searchTerm,
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Show first product details
                if (!searchResults.isEmpty()) {
                    showProductDetails(searchResults.get(0));
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(view,
                "Error searching products: " + e.getMessage(),
                "Search Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void handleProfile() {
        if (SessionManager.isLoggedIn()) {
            UserData currentUser = SessionManager.getCurrentUser();
            showUserProfile(currentUser);
        } else {
            // Navigate to login
            navigateToLogin();
        }
    }
    
    private void handleLogout() {
        if (SessionManager.isLoggedIn()) {
            int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.logout();
                view.dispose();
                navigateToLogin();
            }
        } else {
            // Navigate to signup
            navigateToSignup();
        }
    }
    
    private void handleBannerBuyNow() {
        JOptionPane.showMessageDialog(view,
            "Banner product coming soon!",
            "Featured Product",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showCategories() {
        String[] categories = {"All", "Running", "Basketball", "Casual", "Lifestyle"};
        String selected = (String) JOptionPane.showInputDialog(view,
            "Select a category:",
            "Categories",
            JOptionPane.QUESTION_MESSAGE,
            null,
            categories,
            categories[0]);
        
        if (selected != null) {
            try {
                List<ProductModel> products;
                if (selected.equals("All")) {
                    products = productDao.getAllProducts();
                } else {
                    products = productDao.getProductsByCategory(selected);
                }
                
                if (products.isEmpty()) {
                    JOptionPane.showMessageDialog(view,
                        "No products found in " + selected + " category",
                        "Category Results",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view,
                        "Found " + products.size() + " products in " + selected + " category",
                        "Category Results",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view,
                    "Error loading category: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshDashboard() {
        loadProducts();
        JOptionPane.showMessageDialog(view,
            "Dashboard refreshed!",
            "Refresh",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleBuyNow(ProductModel product) {
        if (product == null) {
            JOptionPane.showMessageDialog(view,
                "Product not available!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!SessionManager.isLoggedIn()) {
            int option = JOptionPane.showConfirmDialog(view,
                "You need to login to purchase. Login now?",
                "Login Required",
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                navigateToLogin();
            }
            return;
        }
        
        if (!product.isInStock()) {
            JOptionPane.showMessageDialog(view,
                product.getName() + " is out of stock!",
                "Stock Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Show product details with purchase option
        showProductDetails(product);
    }
    
    private void handleFavorite(ProductModel product) {
        if (product == null) return;
        
        if (!SessionManager.isLoggedIn()) {
            int option = JOptionPane.showConfirmDialog(view,
                "You need to login to add favorites. Login now?",
                "Login Required",
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                navigateToLogin();
            }
            return;
        }
        
        try {
            int userId = SessionManager.getCurrentUserId();
            boolean isFavorite = favoriteDao.isFavorite(userId, product.getProductId());
            
            if (isFavorite) {
                // Remove from favorites
                if (favoriteDao.removeFromFavorites(userId, product.getProductId())) {
                    JOptionPane.showMessageDialog(view,
                        "Removed " + product.getName() + " from favorites",
                        "Favorites",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // Add to favorites
                if (favoriteDao.addToFavorites(userId, product.getProductId())) {
                    JOptionPane.showMessageDialog(view,
                        "Added " + product.getName() + " to favorites",
                        "Favorites",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                "Error updating favorites: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // === HELPER METHODS ===
    
    private void showProductDetails(ProductModel product) {
        if (product == null) return;
        
        String message = "Product: " + product.getName() + "\n" +
                        "Brand: " + product.getBrand() + "\n" +
                        "Price: $" + product.getPrice() + "\n" +
                        "Category: " + product.getCategory() + "\n" +
                        "In Stock: " + (product.isInStock() ? "Yes" : "No") + "\n" +
                        "Description: " + product.getDescription();
        
        if (SessionManager.isLoggedIn()) {
            Object[] options = {"Add to Cart", "View Details", "Cancel"};
            int choice = JOptionPane.showOptionDialog(view,
                message,
                "Product Details",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
            
            if (choice == 0) {
                // Add to cart
                addToCart(product);
            } else if (choice == 1) {
                // Show more details
                showFullProductDetails(product);
            }
        } else {
            JOptionPane.showMessageDialog(view,
                message,
                "Product Details",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void addToCart(ProductModel product) {
        if (!product.isInStock()) {
            JOptionPane.showMessageDialog(view,
                product.getName() + " is out of stock!",
                "Cart Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // You would implement cart logic here
        JOptionPane.showMessageDialog(view,
            "Added " + product.getName() + " to cart!\nPrice: $" + product.getPrice(),
            "Cart",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showFullProductDetails(ProductModel product) {
        String details = "=== PRODUCT DETAILS ===\n\n" +
                        "Name: " + product.getName() + "\n" +
                        "Brand: " + product.getBrand() + "\n" +
                        "Category: " + product.getCategory() + "\n" +
                        "Price: NRs" + product.getPrice() + "\n" +
                        "Size: " + (product.getSize() != null ? product.getSize() : "Various") + "\n" +
                        "Color: " + (product.getColor() != null ? product.getColor() : "Multiple") + "\n" +
                        "Stock: " + product.getStockQuantity() + " units\n" +
                        "Status: " + (product.isInStock() ? "In Stock" : "Out of Stock") + "\n\n" +
                        "Description:\n" + product.getDescription();
        
        JOptionPane.showMessageDialog(view,
            details,
            "Full Product Details - " + product.getName(),
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showUserProfile(UserData user) {
        String profile = "=== USER PROFILE ===\n\n" +
                        "Username: " + user.getUsername() + "\n" +
                        "Email: " + user.getEmail() + "\n" +
                        "Full Name: " + (user.getFullName() != null ? user.getFullName() : "Not set") + "\n" +
                        "Phone: " + (user.getPhone() != null ? user.getPhone() : "Not set") + "\n" +
                        "Address: " + (user.getAddress() != null ? user.getAddress() : "Not set") + "\n" +
                        "Member Since: " + (user.getCreatedAt() != null ? user.getCreatedAt().toString() : "Recently");
        
        JOptionPane.showMessageDialog(view,
            profile,
            "My Profile",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void navigateToLogin() {
        view.dispose();
        java.awt.EventQueue.invokeLater(() -> {
            view.GraveRunNewLogin loginView = new view.GraveRunNewLogin();
            // You would pass the controller here
            loginView.setVisible(true);
        });
    }
    
    private void navigateToSignup() {
        view.dispose();
        java.awt.EventQueue.invokeLater(() -> {
            view.GraveRunSignup signupView = new view.GraveRunSignup();
            signupView.setVisible(true);
        });
    }
    
    public void showDashboard() {
        view.setVisible(true);
        view.setLocationRelativeTo(null); // Center window
    }
}