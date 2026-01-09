package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import javax.swing.JOptionPane;

public class ConfirmPasswordController {
    private final UserDAO userDao;

    public ConfirmPasswordController() {
        this.userDao = new UserDAOImpl();
    }

    /**
     * Validates and updates the user's password
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
                "Empty Fields", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 2. Check if passwords match
        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(null, 
                "Passwords do not match!\nPlease try again.", 
                "Password Mismatch", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 3. Check password strength (minimum 6 characters)
        if (newPass.length() < 6) {
            JOptionPane.showMessageDialog(null, 
                "Password must be at least 6 characters long.", 
                "Weak Password", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 4. Update password in database (plain text for now - as in your current DAO)
        boolean success = userDao.updatePassword(email, newPass);

        if (success) {
            JOptionPane.showMessageDialog(null, 
                "Password updated successfully!\nYou can now log in with your new password.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, 
                "Failed to update password.\nPlease try again later.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}