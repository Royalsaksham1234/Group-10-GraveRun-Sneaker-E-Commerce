package util;

import model.UserData;
import java.io.*;
import java.util.Properties;

/**
 * SessionManager handles user session persistence
 */
public class SessionManager {
    private static final String SESSION_FILE = "session.properties";
    private static UserData currentUser = null;
    
    /**
     * Save user session to file
     * @param user User to save
     */
    public static void saveSession(UserData user) {
        currentUser = user;
        Properties properties = new Properties();
        
        properties.setProperty("userId", String.valueOf(user.getUserId()));
        properties.setProperty("username", user.getUsername());
        properties.setProperty("email", user.getEmail());
        properties.setProperty("password", user.getPassword());
        
        try (FileOutputStream out = new FileOutputStream(SESSION_FILE)) {
            properties.store(out, "User Session Data");
            System.out.println("Session saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving session: " + e.getMessage());
        }
    }
    
    /**
     * Load user session from file
     * @return User object if session exists, null otherwise
     */
    public static UserData loadSession() {
        File sessionFile = new File(SESSION_FILE);
        
        if (!sessionFile.exists()) {
            return null;
        }
        
        Properties properties = new Properties();
        
        try (FileInputStream in = new FileInputStream(SESSION_FILE)) {
            properties.load(in);
            
            int userId = Integer.parseInt(properties.getProperty("userId"));
            String username = properties.getProperty("username");
            String email = properties.getProperty("email");
            String password = properties.getProperty("password");
            
            currentUser = new UserData(userId, username, email,password);
            System.out.println("Session loaded successfully!");
            return currentUser;
            
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading session: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Clear user session (logout)
     */
    public static void clearSession() {
        currentUser = null;
        File sessionFile = new File(SESSION_FILE);
        
        if (sessionFile.exists()) {
            if (sessionFile.delete()) {
                System.out.println("Session cleared successfully!");
            } else {
                System.err.println("Failed to delete session file.");
            }
        }
    }
    
    /**
     * Check if user is logged in
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        if (currentUser == null) {
            currentUser = loadSession();
        }
        return currentUser != null;
    }
    
    /**
     * Get current logged-in user
     * @return Current user or null if not logged in
     */
    public static UserData getCurrentUser() {
        if (currentUser == null) {
            currentUser = loadSession();
        }
        return currentUser;
    }
    
    /**
     * Update current user in session
     * @param user Updated user object
     */
    public static void updateCurrentUser(UserData user) {
        saveSession(user);
    }
}