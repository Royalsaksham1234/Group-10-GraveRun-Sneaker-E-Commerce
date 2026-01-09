package view;

import controller.AdminOrderController;
import model.AdminOrderModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View Component for Orders Management
 * Displays order data and provides UI for order operations
 * Follows strict MVC pattern - no business logic, only UI
 */
public class OrdersPanel extends JPanel {
    
    private final AdminOrderController orderController;
    private DefaultTableModel tableModel;
    
    public OrdersPanel(AdminOrderController controller) {
        this.orderController = controller;
        initComponents();
        setupTable();
        refreshView();
        addActionListeners();
    }
    private void setupTable() {
        String[] columns = {"Order ID", "User ID", "Order Date", "Total Amount", "Status", "Shipping Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable.setModel(tableModel);
        
        // Set column widths
        ordersTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        ordersTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        ordersTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        ordersTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        ordersTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        ordersTable.getColumnModel().getColumn(5).setPreferredWidth(250);
    }
    
    /**
     * Add action listeners to UI components
     */
    private void addActionListeners() {
        searchButton.addActionListener(e -> handleSearch());
        searchField.addActionListener(e -> handleSearch());
        statusFilterComboBox.addActionListener(e -> handleFilter());
        viewDetailsButton.addActionListener(e -> handleViewDetails());
        changeStatusButton.addActionListener(e -> handleChangeStatus());
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
        
        List<AdminOrderModel> orders = orderController.searchOrders(searchText);
        updateTableView(orders);
        
        if (orders.isEmpty()) {
            showEmptyState("No orders found matching '" + searchText + "'");
        }
    }
    
    /**
     * Handle filter action
     */
    private void handleFilter() {
        String selectedStatus = (String) statusFilterComboBox.getSelectedItem();
        
        if ("All".equals(selectedStatus)) {
            refreshView();
            return;
        }
        
        List<AdminOrderModel> orders = orderController.filterByStatus(selectedStatus);
        updateTableView(orders);
        
        if (orders.isEmpty()) {
            showEmptyState("No orders found with status '" + selectedStatus + "'");
        }
    }
    
    /**
     * Handle view details action
     */
    private void handleViewDetails() {
        int selectedRow = ordersTable.getSelectedRow();
        
        if (selectedRow == -1) {
            showWarning("Please select an order to view details", "No Selection");
            return;
        }
        
        int orderId = (int) tableModel.getValueAt(selectedRow, 0);
        AdminOrderModel order = orderController.getOrderById(orderId);
        
        if (order != null) {
            showOrderDetailsDialog(order);
        } else {
            showError("Error loading order details", "Error");
        }
    }
    
    /**
     * Handle change status action
     */
    private void handleChangeStatus() {
        int selectedRow = ordersTable.getSelectedRow();
        
        if (selectedRow == -1) {
            showWarning("Please select an order to change status", "No Selection");
            return;
        }
        
        int orderId = (int) tableModel.getValueAt(selectedRow, 0);
        AdminOrderModel order = orderController.getOrderById(orderId);
        
        if (order != null) {
            boolean updated = showEditOrderStatusDialog(order);
            if (updated) {
                refreshView();
            }
        } else {
            showError("Error loading order", "Error");
        }
    }
    
    /**
     * Handle refresh action
     */
    private void handleRefresh() {
        searchField.setText("");
        statusFilterComboBox.setSelectedIndex(0);
        refreshView();
    }
    
    // ==================== View Update Methods ====================
    
    /**
     * Refresh the entire view with all orders
     */
    public void refreshView() {
        List<AdminOrderModel> orders = orderController.getAllOrders();
        updateTableView(orders);
        
        if (orders.isEmpty()) {
            showEmptyState("No orders found in the system.");
        }
    }
    
    /**
     * Update table with order data
     */
    private void updateTableView(List<AdminOrderModel> orders) {
        tableModel.setRowCount(0);
        
        if (orders.isEmpty()) {
            ordersTable.setVisible(false);
            return;
        }
        
        emptyStateLabel.setVisible(false);
        ordersTable.setVisible(true);
        
        for (AdminOrderModel order : orders) {
            Object[] row = orderController.formatOrderForTable(order);
            tableModel.addRow(row);
        }
    }
    
    /**
     * Show empty state with message
     */
    private void showEmptyState(String message) {
        emptyStateLabel.setText(message);
        emptyStateLabel.setVisible(true);
        ordersTable.setVisible(false);
    }
    
    /**
     * Show order details dialog
     */
    private void showOrderDetailsDialog(AdminOrderModel order) {
        OrderDetailsDialog dialog = new OrderDetailsDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            true,
            orderController,
            order
        );
        dialog.setVisible(true);
    }
    
