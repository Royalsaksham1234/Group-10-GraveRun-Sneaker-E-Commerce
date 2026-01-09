package util;

import java.sql.Timestamp;

public class UserData {
    private final int userId;
    private final String fullName;
    private final String username;
    private final String email;
    private final String phone;
    private final String address;
    private final String role;
    private final boolean isActive;
    private final Timestamp createdAt;


    public UserData() {
        this.userId = 0;
        this.fullName = null;
        this.username = null;
        this.email = null;
        this.phone = null;
        this.address = null;
        this.role = "customer";
        this.isActive = true;
        this.createdAt = null;
    }

    // Main constructor for creating a new user (before insert - userId = 0)
    public UserData(String fullName, String username, String email, String phone, String address, String role) {
        this.userId = 0; // will be set by DB
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role != null ? role : "customer";
        this.isActive = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Full constructor - used by DAO after fetching from DB (with real userId)
    public UserData(int userId, String fullName, String username, String email,
                    String phone, String address, String role, boolean isActive, Timestamp createdAt) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role != null ? role : "customer";
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters only (immutable)
    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getRole() { return role; }
    public boolean isActive() { return isActive; }
    public Timestamp getCreatedAt() { return createdAt; }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}