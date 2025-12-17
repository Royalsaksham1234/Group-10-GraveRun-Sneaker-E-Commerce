package dao;

import model.ProductModel;
import java.util.List;

/**
 * ProductDAO interface for product-related database operations
 */
public interface ProductDao {
    
    /**
     * Get all products from database
     * @return List of all products
     */
    List<ProductModel> getAllProducts();
    
    /**
     * Get best selling products (ordered by sales count)
     * @param limit Number of products to retrieve
     * @return List of best selling products
     */
    List<ProductModel> getBestSellingProducts(int limit);
    
    /**
     * Search products by keyword (searches in name, brand, category, description)
     * @param keyword Search keyword
     * @return List of matching products
     */
    List<ProductModel> searchProducts(String keyword);
    
    /**
     * Get product by ID
     * @param productId Product ID
     * @return Product object or null if not found
     */
    ProductModel getProductById(int productId);
    
    /**
     * Get products by category
     * @param category Category name
     * @return List of products in the category
     */
    List<ProductModel> getProductsByCategory(String category);
    
    /**
     * Get products by brand
     * @param brand Brand name
     * @return List of products by the brand
     */
    List<ProductModel> getProductsByBrand(String brand);
    
    /**
     * Add new product to database
     * @param product Product object to add
     * @return true if successful, false otherwise
     */
    boolean addProduct(ProductModel product);
    
    /**
     * Update product in database
     * @param product Product object with updated information
     * @return true if successful, false otherwise
     */
    boolean updateProduct(ProductModel product);
    
    /**
     * Delete product from database
     * @param productId Product ID to delete
     * @return true if successful, false otherwise
     */
    boolean deleteProduct(int productId);
    
    /**
     * Update product stock
     * @param productId Product ID
     * @param newStock New stock quantity
     * @return true if successful, false otherwise
     */
    boolean updateStock(int productId, int newStock);
    
    /**
     * Increment sales count for a product
     * @param productId Product ID
     * @return true if successful, false otherwise
     */
    boolean incrementSalesCount(int productId);
}