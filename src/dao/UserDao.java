package dao;

import model.UserData;
import java.util.List;

public interface userDAO {
    // User operations
    boolean createUser(UserData user);
    UserData getUserById(int userId);
    UserData getUserByEmail(String email);
    boolean updateUser(UserData user);
    boolean deleteUser(int userId);
    boolean authenticateUser(String email, String password);
    boolean userExists(String email);
    List<UserData> getAllUsers();
    boolean updatePassword(int userId, String newPasswordHash);
    
    // Profile operations
    boolean updateProfile(UserData user);
    boolean deactivateUser(int userId);
}