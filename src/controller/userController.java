package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.GraveRunSignup;
import view.GraveRunLogin;

public class userController {
    private final UserDao userDao;
    private final GraveRunSignup signupView;

    public userController(GraveRunSignup signupView) {
        this.signupView = signupView;
        this.userDao = new UserDao();

        // Attach listeners using getters
        this.signupView.getSignupButton().addActionListener(new SignupListener());
        this.signupView.getLoginRedirectLink().addActionListener(new LoginRedirectListener());
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
            String password = new String(signupView.getPasswordField().getPassword()).trim();

            // Basic validation
            if (username.isEmpty() || "Username".equals(username)
                || email.isEmpty() || "Email".equals(email)
                || password.isEmpty() || "Password".equals(password)) {
                JOptionPane.showMessageDialog(signupView, "Please fill in all fields.");
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(signupView, "Please enter a valid email address.");
                return;
            }

            try {
                // Check if user already exists
                if (userDao.checkUserExists(username, email)) {
                    JOptionPane.showMessageDialog(signupView, "User already exists. Try another email/username.");
                    return;
                }

                // Register new user
                boolean created = userDao.registerUser(username, email, password);

                if (created) {
                    JOptionPane.showMessageDialog(signupView, "Signup successful! You can now log in.");
                    // Clear fields
                    signupView.getUsernameField().setText("");
                    signupView.getEmailField().setText("");
                    signupView.getPasswordField().setText("");

                    // Redirect to login
                    closeSignupForm();
                    GraveRunLogin loginView = new GraveRunLogin();
                    new LoginController(loginView).showLoginForm();
                } else {
                    JOptionPane.showMessageDialog(signupView, "Signup failed. Try again.");
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(signupView, "An unexpected error occurred: " + ex.getMessage());
            }
        }
    }

    class LoginRedirectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Redirect to login
            closeSignupForm();
            GraveRunLogin loginView = new GraveRunLogin();
            new LoginController(loginView).showLoginForm();
        }
    }
}
