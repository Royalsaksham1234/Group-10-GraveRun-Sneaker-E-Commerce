package controller;

import dao.UserProfileDao;
import javax.swing.SwingUtilities;
import model.UserProfileData;

import javax.swing.table.DefaultTableModel;
import view.GraveRunNewLogin;

public class UserProfileController {

    private UserProfileDao dao;
    private javax.swing.JTable table; // pass your JTable
    private javax.swing.JLabel username;
    private javax.swing.JLabel email;
    private int userId;
     private javax.swing.JButton logout;
    



    public UserProfileController(javax.swing.JTable table, javax.swing.JLabel lblUsername, javax.swing.JLabel lblEmail, int userId, javax.swing.JButton logout) {
        this.dao = new UserProfileDao();
        this.table = table;
        this.username = lblUsername;
        this.email = lblEmail;
        this.userId = userId;
        this.logout = logout;
        
        attachButtonActions();


        
    }

    public void loadProfileData() {
        UserProfileData profile = dao.getUserProfile(userId);

        // Set username/email
        username.setText("Username: " + profile.getUsername());
        email.setText("Email: " + profile.getEmail());

        // Fill JTable
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (profile.getOrders().isEmpty()) {
            model.addRow(new Object[]{"No orders yet", "", "", "", ""});
        } else {
            profile.getOrders().forEach(o -> {
                model.addRow(new Object[]{
                        o.getProductName(),
                        o.getQuantity(),
                        o.getTotalAmount(),
                        o.getStatus(),
                        o.getOrderDate()
                });
            });
        }
    }
        private void attachButtonActions() {
        // Logout
        logout.addActionListener(e -> {
            // Open login page
            GraveRunNewLogin loginPage = new GraveRunNewLogin();
            loginPage.setVisible(true);

            // Close current page
            SwingUtilities.getWindowAncestor(logout).dispose();
        });
    
    
    }
    
}
