package controller;

import javax.swing.JOptionPane;
import java.util.regex.Pattern;

/**
 * ForgotPasswordController
 * Handles forgot password email validation & OTP trigger logic
 */
public class ForgotPasswordController {

    /**
     * Validate email and simulate OTP sending
     * @param email
     */
    public void sendOTP(String email) {

        if (email == null || email.isEmpty() || email.equals("Email")) {
            JOptionPane.showMessageDialog(null,
                    "Please enter your email",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simple email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailRegex, email)) {
            JOptionPane.showMessageDialog(null,
                    "Invalid email format",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TEMP OTP LOGIC
        JOptionPane.showMessageDialog(null,
                "OTP has been sent to your email",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
