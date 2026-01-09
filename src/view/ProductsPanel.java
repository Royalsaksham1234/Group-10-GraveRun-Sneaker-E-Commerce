package view;

import controller.AdminProductController;
import model.AdminProductModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * VIEW: Panel for managing products
 * Responsibilities: UI display, user interaction, event handling only
 * All business logic delegated to AdminProductController
 * Follows strict MVC pattern - consistent with UsersPanel and OrdersPanel
 */
public class ProductsPanel extends javax.swing.JPanel {
    
    private final AdminProductController productController;
    private DefaultTableModel tableModel;

    /**
     * Constructor - takes controller (following MVC pattern)
     */
    public ProductsPanel(AdminProductController controller) {
        this.productController = controller;
        initComponents();
        setupTable();
        refreshView();
        addActionListeners();
    }

    // ==================== UI Setup Methods ====================
    
    private void setupTable() {
        String[] columns = {"Image", "Product ID", "Name", "Category", "Price", "Stock Quantity"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return ImageIcon.class;
                return Object.class;
            }
        };
        productsTable.setModel(tableModel);
        
        // Set row height for images
        productsTable.setRowHeight(50);
    }
    
    private void addActionListeners() {
        addProductButton.addActionListener(evt -> addProduct());
        editProductButton.addActionListener(evt -> editProduct());
        deleteProductButton.addActionListener(evt -> deleteProduct());
        viewDetailsButton.addActionListener(evt -> viewProductDetails());
        Refresh.addActionListener(evt -> refreshProducts());
        searchButton.addActionListener(evt -> searchProducts());
        searchField.addActionListener(evt -> searchProducts());
        categoryFilterComboBox.addActionListener(evt -> filterByCategory());
    }

    // ==================== View Update Methods ====================
    
    /**
     * Refresh the entire view with all products
     * FIXED: Renamed from loadProducts() to refreshView() for consistency
     */
    public void refreshView() {
        // Delegate to controller
        List<AdminProductModel> products = productController.getAllProducts();
        updateTableView(products);
        
        if (products.isEmpty()) {
            showEmptyState("No products available. Click 'Add Product' to get started.");
        }
    }

    /**
     * Update table with product data
     */
    private void updateTableView(List<AdminProductModel> products) {
        tableModel.setRowCount(0);
        
        if (products.isEmpty()) {
            productsTable.setVisible(false);
            return;
        }
        
        // Display products
        emptyStateLabel.setVisible(false);
        productsTable.setVisible(true);
        
        for (AdminProductModel product : products) {
            ImageIcon imageIcon = loadProductImage(product.getImageUrl());
            // Controller formats data for table
            Object[] row = productController.formatProductForTable(product, imageIcon);
            tableModel.addRow(row);
        }
    }

    // ==================== User Action Handlers ====================
    
    private void addProduct() {
        AddProductDialog dialog = new AddProductDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            true, 
            productController
        );
        dialog.setVisible(true);
        
        if (dialog.isProductAdded()) {
            refreshView();
        }
    }

    private void editProduct() {
        int selectedRow = productsTable.getSelectedRow();
        
        if (!isRowSelected(selectedRow)) {
            showWarning("Please select a product to edit.");
            return;
        }
        
        int productId = (int) tableModel.getValueAt(selectedRow, 1);
        
        // Controller retrieves product
        AdminProductModel product = productController.getProductById(productId);
        
        if (product != null) {
            EditProductDialog dialog = new EditProductDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                productController, 
                product
            );
            dialog.setVisible(true);
            
            if (dialog.isProductUpdated()) {
                refreshView();
            }
        }
    }

    private void deleteProduct() {
        int selectedRow = productsTable.getSelectedRow();
        
        if (!isRowSelected(selectedRow)) {
            showWarning("Please select a product to delete.");
            return;
        }
        
        int productId = (int) tableModel.getValueAt(selectedRow, 1);
        String productName = (String) tableModel.getValueAt(selectedRow, 2);
        
        if (confirmDelete(productName)) {
            // Controller handles deletion logic and validation
            boolean success = productController.deleteProduct(productId);
            
            if (success) {
                showSuccess("Product deleted successfully!");
                refreshView();
            }
        }
    }

    private void viewProductDetails() {
        int selectedRow = productsTable.getSelectedRow();
        
        if (!isRowSelected(selectedRow)) {
            showWarning("Please select a product to view details");
            return;
        }
        
        int productId = (int) tableModel.getValueAt(selectedRow, 1);
        
        // Controller retrieves product
        AdminProductModel product = productController.getProductById(productId);
        
        if (product != null) {
            ProductDetailsDialog dialog = new ProductDetailsDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), 
                product
            );
            dialog.setVisible(true);
        } else {
            showError("Error loading product details");
        }
    }

    // ==================== Search and Filter Methods ====================
    
    private void searchProducts() {
        String searchText = searchField.getText().trim();
        
        if (searchText.isEmpty()) {
            refreshView();
            return;
        }
        
        // Controller handles search logic
        List<AdminProductModel> products = productController.searchProducts(searchText);
        
        tableModel.setRowCount(0);
        
        if (products.isEmpty()) {
            showEmptyState("No products found matching '" + searchText + "'");
        } else {
            updateTableView(products);
        }
    }

    private void filterByCategory() {
        String selectedCategory = (String) categoryFilterComboBox.getSelectedItem();
        
        if ("All".equals(selectedCategory)) {
            refreshView();
            return;
        }
        
        // Controller handles filter logic
        List<AdminProductModel> products = productController.filterByCategory(selectedCategory);
        
        tableModel.setRowCount(0);
        
        if (products.isEmpty()) {
            showEmptyState("No products found in category '" + selectedCategory + "'");
        } else {
            updateTableView(products);
        }
    }

    private void refreshProducts() {
        searchField.setText("");
        categoryFilterComboBox.setSelectedIndex(0);
        refreshView();
    }

    // ==================== UI Helper Methods ====================
    
    private ImageIcon loadProductImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return createEmptyIcon();
        }
        
        try {
            File imageFile = new File("src/images/" + imageUrl);
            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        
        return createEmptyIcon();
    }

    private ImageIcon createEmptyIcon() {
        Image emptyImage = new java.awt.image.BufferedImage(40, 40, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(emptyImage);
    }

    private void showEmptyState(String message) {
        emptyStateLabel.setText(message);
        emptyStateLabel.setVisible(true);
        productsTable.setVisible(false);
    }

    private boolean isRowSelected(int row) {
        return row != -1;
    }

    private boolean confirmDelete(String productName) {
        return JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete '" + productName + "'?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    // ==================== Dialog Helper Methods ====================
    
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "No Selection", JOptionPane.WARNING_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
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
        categoryFilterComboBox = new javax.swing.JComboBox<>();
        scrollPane = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        addProductButton = new javax.swing.JButton();
        editProductButton = new javax.swing.JButton();
        deleteProductButton = new javax.swing.JButton();
        viewDetailsButton = new javax.swing.JButton();
        Refresh = new javax.swing.JButton();
        emptyStateLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(18, 18, 18));
        setMinimumSize(new java.awt.Dimension(1080, 660));
        setPreferredSize(new java.awt.Dimension(1080, 660));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Product Management");
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
        searchField.setBounds(90, 15, 300, 30);

        searchButton.setBackground(new java.awt.Color(70, 130, 180));
        searchButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.setFocusPainted(false);
        searchPanel.add(searchButton);
        searchButton.setBounds(405, 13, 100, 35);

        filterLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        filterLabel.setForeground(new java.awt.Color(255, 255, 255));
        filterLabel.setText("Category:");
        searchPanel.add(filterLabel);
        filterLabel.setBounds(540, 17, 80, 25);

        categoryFilterComboBox.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        categoryFilterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Performance", "Running", "Casual", "Basketball" }));
        searchPanel.add(categoryFilterComboBox);
        categoryFilterComboBox.setBounds(625, 15, 160, 30);

        add(searchPanel);
        searchPanel.setBounds(30, 70, 1020, 60);

        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 60, 60)));

        productsTable.setBackground(new java.awt.Color(28, 28, 28));
        productsTable.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        productsTable.setForeground(new java.awt.Color(255, 255, 255));
        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Image", "Product ID", "Name", "Category", "Price", "Stock  Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        productsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        productsTable.setGridColor(new java.awt.Color(60, 60, 60));
        productsTable.setRowHeight(50);
        productsTable.setSelectionBackground(new java.awt.Color(70, 130, 180));
        productsTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        productsTable.setShowGrid(true);
        scrollPane.setViewportView(productsTable);

        add(scrollPane);
        scrollPane.setBounds(30, 150, 1020, 410);

        buttonPanel.setBackground(new java.awt.Color(18, 18, 18));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 0));

        addProductButton.setBackground(new java.awt.Color(34, 139, 34));
        addProductButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        addProductButton.setForeground(new java.awt.Color(255, 255, 255));
        addProductButton.setText("Add Product");
        addProductButton.setPreferredSize(new java.awt.Dimension(150, 45));
        buttonPanel.add(addProductButton);

        editProductButton.setBackground(new java.awt.Color(255, 152, 0));
        editProductButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        editProductButton.setForeground(new java.awt.Color(255, 255, 255));
        editProductButton.setText("Edit Product");
        editProductButton.setPreferredSize(new java.awt.Dimension(140, 45));
        buttonPanel.add(editProductButton);

        deleteProductButton.setBackground(new java.awt.Color(220, 53, 69));
        deleteProductButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        deleteProductButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteProductButton.setText("Delete Product");
        deleteProductButton.setPreferredSize(new java.awt.Dimension(160, 45));
        buttonPanel.add(deleteProductButton);

        viewDetailsButton.setBackground(new java.awt.Color(70, 130, 180));
        viewDetailsButton.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        viewDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        viewDetailsButton.setText("View Details");
        viewDetailsButton.setPreferredSize(new java.awt.Dimension(140, 45));
        buttonPanel.add(viewDetailsButton);

        Refresh.setBackground(new java.awt.Color(108, 117, 125));
        Refresh.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        Refresh.setForeground(new java.awt.Color(255, 255, 255));
        Refresh.setText("Refresh");
        Refresh.setPreferredSize(new java.awt.Dimension(120, 45));
        buttonPanel.add(Refresh);

        add(buttonPanel);
        buttonPanel.setBounds(30, 580, 1020, 50);

        emptyStateLabel.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        emptyStateLabel.setForeground(new java.awt.Color(150, 150, 150));
        emptyStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emptyStateLabel.setText("No products available");
        add(emptyStateLabel);
        emptyStateLabel.setBounds(30, 320, 1020, 30);
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Refresh;
    private javax.swing.JButton addProductButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JComboBox<String> categoryFilterComboBox;
    private javax.swing.JButton deleteProductButton;
    private javax.swing.JButton editProductButton;
    private javax.swing.JLabel emptyStateLabel;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JTable productsTable;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton viewDetailsButton;
    // End of variables declaration//GEN-END:variables


}