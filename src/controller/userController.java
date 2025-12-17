package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserData;
import view.SignupView;

public class userController {
    private final UserDao userDao;
    private final SignupView signupView;

    public userController(SignupView signupView) {
        this.signupView = signupView;
        this.userDao = new UserDao();
        this.signupView.addSignupListener(new SignupListener());
    }

    public void showSignupForm() {
        signupView.setLocationRelativeTo(null);
        signupView.setVisible(true);
    }

    public void closeSignupForm() {
        signupView.dispose();
    }

    class SignupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = signupView.getUsernameField().getText().trim();
            String email = signupView.getEmailField().getText().trim();
            String password = signupView.getPasswordField().getText().trim();
            String confirmPassword = signupView.getConfirmPasswordField().getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(signupView, "Please fill in all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(signupView, "Passwords do not match.");
                return;
            }

            try {
                UserData user = new UserData(username, email, password);
                boolean exists = userDao.checkUserExists(user);

                if (exists) {
                    JOptionPane.showMessageDialog(signupView, "User already exists.");
                } else {
                    userDao.registerUser(user);
                    JOptionPane.showMessageDialog(signupView, "Account created successfully.");
                    signupView.clearFields();
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(signupView, "An unexpected error occurred: " + ex.getMessage());
            }
        }
    }
}
