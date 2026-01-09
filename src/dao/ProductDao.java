package dao;

import model.AdminProductModel;
import java.util.List;

public interface ProductDao {

    // Product operations
    boolean addProduct(AdminProductModel product);

    AdminProductModel getProductById(int productId);

    List<AdminProductModel> getAllProducts();

    List<AdminProductModel> searchProducts(String keyword);

    boolean updateProduct(AdminProductModel product);

    boolean deleteProduct(int productId);

    boolean updateStock(int productId, int quantity);

    // Get products by brand
    List<AdminProductModel> getProductsByBrand(String brand);
}
