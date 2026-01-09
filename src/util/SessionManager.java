package util;

import model.AdminUserData;

public class SessionManager {
    private static AdminUserData currentUser = null;
    private static boolean isLoggedIn = false;

    // Private constructor to prevent instantiation
    private SessionManager() {}

    public static void initialize() {
        currentUser = null;
        isLoggedIn = false;
    }

    public static void login(UserData user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot log in with null user");
        }
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

    public static String getCurrentUserFullName() {
        return currentUser != null ? currentUser.getFullName() : "Guest";
    }

    public static UserData getCurrentUser() {
            currentUser = updatedUser;
            // isLoggedIn remains true
        }
    }

    // Optional: alias for login, for clarity in some contexts
    public static void setCurrentUser(UserData user) {
        login(user);
    }
}

    public static String getCurrentUserPhone() {
        return currentUser != null ? currentUser.getPhone() : "";
    }

    public static String getCurrentUserAddress() {
        return currentUser != null ? currentUser.getAddress() : "";
    }

    public static boolean isAdmin() {
        return currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole());
    }

    // Safe way to update profile - replaces the entire session user
    public static void updateUserProfile(UserData updatedUser) {
        if (updatedUser == null) {
            throw new IllegalArgumentException("Updated user cannot be null");
        }
        if (isLoggedIn) {
