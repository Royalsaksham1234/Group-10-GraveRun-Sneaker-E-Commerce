package dao;

import database.MySqlConnection;
import model.ProductModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDAO implementation
 */
public class ProductDAOImpl implements ProductDao {
    private final MySqlConnection mysql = new MySqlConnection();
    @Override
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        
         try (Connection conn = mysql.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all products: " + e.getMessage());
        }
        return products;
    }
    
    @Override
    public List<ProductModel> getBestSellingProducts(int limit) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY sales_count DESC LIMIT ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching best selling products: " + e.getMessage());
        }
        return products;
    }
    
    @Override
    public List<ProductModel> searchProducts(String keyword) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name LIKE ? OR brand LIKE ? OR category LIKE ? OR description LIKE ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching products: " + e.getMessage());
        }
        return products;
    }
    
    @Override
    public ProductModel getProductById(int productId) {
        String query = "SELECT * FROM products WHERE id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractProductFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<ProductModel> getProductsByCategory(String category) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products by category: " + e.getMessage());
        }
        return products;
    }
    
    @Override
    public List<ProductModel> getProductsByBrand(String brand) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE brand = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, brand);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products by brand: " + e.getMessage());
        }
        return products;
    }
    
    @Override
    public boolean addProduct(ProductModel product) {
        String query = "INSERT INTO products (name, brand, category, description, price, image_path, stock, sales_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getBrand());
            pstmt.setString(3, product.getCategory());
            pstmt.setString(4, product.getDescription());
            pstmt.setDouble(5, product.getPrice());
            pstmt.setString(6, product.getImagePath());
            pstmt.setInt(7, product.getStock());
            pstmt.setInt(8, product.getSalesCount());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean updateProduct(ProductModel product) {
        String query = "UPDATE products SET name=?, brand=?, category=?, description=?, price=?, image_path=?, stock=?, sales_count=? WHERE id=?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getBrand());
            pstmt.setString(3, product.getCategory());
            pstmt.setString(4, product.getDescription());
            pstmt.setDouble(5, product.getPrice());
            pstmt.setString(6, product.getImagePath());
            pstmt.setInt(7, product.getStock());
            pstmt.setInt(8, product.getSalesCount());
            pstmt.setInt(9, product.getProductId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean updateStock(int productId, int newStock) {
        String query = "UPDATE products SET stock = ? WHERE id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean incrementSalesCount(int productId) {
        String query = "UPDATE products SET sales_count = sales_count + 1 WHERE id = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error incrementing sales count: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Helper method to extract Product object from ResultSet
     */
    private ProductModel extractProductFromResultSet(ResultSet rs) throws SQLException {
        return new ProductModel(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("brand"),
            rs.getString("category"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getString("image_path"),
            rs.getInt("stock"),
            rs.getInt("sales_count")
        );
    }
}