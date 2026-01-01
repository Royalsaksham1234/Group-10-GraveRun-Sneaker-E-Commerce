
package view;

import controller.AdminUserController;
import controller.OrderController;
import model.UserData;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;
import model.OrderModel;

/**
 * Dialog for viewing user details
 * @author srsro
 */
public class UserDetailsDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(UserDetailsDialog.class.getName());
    
    private final AdminUserController userController;
    private final UserData user;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");

    public UserDetailsDialog(java.awt.Frame parent, boolean modal, 
                            AdminUserController userController, UserData user) {
        super(parent, modal);
        this.userController = userController;
        this.user = user;
        initComponents();
        loadUserDetails();
        loadOrderHistory(user.getid());
        addActionListener();
        setLocationRelativeTo(parent);
    }

    private void loadUserDetails() {
        userIdLabel.setText("User #" + user.getid());
        usernameLabel.setText("Username: " + user.getUsername());
        fullNameLabel.setText("Full Name:" + user.getFullName());
        emailLabel.setText("Email: " + user.getEmail());
        phoneLabel.setText("Phone: " + (user.getPhone() != null ? user.getPhone() : "N/A"));
        addressLabel.setText("Address: " + (user.getAddress() != null ? user.getAddress() : "N/A"));
        createdLabel.setText("Created: " + (user.getCreatedAt() != null ? dateFormat.format(user.getCreatedAt()) : "N/A"));
    }
private void loadOrderHistory(int userId) {
    try {
        OrderController orderController = new OrderController();
        List<OrderModel> orders = orderController.getOrdersByUserId(userId);
        
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found for user ID: " + userId);
            // Display "No orders" message in UI
            return;
        }
        
        // Display orders in your table/list
        for (OrderModel order : orders) {
            System.out.println("Order ID: " + order.getId() + 
                             ", Total: " + order.getTotalAmount() + 
                             ", Status: " + order.getStatus());
        }
        
    } catch (Exception e) {
        System.err.println("Error loading order history: " + e.getMessage());
        e.printStackTrace();
    }
}
    private void addActionListener() {
        closeButton.addActionListener(evt -> dispose());
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        userIdLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        fullNameLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        createdLabel = new javax.swing.JLabel();
        ordersLabel = new javax.swing.JLabel();
        ordersScrollPane = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User Details");
        setModal(true);
        setPreferredSize(new java.awt.Dimension(700, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(28, 28, 28));
        mainPanel.setLayout(null);

        headerPanel.setBackground(new java.awt.Color(18, 18, 18));
        headerPanel.setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("User Information");
        headerPanel.add(titleLabel);
        titleLabel.setBounds(20, 15, 400, 30);

        mainPanel.add(headerPanel);
        headerPanel.setBounds(0, 0, 700, 60);

        infoPanel.setBackground(new java.awt.Color(40, 40, 40));
        infoPanel.setLayout(null);

        userIdLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        userIdLabel.setForeground(new java.awt.Color(200, 200, 200));
        userIdLabel.setText("User ID: 1");
        infoPanel.add(userIdLabel);
        userIdLabel.setBounds(15, 10, 300, 20);

        usernameLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel.setText("Username: john_doe");
        infoPanel.add(usernameLabel);
        usernameLabel.setBounds(15, 35, 300, 20);

        emailLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("Email: john@example.com");
        infoPanel.add(emailLabel);
        emailLabel.setBounds(15, 60, 400, 20);

        fullNameLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        fullNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        fullNameLabel.setText("Full Name: John Doe");
        infoPanel.add(fullNameLabel);
        fullNameLabel.setBounds(15, 85, 400, 20);

        phoneLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        phoneLabel.setForeground(new java.awt.Color(255, 255, 255));
        phoneLabel.setText("Phone: +1234567890");
        infoPanel.add(phoneLabel);
        phoneLabel.setBounds(15, 110, 300, 20);

        addressLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        addressLabel.setForeground(new java.awt.Color(220, 220, 220));
        addressLabel.setText("Address: 123 Main St, City, State 12345");
        infoPanel.add(addressLabel);
        addressLabel.setBounds(15, 135, 630, 20);

        statusLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(0, 200, 100));
        statusLabel.setText("Status: Active");
        infoPanel.add(statusLabel);
        statusLabel.setBounds(500, 10, 150, 20);

        createdLabel.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        createdLabel.setForeground(new java.awt.Color(200, 200, 200));
        createdLabel.setText("Registered: Jan 15, 2024");
        infoPanel.add(createdLabel);
        createdLabel.setBounds(15, 160, 300, 20);

        mainPanel.add(infoPanel);
        infoPanel.setBounds(20, 80, 660, 190);

        ordersLabel.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        ordersLabel.setForeground(new java.awt.Color(255, 255, 255));
        ordersLabel.setText("Order History");
        mainPanel.add(ordersLabel);
        ordersLabel.setBounds(20, 290, 150, 20);

        ordersScrollPane.setBackground(new java.awt.Color(40, 40, 40));
        ordersScrollPane.setBorder(null);

        ordersTable.setBackground(new java.awt.Color(40, 40, 40));
        ordersTable.setForeground(new java.awt.Color(255, 255, 255));
        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Order ID", "Date", "Total Amount", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        ordersTable.setGridColor(new java.awt.Color(60, 60, 60));
        ordersTable.setRowHeight(35);
        ordersTable.setSelectionBackground(new java.awt.Color(0, 120, 215));
        ordersTable.setShowGrid(true);
        ordersScrollPane.setViewportView(ordersTable);

        mainPanel.add(ordersScrollPane);
        ordersScrollPane.setBounds(20, 320, 660, 230);

        buttonPanel.setBackground(new java.awt.Color(28, 28, 28));

        closeButton.setBackground(new java.awt.Color(60, 60, 60));
        closeButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("Close");
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new java.awt.Dimension(120, 35));
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel);
        buttonPanel.setBounds(0, 560, 700, 50);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 700, 650);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel createdLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel fullNameLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel ordersLabel;
    private javax.swing.JScrollPane ordersScrollPane;
    private javax.swing.JTable ordersTable;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel userIdLabel;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
