package dao;

import model.UserModel;
import util.UserData;

public interface UserDAO {
    int registerUser(UserModel user);  // returns generated user ID

    boolean authenticate(String email, String password);

    boolean isEmailTaken(String email);

    boolean updatePassword(String email, String newPasswordHash);

    String getUserRole(String email);

    int getUserId(String email);

    UserData getUserByEmail(String email);  // optional but useful
}