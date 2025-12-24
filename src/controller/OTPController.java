package controller;

import java.util.Random;
import javax.swing.JOptionPane;

public class OTPController {
    
    private String generatedOTP;

    // Generate a 4-digit OTP
    public void generateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); // ensures 4-digit
        generatedOTP = String.valueOf(otp);
        System.out.println("Generated OTP: " + generatedOTP); // for testing
        // You can integrate your email/SMS API here to send OTP
        JOptionPane.showMessageDialog(null, "OTP sent: " + generatedOTP);
    }

    // Verify the user input OTP
    public boolean verifyOTP(String userInput) {
        if (userInput == null || userInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter OTP");
            return false;
        }
        if (userInput.equals(generatedOTP)) {
            JOptionPane.showMessageDialog(null, "OTP verified successfully!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid OTP. Try again.");
            return false;
        }
    }
}
