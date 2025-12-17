package controller;

import dao.*;
import model.ProductModel;
import model.UserData;
import util.SessionManager;
import view.dashboard;
import view.GraveRunNewLogin;
import view.GraveRunSignup;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * Controller for Dashboard view
 */
public class DashboardController {
    private dashboard view;
    private ProductDao productDAO;
    private UserDao userDAO;
    private FavoriteDAO favoriteDAO;
    private List<ProductModel> allBestSellers; // Store 8 products
    private List<ProductModel> displayedProducts; // Display 4 products
    
    public DashboardController(dashboard view) {
        this.view = view;
        this.productDAO = new ProductDAOImpl();
        this.userDAO = new UserDao();
        this.favoriteDAO = new FavoriteDAOImpl();
        
        initController();
    }
    
    /**
     * Initialize controller - set up event listeners and load data
     */
    private void initController() {
        // Check if user is logged in and update UI accordingly
        updateUIBasedOnLoginStatus();
        
        // Load best selling products on startup
        loadBestSellingProducts();
        
        // Set up event listeners
        setupEventListeners();
    }
    
    /**
     * Update UI based on whether user is logged in or not
     */
    private void updateUIBasedOnLoginStatus() {
        if (SessionManager.isLoggedIn()) {
            UserData currentUser = SessionManager.getCurrentUser();
            System.out.println("Welcome back, " + currentUser.getUsername() + "!");
            // UI updates will be handled in login/signup listeners
        }
    }
    
