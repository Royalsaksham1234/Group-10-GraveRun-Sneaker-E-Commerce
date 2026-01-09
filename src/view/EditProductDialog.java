package view;

import controller.AdminProductController;
import model.AdminProductModel;
import javax.swing.*;
import java.math.BigDecimal;

public class EditProductDialog extends javax.swing.JDialog {
    
    private final AdminProductController productController;
    private final AdminProductModel product;
    private boolean productUpdated;
    private String selectedImageFileName;

    public EditProductDialog(java.awt.Frame parent, boolean modal, 
                    AdminProductController productController, AdminProductModel product) {
    super(parent, modal);
    this.productController = productController;
    this.product = product;
    initComponents();
    loadProductData();
    addActionListeners();
    setLocationRelativeTo(parent);
}
    public boolean isProductUpdated() {
        return productUpdated;
    }

    private void loadProductData() {
        nameField.setText(product.getName());
        categoryField.setText(product.getCategory());
        priceField.setText(product.getPrice().toString());
        stockField.setText(String.valueOf(product.getStockQuantity()));
        descriptionArea.setText(product.getDescription());
        
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            imageFileLabel.setText(product.getImageUrl());
            selectedImageFileName = product.getImageUrl();
        }
    }

    private void addActionListeners() {
        browseButton.addActionListener(evt -> browseButtonActionPerformed());
        updateButton.addActionListener(evt -> updateButtonActionPerformed());
        cancelButton.addActionListener(evt -> dispose());
    }

    private void browseButtonActionPerformed() {
        String newImage = productController.selectAndCopyImage((JFrame) getOwner());
        if (newImage != null) {
            selectedImageFileName = newImage;
            imageFileLabel.setText(newImage);
        }
    }

    private void updateButtonActionPerformed() {
        try {
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            String priceStr = priceField.getText().trim();
            String stockStr = stockField.getText().trim();
            String description = descriptionArea.getText().trim();

            if (name.isEmpty() || category.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all required fields marked with *", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal price = new BigDecimal(priceStr);
            int stock = Integer.parseInt(stockStr);

            product.setName(name);
            product.setCategory(category);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stock);
            
            if (selectedImageFileName != null) {
                product.setImageUrl(selectedImageFileName);
            }

            boolean success = productController.updateProduct(product);

            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Product updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                productUpdated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update product. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numbers for price and stock quantity.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "An error occurred: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")

   
    // <editor-fold defaultstate="collapsed" desc="Generated Code"> 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        categoryLabel = new javax.swing.JLabel();
        categoryField = new javax.swing.JTextField();
        categoryHintLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        stockLabel = new javax.swing.JLabel();
        stockField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        imageLabel = new javax.swing.JLabel();
        browseButton = new javax.swing.JButton();
        imageFileLabel = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 600));
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(28, 28, 28));
        mainPanel.setLayout(null);

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Edit Product");
        mainPanel.add(titleLabel);
        titleLabel.setBounds(20, 20, 200, 30);

        nameLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        nameLabel.setText("Product Name");
        mainPanel.add(nameLabel);
        nameLabel.setBounds(20, 70, 120, 20);

        nameField.setBackground(new java.awt.Color(40, 40, 40));
        nameField.setForeground(new java.awt.Color(255, 255, 255));
        nameField.setCaretColor(new java.awt.Color(255, 255, 255));
        mainPanel.add(nameField);
        nameField.setBounds(20, 95, 440, 30);

        categoryLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        categoryLabel.setForeground(new java.awt.Color(255, 255, 255));
        categoryLabel.setText("Category");
        mainPanel.add(categoryLabel);
        categoryLabel.setBounds(20, 135, 100, 20);

        categoryField.setBackground(new java.awt.Color(40, 40, 40));
        categoryField.setForeground(new java.awt.Color(255, 255, 255));
        categoryField.setCaretColor(new java.awt.Color(255, 255, 255));
        categoryField.addActionListener(this::categoryFieldActionPerformed);
        mainPanel.add(categoryField);
        categoryField.setBounds(20, 160, 440, 30);

        categoryHintLabel.setFont(new java.awt.Font("SansSerif", 2, 11)); // NOI18N
        categoryHintLabel.setForeground(new java.awt.Color(150, 150, 150));
        categoryHintLabel.setText("Available: Performance, Casual, Basketball, Running");
        mainPanel.add(categoryHintLabel);
        categoryHintLabel.setBounds(20, 192, 300, 15);

        priceLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        priceLabel.setForeground(new java.awt.Color(255, 255, 255));
        priceLabel.setText("Price (Rs) *");
        mainPanel.add(priceLabel);
        priceLabel.setBounds(20, 215, 100, 20);

        priceField.setBackground(new java.awt.Color(40, 40, 40));
        priceField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        priceField.setForeground(new java.awt.Color(255, 255, 255));
        priceField.setCaretColor(new java.awt.Color(255, 255, 255));
        priceField.addActionListener(this::priceFieldActionPerformed);
        mainPanel.add(priceField);
        priceField.setBounds(20, 240, 200, 30);

        stockLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        stockLabel.setForeground(new java.awt.Color(255, 255, 255));
        stockLabel.setText("Stock Quantity");
        mainPanel.add(stockLabel);
        stockLabel.setBounds(260, 215, 120, 20);

        stockField.setBackground(new java.awt.Color(40, 40, 40));
        stockField.setForeground(new java.awt.Color(255, 255, 255));
        stockField.setCaretColor(new java.awt.Color(255, 255, 255));
        mainPanel.add(stockField);
        stockField.setBounds(260, 240, 200, 30);

        descriptionLabel.setBackground(new java.awt.Color(40, 40, 40));
        descriptionLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        descriptionLabel.setForeground(new java.awt.Color(255, 255, 255));
        descriptionLabel.setText("Description");
        mainPanel.add(descriptionLabel);
        descriptionLabel.setBounds(20, 285, 100, 20);

        descriptionScrollPane.setBorder(null);

        descriptionArea.setBackground(new java.awt.Color(40, 40, 40));
        descriptionArea.setColumns(20);
        descriptionArea.setForeground(new java.awt.Color(255, 255, 255));
        descriptionArea.setLineWrap(true);
        descriptionArea.setRows(4);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setCaretColor(new java.awt.Color(255, 255, 255));
        descriptionScrollPane.setViewportView(descriptionArea);

        mainPanel.add(descriptionScrollPane);
        descriptionScrollPane.setBounds(20, 310, 440, 80);

        imageLabel.setBackground(new java.awt.Color(40, 40, 40));
        imageLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        imageLabel.setForeground(new java.awt.Color(255, 255, 255));
        imageLabel.setText("Product Image");
        mainPanel.add(imageLabel);
        imageLabel.setBounds(20, 405, 120, 20);

        browseButton.setBackground(new java.awt.Color(60, 60, 60));
        browseButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        browseButton.setForeground(new java.awt.Color(255, 255, 255));
        browseButton.setText("Browse...");
        browseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browseButton.addActionListener(this::browseButtonActionPerformed);
        mainPanel.add(browseButton);
        browseButton.setBounds(20, 430, 100, 30);

        imageFileLabel.setBackground(new java.awt.Color(60, 60, 60));
        imageFileLabel.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        imageFileLabel.setForeground(new java.awt.Color(150, 150, 150));
        imageFileLabel.setText("No file selected");
        mainPanel.add(imageFileLabel);
        imageFileLabel.setBounds(20, 455, 350, 20);

        buttonPanel.setBackground(new java.awt.Color(28, 28, 28));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        cancelButton.setBackground(new java.awt.Color(60, 60, 60));
        cancelButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Cancel");
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new java.awt.Dimension(100, 35));
        cancelButton.addActionListener(this::cancelButtonActionPerformed);
        buttonPanel.add(cancelButton);

        updateButton.setBackground(new java.awt.Color(0, 120, 215));
        updateButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setText("Update Product");
        updateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateButton.setPreferredSize(new java.awt.Dimension(150, 35));
        updateButton.addActionListener(this::updateButtonActionPerformed);
        buttonPanel.add(updateButton);

        mainPanel.add(buttonPanel);
        buttonPanel.setBounds(0, 510, 500, 50);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 500, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void categoryFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryFieldActionPerformed

    private void priceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceFieldActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed

    }//GEN-LAST:event_browseButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
 


    }//GEN-LAST:event_updateButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField categoryField;
    private javax.swing.JLabel categoryHintLabel;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JLabel imageFileLabel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JTextField stockField;
    private javax.swing.JLabel stockLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
