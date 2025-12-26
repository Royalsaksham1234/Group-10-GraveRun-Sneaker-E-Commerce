package graverun;

import controller.LoginController;
import controller.DashboardController;
import dao.UserDao;
import view.GraveRunLogin;
import util.SessionManager;

public class graverun {
    public static void main(String[] args) {
        try {
            // Initialize session manager
            SessionManager.initialize();
            
            // Initialize DAO
            UserDao userDao = new UserDao();
            
            // Initialize view
            GraveRunLogin loginView = new GraveRunLogin();
            
            // Initialize controller
            LoginController loginController = new LoginController();
            
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