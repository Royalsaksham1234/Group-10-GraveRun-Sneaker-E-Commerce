package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for secure password hashing and verification
 * Uses BCrypt algorithm for strong password security
 */
public class PasswordHasher {
    
    // BCrypt work factor (log rounds) - higher is more secure but slower
    // 12 is a good balance between security and performance
    private static final int WORK_FACTOR = 12;
    
    /**
     * Hashes a plain text password using BCrypt
     * 
     * @param plainTextPassword The password to hash
     * @return The hashed password
     */
    public static String hashPassword(String plainTextPassword) {
        if (plainTextPassword == null || plainTextPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        // Generate a salt and hash the password
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(WORK_FACTOR));
    }
    
    /**
     * Verifies a plain text password against a hashed password
     * 
     * @param plainTextPassword The password to verify
     * @param hashedPassword The hashed password to check against
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        if (plainTextPassword == null || plainTextPassword.isEmpty()) {
            return false;
        }
        
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        
        try {
            // BCrypt handles salt automatically from the hashed password
            return BCrypt.checkpw(plainTextPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Invalid hash format
            System.err.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a password needs rehashing (e.g., if work factor has changed)
     * 
     * @param hashedPassword The hashed password to check
     * @return true if the password should be rehashed, false otherwise
     */
    public static boolean needsRehash(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return true;
        }
        
        try {
            // Extract the work factor from the hash
            String[] parts = hashedPassword.split("\\$");
            if (parts.length >= 3) {
                int currentWorkFactor = Integer.parseInt(parts[2]);
                return currentWorkFactor < WORK_FACTOR;
            }
        } catch (Exception e) {
            System.err.println("Error checking hash: " + e.getMessage());
        }
        
        return true;
    }
    
    /**
     * Validates password strength
     * 
     * @param password The password to validate
     * @return true if password meets minimum requirements
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }
        
        // Require at least 3 out of 4 character types
        int typesPresent = (hasUpper ? 1 : 0) + (hasLower ? 1 : 0) + 
                          (hasDigit ? 1 : 0) + (hasSpecial ? 1 : 0);
        
        return typesPresent >= 3;
    }
    
    /**
     * Gets a detailed password strength message
     * 
     * @param password The password to evaluate
     * @return A message describing password requirements
     */
    public static String getPasswordStrengthMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty";
        }
        
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }
        
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^A-Za-z0-9].*");
        
        StringBuilder message = new StringBuilder("Password should contain: ");
        boolean needsImprovement = false;
        
        if (!hasUpper) {
            message.append("uppercase letter, ");
            needsImprovement = true;
        }
        if (!hasLower) {
            message.append("lowercase letter, ");
            needsImprovement = true;
        }
        if (!hasDigit) {
            message.append("number, ");
            needsImprovement = true;
        }
        if (!hasSpecial) {
            message.append("special character, ");
            needsImprovement = true;
        }
        
        if (!needsImprovement) {
            return "Password is strong";
        }
        
        // Remove trailing comma and space
        String result = message.toString();
        return result.substring(0, result.length() - 2);
    }
}