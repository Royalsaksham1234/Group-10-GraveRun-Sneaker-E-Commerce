package graverun;



import dao.AdminUserDAOImpl;

import util.SessionManager;
import dao.AdminUserDAO;

public class graverun {
    public static void main(String[] args) {
        try {
            // Initialize session manager
            SessionManager.initialize();
            
            // Initialize DAO
            AdminUserDAO userDao = new AdminUserDAOImpl();
            
            // Initialize view
           
            
            // Initialize controller
            
            
            // Show login view
            ;
            
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                "Failed to start application: " + e.getMessage(),
                "Startup Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}