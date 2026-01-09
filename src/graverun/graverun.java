package graverun;

import dao.AdminUserDAOImpl;
import util.SessionManager;
import dao.AdminUserDAO;
import view.Dashboard1;

/**
 * Main Application Entry Point
 * Starts with Customer Dashboard (allows guest browsing)
 */
public class graverun {
    public static void main(String[] args) {
        try {
            // Initialize session manager
            SessionManager.initialize();
            
            // Initialize DAO
            AdminUserDAO userDao = new AdminUserDAOImpl();
            
            // ✅ START WITH CUSTOMER DASHBOARD (Guest mode enabled)
            java.awt.EventQueue.invokeLater(() -> {
                Dashboard1 dashboard = new Dashboard1();
                dashboard.setLocationRelativeTo(null);
                dashboard.setVisible(true);
                
                System.out.println("🏠 Customer Dashboard launched in GUEST mode");
                System.out.println("📌 Users can browse, search, and view products");
                System.out.println("🔐 Login required for: Cart, Favorites, Checkout, Profile");
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                "Failed to start application: " + e.getMessage(),
                "Startup Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}