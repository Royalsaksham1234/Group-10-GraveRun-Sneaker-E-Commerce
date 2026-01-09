package util;

import model.AdminUserData;

public class SessionManager {
    private static AdminUserData currentUser = null;
    private static boolean isLoggedIn = false;
    
    public static void initialize() {
        currentUser = null;
        isLoggedIn = false;
    }
    
    public static void login(AdminUserData user) {
        currentUser = user;
        isLoggedIn = true;
    }
    
    public static void logout() {
        currentUser = null;
        isLoggedIn = false;
    }
    
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public static AdminUserData getCurrentUser() {
        return currentUser;
    }
    
    public static int getCurrentUserId() {
        return currentUser != null ? currentUser.getid() : -1;
    }
    
    public static String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : "Guest";
    }
    
    public static String getCurrentUserEmail() {
        return currentUser != null ? currentUser.getEmail() : "";
    }
    
    public static boolean hasPermission(String permission) {
        // Add permission logic if needed
        return isLoggedIn;
    }
    
    public static void updateUserProfile(AdminUserData updatedUser) {
        if (currentUser != null) {
            currentUser = updatedUser;
        }
    }
}