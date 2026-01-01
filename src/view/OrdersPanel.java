package view;

import controller.OrderController;
import model.OrderModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Panel for viewing orders
 * @author srsro
 */
public class OrdersPanel extends javax.swing.JPanel {
    
    private final OrderController orderController;
    private DefaultTableModel tableModel;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    public OrdersPanel() {
        this.orderController = new OrderController();
        initComponents();
        setupTable();
        loadOrders();
        addActionListeners();
    }

    private void setupTable() {
        String[] columns = {"Order ID", "Username", "Total Amount", "Status", "Shipping Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable.setModel(tableModel);
        
        ordersTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        ordersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        ordersTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        ordersTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        ordersTable.getColumnModel().getColumn(4).setPreferredWidth(300);
    }

    public void loadOrders() {
        tableModel.setRowCount(0);
        List<OrderModel> orders = orderController.getAllOrders();
        
        if (orders.isEmpty()) {
            emptyStateLabel.setVisible(true);
            tableScrollPane.setVisible(false);
        } else {
            emptyStateLabel.setVisible(false);
            tableScrollPane.setVisible(true);
            
            for (OrderModel order : orders) {
                Object[] row = {
                    order.getId(),
                    order.getUsername() != null ? order.getUsername() : "N/A",
                    String.format("Rs %.2f", order.getTotalAmount()),
                    order.getStatus(),
                    order.getShippingAddress()
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
        int selectedRow = ordersTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an order to view details.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int orderId = (int) tableModel.getValueAt(selectedRow, 0);
        OrderModel order = orderController.getOrderById(orderId);
        
        if (order != null) {
            OrderDetailsDialog dialog = new OrderDetailsDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                orderController, 
                order);
            dialog.setVisible(true);
        }
    }

    private void refreshButtonActionPerformed() {
        loadOrders();
        JOptionPane.showMessageDialog(this, 
            "Orders list refreshed!", 
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
        ordersTable = new javax.swing.JTable();
        emptyStateLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(18, 18, 18));
        setLayout(null);

        headerPanel.setBackground(new java.awt.Color(28, 28, 28));
        headerPanel.setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Orders Management");
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

        ordersTable.setBackground(new java.awt.Color(28, 28, 28));
        ordersTable.setForeground(new java.awt.Color(255, 255, 255));
        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Username", "Total Amount", "Status", "Shipping Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        ordersTable.setGridColor(new java.awt.Color(40, 40, 40));
        ordersTable.setRowHeight(40);
        ordersTable.setSelectionBackground(new java.awt.Color(0, 120, 215));
        ordersTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        ordersTable.setShowGrid(true);
        tableScrollPane.setViewportView(ordersTable);

        add(tableScrollPane);
        tableScrollPane.setBounds(20, 80, 1040, 560);

        emptyStateLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        emptyStateLabel.setForeground(new java.awt.Color(150, 150, 150));
        emptyStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emptyStateLabel.setText("No orders found.");
        add(emptyStateLabel);
        emptyStateLabel.setBounds(20, 300, 1040, 30);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel emptyStateLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTable ordersTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton viewDetailsButton;
    // End of variables declaration//GEN-END:variables
}
