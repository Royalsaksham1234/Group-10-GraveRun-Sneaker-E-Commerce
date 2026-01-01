
package view;

import controller.AdminUserController;
import dao.UserDao;
import model.UserData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Panel for viewing users
 * @author srsro
 */
public class UsersPanel extends javax.swing.JPanel {
    
    private final AdminUserController userController;
    private DefaultTableModel tableModel;

    public UsersPanel(UserDao userDAO) {
        this.userController = new AdminUserController(userDAO);
        initComponents();
        setupTable();
        loadUsers();
        addActionListeners();
    }

    private void setupTable() {
        String[] columns = {"User ID", "Username", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable.setModel(tableModel);
        
        usersTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        usersTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        usersTable.getColumnModel().getColumn(2).setPreferredWidth(300);
    }

    public void loadUsers() {
        tableModel.setRowCount(0);
        List<UserData> users = userController.getAllUsers();
        
        if (users.isEmpty()) {
            emptyStateLabel.setVisible(true);
            tableScrollPane.setVisible(false);
        } else {
            emptyStateLabel.setVisible(false);
            tableScrollPane.setVisible(true);
            
            for (UserData user : users) {
                Object[] row = {
                    user.getid(),
                    user.getUsername(),
                    user.getEmail()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void addActionListeners() {
        viewDetailsButton.addActionListener(evt -> viewDetailsButtonActionPerformed());
        refreshButton.addActionListener(evt -> refreshButtonActionPerformed());
    }

    private void viewDetailsButtonActionPerformed() {
        int selectedRow = usersTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a user to view details.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        UserData user = userController.getUserById(userId);
        
        if (user != null) {
            UserDetailsDialog dialog = new UserDetailsDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                userController, 
                user);
            dialog.setVisible(true);
        }
    }

    private void refreshButtonActionPerformed() {
        loadUsers();
        JOptionPane.showMessageDialog(this, 
            "Users list refreshed!", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        viewDetailsButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        tableScrollPane = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        emptyStateLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(18, 18, 18));
        setPreferredSize(new java.awt.Dimension(1080, 660));
        setLayout(null);

        headerPanel.setBackground(new java.awt.Color(28, 28, 28));
        headerPanel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        headerPanel.setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Users Management");
        headerPanel.add(titleLabel);
        titleLabel.setBounds(20, 15, 250, 30);

        viewDetailsButton.setBackground(new java.awt.Color(0, 120, 215));
        viewDetailsButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        viewDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        viewDetailsButton.setText("View Details");
        viewDetailsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        headerPanel.add(viewDetailsButton);
        viewDetailsButton.setBounds(840, 15, 120, 35);

        refreshButton.setBackground(new java.awt.Color(40, 40, 40));
        refreshButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshButton.setText("Refresh");
        refreshButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        headerPanel.add(refreshButton);
        refreshButton.setBounds(970, 15, 90, 35);

        add(headerPanel);
        headerPanel.setBounds(0, 0, 1080, 60);

        tableScrollPane.setBackground(new java.awt.Color(28, 28, 28));
        tableScrollPane.setBorder(null);

        usersTable.setBackground(new java.awt.Color(28, 28, 28));
        usersTable.setForeground(new java.awt.Color(255, 255, 255));
        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "User ID", "Username", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        usersTable.setGridColor(new java.awt.Color(40, 40, 40));
        usersTable.setRowHeight(40);
        usersTable.setSelectionBackground(new java.awt.Color(0, 120, 215));
        usersTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        usersTable.setShowGrid(true);
        tableScrollPane.setViewportView(usersTable);

        add(tableScrollPane);
        tableScrollPane.setBounds(20, 80, 1040, 560);

        emptyStateLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        emptyStateLabel.setForeground(new java.awt.Color(150, 150, 150));
        emptyStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emptyStateLabel.setText("No users found.");
        add(emptyStateLabel);
        emptyStateLabel.setBounds(20, 300, 1040, 30);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel emptyStateLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTable usersTable;
    private javax.swing.JButton viewDetailsButton;
    // End of variables declaration//GEN-END:variables
}
