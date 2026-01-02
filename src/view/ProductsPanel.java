package view;

import controller.ProductController;
import dao.productDAO;
import model.ProductModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Panel for managing products
 * @author srsro
 */
public class ProductsPanel extends javax.swing.JPanel {
    
    private final ProductController productController;
    private DefaultTableModel tableModel;

    public ProductsPanel(productDAO productDAO) {
        this.productController = new ProductController(productDAO);
        initComponents();
        setupTable();
        loadProducts();
        addActionListeners();
    }

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
        
        productsTable.setRowHeight(50);
        productsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        productsTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        productsTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        productsTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        productsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        productsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        productsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    }

    public void loadProducts() {
        tableModel.setRowCount(0);
        List<ProductModel> products = productController.getAllProducts();
        
        if (products.isEmpty()) {
            emptyStateLabel.setVisible(true);
            tableScrollPane.setVisible(false);
        } else {
            emptyStateLabel.setVisible(false);
            tableScrollPane.setVisible(true);
            
            for (ProductModel product : products) {
                ImageIcon imageIcon = loadProductImage(product.getImageUrl());
                
                Object[] row = {
                    imageIcon,
                    product.getProductId(),
                    product.getName(),
                    product.getCategory(),
                    String.format("Rs %.2f", product.getPrice()),
                    product.getStockQuantity()
                };
                tableModel.addRow(row);
            }
        }
    }

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

    private void addActionListeners() {
        addButton.addActionListener(evt -> addButtonActionPerformed());
        editButton.addActionListener(evt -> editButtonActionPerformed());
        deleteButton.addActionListener(evt -> deleteButtonActionPerformed());
    }

    private void addButtonActionPerformed() {
        AddProductDialog dialog = new AddProductDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            true, 
            productController
        );
        dialog.setVisible(true);
        
        if (dialog.isProductAdded()) {
            loadProducts();
        }
    }

    private void editButtonActionPerformed() {
        int selectedRow = productsTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a product to edit.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int productId = (int) tableModel.getValueAt(selectedRow, 1);
        ProductModel product = productController.getProductById(productId);
        
        if (product != null) {
            EditProductDialog dialog = new EditProductDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                true, 
                productController, 
                product
            );
            dialog.setVisible(true);
            
            if (dialog.isProductUpdated()) {
                loadProducts();
            }
        }
    }

    private void deleteButtonActionPerformed() {
        int selectedRow = productsTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a product to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int productId = (int) tableModel.getValueAt(selectedRow, 1);
        String productName = (String) tableModel.getValueAt(selectedRow, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete '" + productName + "'?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = productController.deleteProduct(productId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Product deleted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadProducts();
            }
        }
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        emptyStateLabel = new javax.swing.JLabel();
        tableScrollPane = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(18, 18, 18));
        setMinimumSize(new java.awt.Dimension(1080, 660));
        setPreferredSize(new java.awt.Dimension(1080, 660));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headerPanel.setBackground(new java.awt.Color(28, 28, 28));
        headerPanel.setLayout(null);

        titleLabel.setBackground(new java.awt.Color(128, 128, 128));
        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Products Management");
        headerPanel.add(titleLabel);
        titleLabel.setBounds(20, 15, 270, 50);

        addButton.setBackground(new java.awt.Color(0, 120, 215));
        addButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setText("Add Products");
        headerPanel.add(addButton);
        addButton.setBounds(660, 20, 130, 30);

        editButton.setBackground(new java.awt.Color(40, 40, 40));
        editButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setText("Edit");
        editButton.addActionListener(this::editButtonActionPerformed);
        headerPanel.add(editButton);
        editButton.setBounds(830, 20, 90, 30);

        deleteButton.setBackground(new java.awt.Color(180, 40, 40));
        deleteButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete");
        headerPanel.add(deleteButton);
        deleteButton.setBounds(950, 20, 90, 30);

        add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 60));

        emptyStateLabel.setBackground(new java.awt.Color(150, 150, 150));
        emptyStateLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        emptyStateLabel.setForeground(new java.awt.Color(255, 255, 255));
        emptyStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emptyStateLabel.setText("No products found. Click 'Add Product' to get started");
        add(emptyStateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 460, 70));

        tableScrollPane.setBackground(new java.awt.Color(28, 28, 28));
        tableScrollPane.setBorder(null);
        tableScrollPane.setForeground(new java.awt.Color(255, 255, 255));
        tableScrollPane.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        productsTable.setBackground(new java.awt.Color(28, 28, 28));
        productsTable.setForeground(new java.awt.Color(255, 255, 255));
        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Name", "Category", "Price", "Stock Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        productsTable.setGridColor(new java.awt.Color(40, 40, 40));
        productsTable.setRowHeight(40);
        productsTable.setShowVerticalLines(true);
        tableScrollPane.setViewportView(productsTable);
        if (productsTable.getColumnModel().getColumnCount() > 0) {
            productsTable.getColumnModel().getColumn(0).setResizable(false);
            productsTable.getColumnModel().getColumn(0).setPreferredWidth(80);
            productsTable.getColumnModel().getColumn(1).setResizable(false);
            productsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            productsTable.getColumnModel().getColumn(2).setResizable(false);
            productsTable.getColumnModel().getColumn(2).setPreferredWidth(120);
            productsTable.getColumnModel().getColumn(3).setResizable(false);
            productsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            productsTable.getColumnModel().getColumn(4).setResizable(false);
            productsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        add(tableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1040, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel emptyStateLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTable productsTable;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

}
