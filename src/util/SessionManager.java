package util;

import java.sql.Timestamp;

/**
 * Session Manager for maintaining user session state
 * Uses util.UserData (immutable) for session management
 */
public class SessionManager {
    
    private static UserData currentUser = null;
    private static boolean isLoggedIn = false;
    
    // Private constructor to prevent instantiation
    private SessionManager() {}
    
    /**
     * Initialize/Reset session
     */
    public static void initialize() {
        currentUser = null;
        isLoggedIn = false;
    }
    
    /**
     * Login user with UserData
     */
    public static void login(UserData user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot log in with null user");
        }
        currentUser = user;
        isLoggedIn = true;
    }
    
    /**
     * Logout current user
     */
    public static void logout() {
        currentUser = null;
        isLoggedIn = false;
    }
    
    /**
     * Check if user is logged in
     */
    public static boolean isLoggedIn() {
        return isLoggedIn && currentUser != null;
    }
    
    /**
     * Get current logged in user
     */
    public static UserData getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Get current user ID
     */
    public static int getCurrentUserId() {
        return currentUser != null ? currentUser.getid() : -1;
    }
    
    /**
     * Get current username
     */
    public static String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : "Guest";
    }
    
    /**
     * Get current user email
     */
    public static String getCurrentUserEmail() {
        return currentUser != null ? currentUser.getEmail() : "";
    }
    
    /**
     * Get current user full name
     */
    public static String getCurrentUserFullName() {
        return currentUser != null ? currentUser.getFullName() : "Guest";
    }
    
    /**
     * Get current user phone
     */
    public static String getCurrentUserPhone() {
        return currentUser != null ? currentUser.getPhone() : "";
    }
    
    /**
     * Get current user address
     */
    public static String getCurrentUserAddress() {
        return currentUser != null ? currentUser.getAddress() : "";
    }
    
    /**
     * Get current user role
     */
    public static String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : "customer";
    }
    
    /**
     * Check if current user is admin
     */
    public static boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }
    
    /**
     * Check if current user is active
     */
    public static boolean isActive() {
        return currentUser != null && currentUser.isActive();
    }
    
    /**
     * Update user profile - replaces the entire session user
     * Since UserData is immutable, we need to create a new instance
     */
    public static void updateUserProfile(UserData updatedUser) {
        if (updatedUser == null) {
            throw new IllegalArgumentException("Updated user cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Cannot update profile when no user is logged in");
        }
        currentUser = updatedUser;
        // isLoggedIn remains true
    }
    
    /**
     * Alias for login, for clarity in some contexts
     */
    public static void setCurrentUser(UserData user) {
        login(user);
    }
    
    /**
     * Check if session exists and is valid
     */
    public static boolean hasValidSession() {
        return isLoggedIn && currentUser != null && currentUser.isActive();
    }
    
    /**
     * Get session info for debugging
     */
    public static String getSessionInfo() {
        if (currentUser != null) {
            return String.format("User: %s (ID: %d, Role: %s, Active: %s)", 
                getCurrentUsername(), 
                getCurrentUserId(), 
                getCurrentUserRole(),
                isActive());
        }
        return "No active session";
    }
    
    /**
     * Get created timestamp
     */
    public static Timestamp getCreatedAt() {
        return currentUser != null ? currentUser.getCreatedAt() : null;
    }
}