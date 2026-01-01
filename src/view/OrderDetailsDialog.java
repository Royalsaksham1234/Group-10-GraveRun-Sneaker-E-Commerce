package view;

import controller.OrderController;
import model.OrderModel;
import model.OrderItemModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Dialog for viewing order details
 * @author srsro
 */
public class OrderDetailsDialog extends javax.swing.JDialog {
    
    private final OrderController orderController;
    private final OrderModel order;
    private DefaultTableModel itemsTableModel;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");

    public OrderDetailsDialog(java.awt.Frame parent, boolean modal, 
                             OrderController orderController, OrderModel order) {
        super(parent, modal);
        this.orderController = orderController;
        this.order = order;
        initComponents();
        loadOrderDetails();
        loadOrderItems();
        addActionListener();
        setLocationRelativeTo(parent);
    }

    private void loadOrderDetails() {
        orderIdLabel.setText("Order #" + order.getId());
        customerLabel.setText("Customer: " + (order.getUsername() != null ? order.getUsername() : "N/A"));
        emailLabel.setText(order.getUserEmail() != null ? order.getUserEmail() : "N/A");
        totalLabel.setText("Total: Rs " + String.format("%.2f", order.getTotalAmount()));
        statusLabel.setText("Status: " + order.getStatus());
        addressLabel.setText("Address: " + order.getShippingAddress());
        dateLabel.setText("Order Date: " + (order.getCreatedAt() != null ? dateFormat.format(order.getCreatedAt()) : "N/A"));
    }

    private void loadOrderItems() {
        String[] columns = {"Product", "Brand", "Quantity", "Price", "Subtotal"};
        itemsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        itemsTable.setModel(itemsTableModel);
        
        List<OrderItemModel> items = orderController.getOrderItems(order.getId());
        
        for (OrderItemModel item : items) {
            Object[] row = {
                item.getProductName() != null ? item.getProductName() : "Product #" + item.getProductId(),
                item.getProductBrand() != null ? item.getProductBrand() : "N/A",
                item.getQuantity(),
                String.format("Rs %.2f", item.getPrice()),
                String.format("Rs %.2f", item.getSubtotal())
            };
            itemsTableModel.addRow(row);
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
        orderIdLabel = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        customerLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        itemsLabel = new javax.swing.JLabel();
        itemsScrollPane = new javax.swing.JScrollPane();
        itemsTable = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Order Details");
        setModal(true);
        setPreferredSize(new java.awt.Dimension(700, 600));
        setResizable(false);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(28, 28, 28));
        mainPanel.setLayout(null);

        headerPanel.setBackground(new java.awt.Color(18, 18, 18));
        headerPanel.setLayout(null);

        orderIdLabel.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        orderIdLabel.setForeground(new java.awt.Color(255, 255, 255));
        orderIdLabel.setText("Order #12345");
        headerPanel.add(orderIdLabel);
        orderIdLabel.setBounds(20, 15, 400, 30);

        mainPanel.add(headerPanel);
        headerPanel.setBounds(0, 0, 700, 60);

        infoPanel.setBackground(new java.awt.Color(40, 40, 40));
        infoPanel.setLayout(null);

        customerLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        customerLabel.setForeground(new java.awt.Color(255, 255, 255));
        customerLabel.setText("Customer: John Doe");
        infoPanel.add(customerLabel);
        customerLabel.setBounds(15, 10, 300, 20);

        emailLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("john@example.com");
        infoPanel.add(emailLabel);
        emailLabel.setBounds(15, 35, 300, 20);

        statusLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(0, 200, 100));
        statusLabel.setText("Status: Delivered");
        infoPanel.add(statusLabel);
        statusLabel.setBounds(15, 60, 200, 20);

        totalLabel.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        totalLabel.setForeground(new java.awt.Color(255, 255, 255));
        totalLabel.setText("Total: Rs 15999");
        infoPanel.add(totalLabel);
        totalLabel.setBounds(500, 10, 150, 25);

        dateLabel.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(200, 200, 200));
        dateLabel.setText("Order Date: Dec 29, 2025 14:30");
        infoPanel.add(dateLabel);
        dateLabel.setBounds(15, 85, 300, 20);

        addressLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        addressLabel.setForeground(new java.awt.Color(220, 220, 220));
        addressLabel.setText("Address: 123 Main St, City, State 12345");
        infoPanel.add(addressLabel);
        addressLabel.setBounds(15, 110, 650, 20);

        mainPanel.add(infoPanel);
        infoPanel.setBounds(20, 80, 660, 140);

        itemsLabel.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        itemsLabel.setForeground(new java.awt.Color(255, 255, 255));
        itemsLabel.setText("Order Items");
        mainPanel.add(itemsLabel);
        itemsLabel.setBounds(20, 240, 150, 20);

        itemsScrollPane.setBackground(new java.awt.Color(40, 40, 40));
        itemsScrollPane.setBorder(null);

        itemsTable.setBackground(new java.awt.Color(40, 40, 40));
        itemsTable.setForeground(new java.awt.Color(255, 255, 255));
        itemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Product", "Brand", "Quantity", "Price", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        itemsTable.setGridColor(new java.awt.Color(60, 60, 60));
        itemsTable.setRowHeight(35);
        itemsTable.setSelectionBackground(new java.awt.Color(0, 120, 215));
        itemsTable.setShowGrid(true);
        itemsScrollPane.setViewportView(itemsTable);

        mainPanel.add(itemsScrollPane);
        itemsScrollPane.setBounds(20, 270, 660, 240);

        buttonPanel.setBackground(new java.awt.Color(28, 28, 28));

        closeButton.setBackground(new java.awt.Color(60, 60, 60));
        closeButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("Close");
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new java.awt.Dimension(120, 35));
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel);
        buttonPanel.setBounds(0, 520, 700, 50);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 700, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel customerLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel itemsLabel;
    private javax.swing.JScrollPane itemsScrollPane;
    private javax.swing.JTable itemsTable;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel orderIdLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel totalLabel;
    // End of variables declaration//GEN-END:variables
}
