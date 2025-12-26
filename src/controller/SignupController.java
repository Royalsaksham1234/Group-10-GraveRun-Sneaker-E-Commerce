package controller;

import dao.UserDao;
import view.GraveRunLogin;
import javax.swing.JOptionPane;

/**
 * SignupController for GraveRun
 * Handles Signup validation, DB, and navigation
 */
public class SignupController {

    private final UserDao userDao = new UserDao();

    /**
     * Handles user signup and navigation
     * @param username
     * @param email
     * @param password
     * @param confirmPassword
     */
    public void signup(String username, String email, String password, String confirmPassword) {

        // Validation
        if (username == null || username.isEmpty() ||
            email == null || email.isEmpty() ||
            password == null || password.isEmpty() ||
            confirmPassword == null || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(null, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        public void addLoginActionListener(ActionListener listener) {
            Login.addActionListener(listener);
        }
        
    }
    
    // This method should be called from GraveRunSignup's SignActionPerformed
    public void handleSignUp(String username, String email, char[] password, char[] confirmPassword) {
        try {
            // Validate inputs
            if (!validateSignUpInputs(username, email, password, confirmPassword)) {
                return;
            }
            
            // Check if user already exists
            if (userDao.userExists(email)) {
                JOptionPane.showMessageDialog(view,
                    "User with this email already exists!",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create new user
            UserData newUser = new UserData();
            newUser.setEmail(email);
            newUser.setUsername(username);
            // In production, hash the password!
            newUser.setPasswordHash(new String(password));
            
            // Save to database
            if (userDao.createUser(newUser)) {
                // Get the saved user with ID
                UserData savedUser = userDao.getUserByEmail(email);
                
                // Set session
                SessionManager.login(savedUser);
                
                // Clear sensitive data
                clearPasswordArrays(password, confirmPassword);
                
                // Show success message
                JOptionPane.showMessageDialog(view,
                    "Account created successfully! Welcome " + username,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Navigate to dashboard
                navigateToDashboard();
            } else {
                JOptionPane.showMessageDialog(view,
                    "Failed to create account. Please try again!",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                "An error occurred: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private boolean validateSignUpInputs(String username, String email, 
                                        char[] password, char[] confirmPassword) {
        // Check username
        if (username.isEmpty() || "Username".equals(username)) {
            JOptionPane.showMessageDialog(view,
                "Please enter a username!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(view,
                "Username must be at least 3 characters long!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Check email
        if (email.isEmpty() || "Email".equals(email)) {
            JOptionPane.showMessageDialog(view,
                "Please enter your email address!",
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
        
        // Check password
        String passwordStr = new String(password);
        if (passwordStr.isEmpty() || "Password".equals(passwordStr)) {
            JOptionPane.showMessageDialog(view,
                "Please enter a password!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (password.length < 8) {
            JOptionPane.showMessageDialog(view,
                "Password must be at least 8 characters long!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Check password strength
        if (!isPasswordStrong(password)) {
            JOptionPane.showMessageDialog(view,
                "Password must contain at least one uppercase letter, " +
                "one lowercase letter, and one digit!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Check confirm password
        String confirmPasswordStr = new String(confirmPassword);
        if (confirmPasswordStr.isEmpty() || "Confirm Password".equals(confirmPasswordStr)) {
            JOptionPane.showMessageDialog(view,
                "Please confirm your password!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Check if passwords match
        if (!passwordStr.equals(confirmPasswordStr)) {
            JOptionPane.showMessageDialog(view,
                "Passwords do not match!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean isPasswordStrong(char[] password) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        
        for (char c : password) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
            
            if (hasUpper && hasLower && hasDigit) {
                return true;
            }
        }
        
        return false;
    }
    
    private void clearPasswordArrays(char[] password1, char[] password2) {
        if (password1 != null) {
            Arrays.fill(password1, '\0');
        }
        if (password2 != null) {
            Arrays.fill(password2, '\0');
        }
    }
    
    private void navigateToDashboard() {
        view.dispose();
        
        // Navigate to dashboard
        java.awt.EventQueue.invokeLater(() -> {
            DashboardController dashboardController = new DashboardController();
            dashboardController.showDashboard();
        });
    }
}