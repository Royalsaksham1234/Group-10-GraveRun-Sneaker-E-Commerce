package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.awt.Dialog.ModalityType;
import view.GraveRunLogin;
import view.GraveRunSignup;
import view.GraveRunForgotPassword;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import util.SessionManager;
import util.UserData;

public class LoginController {

    private final GraveRunLogin view;
    private final UserDAO userDAO;

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public LoginController(GraveRunLogin view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();
        initController();
    }

    private void initController() {

        // Login button
        view.getLoginButton().addActionListener(e -> performLogin());

        // Signup link
        view.getSignuplink().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openSignup();
            }
        });

        // Forgot password link
        view.getForgotpassword().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openForgotPassword();
            }
        });
    }

    private void performLogin() {

        String email = view.getEmailField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());

        // Validation
        if (email.isEmpty() || email.equals("Email")) {
            JOptionPane.showMessageDialog(view, "Please enter your email.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(view, "Please enter a valid email address.", "Invalid Email", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty() || password.equals("PASSWORD")) {
            JOptionPane.showMessageDialog(view, "Please enter your password.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1️⃣ Authenticate
        if (!userDAO.authenticate(email, password)) {
            JOptionPane.showMessageDialog(
                view,
                "Invalid email or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 2️⃣ Get full user data
        UserData user = userDAO.getUserByEmail(email);

        if (user == null) {
            JOptionPane.showMessageDialog(
                view,
                "Login failed. User data not found.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 3️⃣ Store in session
        SessionManager.login(user);

        JOptionPane.showMessageDialog(
            view,
            "Login Successful!\nWelcome back!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );

        // 4️⃣ Close ONLY login dialog
        view.dispose();
    }

    private void openSignup() {
        GraveRunSignup signup = new GraveRunSignup(view, ModalityType.APPLICATION_MODAL);
        signup.setLocationRelativeTo(view);
        signup.setVisible(true);
    }

    private void openForgotPassword() {
        GraveRunForgotPassword forgotPasswordDialog =
            new GraveRunForgotPassword(view, ModalityType.APPLICATION_MODAL);

        forgotPasswordDialog.setLocationRelativeTo(view);
        forgotPasswordDialog.setVisible(true);
    }
}
