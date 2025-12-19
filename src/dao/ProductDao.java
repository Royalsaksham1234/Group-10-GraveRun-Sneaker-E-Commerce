package dao;

import model.ProductModel;
import java.util.List;

public interface productDAO {
    // Product operations
    boolean addProduct(ProductModel product);
    ProductModel getProductById(int productId);
    List<ProductModel> getAllProducts();
    List<ProductModel> getProductsByCategory(String category);
    List<ProductModel> getProductsByBrand(String brand);
    List<ProductModel> searchProducts(String keyword);
    boolean updateProduct(ProductModel product);
    boolean deleteProduct(int productId);
    boolean updateStock(int productId, int quantity);
    
    // Featured products
    List<ProductModel> getFeaturedProducts();
    List<ProductModel> getNewArrivals();
}