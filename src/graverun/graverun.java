package graverun;



import dao.UserDao;
import dao.userDAOImpl;

import util.SessionManager;

public class graverun {
    public static void main(String[] args) {
        try {
            // Initialize session manager
            SessionManager.initialize();
            
            // Initialize DAO
            UserDao userDao = new userDAOImpl();
            
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