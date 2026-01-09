package controller;

import model.AdminUserData;
import dao.AdminUserDAO;
import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for User Management
 * Handles all business logic for user operations
 */
public class AdminUserController {
    
    private final AdminUserDAO userDAO;
    
    public AdminUserController(AdminUserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    // ==================== Data Retrieval Methods ====================
    
    /**
     * Get all users from the database
     */
    public List<AdminUserData> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            return List.of(); // Return empty list instead of null
        }
    }
    
    /**
     * Get a specific user by ID
     */
    public AdminUserData getUserById(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (Exception e) {
            System.err.println("Error retrieving user: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get a specific user by email
     */
    public AdminUserData getUserByEmail(String email) {
        try {
            return userDAO.getUserByEmail(email);
        } catch (Exception e) {
            System.err.println("Error retrieving user by email: " + e.getMessage());
            return null;
        }
    }
    
    // ==================== User Operations ====================
    
    /**
     * Delete a user with validation
     */
    public boolean deleteUser(int userId) {
        // Check if user exists
        AdminUserData user = getUserById(userId);
        if (user == null) {
            JOptionPane.showMessageDialog(null, 
                "User not found.", 
                "Delete Failed", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            boolean success = userDAO.deleteUser(userId);
            if (!success) {
                JOptionPane.showMessageDialog(null, 
                    "Failed to delete user. Please try again.", 
                    "Delete Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error deleting user: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Deactivate a user (soft delete)
     */
    public boolean deactivateUser(int userId) {
        AdminUserData user = getUserById(userId);
        if (user == null) {
            JOptionPane.showMessageDialog(null, 
                "User not found.", 
                "Deactivation Failed", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            return userDAO.deactivateUser(userId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error deactivating user: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Update user information
     */
    public boolean updateUser(AdminUserData user) {
        if (user == null) {
            JOptionPane.showMessageDialog(null, 
                "Invalid user data.", 
                "Update Failed", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            return userDAO.updateUser(user);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error updating user: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // ==================== Search and Filter Methods ====================
    
    /**
     * Search users by keyword (searches username, email, and full name)
     */
    public List<AdminUserData> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllUsers();
        }
        
        String searchTerm = keyword.toLowerCase().trim();
        List<AdminUserData> allUsers = getAllUsers();
        
        return allUsers.stream()
            .filter(user -> matchesSearchCriteria(user, searchTerm))
            .collect(Collectors.toList());
    }

    /**
     * Check if user matches search criteria
     */
    private boolean matchesSearchCriteria(AdminUserData user, String searchTerm) {
        String username = user.getUsername() != null ? user.getUsername().toLowerCase() : "";
        String email = user.getEmail() != null ? user.getEmail().toLowerCase() : "";
        String fullName = user.getFullName() != null ? user.getFullName().toLowerCase() : "";
        
        return username.contains(searchTerm) ||
               email.contains(searchTerm) ||
               fullName.contains(searchTerm);
    }

    // ==================== Data Formatting Methods ====================
    
    /**
     * Format user data for table display
     */
    public Object[] formatUserForTable(AdminUserData user) {
        return new Object[]{
            user.getid(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName() != null ? user.getFullName() : "N/A",
            user.getPhone() != null ? user.getPhone() : "N/A",
            user.isActive() ? "Active" : "Inactive"
        };
    }

    /**
     * Get status display text
     */
    public String getStatusText(boolean isActive) {
        return isActive ? "Active" : "Inactive";
    }

    // ==================== State Check Methods ====================
    
    /**
     * Check if users list is empty
     */
    public boolean hasUsers() {
        return !getAllUsers().isEmpty();
    }

    /**
     * Get total user count
     */
    public int getTotalUserCount() {
        return getAllUsers().size();
    }

    /**
     * Get active user count
     */
    public int getActiveUserCount() {
        return (int) getAllUsers().stream()
            .filter(AdminUserData::isActive)
            .count();
    }

    /**
     * Get inactive user count
     */
    public int getInactiveUserCount() {
        return (int) getAllUsers().stream()
            .filter(user -> !user.isActive())
            .count();
    }

    // ==================== Validation Methods ====================
    
    /**
     * Validate user data
     */
    public boolean validateUser(AdminUserData user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Username cannot be empty.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Email cannot be empty.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidEmail(user.getEmail())) {
            JOptionPane.showMessageDialog(null, 
                "Invalid email format.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}