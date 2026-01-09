package controller;

import model.AdminProductModel;
import dao.AdminProductDAO;
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
import java.util.stream.Collectors;

/**
 * Controller for Product Management
 * Handles all business logic for product operations
 */
public class AdminProductController {
    
    private final AdminProductDAO productDAO;
    private final MySqlConnection db;
    private static final String IMAGE_DIRECTORY = "src/images/";

    public AdminProductController(AdminProductDAO productDAO) {
        this.productDAO = productDAO;
        this.db = new MySqlConnection();
    }

    // ==================== Data Retrieval Methods ====================
    
    /**
     * Get all products from database
     */
    public List<AdminProductModel> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (Exception e) {
            System.err.println("Error retrieving products: " + e.getMessage());
            return List.of(); // Return empty list instead of null
        }
    }

    /**
     * Get product by ID
     */
    public AdminProductModel getProductById(int productId) {
        try {
            return productDAO.getProductById(productId);
        } catch (Exception e) {
            System.err.println("Error retrieving product: " + e.getMessage());
            return null;
        }
    }

    // ==================== Product Operations ====================
    
    /**
     * Add new product with validation
     */
    public boolean addProduct(AdminProductModel product) {
        if (!validateProduct(product)) {
            return false;
        }
        
        // Set fixed brand
        product.setBrand("GraveRun");
        
        try {
            return productDAO.addProduct(product);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error adding product: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Update existing product with validation
     */
    public boolean updateProduct(AdminProductModel product) {
        if (!validateProduct(product)) {
            return false;
        }
        
        // Set fixed brand
        product.setBrand("GraveRun");
        
        try {
            return productDAO.updateProduct(product);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error updating product: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Delete product with order dependency check
     */
    public boolean deleteProduct(int productId) {
        if (isProductInOrders(productId)) {
            JOptionPane.showMessageDialog(null, 
                "Cannot delete product. It exists in one or more orders.", 
                "Delete Failed", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            return productDAO.deleteProduct(productId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error deleting product: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== Search and Filter Methods ====================
    
    /**
     * Search products by name, category, or description
     */
    public List<AdminProductModel> searchProducts(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return getAllProducts();
        }
        
        String search = searchText.toLowerCase().trim();
        List<AdminProductModel> allProducts = getAllProducts();
        
        return allProducts.stream()
            .filter(product -> 
                product.getName().toLowerCase().contains(search) ||
                product.getCategory().toLowerCase().contains(search) ||
                (product.getDescription() != null && 
                 product.getDescription().toLowerCase().contains(search))
            )
            .collect(Collectors.toList());
    }

    /**
     * Filter products by category
     */
    public List<AdminProductModel> filterByCategory(String category) {
        if (category == null || category.trim().isEmpty() || "All".equals(category)) {
            return getAllProducts();
        }
        
        List<AdminProductModel> allProducts = getAllProducts();
        
        return allProducts.stream()
            .filter(product -> product.getCategory().equals(category))
            .collect(Collectors.toList());
    }

    // ==================== Data Formatting Methods ====================
    
    /**
     * Format product data for table display
     */
    public Object[] formatProductForTable(AdminProductModel product, javax.swing.ImageIcon imageIcon) {
        return new Object[]{
            imageIcon,
            product.getProductId(),
            product.getName(),
            product.getCategory(),
            formatPrice(product.getPrice()),
            product.getStockQuantity()
        };
    }

    /**
     * Format price with currency
     */
    public String formatPrice(BigDecimal price) {
        return String.format("Rs %.2f", price);
    }

    // ==================== Validation Methods ====================
    
    /**
     * Validate product data
     */
    private boolean validateProduct(AdminProductModel product) {
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

    /**
     * Check if product exists in any orders
     */
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

    // ==================== Image Handling Methods ====================
    
    /**
     * Handle image selection and copying
     */
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
            return copyImageToDirectory(selectedFile, parent);
        }
        
        return null;
    }

    /**
     * Copy image file to application directory
     */
    private String copyImageToDirectory(File selectedFile, JFrame parent) {
        String fileName = selectedFile.getName().toLowerCase();
        
        if (!isValidImageFile(fileName)) {
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

    /**
     * Validate image file extension
     */
    private boolean isValidImageFile(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || 
               fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    // ==================== State Check Methods ====================
    
    /**
     * Check if products list is empty
     */
    public boolean hasProducts() {
        return !getAllProducts().isEmpty();
    }

    /**
     * Get total product count
     */
    public int getTotalProductCount() {
        return getAllProducts().size();
    }
}