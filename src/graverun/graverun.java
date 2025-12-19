package graverun;

import controller.GraveRunLoginController;
import controller.DashboardController;
import dao.userDAO;
import dao.userDAOImpl;
import view.GraveRunNewLogin;
import util.SessionManager;

public class graverun {
    public static void main(String[] args) {
        try {
            // Initialize session manager
            SessionManager.initialize();
            
            // Initialize DAO
            userDAOImpl userDao = new userDAOImpl();
            
            // Initialize view
            GraveRunNewLogin loginView = new GraveRunNewLogin();
            
            // Initialize controller
            GraveRunLoginController loginController = new GraveRunLoginController(loginView, userDao);
            
            // Show login view
            loginView.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                "Failed to start application: " + e.getMessage(),
                "Startup Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}