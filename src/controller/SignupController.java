package controller;

import view.GraveRunSignup;
import view.GraveRunLogin;
import dao.UserDAO;
import dao.UserDAOImpl;
import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import model.UserModel;
import util.SessionManager;
import util.UserData;

public class SignupController {

    private final GraveRunSignup view;
    private final UserDAO userDAO = new UserDAOImpl();

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|net|org|edu|gov|co|info|biz|me|io)$"
    );

    public SignupController(GraveRunSignup view) {
        this.view = view;
        initController();
    }

    private void initController() {

        // Signup button
        view.getSignupButton().addActionListener(e -> validateAndSignup());

        // Login link (ONLY ONE handler)
        view.getLogin().addActionListener(e -> openLogin());
    }

    private void openLogin() {
        Window parent = SwingUtilities.getWindowAncestor(view);

        GraveRunLogin login = new GraveRunLogin(
                parent,
                Dialog.ModalityType.APPLICATION_MODAL
        );
        login.setLocationRelativeTo(view);
        login.setVisible(true);

        view.dispose();
    }

    private void validateAndSignup() {

        String email = view.getEmailField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());
        String confirmPassword = new String(view.getConfirmPasswordField().getPassword());

        if (email.isEmpty() || email.equals("Email")) {
            JOptionPane.showMessageDialog(view, "Please enter your email.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(view, "Invalid email format.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter password.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(view, "Passwords do not match.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (userDAO.isEmailTaken(email)) {
            JOptionPane.showMessageDialog(view, "Email already registered.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        registerUser(email, password);
    }

    private void registerUser(String email, String password) {

        UserModel user = new UserModel(email, password);
        int userId = userDAO.registerUser(user);

        if (userId <= 0) {
            JOptionPane.showMessageDialog(view, "Signup failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Create session user
        String username = email.split("@")[0];

        UserData sessionUser = new UserData(
                userId,
                username,
                username,
                email,
                "",
                "",
                "customer",
                true,
                new java.sql.Timestamp(System.currentTimeMillis())
        );

        // ✅ LOGIN USER
        SessionManager.login(sessionUser);

        JOptionPane.showMessageDialog(
                view,
                "Signup successful! Welcome, " + username,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        // ✅ Close signup dialog ONLY
        view.dispose();
    }
}
