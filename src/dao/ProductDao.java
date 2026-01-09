package dao;

import model.ProductModel;
import java.util.List;

public interface ProductDao {

    // Product operations
    boolean addProduct(ProductModel product);

    ProductModel getProductById(int productId);

    List<ProductModel> getAllProducts();

    List<ProductModel> searchProducts(String keyword);

    boolean updateProduct(ProductModel product);

    boolean deleteProduct(int productId);

    boolean updateStock(int productId, int quantity);

    // Get products by brand
    List<ProductModel> getProductsByBrand(String brand);
}
