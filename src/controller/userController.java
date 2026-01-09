package controller;

import javax.swing.JOptionPane;
import java.util.HashMap;

public class userController {

    // Simulated user database (email -> password)
    private static final HashMap<String, String> userDB = new HashMap<>();

    static {
        // Example existing user
        userDB.put("testuser@gmail.com", "password123");
    }

    // SIGNUP: Add a new user
    public boolean signup(String email, String password, String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match");
            return false;
        }

        if (userDB.containsKey(email)) {
            JOptionPane.showMessageDialog(null, "User already exists");
            return false;
        }

        userDB.put(email, password);
        JOptionPane.showMessageDialog(null, "Signup successful!");
        return true;
    }

    // LOGIN: Check email and password
    public boolean login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
            return false;
        }

        if (userDB.containsKey(email) && userDB.get(email).equals(password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid email or password");
            return false;
        }
    }

    // FORGOT PASSWORD: Reset password if user exists
    public boolean resetPassword(String email, String newPassword, String confirmPassword) {
        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
            return false;
        }

        if (!userDB.containsKey(email)) {
            JOptionPane.showMessageDialog(null, "User not found");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match");
            return false;
        }

        userDB.put(email, newPassword);
        JOptionPane.showMessageDialog(null, "Password reset successful!");
        return true;
    }
}
