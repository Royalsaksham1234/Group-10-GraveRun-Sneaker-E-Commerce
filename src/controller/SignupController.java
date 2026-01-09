package controller;

import view.GraveRunSignup;
import view.GraveRunLogin;
import view.Dashboard1;
import dao.UserDAO;
import dao.UserDAOImpl;
import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import model.UserModel;
import util.SessionManager;
import util.UserData;
import util.PasswordHasher;
import java.sql.Timestamp;

/**
 * Controller for Signup operations
 * Handles user registration and session creation
 */
public class SignupController {
    
    private final GraveRunSignup view;
    private final UserDAO userDAO;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|edu|gov|co|info|biz|me|io)$"
    );
    
    public SignupController(GraveRunSignup view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();
        initController();
    }
    
    private void initController() {
        // Signup button
        view.getSignupButton().addActionListener(e -> validateAndSignup());
        
        // Login link
        view.getLogin().addActionListener(e -> openLogin());
    }
    
    private void openLogin() {
        Window parent = SwingUtilities.getWindowAncestor(view);
        GraveRunLogin login = new GraveRunLogin(
            parent,
            Dialog.ModalityType.APPLICATION_MODAL
        );
        login.setLocationRelativeTo(view);
        login.setVisible(true);
        view.dispose();
    }
    
    private void validateAndSignup() {
        String email = view.getEmailField().getText().trim();
        String fullName = view.getFullNameField().getText().trim();  // Get full name from view
        String password = new String(view.getPasswordField().getPassword());
        String confirmPassword = new String(view.getConfirmPasswordField().getPassword());
        
        // Validate full name
        if (!validateFullName(fullName)) {
            return;
        }
        
        // Validate email
        if (!validateEmail(email)) {
            return;
        }
        
        // Validate password
        if (!validatePassword(password, confirmPassword)) {
            return;
        }
        
        // Check if email already exists
        if (userDAO.isEmailTaken(email)) {
            JOptionPane.showMessageDialog(
                view, 
                "Email already registered. Please login or use a different email.", 
                "Email Exists", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Register user
        registerUser(email, fullName, password);
    }
    
    private boolean validateFullName(String fullName) {
        if (fullName.isEmpty() || fullName.equals("Full Name")) {
            JOptionPane.showMessageDialog(
                view, 
                "Please enter your full name.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        if (fullName.length() < 2) {
            JOptionPane.showMessageDialog(
                view, 
                "Full name must be at least 2 characters long.", 
                "Invalid Name", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        return true;
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
                "Invalid email format.\nExample: user@gmail.com", 
                "Invalid Email", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        return true;
    }
    
    private boolean validatePassword(String password, String confirmPassword) {
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(
                view, 
                "Please enter password.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(
                view, 
                "Password must be at least 8 characters long.", 
                "Weak Password", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        // Check password strength (optional but recommended)
        if (!PasswordHasher.isPasswordStrong(password)) {
            String message = PasswordHasher.getPasswordStrengthMessage(password);
            int result = JOptionPane.showConfirmDialog(
                view,
                message + "\n\nDo you want to continue with this password?",
                "Weak Password",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (result != JOptionPane.YES_OPTION) {
                return false;
            }
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                view, 
                "Passwords do not match.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        return true;
    }
    
    private void registerUser(String email, String fullName, String password) {
        // Create username from email
        String username = email.split("@")[0];
        
        // Create UserModel for registration (this goes to DAO)
        UserModel user = new UserModel(email, fullName, password, null);  // Pass fullName
        
        // Register in database
        int userId = userDAO.registerUser(user);
        
        if (userId <= 0) {
            JOptionPane.showMessageDialog(
                view, 
                "Signup failed. Please try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Create UserData for session (immutable)
        UserData sessionUser = new UserData(
            userId,                                      // userId from DB
            fullName,                                    // fullName
            username,                                    // username
            email,                                       // email
            "",                                          // phone (empty initially)
            "",                                          // address (empty initially)
            "customer",                                  // role
            true,                                        // isActive
            new Timestamp(System.currentTimeMillis())   // createdAt
        );
        
        // Login user immediately after signup
        SessionManager.login(sessionUser);
        
        // Success message
        JOptionPane.showMessageDialog(
            view,
            "Signup successful!\nWelcome, " + fullName + "!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Close signup dialog
        view.dispose();
        
        // Open Customer Dashboard (default for new signups)
        Dashboard1 customerDashboard = new Dashboard1();
        customerDashboard.setLocationRelativeTo(null);
        customerDashboard.setVisible(true);
    }
}