package controller;

import dao.productDAO;
import model.ProductModel;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.MySqlConnection;
import java.util.List;

public class ProductController {
    
    private final productDAO productDAO;
    private final MySqlConnection db;
    private static final String IMAGE_DIRECTORY = "src/images/";

    public ProductController(productDAO productDAO) {
        this.productDAO = productDAO;
        this.db = new MySqlConnection();
    }

    public List<ProductModel> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public boolean addProduct(ProductModel product) {
        if (!validateProduct(product)) {
            return false;
        }
        
        // Set fixed brand
        product.setBrand("GraveRun");
        
        return productDAO.addProduct(product);
    }

    public boolean updateProduct(ProductModel product) {
        if (!validateProduct(product)) {
            return false;
        }
        
        // Set fixed brand
        product.setBrand("GraveRun");
        
        return productDAO.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {
        if (isProductInOrders(productId)) {
            JOptionPane.showMessageDialog(null, 
                "Cannot delete product. It exists in one or more orders.", 
                "Delete Failed", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return productDAO.deleteProduct(productId);
    }

    private boolean isProductInOrders(int productId) {
        String query = "SELECT COUNT(*) as count FROM orderitems WHERE product_id = ?";
        Connection conn = null;
        
        try {
            conn = db.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.err.println("Error checking product in orders: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return false;
    }

    private boolean validateProduct(ProductModel product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Product name cannot be empty.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Category cannot be empty.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, 
                "Price must be greater than zero.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (product.getStockQuantity() < 0) {
            JOptionPane.showMessageDialog(null, 
                "Stock quantity cannot be negative.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public String selectAndCopyImage(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Product Image");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        int result = fileChooser.showOpenDialog(parent);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            String fileName = selectedFile.getName().toLowerCase();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && 
                !fileName.endsWith(".png") && !fileName.endsWith(".gif")) {
                JOptionPane.showMessageDialog(parent, 
                    "Please select a valid image file (jpg, jpeg, png, gif).", 
                    "Invalid File", 
                    JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            try {
                File imageDir = new File(IMAGE_DIRECTORY);
                if (!imageDir.exists()) {
                    imageDir.mkdirs();
                }
                
                String extension = fileName.substring(fileName.lastIndexOf("."));
                String uniqueFileName = System.currentTimeMillis() + extension;
                
                Path sourcePath = selectedFile.toPath();
                Path destinationPath = Paths.get(IMAGE_DIRECTORY + uniqueFileName);
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                
                return uniqueFileName;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, 
                    "Error copying image file: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        
        return null;
    }

    public ProductModel getProductById(int productId) {
        return productDAO.getProductById(productId);
    }
}