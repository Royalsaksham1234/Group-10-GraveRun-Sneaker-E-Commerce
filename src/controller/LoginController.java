package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.awt.Dialog.ModalityType;
import view.GraveRunLogin;
import view.GraveRunSignup;
import view.GraveRunForgotPassword;
import view.AdminDashboard;
import view.Dashboard1;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import util.SessionManager;
import util.UserData;

/**
 * Controller for Login operations
 * Handles authentication and session creation with role-based access
 */
public class LoginController {
    
    private final GraveRunLogin view;
    private final UserDAO userDAO;
    
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    public LoginController(GraveRunLogin view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();
        initController();
    }
    
    private void initController() {
        // Login button
        view.getLoginButton().addActionListener(e -> performLogin());
        
        // Signup link
        view.getSignuplink().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openSignup();
            }
        });
        
        // Forgot password link
        view.getForgotpassword().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openForgotPassword();
            }
        });
    }
    
    private void performLogin() {
        String email = view.getEmailField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());
        
        // Validation
        if (!validateEmail(email)) {
            return;
        }
        
        if (!validatePassword(password)) {
            return;
        }
        
        // Authenticate
        if (!userDAO.authenticate(email, password)) {
            JOptionPane.showMessageDialog(
                view,
                "Invalid email or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Get full user data from DAO
        UserData user = userDAO.getUserByEmail(email);
        
        if (user == null) {
            JOptionPane.showMessageDialog(
                view,
                "Login failed. User data not found.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Check if user is active
        if (!user.isActive()) {
            JOptionPane.showMessageDialog(
                view,
                "Your account has been deactivated. Please contact support.",
                "Account Inactive",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Store in session
        SessionManager.login(user);
        
        // Success message
        JOptionPane.showMessageDialog(
            view,
            "Login Successful!\nWelcome back, " + user.getFullName() + "!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Close login dialog
        view.dispose();
        
        // Open appropriate dashboard based on role
        openDashboardByRole(user);
    }
    
    /**
     * Opens the appropriate dashboard based on user role
     * @param user The authenticated user
     */
    private void openDashboardByRole(UserData user) {
    String role = user.getRole();
    
    // ✅ CRITICAL: Close ALL existing dashboards first
    closeAllDashboards();
    
    if (role != null && role.equalsIgnoreCase("admin")) {
        // Open Admin Dashboard
        System.out.println("🔐 Opening Admin Dashboard for: " + user.getFullName());
        java.awt.EventQueue.invokeLater(() -> {
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.setVisible(true);
        });
    } else {
        // Open Customer Dashboard
        System.out.println("👤 Opening Customer Dashboard for: " + user.getFullName());
        java.awt.EventQueue.invokeLater(() -> {
            Dashboard1 customerDashboard = new Dashboard1();
            customerDashboard.setLocationRelativeTo(null);
            customerDashboard.setVisible(true);
        });
    }
}

/**
 * ✅ Close all existing Dashboard windows
 */
private void closeAllDashboards() {
    java.awt.Window[] windows = java.awt.Window.getWindows();
    for (java.awt.Window window : windows) {
        if (window instanceof Dashboard1 || window instanceof AdminDashboard) {
            System.out.println("🗑️ Closing existing dashboard: " + window.getClass().getSimpleName());
            window.dispose();
        }
    }

    }
    
    private boolean validateEmail(String email) {
        if (email.isEmpty() || email.equals("Email")) {
            JOptionPane.showMessageDialog(
                view, 
                "Please enter your email.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(
                view, 
                "Please enter a valid email address.", 
                "Invalid Email", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        return true;
    }
    
    private boolean validatePassword(String password) {
        if (password.isEmpty() || password.equals("PASSWORD")) {
            JOptionPane.showMessageDialog(
                view, 
                "Please enter your password.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        return true;
    }
    
    private void openSignup() {
        GraveRunSignup signup = new GraveRunSignup(view, ModalityType.APPLICATION_MODAL);
        signup.setLocationRelativeTo(view);
        signup.setVisible(true);
    }
    
    private void openForgotPassword() {
        GraveRunForgotPassword forgotPasswordDialog =
            new GraveRunForgotPassword(view, ModalityType.APPLICATION_MODAL);
        forgotPasswordDialog.setLocationRelativeTo(view);
        forgotPasswordDialog.setVisible(true);
    }
}