    /**
     * Load best selling products from database
     */
    private void loadBestSellingProducts() {
        // Load 8 best selling products
        allBestSellers = productDAO.getBestSellingProducts(8);
        
        if (allBestSellers.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "No products available in the database.", 
                "No Products", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Display first 4 products
        displayedProducts = allBestSellers.subList(0, Math.min(4, allBestSellers.size()));
        displayProducts(displayedProducts);
    }
    
    /**
     * Display products on the dashboard
     */
    private void displayProducts(List<ProductModel> products) {
        // Get product panels from view
        JPanel[] productPanels = {
            view.getProductlabel2(),
            view.getProductLabel1(),
            view.getProductlabel3(),
            view.getProductlevel4()
        };
        
        // Product components arrays
        JLabel[] imageLabels = {
            view.getjLabel6(),
            view.getShoeImg(),
            view.getjLabel11(),
            view.getjLabel14()
        };
        
        JLabel[] nameLabels = {
            view.getjLabel9(),
            view.getShoeName(),
            view.getjLabel12(),
            view.getjLabel15()
        };
        
        JLabel[] priceLabels = {
            view.getjLabel10(),
            view.getPrice(),
            view.getjLabel13(),
            view.getjLabel16()
        };
        
        // Display products
        for (int i = 0; i < products.size() && i < 4; i++) {
            ProductModel product = products.get(i);
            
            // Set product name
            nameLabels[i].setText(product.getName());
            
            // Set product price
            priceLabels[i].setText("Rs. " + String.format("%.2f", product.getPrice()));
            
            // Set product image
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(product.getImagePath()));
                Image image = icon.getImage().getScaledInstance(230, 87, Image.SCALE_SMOOTH);
                imageLabels[i].setIcon(new ImageIcon(image));
            } catch (Exception e) {
                imageLabels[i].setText("No Image");
                System.err.println("Error loading image: " + e.getMessage());
            }
        }
    }
    
    /**
     * Set up all event listeners
     */
    private void setupEventListeners() {
        // Search button listener
        view.AddbtnSearchListener(e -> handleSearch());
        
        // Login button listener
        view.AddloginListener(e -> handleLogin());
        
        // Signup button listener
        view.AddSignupListener(e -> handleSignup());
        
        // Banner "Buy Now" button listener
        view.AddgetBuynowListener(e -> handleBannerBuyNow());
        
        // Logo button listener (return to dashboard)
        view.getLogoListener(e -> refreshDashboard());
        
        // Buy Now button listeners with product index
        view.getBuyNow1Listener(e -> handleBuyNow(0));
        view.getBuyNow2Listener(e -> handleBuyNow(1));
        view.getBuyNow3Listener(e -> handleBuyNow(2));
        view.getBuyNow4Listener(e -> handleBuyNow(3));
        
        // Favorite button listeners with product index
        view.getFavourite1Listener(e -> handleAddToFavorites(0));
        view.getFavourite2Listener(e -> handleAddToFavorites(1));
        view.getFavourite3Listener(e -> handleAddToFavorites(2));
        view.getFavourite4Listener(e -> handleAddToFavorites(3));
    }
    
    /**
     * Handle search functionality
     */
    private void handleSearch() {
        String searchQuery = view.getTxtSearch().getText().trim();
        
        // Ignore default placeholder text
        if (searchQuery.isEmpty() || searchQuery.equals("Search sneakers, brands or collections… ")) {
            JOptionPane.showMessageDialog(view, 
                "Please enter a search term.", 
                "Empty Search", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Search products
        List<ProductModel> searchResults = productDAO.searchProducts(searchQuery);
        
        // Show search results
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "No products found matching: " + searchQuery,
                "No Results",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            // TODO: Create and show SearchDialog
            StringBuilder results = new StringBuilder("Found " + searchResults.size() + " products:\n\n");
            for (ProductModel product : searchResults) {
                results.append(product.getName()).append(" - Rs. ").append(product.getPrice()).append("\n");
            }
            JOptionPane.showMessageDialog(view, results.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Handle login button click
     */
    private void handleLogin() {
        if (SessionManager.isLoggedIn()) {
            // If already logged in, show profile
            UserData currentUser = SessionManager.getCurrentUser();
            JOptionPane.showMessageDialog(view,
                "Logged in as: " + currentUser.getUsername() + "\n" +
                "Email: " + currentUser.getEmail() + "\n" +
                "User ID: " + currentUser.getUserId(),
                "User Profile",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Open login window
            GraveRunNewLogin loginFrame = new GraveRunNewLogin();
            
            // Pass controller reference to login frame (if method exists)
            try {
                loginFrame.getClass().getMethod("setDashboardController", DashboardController.class)
                    .invoke(loginFrame, this);
            } catch (Exception e) {
                // Method doesn't exist yet, login will work in standalone mode
                System.out.println("Note: setDashboardController method not found in GraveRunNewLogin");
            }
            
            loginFrame.setVisible(true);
            loginFrame.setLocationRelativeTo(view);
            
            // Optional: Hide dashboard while login is open
            // view.setVisible(false);
        }
    }
    
    /**
     * Handle signup button click
     */
    private void handleSignup() {
        if (SessionManager.isLoggedIn()) {
            // If logged in, this button becomes logout
            int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.clearSession();
                updateUIBasedOnLoginStatus();
                JOptionPane.showMessageDialog(view,
                    "Logged out successfully!",
                    "Logout",
                    JOptionPane.INFORMATION_MESSAGE);
                refreshDashboard();
            }
        } else {
            // Open signup window
            GraveRunSignup signupFrame = new GraveRunSignup();
            
            // Pass controller reference to signup frame (if method exists)
            try {
                signupFrame.getClass().getMethod("setDashboardController", DashboardController.class)
                    .invoke(signupFrame, this);
            } catch (Exception e) {
                // Method doesn't exist yet, signup will work in standalone mode
                System.out.println("Note: setDashboardController method not found in GraveRunSignup");
            }
            
            signupFrame.setVisible(true);
            signupFrame.setLocationRelativeTo(view);
            
            // Optional: Hide dashboard while signup is open
            // view.setVisible(false);
        }
    }
    
    /**
     * Handle Buy Now button click by product index
     */
    private void handleBuyNow(int productIndex) {
        if (displayedProducts == null || productIndex >= displayedProducts.size()) {
            JOptionPane.showMessageDialog(view,
                "Product not available.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ProductModel product = displayedProducts.get(productIndex);
        
        if (!SessionManager.isLoggedIn()) {
            JOptionPane.showMessageDialog(view,
                "Please login to purchase products.",
                "Login Required",
                JOptionPane.WARNING_MESSAGE);
            handleLogin(); // Open login dialog
            return;
        }
        
        if (product.getStock() <= 0) {
            JOptionPane.showMessageDialog(view,
                "Sorry, this product is out of stock.",
                "Out of Stock",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Navigate to checkout
        // TODO: Create and open CheckoutFrame
        System.out.println("Navigating to checkout for: " + product.getName());
        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Price: Rs. " + product.getPrice());
        
        // For now, show message
        JOptionPane.showMessageDialog(view,
            "Checkout feature will be implemented.\n" +
            "Product: " + product.getName() + "\n" +
            "Price: Rs. " + product.getPrice(),
            "Checkout",
            JOptionPane.INFORMATION_MESSAGE);
        
        // When CheckoutFrame is ready, use:
        // CheckoutFrame checkoutFrame = new CheckoutFrame(product.getProductId(), product.getPrice());
        // checkoutFrame.setVisible(true);
    }
    
    /**
     * Handle banner Buy Now button click
     */
    private void handleBannerBuyNow() {
        // Get the featured product (first best seller)
        if (displayedProducts != null && !displayedProducts.isEmpty()) {
            handleBuyNow(0);
        } else {
            JOptionPane.showMessageDialog(view,
                "Featured product not available.",
                "Product Unavailable",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Handle Add to Favorites button click by product index
     */
    private void handleAddToFavorites(int productIndex) {
        if (displayedProducts == null || productIndex >= displayedProducts.size()) {
            JOptionPane.showMessageDialog(view,
                "Product not available.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ProductModel product = displayedProducts.get(productIndex);
        
        if (!SessionManager.isLoggedIn()) {
            JOptionPane.showMessageDialog(view,
                "Please login to add products to favorites.",
                "Login Required",
                JOptionPane.WARNING_MESSAGE);
            handleLogin(); // Open login dialog
            return;
        }
        
        UserData currentUser = SessionManager.getCurrentUser();
        
        // Check if already in favorites
        if (favoriteDAO.isFavorite(currentUser.getUserId(), product.getProductId())) {
            // Remove from favorites
            if (favoriteDAO.removeFromFavorites(currentUser.getUserId(), product.getProductId())) {
                JOptionPane.showMessageDialog(view,
                    "Removed from favorites!",
                    "Favorites",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Add to favorites
            if (favoriteDAO.addToFavorites(currentUser.getUserId(), product.getProductId())) {
                JOptionPane.showMessageDialog(view,
                    "Added to favorites!",
                    "Favorites",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view,
                    "Failed to add to favorites.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Open product detail page
     */
    private void openProductDetail(ProductModel product) {
        // TODO: Create and open ProductDetailFrame
        System.out.println("Opening product detail for: " + product.getName());
        
        JOptionPane.showMessageDialog(view,
            "Product Detail Page will be implemented.\n\n" +
            "Product: " + product.getName() + "\n" +
            "Brand: " + product.getBrand() + "\n" +
            "Category: " + product.getCategory() + "\n" +
            "Price: Rs. " + product.getPrice() + "\n" +
            "Stock: " + product.getStock() + "\n\n" +
            "Description: " + product.getDescription(),
            "Product Details",
            JOptionPane.INFORMATION_MESSAGE);
        
        // When ProductDetailFrame is ready, use:
        // ProductDetailFrame detailFrame = new ProductDetailFrame(product);
        // detailFrame.setVisible(true);
        // view.dispose(); // Close dashboard if needed
    }
    
    /**
     * Refresh dashboard
     */
    private void refreshDashboard() {
        loadBestSellingProducts();
        updateUIBasedOnLoginStatus();
    }
    
    /**
     * Public method to refresh dashboard from external classes (e.g., after login)
     * Call this from GraveRunNewLogin after successful login
     */
    public void onLoginSuccess() {
        updateUIBasedOnLoginStatus();
        refreshDashboard();
        view.setVisible(true); // Show dashboard again if it was hidden
    }
    
    /**
     * Get the dashboard view
     * Useful if login/signup needs to close the dashboard temporarily
     */
    public dashboard getView() {
        return view;
    }
}