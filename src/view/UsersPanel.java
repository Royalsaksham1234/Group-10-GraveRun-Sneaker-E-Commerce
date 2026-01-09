package view;

import controller.AdminUserController;
import model.AdminUserData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View Component for Users Management
 * Displays user data and provides UI for user operations
 * Follows strict MVC pattern - no business logic, only UI
 */
public class UsersPanel extends JPanel {
    
    private final AdminUserController userController;
    private DefaultTableModel tableModel;
     public UsersPanel(AdminUserController controller) {
        this.userController = controller;
        initComponents();
        setupTable();
        refreshView();
        addActionListeners();
    }
    
    private void setupTable() {
        String[] columns = {"User ID", "Username", "Email", "Full Name", "Phone", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable.setModel(tableModel);
        
        // Set column widths
        usersTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        usersTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        usersTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        usersTable.getColumnModel().getColumn(3).setPreferredWidth(180);
        usersTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        usersTable.getColumnModel().getColumn(5).setPreferredWidth(80);
    }
    
    /**
     * Add action listeners to UI components
     */
    private void addActionListeners() {
        searchButton.addActionListener(e -> handleSearch());
        searchField.addActionListener(e -> handleSearch());
        viewDetailsButton.addActionListener(e -> handleViewDetails());
        deleteButton.addActionListener(e -> handleDelete());
        refreshButton.addActionListener(e -> handleRefresh());
    }
    
    // ==================== Event Handlers ====================
    
    /**
     * Handle search action
     */
    private void handleSearch() {
        String searchText = searchField.getText().trim();
        
        if (searchText.isEmpty()) {
            refreshView();
            return;
        }
        
        List<AdminUserData> users = userController.searchUsers(searchText);
        updateTableView(users);
        
        if (users.isEmpty()) {
            showEmptyState("No users found matching '" + searchText + "'");
        }
    }
    
    /**
     * Handle view details action
     */
    private void handleViewDetails() {
        int selectedRow = usersTable.getSelectedRow();
        
        if (selectedRow == -1) {
            showWarning("Please select a user to view details", "No Selection");
            return;
        }
        
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        AdminUserData user = userController.getUserById(userId);
        
        if (user != null) {
            showUserDetailsDialog(user);
        } else {
            showError("Error loading user details", "Error");
        }
    }
    
    /**
     * Handle delete action
     */
    private void handleDelete() {
        int selectedRow = usersTable.getSelectedRow();
        
        if (selectedRow == -1) {
            showWarning("Please select a user to delete", "No Selection");
            return;
        }
        
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete user '" + username + "'?\nThis action cannot be undone.",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = userController.deleteUser(userId);
            
            if (success) {
                showSuccess("User '" + username + "' deleted successfully!");
                refreshView();
            }
        }
    }
    
    /**
     * Handle refresh action
     */
    private void handleRefresh() {
        searchField.setText("");
        refreshView();
    }
    
    // ==================== View Update Methods ====================
    
    /**
     * Refresh the entire view with all users
     */
    public void refreshView() {
        List<AdminUserData> users = userController.getAllUsers();
        updateTableView(users);
        
        if (users.isEmpty()) {
            showEmptyState("No users found in the system.");
        }
    }
    
    /**
     * Update table with user data
     */
    private void updateTableView(List<AdminUserData> users) {
        tableModel.setRowCount(0);
        
        if (users.isEmpty()) {
            usersTable.setVisible(false);
            return;
        }
        
        emptyStateLabel.setVisible(false);
        usersTable.setVisible(true);
        
        for (AdminUserData user : users) {
            Object[] row = userController.formatUserForTable(user);
            tableModel.addRow(row);
        }
    }
    
    /**
     * Show empty state with message
     */
    private void showEmptyState(String message) {
        emptyStateLabel.setText(message);
        emptyStateLabel.setVisible(true);
        usersTable.setVisible(false);
    }
    
    /**
     * Show user details dialog
     */
    private void showUserDetailsDialog(AdminUserData user) {
        UserDetailsDialog dialog = new UserDetailsDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            user
        );
        dialog.setVisible(true);
    }
    
    // ==================== Dialog Helper Methods ====================
    
    private void showWarning(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }


@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        emptyStateLabel = new javax.swing.JLabel();
        searchPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        buttonPanel = new javax.swing.JPanel();
        viewDetailsButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(18, 18, 18));
        setPreferredSize(new java.awt.Dimension(1080, 660));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Users Management");
        add(titleLabel);
        titleLabel.setBounds(30, 20, 300, 35);

        scrollPane.setBackground(new java.awt.Color(28, 28, 28));
        scrollPane.setBorder(null);

        usersTable.setBackground(new java.awt.Color(28, 28, 28));
        usersTable.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        usersTable.setForeground(new java.awt.Color(255, 255, 255));
        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "User ID", "Username", "Email", "Full Name", "Phone", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersTable.setGridColor(new java.awt.Color(60, 60, 60));
        usersTable.setRowHeight(35);
        usersTable.setSelectionBackground(new java.awt.Color(0, 120, 215));
        usersTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        usersTable.setShowGrid(true);
        scrollPane.setViewportView(usersTable);

        add(scrollPane);
        scrollPane.setBounds(30, 150, 1020, 410);

        emptyStateLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        emptyStateLabel.setForeground(new java.awt.Color(150, 150, 150));
        emptyStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emptyStateLabel.setText("No users found.");
        add(emptyStateLabel);
        emptyStateLabel.setBounds(20, 300, 1040, 30);

        searchPanel.setBackground(new java.awt.Color(43, 43, 43));
        searchPanel.setLayout(null);

        searchLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchLabel.setText("Search:");
        searchPanel.add(searchLabel);
        searchLabel.setBounds(20, 17, 70, 25);

        searchField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        searchPanel.add(searchField);
        searchField.setBounds(90, 15, 350, 30);

        searchButton.setBackground(new java.awt.Color(70, 130, 180));
        searchButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.addActionListener(this::searchButtonActionPerformed);
        searchPanel.add(searchButton);
        searchButton.setBounds(460, 13, 100, 35);

        add(searchPanel);
        searchPanel.setBounds(30, 70, 1020, 60);

        buttonPanel.setBackground(new java.awt.Color(18, 18, 18));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 0));

        viewDetailsButton.setBackground(new java.awt.Color(70, 130, 180));
        viewDetailsButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        viewDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        viewDetailsButton.setText("View Details");
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.setPreferredSize(new java.awt.Dimension(150, 45));
        buttonPanel.add(viewDetailsButton);

        deleteButton.setBackground(new java.awt.Color(220, 53, 69));
        deleteButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete User");
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new java.awt.Dimension(150, 45));
        buttonPanel.add(deleteButton);

        refreshButton.setBackground(new java.awt.Color(108, 117, 125));
        refreshButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshButton.setText("Refresh");
        refreshButton.setPreferredSize(new java.awt.Dimension(130, 45));
        buttonPanel.add(refreshButton);

        add(buttonPanel);
        buttonPanel.setBounds(30, 580, 1020, 50);
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel emptyStateLabel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTable usersTable;
    private javax.swing.JButton viewDetailsButton;
    // End of variables declaration//GEN-END:variables
}
