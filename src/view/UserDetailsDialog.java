
package view;

import model.AdminUserData;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * Dialog to display detailed user information
 */
public class UserDetailsDialog extends JDialog {
    
    private AdminUserData user;
    public UserDetailsDialog(Frame parent, AdminUserData user) {
        super(parent, "User Details", true);
        this.user = user;
        initComponents();
        loadUserData();
        setLocationRelativeTo(parent);
        
        // Add close button action
        closeButton.addActionListener(e -> dispose());
    }
    
    private void loadUserData() {
        if (user != null) {
            userIdLabel.setText(String.valueOf(user.getid()));
            usernameLabel.setText(user.getUsername() != null ? user.getUsername() : "N/A");
            emailLabel.setText(user.getEmail());
            fullNameLabel.setText(user.getFullName() != null ? user.getFullName() : "Not provided");
            phoneLabel.setText(user.getPhone() != null ? user.getPhone() : "Not provided");
            
            // Set status with color
            if (user.isActive()) {
                statusLabel.setText("Active");
                statusLabel.setForeground(new Color(76, 175, 80)); // Green
            } else {
                statusLabel.setText("Inactive");
                statusLabel.setForeground(new Color(244, 67, 54)); // Red
            }
            
            // Format created date
            if (user.getCreatedAt() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
                createdAtLabel.setText(dateFormat.format(user.getCreatedAt()));
            } else {
                createdAtLabel.setText("N/A");
            }
            
            addressArea.setText(user.getAddress() != null ? user.getAddress() : "No address provided");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        separator1 = new javax.swing.JSeparator();
        idLabelText = new javax.swing.JLabel();
        usernameLabelText = new javax.swing.JLabel();
        emailLabelText = new javax.swing.JLabel();
        fullNameLabelText = new javax.swing.JLabel();
        phoneLabelText = new javax.swing.JLabel();
        statusLabelText = new javax.swing.JLabel();
        createdAtLabelText = new javax.swing.JLabel();
        userIdLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        fullNameLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        createdAtLabel = new javax.swing.JLabel();
        addressLabelText = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        addressArea = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();
        separator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User Details");
        setBackground(new java.awt.Color(28, 28, 28));
        setMaximumSize(new java.awt.Dimension(600, 600));
        setMinimumSize(new java.awt.Dimension(600, 600));
        setModal(true);
        setPreferredSize(new java.awt.Dimension(600, 600));
        setResizable(false);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(28, 28, 28));
        mainPanel.setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("User Details");
        mainPanel.add(titleLabel);
        titleLabel.setBounds(30, 20, 220, 35);

        separator1.setForeground(new java.awt.Color(60, 60, 60));
        mainPanel.add(separator1);
        separator1.setBounds(30, 65, 540, 10);

        idLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        idLabelText.setForeground(new java.awt.Color(255, 255, 255));
        idLabelText.setText("User ID:");
        mainPanel.add(idLabelText);
        idLabelText.setBounds(40, 85, 140, 25);

        usernameLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        usernameLabelText.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabelText.setText("Username:");
        mainPanel.add(usernameLabelText);
        usernameLabelText.setBounds(40, 120, 140, 25);

        emailLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        emailLabelText.setForeground(new java.awt.Color(255, 255, 255));
        emailLabelText.setText("Email:");
        mainPanel.add(emailLabelText);
        emailLabelText.setBounds(40, 155, 140, 25);

        fullNameLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        fullNameLabelText.setForeground(new java.awt.Color(255, 255, 255));
        fullNameLabelText.setText("Full Name:");
        mainPanel.add(fullNameLabelText);
        fullNameLabelText.setBounds(40, 190, 140, 25);

        phoneLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        phoneLabelText.setForeground(new java.awt.Color(255, 255, 255));
        phoneLabelText.setText("Phone:");
        mainPanel.add(phoneLabelText);
        phoneLabelText.setBounds(40, 225, 140, 25);

        statusLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        statusLabelText.setForeground(new java.awt.Color(255, 255, 255));
        statusLabelText.setText("Status:");
        mainPanel.add(statusLabelText);
        statusLabelText.setBounds(40, 260, 140, 25);

        createdAtLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        createdAtLabelText.setForeground(new java.awt.Color(255, 255, 255));
        createdAtLabelText.setText("Created At:");
        mainPanel.add(createdAtLabelText);
        createdAtLabelText.setBounds(40, 295, 140, 25);

        userIdLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        userIdLabel.setForeground(new java.awt.Color(200, 200, 200));
        mainPanel.add(userIdLabel);
        userIdLabel.setBounds(190, 85, 370, 25);

        usernameLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(200, 200, 200));
        mainPanel.add(usernameLabel);
        usernameLabel.setBounds(190, 120, 370, 25);

        emailLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(200, 200, 200));
        mainPanel.add(emailLabel);
        emailLabel.setBounds(190, 155, 370, 25);

        fullNameLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        fullNameLabel.setForeground(new java.awt.Color(200, 200, 200));
        mainPanel.add(fullNameLabel);
        fullNameLabel.setBounds(190, 190, 370, 25);

        phoneLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        phoneLabel.setForeground(new java.awt.Color(200, 200, 200));
        mainPanel.add(phoneLabel);
        phoneLabel.setBounds(190, 225, 375, 25);

        statusLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(76, 175, 80));
        mainPanel.add(statusLabel);
        statusLabel.setBounds(190, 260, 370, 25);

        createdAtLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        createdAtLabel.setForeground(new java.awt.Color(200, 200, 200));
        mainPanel.add(createdAtLabel);
        createdAtLabel.setBounds(190, 295, 370, 25);

        addressLabelText.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        addressLabelText.setForeground(new java.awt.Color(255, 255, 255));
        addressLabelText.setText("Address:");
        mainPanel.add(addressLabelText);
        addressLabelText.setBounds(40, 350, 140, 25);

        scrollPane.setViewportBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 60, 60)));

        addressArea.setEditable(false);
        addressArea.setBackground(new java.awt.Color(43, 43, 43));
        addressArea.setColumns(20);
        addressArea.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        addressArea.setForeground(new java.awt.Color(200, 200, 200));
        addressArea.setLineWrap(true);
        addressArea.setRows(5);
        addressArea.setWrapStyleWord(true);
        addressArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setViewportView(addressArea);

        mainPanel.add(scrollPane);
        scrollPane.setBounds(40, 380, 520, 110);

        closeButton.setBackground(new java.awt.Color(70, 130, 180));
        closeButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("Close");
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(this::closeButtonActionPerformed);
        mainPanel.add(closeButton);
        closeButton.setBounds(240, 510, 120, 40);

        separator2.setForeground(new java.awt.Color(60, 60, 60));
        mainPanel.add(separator2);
        separator2.setBounds(30, 335, 540, 2);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 600, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea addressArea;
    private javax.swing.JLabel addressLabelText;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel createdAtLabel;
    private javax.swing.JLabel createdAtLabelText;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailLabelText;
    private javax.swing.JLabel fullNameLabel;
    private javax.swing.JLabel fullNameLabelText;
    private javax.swing.JLabel idLabelText;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel phoneLabelText;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel statusLabelText;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel userIdLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel usernameLabelText;
    // End of variables declaration//GEN-END:variables
}
