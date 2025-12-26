/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPService {
    private static final Map<String, String> otpStore = new HashMap<>();

    public static String generateOTP(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // digits only
        }
        return sb.toString();
    }

    public static void storeOTP(String email, String otp) {
        otpStore.put(email, otp);
    }

    public static boolean validateOTP(String email, String enteredOtp) {
        return otpStore.containsKey(email) && otpStore.get(email).equals(enteredOtp);
    }
}
