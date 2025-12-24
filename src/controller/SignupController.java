package controller;

import view.GraveRunSignup;
import dao.userDAO;
import model.UserData;
import util.SessionManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SignupController {
    private GraveRunSignup view;
    private userDAO userDao;
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    public SignupController(GraveRunSignup view, userDAO userDao) {
        this.view = view;
        this.userDao = userDao;
        initController();
    }
    
    private void initController() {
        // Getter methods need to be added to GraveRunSignup
        // For now, we'll handle the events differently
        // You need to add these methods to GraveRunSignup:
        // - getUsername()
        // - getEmail()
        // - getPassword()
        // - getConfirmPassword()
        // - addSignUpActionListener()
        // - addLoginActionListener()
        
        // Temporarily, we'll use a workaround
        setupTemporaryListeners();
    }
    
    private void setupTemporaryListeners() {
        // This is a temporary solution. You should add proper getters to GraveRunSignup
        // and use: view.addSignUpActionListener(...)
        
        // Since GraveRunSignup doesn't have getter methods yet,
        // you need to add these to the view:
        /*
        In GraveRunSignup.java, add:
        
        public String getUsername() {
            return Username.getText().trim();
        }
        
        public String getEmail() {
            return emailField1.getText().trim();
        }
        
        public char[] getPassword() {
            return passwordField.getPassword();
        }
        
        public char[] getConfirmPassword() {
            return ConfirmP.getPassword();
        }
        
        public void clearPasswordFields() {
            passwordField.setText("");
            ConfirmP.setText("");
        }
        
        public void addSignUpActionListener(ActionListener listener) {
            Sign.addActionListener(listener);
        }
        
        public void addLoginActionListener(ActionListener listener) {
            Login.addActionListener(listener);
        }
        */
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
    
}