package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import util.PasswordHasher;
import javax.swing.JOptionPane;

/**
 * Controller for Confirm Password functionality
 * Handles password validation and updating
 * Works with the same UI for both Forgot Password and Profile Change flows
 */
public class ConfirmPasswordController {
    
    private final UserDAO userDao;
    
    public ConfirmPasswordController() {
        this.userDao = new UserDAOImpl();
    }
    
    /**
     * Validates and updates the user's password
     * Used for both:
     * 1. Forgot Password flow (after OTP verification)
     * 2. Profile password change (for logged-in users)
     * 
     * @param email the user's email
     * @param newPass new password
     * @param confirmPass confirm password
     * @return true if password updated successfully, false otherwise
     */
    public boolean updatePassword(String email, String newPass, String confirmPass) {
        // 1. Check if fields are empty
        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please fill in both password fields.", 
                "Empty Fields", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 2. Check if passwords match
        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(null, 
                "Passwords do not match!\nPlease try again.", 
                "Password Mismatch", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // 3. Check password strength (minimum 8 characters)
        if (newPass.length() < 8) {
            JOptionPane.showMessageDialog(null, 
                "Password must be at least 8 characters long.", 
                "Weak Password", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 4. Check password strength with PasswordHasher
        if (!PasswordHasher.isPasswordStrong(newPass)) {
            String message = PasswordHasher.getPasswordStrengthMessage(newPass);
            int choice = JOptionPane.showConfirmDialog(null,
                message + "\n\nDo you want to continue with this password?",
                "Weak Password",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (choice != JOptionPane.YES_OPTION) {
                return false;
            }
        }
        
        // 5. Update password in database (will be hashed by DAO)
        boolean success = userDao.updatePassword(email, newPass);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "Password updated successfully!\nYou can now log in with your new password.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, 
                "Failed to update password.\nPlease try again later.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}