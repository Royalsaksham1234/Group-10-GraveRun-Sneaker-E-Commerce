package controller;

import javax.swing.JOptionPane;

/**
 * ConfirmPasswordController
 * Handles password reset validation logic
 */
public class ConfirmPasswordController {

    /**
     * Validate and reset password
     * @param email user's email (optional for future DB use)
     * @param newPassword
     * @param confirmPassword
     * @return true if password reset is successful, false otherwise
     */
    public boolean updatePassword(String email, String newPassword, String confirmPassword) {

        if (newPassword == null || confirmPassword == null ||
            newPassword.isEmpty() || confirmPassword.isEmpty() ||
            newPassword.equals("PASSWORD") || confirmPassword.equals("PASSWORD")) {

            JOptionPane.showMessageDialog(null,
                    "Please fill all fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(null,
                    "Password must be at least 6 characters",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null,
                    "Passwords do not match",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // TODO: Replace this with database update logic later
        JOptionPane.showMessageDialog(null,
                "Password reset successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        return true;
    }
}
