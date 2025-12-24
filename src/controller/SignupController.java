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

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if user already exists
        if (userDao.checkUserExists(username, email)) {
            JOptionPane.showMessageDialog(null, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Register user in DB
        boolean success = userDao.registerUser(username, email, password);
        if (success) {
            JOptionPane.showMessageDialog(null, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Navigate to login screen
            new GraveRunLogin().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Signup failed due to DB error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
