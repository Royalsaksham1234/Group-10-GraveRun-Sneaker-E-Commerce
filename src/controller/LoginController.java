package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.LoginView;
import view.SignupView;

public class LoginController {
    private final UserDao userDao;
    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.userDao = new UserDao();
        this.loginView.addLoginListener(new LoginListener());
        this.loginView.addSignupRedirectListener(new SignupRedirectListener());
    }

    public void showLoginForm() {
        loginView.setLocationRelativeTo(null);
        loginView.setVisible(true);
    }

    public void closeLoginForm() {
        loginView.dispose();
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmailField().getText().trim();
            String password = new String(loginView.getPasswordField().getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Please enter both email and password.");
                return;
            }

            try {
                boolean valid = userDao.validateLogin(email, password);

                if (valid) {
                    JOptionPane.showMessageDialog(loginView, "Login successful! Welcome to GraveRun.");
                    loginView.clearFields();            
                } else {
                    JOptionPane.showMessageDialog(loginView, "Invalid email or password.");
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(loginView, "An unexpected error occurred: " + ex.getMessage());
            }
        }
    }

    class SignupRedirectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            closeLoginForm();
            SignupView signupView = new SignupView();
            new userController(signupView).showSignupForm();
        }
    }
}
