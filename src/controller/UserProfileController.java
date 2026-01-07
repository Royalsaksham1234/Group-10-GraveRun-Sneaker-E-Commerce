package controller;

import dao.UserProfileDao;
import model.UserProfileData;

import javax.swing.table.DefaultTableModel;

public class UserProfileController {

    private UserProfileDao dao;
    private javax.swing.JTable table; // pass your JTable
    private javax.swing.JLabel username;
    private javax.swing.JLabel email;
    private int userId;

    public UserProfileController(javax.swing.JTable table, javax.swing.JLabel lblUsername, javax.swing.JLabel lblEmail, int userId) {
        this.dao = new UserProfileDao();
        this.table = table;
        this.username = lblUsername;
        this.email = lblEmail;
        this.userId = userId;
        
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
}
