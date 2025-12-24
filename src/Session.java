public class Session {

    private static String currentUserEmail;

    // Private constructor to prevent creating instances
    private Session() { }

    // Set current logged-in user
    public static void setCurrentUser(String email) {
        currentUserEmail = email; // no 'this' here
    }

    // Get current logged-in user
    public static String getCurrentUser() {
        return currentUserEmail;
    }

    // Clear session (logout)
    public static void logout() {
        currentUserEmail = null;
    }

    // Check if a user is logged in
    public static boolean isLoggedIn() {
        return currentUserEmail != null;
    }
}
