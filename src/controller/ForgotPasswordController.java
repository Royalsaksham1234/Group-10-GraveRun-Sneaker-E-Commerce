package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import util.EmailSender;
import view.GraveRunForgotPassword;
import view.GraveRunOTP;
import model.OTPService;

import javax.swing.*;
import java.util.regex.Pattern;

public class ForgotPasswordController {

    private final GraveRunForgotPassword view;
    private final UserDAO userDAO;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|edu|gov|co|info|biz|me|io|ph)$",
            Pattern.CASE_INSENSITIVE
    );

    public ForgotPasswordController(GraveRunForgotPassword view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();
        initController();
    }

    private void initController() {
        view.getSendResetCodeButton().addActionListener(e -> sendOTP());
    }

    private void sendOTP() {
        String emailText = view.getEmailField().getText().trim();
        String email = emailText.toLowerCase();

        // Validate email
        if (emailText.isEmpty() || emailText.equals(" Email")) {
            JOptionPane.showMessageDialog(view, "Please enter your email address.", "Empty Email", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(view, "Invalid email format.\nExample: user@gmail.com", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!userDAO.isEmailTaken(email)) {
            JOptionPane.showMessageDialog(view, "No account found with this email:\n" + email, "Email Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disable button to prevent multiple clicks
        view.disableSendButton();

        // Generate OTP and store it
        String otp = OTPService.generateOTP(6);
        OTPService.storeOTP(email, otp);

        // Send OTP in a separate thread
        new Thread(() -> {
            boolean sent = EmailSender.sendOTP(email, otp);

            SwingUtilities.invokeLater(() -> {
                view.enableSendButton();

                if (sent) {
                    JOptionPane.showMessageDialog(view,
                            "OTP sent to " + email + "\n(Check inbox or spam)",
                            "OTP Sent",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Open OTP frame (email only, OTP is stored in OTPService)
                    new GraveRunOTP(email).setVisible(true);
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Failed to send OTP. Check internet connection or App Password.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }).start();
    }
}
