package model;

public class UserModel {
    private int userId;         // Added: needed after DB insert
    private String username;    // Added: for display/session
    private String email;
    private String password;
    private String otp;

    // Default constructor (useful for DAO)
    public UserModel() {
    }

    // For signup (with OTP)
    public UserModel(String email, String password, String otp) {
        this.email = email;
        this.password = password;
        this.otp = otp;
    }

    // For login
    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Full constructor (useful after fetching from DB)
    public UserModel(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}