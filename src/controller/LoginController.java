package controller;

import dao.UserDao;
import javax.swing.JOptionPane;

public class LoginController {

    private final UserDao userDao;

    public LoginController() {
        this.userDao = new UserDao();
    }

    /**
     * Login method to validate user credentials with database
     * @param email
     * @param password
     * @return 
     */
    public boolean login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both email and password");
            return false;
        }

        boolean isValid = userDao.validateLogin(email, password);
        if (isValid) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "User not found or incorrect password");
            return false;
        }
    }

    /**
     * Optional: Signup new user
     * @param username
     * @param email
     * @param password
     * @return 
     */
    public boolean addUser(String username, String email, String password) {
        if (userDao.checkUserExists(username, email)) {
            JOptionPane.showMessageDialog(null, "User already exists");
            return false;
        }
        boolean registered = userDao.registerUser(username, email, password);
        if (registered) {
            JOptionPane.showMessageDialog(null, "User registered successfully!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed");
            return false;
        }
    }
}
