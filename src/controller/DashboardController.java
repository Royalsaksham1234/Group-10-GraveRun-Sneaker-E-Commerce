package controller;

import dao.CartDAO;
import dao.FavoriteDAO;
import dao.ProductDAOImpl;
import dao.ProductDao;
import model.AdminProductModel;
import util.SessionManager;
import view.*;
import java.util.List;
import javax.swing.*;
import java.awt.Frame;

/**
 * DASHBOARD CONTROLLER - Handles ALL business logic and user interactions
 * Following strict MVC architecture
 */
public class DashboardController {
    private final Dashboard1 view;
    private final ProductDao productDao;

    public DashboardController(Dashboard1 view) {
        this.view = view;
        this.productDao = new ProductDAOImpl();
        
        initController();
        refreshDashboard();
    }

    /**
     * INITIALIZE ALL LISTENERS
     */
    private void initController() {
        // Header Navigation Buttons
        view.getSearchButton().addActionListener(e -> handleSearch());
        view.getFavoritesButton().addActionListener(e -> handleFavorites());
        view.getCartButton().addActionListener(e -> handleCart());
        view.getProfileButton().addActionListener(e -> handleProfileClick());
        view.getHelpButton().addActionListener(e -> handleHelp());
        
        // Logo - Refresh Dashboard
        view.getLogoLabel().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshDashboard();
            }
        });
        
        // Profile Popup Menu Items
        view.getMenuItemAccount().addActionListener(e -> handleAccountClick());
        view.getMenuItemOrder().addActionListener(e -> handleOrderClick());
        view.getMenuItemLogout().addActionListener(e -> handleLogout());
    }

    /**
     * MASTER REFRESH METHOD
     */
    private void refreshDashboard() {
        view.updateSessionState();
        loadProducts();
        view.resetSearchField();
    }

    /**
     * LOAD PRODUCTS FROM DATABASE
     */
    private void loadProducts() {
        try {
            List<AdminProductModel> products = productDao.getAllProducts();
            System.out.println("✅ Fetched " + products.size() + " products from database");
            
            view.clearProductDisplay();

            for (AdminProductModel product : products) {
                System.out.println("📦 Adding product: " + product.getName() + 
                                 " | Image: " + product.getImageUrl());
                
                ProductCardPanel card = view.addProductCard(product);
                card.setProductSelectionListener(selectedId -> handleProductClick(product));
            }
            
            view.refreshProductDisplay();
            System.out.println("✅ Product display refreshed");
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error loading products: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * SECURITY CHECK + LOGIN PROMPT
     */
    private boolean checkSessionOrPrompt() {
        if (SessionManager.isLoggedIn()) {
            return true;
        } else {
            promptForLogin();
            return SessionManager.isLoggedIn();
        }
    }

    /**
     * PROMPT USER TO LOGIN/SIGNUP
     */
    private void promptForLogin() {
        int choice = JOptionPane.showConfirmDialog(view,
                "You need to login to access this feature.\nDo you want to signup now?",
                "Login Required",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            openSignupDialog();
        }
    }

    /**
     * OPEN SIGNUP DIALOG
     */
    private void openSignupDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(view);
        GraveRunSignup signup = new GraveRunSignup(parentFrame, true);
        signup.setLocationRelativeTo(view);
        signup.setVisible(true);
        
        if (SessionManager.isLoggedIn()) {
            refreshDashboard();
        }
    }

    // ==================== NAVIGATION HANDLERS ====================

    /**
     * HANDLE PROFILE BUTTON CLICK
     */
    private void handleProfileClick() {
        if (SessionManager.isLoggedIn()) {
            if (view.getProfileButton().isShowing()) {
                view.getPopupMenu().show(
                    view.getProfileButton(), 
                    0, 
                    view.getProfileButton().getHeight()
                );
            }
        } else {
            promptForLogin();
        }
    }

    /**
     * HANDLE ACCOUNT MENU ITEM CLICK
     */
    private void handleAccountClick() {
        if (checkSessionOrPrompt()) {
            int userId = SessionManager.getCurrentUserId();
            UserProfilePage profile = new UserProfilePage(userId);
            profile.setLocationRelativeTo(view);
            profile.setVisible(true);
            view.dispose(); // ✅ Clean disposal
        }
    }

    /**
     * HANDLE ORDER MENU ITEM CLICK
     */
    private void handleOrderClick() {
        if (checkSessionOrPrompt()) {
            try {
                int userId = SessionManager.getCurrentUserId();
                
                Order_Tracking trackingView = new Order_Tracking();
                OrderTrackingController controller = new OrderTrackingController(trackingView, view);
                
                controller.loadMostRecentOrder(userId);
                controller.showScreen();
                
                view.setVisible(false); // ✅ Hide, don't dispose
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view,
                    "Error opening order tracking: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    /**
     * HANDLE LOGOUT
     */
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.logout();
            refreshDashboard(); // ✅ Refresh to guest state
            JOptionPane.showMessageDialog(view, 
                "You have been logged out successfully.",
                "Logged Out",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * HANDLE FAVORITES BUTTON CLICK
     */
    private void handleFavorites() {
        if (checkSessionOrPrompt()) {
            Favourites favView = new Favourites();
            new FavouriteController(favView);
            favView.setVisible(true);
            view.dispose(); // ✅ Clean disposal
        }
    }

    /**
     * HANDLE CART BUTTON CLICK
     */
    private void handleCart() {
        if (checkSessionOrPrompt()) {
            CartDAO cartDAO = new CartDAO();
            CartView cartView = new CartView(cartDAO);
            cartView.setLocationRelativeTo(view);
            cartView.setVisible(true);
            // ✅ Don't dispose - let user keep browsing
        }
    }

    /**
     * HANDLE HELP BUTTON CLICK
     */
    private void handleHelp() {
        HelpPage help = new HelpPage();
        help.setLocationRelativeTo(view);
        help.setVisible(true);
        view.dispose(); // ✅ Clean disposal
    }

    /**
     * HANDLE PRODUCT CARD CLICK
     */
    private void handleProductClick(AdminProductModel product) {
        if (checkSessionOrPrompt()) {
            openProductDetail(product);
        }
    }

    /**
     * OPEN PRODUCT DETAIL PAGE
     */
    private void openProductDetail(AdminProductModel product) {
        ProductDetail detailPanel = new ProductDetail(product);
        new ProductDetailController(detailPanel, product, new CartDAO(), new FavoriteDAO());
        
        view.clearProductDisplay();
        view.getProductContainer().add(detailPanel);
        view.refreshProductDisplay();
    }

    /**
     * HANDLE SEARCH - ✅ FIXED: Use getSearchText() instead of direct getText()
     */
    private void handleSearch() {
        String searchTerm = view.getSearchText(); // ✅ NEW: Excludes placeholder

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Please enter a sneaker name or brand.",
                "Search",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            List<AdminProductModel> results = productDao.getProductsByBrand(searchTerm);

            view.clearProductDisplay();

            if (results.isEmpty()) {
                JLabel noResults = new JLabel("No products found for: " + searchTerm);
                noResults.setFont(new java.awt.Font("Rockwell", java.awt.Font.BOLD, 18));
                noResults.setForeground(java.awt.Color.WHITE);
                noResults.setHorizontalAlignment(SwingConstants.CENTER);
                view.getProductContainer().add(noResults);
            } else {
                for (AdminProductModel product : results) {
                    ProductCardPanel card = view.addProductCard(product);
                    card.setProductSelectionListener(selectedId -> handleProductClick(product));
                }
            }

            view.refreshProductDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Error during search: " + e.getMessage(),
                "Search Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * PUBLIC METHOD for ProductDetailController to trigger Buy Now flow
     */
    public void showOrderConfirm(AdminProductModel product) {
        OrderConfirm orderConfirmView = new OrderConfirm(product);
        
        view.clearProductDisplay();
        view.getProductContainer().add(orderConfirmView);
        view.refreshProductDisplay();
    }
}