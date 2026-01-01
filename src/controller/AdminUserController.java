package controller;

import dao.UserDao;
import model.UserData;
import java.util.List;

public class AdminUserController {
    
    private final UserDao userDAO;

    public AdminUserController(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserData> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public UserData getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public UserData getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}