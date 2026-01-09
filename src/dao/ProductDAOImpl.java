package dao;

import database.MySqlConnection;
import model.AdminProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDao {

    private final MySqlConnection db = new MySqlConnection();

    // =======================
    // GET ALL PRODUCTS
    // =======================
    @Override
    public List<AdminProductModel> getAllProducts() {
        List<AdminProductModel> products = new ArrayList<>();
        String sql = "SELECT product_id, name, price, image_url, description, brand, stock_quantity, size, created_at " +
                     "FROM products ORDER BY created_at DESC";

        Connection conn = db.openConnection();
        if (conn == null) return products;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return products;
    }

    // =======================
    // GET PRODUCT BY ID
    // =======================
    @Override
    public AdminProductModel getProductById(int productId) {
        String sql = "SELECT product_id, name, price, image_url, description, brand, stock_quantity, size, created_at " +
                     "FROM products WHERE product_id=?";

        Connection conn = db.openConnection();
        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return null;
    }

    // =======================
    // SEARCH PRODUCTS
    // =======================
    @Override
    public List<AdminProductModel> searchProducts(String keyword) {
        List<AdminProductModel> products = new ArrayList<>();
        String sql = "SELECT product_id, name, price, image_url, description, brand, stock_quantity, size, created_at " +
                     "FROM products WHERE name LIKE ? OR description LIKE ? OR brand LIKE ?";

        Connection conn = db.openConnection();
        if (conn == null) return products;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return products;
    }

    // =======================
    // GET PRODUCTS BY BRAND
    // =======================
    @Override
    public List<AdminProductModel> getProductsByBrand(String brandName) {
        List<AdminProductModel> products = new ArrayList<>();
        String sql = "SELECT product_id, name, price, image_url, description, brand, stock_quantity, size, created_at " +
                     "FROM products WHERE brand LIKE ?";

        Connection conn = db.openConnection();
        if (conn == null) return products;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + brandName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return products;
    }

    // =======================
    // ADD PRODUCT (ADMIN)
    // =======================
    @Override
    public boolean addProduct(AdminProductModel product) {
        String sql = "INSERT INTO products (name, price, image_url, description, brand, stock_quantity, size) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = db.openConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getBrand());
            ps.setInt(6, product.getStockQuantity());
            ps.setString(7, product.getSize());
            
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return false;
    }

    // =======================
    // UPDATE PRODUCT
    // =======================
    @Override
    public boolean updateProduct(AdminProductModel product) {
        String sql = "UPDATE products SET name=?, price=?, image_url=?, description=?, brand=?, stock_quantity=?, size=? =? " +
                     "WHERE product_id=?";

        Connection conn = db.openConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getBrand());
            ps.setInt(6, product.getStockQuantity());
            ps.setString(7, product.getSize());
           
            ps.setInt(9, product.getProductId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return false;
    }

    // =======================
    // DELETE PRODUCT
    // =======================
    @Override
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id=?";

        Connection conn = db.openConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return false;
    }

    // =======================
    // UPDATE STOCK
    // =======================
    @Override
    public boolean updateStock(int productId, int quantity) {
        String sql = "UPDATE products SET stock_quantity=? WHERE product_id=?";

        Connection conn = db.openConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return false;
    }

    // =======================
    // HELPER METHOD
    // =======================
    private AdminProductModel mapResultSetToProduct(ResultSet rs) throws SQLException {
        AdminProductModel product = new AdminProductModel();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setImageUrl(rs.getString("image_url"));
        product.setDescription(rs.getString("description"));
        product.setBrand(rs.getString("brand"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setSize(rs.getString("size"));
      
        product.setCreatedAt(rs.getTimestamp("created_at"));
        return product;
    }
}
