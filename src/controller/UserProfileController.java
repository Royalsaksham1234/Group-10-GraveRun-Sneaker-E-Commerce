package controller;

import dao.UserProfileDao;
import dao.CartDAO;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.UserProfileData;
import javax.swing.table.DefaultTableModel;
import view.CartView;
import view.Favourites;
import view.GraveRunLogin;
import view.GraveRunConfirmPassword;
import util.SessionManager;

/**
 * Controller for User Profile page
 * Handles profile data loading and button actions
 */
public class UserProfileController {

    private UserProfileDao dao;
    private javax.swing.JTable table;
    private javax.swing.JLabel username;
    private javax.swing.JLabel email;
    private int userId;
    private javax.swing.JButton logout;
    private javax.swing.JButton mywhislist;
    private javax.swing.JButton myorders;
    private javax.swing.JButton confirmPassword;
    private javax.swing.JButton mycart;

    /**
     * Constructor
     * 
     * @param table JTable to display orders
     * @param lblUsername Label to show username
     * @param lblEmail Label to show email
     * @param userId Current user's ID
     * @param logout Logout button
     * @param mywhislist Wishlist button
     * @param myorders My Orders button
     * @param confirmPassword Confirm Password button
     * @param mycart My Cart button
     */
    public UserProfileController(
            javax.swing.JTable table, 
            javax.swing.JLabel lblUsername, 
            javax.swing.JLabel lblEmail, 
            int userId, 
            javax.swing.JButton logout,
            javax.swing.JButton mywhislist,
            javax.swing.JButton myorders,
            javax.swing.JButton confirmPassword,
            javax.swing.JButton mycart) {
        
        this.dao = new UserProfileDao();
        this.table = table;
        this.username = lblUsername;
        this.email = lblEmail;
        this.userId = userId;
        this.logout = logout;
        this.mywhislist = mywhislist;
        this.myorders = myorders;
        this.confirmPassword = confirmPassword;
        this.mycart = mycart;
        
        // Attach button actions
        attachButtonActions();
        
        // Load initial profile data
        loadProfileData();
    }

    /**
     * Loads user profile data and displays it
     */
    public void loadProfileData() {
        try {
            UserProfileData profile = dao.getUserProfile(userId);

            if (profile == null) {
                JOptionPane.showMessageDialog(
                    null,
                    "Failed to load profile data.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Set username/email labels
            username.setText("Username: " + profile.getUsername());
            email.setText("Email: " + profile.getEmail());

            // Fill JTable with orders
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows

            if (profile.getOrders() == null || profile.getOrders().isEmpty()) {
                model.addRow(new Object[]{"No orders yet", "", "", "", ""});
            } else {
                profile.getOrders().forEach(order -> {
                    model.addRow(new Object[]{
                        order.getProductName(),
                        order.getQuantity(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getOrderDate()
                    });
                });
            }
            
            System.out.println("✓ Profile data loaded successfully for user ID: " + userId);
            
        } catch (Exception e) {
            System.err.println("Error loading profile data: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(
                null,
                "Error loading profile data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Refreshes the profile data (useful after updating orders)
     */
    public void refreshProfile() {
        System.out.println("Refreshing profile data...");
        loadProfileData();
    }

    /**
     * Attaches action listeners to all buttons
     */
    private void attachButtonActions() {
        
        // Logout Button Action
        logout.addActionListener(e -> handleLogout());
        
        // My Wishlist Button Action
        mywhislist.addActionListener(e -> handleMyWishlist());
        
        // My Cart Button Action
        mycart.addActionListener(e -> handleMyCart());
        
        // My Orders Button Action
        myorders.addActionListener(e -> handleMyOrders());
        
        // Confirm Password Button Action
        confirmPassword.addActionListener(e -> handleConfirmPassword());
    }
    
    /**
     * Handles logout action
     */
    private void handleLogout() {
        // Show confirmation dialog
        int choice = JOptionPane.showConfirmDialog(
            SwingUtilities.getWindowAncestor(logout), 
            "Are you sure you want to log out?",     
            "Confirm Logout",                        
            JOptionPane.YES_NO_OPTION,               
            JOptionPane.QUESTION_MESSAGE             
        );

        // If user confirms, logout and open login page
        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Clear session
                SessionManager.logout();
                
                System.out.println("✓ User logged out successfully");
                
                // Open login page
                GraveRunLogin loginPage = new GraveRunLogin();
                loginPage.setLocationRelativeTo(null);
                loginPage.setVisible(true);
                
                // Close current window
                Window currentWindow = SwingUtilities.getWindowAncestor(logout);
                if (currentWindow != null) {
                    currentWindow.dispose();
                }
                
            } catch (Exception e) {
                System.err.println("Error during logout: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Handles My Wishlist button action
     */
    private void handleMyWishlist() {
        try {
            System.out.println("Opening Wishlist...");
            
            Favourites favouritesPage = new Favourites();
            favouritesPage.setLocationRelativeTo(null);
            favouritesPage.setVisible(true);
            
            System.out.println("✓ Wishlist opened");
            
        } catch (Exception e) {
            System.err.println("Error opening wishlist: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(mywhislist),
                "Failed to open wishlist: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Handles My Cart button action
     */
    private void handleMyCart() {
        try {
            System.out.println("Opening Cart...");
            
            // CartView requires CartDAO parameter
            dao.CartDAO cartDAO = new dao.CartDAO();
            CartView cartPage = new CartView(cartDAO);
            cartPage.setLocationRelativeTo(null);
            cartPage.setVisible(true);
            
            System.out.println("✓ Cart opened");
            
        } catch (Exception e) {
            System.err.println("Error opening cart: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(mycart),
                "Failed to open cart: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Handles My Orders button action
     * Refreshes the profile page to show updated orders
     */
    private void handleMyOrders() {
        try {
            System.out.println("Refreshing orders...");
            
            // Refresh the profile data to show latest orders
            refreshProfile();
            
            // Show confirmation message
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(myorders),
                "Orders refreshed successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            System.out.println("✓ Orders refreshed");
            
        } catch (Exception e) {
            System.err.println("Error refreshing orders: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(myorders),
                "Failed to refresh orders: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Handles Confirm Password button action
     * Opens GraveRunConfirmPassword with user's email
     * Uses the SAME UI as Forgot Password flow - just 2 password fields
     * NO current password verification needed (user is already logged in)
     */
    private void handleConfirmPassword() {
        try {
            System.out.println("Opening Change Password page...");
            
            // Get current user's email from session
            String userEmail = SessionManager.getCurrentUser().getEmail();
            
            if (userEmail == null || userEmail.isEmpty()) {
                JOptionPane.showMessageDialog(
                    SwingUtilities.getWindowAncestor(confirmPassword),
                    "Session expired. Please login again.",
                    "Session Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // Open GraveRunConfirmPassword with user's email
            // Uses the SAME UI as forgot password - 2 fields only
            GraveRunConfirmPassword confirmPasswordPage = new GraveRunConfirmPassword(userEmail);
            confirmPasswordPage.setLocationRelativeTo(SwingUtilities.getWindowAncestor(confirmPassword));
            confirmPasswordPage.setVisible(true);
            
            System.out.println("✓ Change Password page opened for: " + userEmail);
            
        } catch (Exception e) {
            System.err.println("Error opening change password: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(confirmPassword),
                "Failed to open password change page: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Gets the current user ID
     * 
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Sets a new user ID and reloads profile data
     * 
     * @param userId New user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
        loadProfileData();
    }
}