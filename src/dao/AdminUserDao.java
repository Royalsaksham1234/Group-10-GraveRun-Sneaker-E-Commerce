package dao;

import model.AdminUserData;
import java.util.List;

public interface AdminUserDAO {
    // User operations
    boolean createUser(AdminUserData user);
    AdminUserData getUserById(int userId);
    AdminUserData getUserByEmail(String email);
    boolean updateUser(AdminUserData user);
    boolean deleteUser(int userId);
    boolean authenticateUser(String email, String password_hash);
    boolean userExists(String email);
    List<AdminUserData> getAllUsers();
    boolean updatePassword(int userId, String newPasswordHash);
    
    // Profile operations
    boolean updateProfile(AdminUserData user);
    boolean deactivateUser(int userId);
}