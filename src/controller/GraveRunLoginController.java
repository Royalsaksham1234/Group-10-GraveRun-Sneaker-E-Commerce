package controller;

import view.GraveRunNewLogin;
import dao.userDAO;
import model.UserData;
import util.SessionManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.regex.Pattern;

public class GraveRunLoginController {
    private GraveRunNewLogin view;
    private userDAO userDao;
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    public GraveRunLoginController(GraveRunNewLogin view, userDAO userDao) {
        this.view = view;
        this.userDao = userDao;
        initController();
    }
    
    private void initController() {
        // Add action listeners
        view.addLoginActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        view.addSignUpActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUpNavigation();
            }
        });
    }
    
    private void handleLogin() {
        try {
            String email = view.getEmail();
            char[] password = view.getPassword();
            char[] confirmPassword = view.getConfirmPassword();
            
            // Validate inputs
            if (!validateLoginInputs(email, password, confirmPassword)) {
                return;
            }
            
            // Check if passwords match
            if (!passwordsMatch(password, confirmPassword)) {
                JOptionPane.showMessageDialog(view,
                    "Passwords do not match!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convert password to string (in production, use hashing)
            String passwordStr = new String(password);
            
            // Authenticate user
            if (userDao.authenticateUser(email, passwordStr)) {
                // Get user data
                UserData user = userDao.getUserByEmail(email);
                
                if (user != null) {
                    // Set session
                    SessionManager.login(user);
                    
                    // Clear sensitive data
                    clearPasswordArrays(password, confirmPassword);
                    
                    // Show success message
                    JOptionPane.showMessageDialog(view,
                        "Login successful! Welcome " + user.getUsername(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Navigate to dashboard
                    navigateToDashboard();
                } else {
                    JOptionPane.showMessageDialog(view,
                        "User not found!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view,
                    "Invalid email or password!",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                "An error occurred: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            // Clear password fields
            view.clearPasswordFields();
        }
    }
    
    private boolean validateLoginInputs(String email, char[] password, char[] confirmPassword) {
        // Check for placeholder values
        if ("Email".equals(email) || email.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Please enter your email address!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String passwordStr = new String(password);
        if ("Password".equals(passwordStr) || password.length == 0) {
            JOptionPane.showMessageDialog(view,
                "Please enter your password!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String confirmPasswordStr = new String(confirmPassword);
        if ("Confirm Password".equals(confirmPasswordStr) || confirmPassword.length == 0) {
            JOptionPane.showMessageDialog(view,
                "Please confirm your password!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validate email format
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(view,
                "Please enter a valid email address!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Check password length
        if (password.length < 8) {
            JOptionPane.showMessageDialog(view,
                "Password must be at least 8 characters long!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean passwordsMatch(char[] password1, char[] password2) {
        if (password1.length != password2.length) {
            return false;
        }
        
        for (int i = 0; i < password1.length; i++) {
            if (password1[i] != password2[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    private void clearPasswordArrays(char[] password1, char[] password2) {
        if (password1 != null) {
            Arrays.fill(password1, '\0');
        }
        if (password2 != null) {
            Arrays.fill(password2, '\0');
        }
    }
    
    private void handleSignUpNavigation() {
        view.dispose();
        // Navigate to signup page
        java.awt.EventQueue.invokeLater(() -> {
            view.GraveRunSignup signupView = new view.GraveRunSignup();
            // You would pass the controller here
            signupView.setVisible(true);
        });
    }
    
    private void navigateToDashboard() {
        view.dispose();
        
        // Navigate to dashboard with current user
        java.awt.EventQueue.invokeLater(() -> {
            DashboardController dashboardController = new DashboardController();
            dashboardController.showDashboard();
        });
    }
}