package model;

public class UserModel {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String otp;

    // Constructor for signup
    public UserModel(String fullName, String email, String password, String confirmPassword) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Constructor for login
    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
    
    // Constructor for OTP verification
public UserModel(String email, String otp, boolean isOtp) {
    this.email = email;
    this.otp = otp;
}
}