    /**
     * Show edit order status dialog
     */
    private boolean showEditOrderStatusDialog(AdminOrderModel order) {
        EditOrderStatusDialog dialog = new EditOrderStatusDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            true,
            orderController,
            order
        );
        dialog.setVisible(true);
        return dialog.isStatusUpdated();
    }
    
    // ==================== Dialog Helper Methods ====================
    
    private void showWarning(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        searchPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        filterLabel = new javax.swing.JLabel();
        statusFilterComboBox = new javax.swing.JComboBox<>();
        scrollPane = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();
        emptyStateLabel = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        viewDetailsButton = new javax.swing.JButton();
        changeStatusButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(18, 18, 18));
        setMaximumSize(new java.awt.Dimension(1080, 660));
        setMinimumSize(new java.awt.Dimension(1080, 660));
        setPreferredSize(new java.awt.Dimension(1080, 660));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Orders Management");
        add(titleLabel);
        titleLabel.setBounds(30, 20, 300, 35);

        searchPanel.setBackground(new java.awt.Color(43, 43, 43));
        searchPanel.setLayout(null);

        searchLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchLabel.setText("Search:");
        searchPanel.add(searchLabel);
        searchLabel.setBounds(20, 17, 70, 25);

        searchField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        searchPanel.add(searchField);
        searchField.setBounds(90, 15, 280, 30);

        searchButton.setBackground(new java.awt.Color(70, 130, 180));
        searchButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.setFocusPainted(false);
        searchPanel.add(searchButton);
        searchButton.setBounds(385, 13, 100, 35);

        filterLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        filterLabel.setForeground(new java.awt.Color(255, 255, 255));
        filterLabel.setText("Filter by Status:");
        searchPanel.add(filterLabel);
        filterLabel.setBounds(520, 17, 120, 25);

        statusFilterComboBox.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        statusFilterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "pending", "processing", "shipped", "delivered", "cancelled", " ", " " }));
        searchPanel.add(statusFilterComboBox);
        statusFilterComboBox.setBounds(640, 15, 150, 30);

        add(searchPanel);
        searchPanel.setBounds(30, 70, 1020, 60);

        scrollPane.setBackground(new java.awt.Color(28, 28, 28));
        scrollPane.setBorder(null);

        ordersTable.setBackground(new java.awt.Color(28, 28, 28));
        ordersTable.setForeground(new java.awt.Color(255, 255, 255));
        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "User ID", "Order Date", "Total Amount", "Status", "Shipping Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class
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
        ordersTable.setGridColor(new java.awt.Color(60, 60, 60));
        ordersTable.setRowHeight(35);
        ordersTable.setSelectionBackground(new java.awt.Color(0, 120, 215));
        ordersTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        ordersTable.setShowGrid(true);
        scrollPane.setViewportView(ordersTable);

        add(scrollPane);
        scrollPane.setBounds(30, 150, 1020, 410);

        emptyStateLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        emptyStateLabel.setForeground(new java.awt.Color(150, 150, 150));
        emptyStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emptyStateLabel.setText("No orders found.");
        add(emptyStateLabel);
        emptyStateLabel.setBounds(20, 300, 1040, 30);

        buttonPanel.setBackground(new java.awt.Color(18, 18, 18));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 0));

        viewDetailsButton.setBackground(new java.awt.Color(70, 130, 180));
        viewDetailsButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        viewDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        viewDetailsButton.setText("View Details");
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.setPreferredSize(new java.awt.Dimension(150, 45));
        buttonPanel.add(viewDetailsButton);

        changeStatusButton.setBackground(new java.awt.Color(255, 152, 0));
        changeStatusButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        changeStatusButton.setForeground(new java.awt.Color(255, 255, 255));
        changeStatusButton.setText("Change Status");
        changeStatusButton.setFocusPainted(false);
        changeStatusButton.setPreferredSize(new java.awt.Dimension(160, 45));
        buttonPanel.add(changeStatusButton);

        refreshButton.setBackground(new java.awt.Color(108, 117, 125));
        refreshButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshButton.setText("Refresh");
        refreshButton.setPreferredSize(new java.awt.Dimension(130, 45));
        buttonPanel.add(refreshButton);

        add(buttonPanel);
        buttonPanel.setBounds(30, 580, 1020, 50);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton changeStatusButton;
    private javax.swing.JLabel emptyStateLabel;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JTable ordersTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JComboBox<String> statusFilterComboBox;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton viewDetailsButton;
    // End of variables declaration//GEN-END:variables
}
