package util;

public class EmailSender {
    /**
     * Sends an OTP to the specified email.
     * @return true if the email was "sent", false otherwise.
     */
    public static boolean sendOTP(String email, String otp) {
        try {
            // For now, just simulate sending
            System.out.println("Sending OTP " + otp + " to " + email);
            
            // Later: integrate with JavaMail API or SMTP
            // If the integration fails, you would return false in the catch block
            
            return true; // Return true because the "sending" was successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}