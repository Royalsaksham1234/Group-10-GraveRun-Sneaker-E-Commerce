package dao;

import model.UserModel;
import util.UserData;

/**
 * Data Access Object interface for User operations
 * Defines all required methods for user management
 * Database field: 'id' (not 'user_id')
 */
public interface UserDAO {
    
  
    int registerUser(UserModel user);
    
    
    boolean authenticate(String email, String password);
    
   
    UserData getUserByEmail(String email);
    
   
    UserData getUserById(int id);
    
    
    boolean isEmailTaken(String email);
    
    
    boolean updatePassword(String email, String newPassword);
    
    
    boolean updateUserProfile(UserData userData);
    
    
    boolean deactivateUser(int id);
    
    
    boolean activateUser(int id);
}