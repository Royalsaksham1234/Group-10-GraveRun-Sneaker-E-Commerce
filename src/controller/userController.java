package controller;

import javax.swing.JOptionPane;
import java.util.HashMap;

public class userController {
    
    private static class UserRecord {
    String fullName;
    String password;

    UserRecord(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }
}

    // Simulated user database (email -> password)
    private static final HashMap<String,UserRecord> userDB = new HashMap<>();
    static {
    // Update the static initializer for the test user
    userDB.put("testuser@gmail.com", new UserRecord("Test User", "password123"));
}

   
    // SIGNUP: Add a new user
   public boolean signup(String email, String full_name, String password, String confirmPassword) {
    if (email.isEmpty() || password.isEmpty() || full_name.isEmpty() || confirmPassword.isEmpty()) {
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

    // Correct way to store multiple fields:
    userDB.put(email, new UserRecord(full_name, password));
    
    JOptionPane.showMessageDialog(null, "Signup successful!");
    return true;
}

    // LOGIN: Check email and password
   public boolean login(String email, String password) {
    if (email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill all fields");
        return false;
    }

    // Retrieve the UserRecord object
    UserRecord user = userDB.get(email);

    if (user != null && user.password.equals(password)) {
        JOptionPane.showMessageDialog(null, "Welcome back, " + user.fullName + "!");
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

    // 1. Check if user exists
    if (!userDB.containsKey(email)) {
        JOptionPane.showMessageDialog(null, "User not found");
        return false;
    }

    if (!newPassword.equals(confirmPassword)) {
        JOptionPane.showMessageDialog(null, "Passwords do not match");
        return false;
    }

    // 2. RETRIEVE the existing record to get the full name
    UserRecord existingUser = userDB.get(email);

    // 3. UPDATE the password in the record object
    existingUser.password = newPassword;

    // 4. PUT the updated object back into the map
    userDB.put(email, existingUser);

    JOptionPane.showMessageDialog(null, "Password reset successful!");
    return true;
}
}