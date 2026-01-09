package controller;

import model.OTPService;
import view.GraveRunOTP;

import javax.swing.*;

public class OTPController {

    private final String userEmail;
    private final GraveRunOTP otpFrame;

    public OTPController(GraveRunOTP frame, String email) {
        this.otpFrame = frame;
        this.userEmail = email;
    }

    public boolean verifyOTP(String enteredOTP) {
        if (enteredOTP == null || enteredOTP.trim().isEmpty()) {
            JOptionPane.showMessageDialog(otpFrame, "Enter the OTP", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        boolean correct = OTPService.validateOTP(userEmail, enteredOTP.trim());

        if (correct) {
            JOptionPane.showMessageDialog(otpFrame, "OTP Verified Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(otpFrame, "Invalid OTP", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return correct;
    }
}
