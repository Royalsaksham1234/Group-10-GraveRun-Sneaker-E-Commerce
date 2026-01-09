package dao;

import model.AdminProductModel;
import java.util.List;

public interface AdminProductDAO {
    // Product operations
    boolean addProduct(AdminProductModel product);
    AdminProductModel getProductById(int productId);
    List<AdminProductModel> getAllProducts();
    List<AdminProductModel> getProductsByCategory(String category);
    List<AdminProductModel> getProductsByBrand(String brand);
    List<AdminProductModel> searchProducts(String keyword);
    boolean updateProduct(AdminProductModel product);
    boolean deleteProduct(int productId);
    boolean updateStock(int productId, int quantity);
    
    // Featured products
    List<AdminProductModel> getFeaturedProducts();
    List<AdminProductModel> getNewArrivals();
}