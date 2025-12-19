package dao;

import database.MySqlConnection;
import model.ProductModel;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class productDAOImpl implements productDAO {
    MySqlConnection db = new MySqlConnection();
    
    @Override
    public boolean addProduct(ProductModel product) {
        String query = "INSERT INTO products (name, description, category, brand, price, " +
                      "stock_quantity, image_url, size, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getCategory());
            ps.setString(4, product.getBrand());
            ps.setBigDecimal(5, product.getPrice());
            ps.setInt(6, product.getStockQuantity());
            ps.setString(7, product.getImageUrl());
            ps.setString(8, product.getSize());
            ps.setString(9, product.getColor());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public ProductModel getProductById(int productId) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting product by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY created_at DESC";
        
        try (
             Statement stmt = db.openConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all products: " + e.getMessage());
        }
        
        return products;
    }
    
    @Override
    public List<ProductModel> getProductsByCategory(String category) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category = ? ORDER BY created_at DESC";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting products by category: " + e.getMessage());
        }
        
        return products;
    }
    
    @Override
    public List<ProductModel> getProductsByBrand(String brand) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE brand = ? ORDER BY created_at DESC";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, brand);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting products by brand: " + e.getMessage());
        }
        
        return products;
    }
    
    @Override
    public List<ProductModel> searchProducts(String keyword) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? " +
                      "OR brand LIKE ? OR category LIKE ? ORDER BY created_at DESC";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching products: " + e.getMessage());
        }
        
        return products;
    }
    
    @Override
    public boolean updateProduct(ProductModel product) {
        String query = "UPDATE products SET name = ?, description = ?, category = ?, " +
                      "brand = ?, price = ?, stock_quantity = ?, image_url = ?, " +
                      "size = ?, color = ? WHERE product_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getCategory());
            ps.setString(4, product.getBrand());
            ps.setBigDecimal(5, product.getPrice());
            ps.setInt(6, product.getStockQuantity());
            ps.setString(7, product.getImageUrl());
            ps.setString(8, product.getSize());
            ps.setString(9, product.getColor());
            ps.setInt(10, product.getProductId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE product_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean updateStock(int productId, int quantity) {
        String query = "UPDATE products SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
        
        try (
             PreparedStatement ps = db.openConnection().prepareStatement(query)) {
            
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<ProductModel> getFeaturedProducts() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE stock_quantity > 0 " +
                      "ORDER BY RAND() LIMIT 8"; // Random 8 products as featured
        
        try (
             Statement stmt = db.openConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting featured products: " + e.getMessage());
        }
        
        return products;
    }
    
    @Override
    public List<ProductModel> getNewArrivals() {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE stock_quantity > 0 " +
                      "ORDER BY created_at DESC LIMIT 10"; // Latest 10 products
        
        try (
             Statement stmt = db.openConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting new arrivals: " + e.getMessage());
        }
        
        return products;
    }
    
    private ProductModel mapResultSetToProduct(ResultSet rs) throws SQLException {
        ProductModel product = new ProductModel();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setCategory(rs.getString("category"));
        product.setBrand(rs.getString("brand"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setImageUrl(rs.getString("image_url"));
        product.setSize(rs.getString("size"));
        product.setColor(rs.getString("color"));
        product.setCreatedAt(rs.getTimestamp("created_at"));
        return product;
    }
}