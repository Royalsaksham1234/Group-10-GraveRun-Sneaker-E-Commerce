package model;

import java.sql.Timestamp;

public class UserData {
    private int id;
    private String email;
    private String password;
    private String username;
    private String fullName;
    private String address;
    private String phone;
    private Timestamp createdAt;
    private boolean isActive;
    
    // Constructors
    public UserData() {}
    
    public UserData(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.isActive = true;
    }
    
    // Getters and Setters
    public int getid() { return id; }
    public void setid(int userId) { this.id = userId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String passwordHash) { this.password = passwordHash; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}