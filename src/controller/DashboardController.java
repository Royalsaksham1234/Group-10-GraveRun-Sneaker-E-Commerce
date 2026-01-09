package controller;

import dao.FavoriteDAO;
import dao.ProductDao;
import dao.ProductDAOImpl;
import model.ProductModel;
import util.SessionManager;
import view.Dashboard1;
import view.Favourites;
import dao.CartDAO;
import view.ProductCardPanel;
import javax.swing.*;
import java.util.List;
import view.CartView;
import view.GraveRunSignup;
import view.ProductDetail;

public class DashboardController {
    private final Dashboard1 view;
    private final ProductDao productDao;

    public DashboardController() {
        this.productDao = new ProductDAOImpl();
        this.view = new Dashboard1();
        initController();
        loadProducts();
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    // Initialize all button actions
    private void initController() {
        // Search button (no login required)
        view.getSearchButton().addActionListener(e -> handleSearch());

        // Favorites button – require login
        view.getFavoritesButton().addActionListener(e -> {
            if (SessionManager.isLoggedIn()) {
                openFavorites();
            } else {
                requireLoginOrSignup();
            }
        });

        // Cart button – require login
        view.getCartButton().addActionListener(e -> {
            if (SessionManager.isLoggedIn()) {
                openCart();
            } else {
                requireLoginOrSignup();
            }
        });

        // Profile button – require login to show popup
        view.getProfileButton().addActionListener(e -> {
            if (SessionManager.isLoggedIn()) {
                view.getPopupMenu().show(view.getProfileButton(), 0, view.getProfileButton().getHeight());
            } else {
                requireLoginOrSignup();
            }
        });

        // Help button (no login required)
        view.getHelpButton().addActionListener(e -> openHelp());
    }

    // Prompt user to login/signup
    private void requireLoginOrSignup() {
        int choice = JOptionPane.showConfirmDialog(view,
                "Please login or signup first to access this feature.",
                "Login Required",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            navigateToSignup();
        }
    }

    // Load all products and attach click listeners (FIXED: safe variable capture)
    private void loadProducts() {
        try {
            List<ProductModel> products = productDao.getAllProducts();
            view.getProductContainer().removeAll();

            for (ProductModel product : products) {
                ProductCardPanel card = view.addProductCard(product);

                // CRITICAL FIX: Capture the current product safely to avoid closure bug
                ProductModel currentProduct = product;
                card.setProductSelectionListener(selectedId -> handleProductClick(currentProduct));
            }

            view.getProductContainer().revalidate();
            view.getProductContainer().repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Error loading products:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Handle product card click
    private void handleProductClick(ProductModel product) {
        if (!SessionManager.isLoggedIn()) {
            int choice = JOptionPane.showConfirmDialog(view,
                    "Please login or signup to purchase this product.",
                    "Login Required",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                navigateToSignup();
            }
            return;
        }
        openBuyNowPage(product);
    }

    // Open Buy Now page with fresh product data from database
private void openBuyNowPage(ProductModel product) {
    ProductDetail detailPanel = new ProductDetail(product);

    CartDAO cartDAO = new CartDAO(); // Your existing constructor
    FavoriteDAO favoriteDAO = new FavoriteDAO();

    new ProductDetailController(detailPanel, product, cartDAO, favoriteDAO);

    view.getProductContainer().removeAll();
    view.getProductContainer().add(detailPanel);
    view.getProductContainer().revalidate();
    view.getProductContainer().repaint();
}

    // Search handling (also with safe capture fix)
    private void handleSearch() {
        String searchTerm = view.getSearchField().getText().trim();
        if (searchTerm.isEmpty() || searchTerm.equals("Search sneakers or brands")) {
            JOptionPane.showMessageDialog(view, "Please enter a search term.", "Empty Search", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<ProductModel> results = productDao.searchProducts(searchTerm);
            view.getProductContainer().removeAll();

            for (ProductModel p : results) {
                ProductCardPanel card = view.addProductCard(p);

                // Safe capture for search results too
                ProductModel currentProduct = p;
                card.setProductSelectionListener(selectedId -> handleProductClick(currentProduct));
            }

            view.getProductContainer().revalidate();
            view.getProductContainer().repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Search failed:\n" + e.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Open Favorites page
private void openFavorites() {
    view.getProductContainer().removeAll();

    FavoriteDAO favoriteDAO = new FavoriteDAO();
    Favourites favouritesPanel = new Favourites(favoriteDAO);

    view.getProductContainer().add(favouritesPanel);
    view.getProductContainer().revalidate();
    view.getProductContainer().repaint();
}


    // Cart placeholder
private void openCart() {
    view.getProductContainer().removeAll();

    // Get current user ID
    int userId = SessionManager.getCurrentUser().getUserId();
    CartDAO cartDAO = new CartDAO();  // create DAO with the correct user
    CartView cartView = new CartView(cartDAO); // pass DAO to CartView

    new CartController(cartView); // controller loads cart items

    view.getProductContainer().add(cartView);
    view.getProductContainer().revalidate();
    view.getProductContainer().repaint();
}

    // Help placeholder
    private void openHelp() {
        JOptionPane.showMessageDialog(view, "Help & Support coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // Navigate to login
    private void navigateToLogin() {
        view.dispose();
        new view.GraveRunLogin().setVisible(true);
    }

    // Navigate to signup
    private void navigateToSignup() {

    view.dispose();  // Remove this — we don't want to close dashboard
GraveRunSignup signupView = new GraveRunSignup(view, true);
// 'view' is your Dashboard1 frame, true = modal
signupView.setLocationRelativeTo(view);  // Optional: center on dashboard
signupView.setVisible(true);
}
    }
