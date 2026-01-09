package dao;

import java.math.BigDecimal;
import java.util.List;
import model.AdminProductModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Integration tests for AdminProductDAOImpl
 * Note: These tests run against the actual database
 * @author srsro
 */
public class AdminProductDAOImplTest {
    
    private AdminProductDAOImpl instance;
    private static AdminProductModel testProduct;
    
    public AdminProductDAOImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("=== Starting AdminProductDAOImpl Tests ===");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("=== Completed AdminProductDAOImpl Tests ===");
    }
    
    @Before
    public void setUp() {
        instance = new AdminProductDAOImpl();
    }
    
    @After
    public void tearDown() {
        if (testProduct != null && testProduct.getProductId() > 0) {
            testProduct = null;
        }
    }

    /**
     * Test of addProduct method, of class AdminProductDAOImpl.
     */
    @Test
    public void testAddProduct() {
        System.out.println("Testing addProduct...");
        
        AdminProductModel product = new AdminProductModel();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setCategory("Test Category");
        product.setBrand("Test Brand");
        product.setPrice(new BigDecimal("99.99"));
        product.setStockQuantity(10);
        product.setImageUrl("test.jpg");
        
        boolean result = instance.addProduct(product);
        
        assertTrue("Product should be added successfully", result);
    }
    
    /**
     * Test of addProduct with null product - should handle gracefully
     */
    @Test
    public void testAddProductWithNull() {
        System.out.println("Testing addProduct with null...");
        
        AdminProductModel product = null;
        boolean result = instance.addProduct(product);
        
        assertFalse("Adding null product should return false", result);
    }

    /**
     * Test of getProductById method, of class AdminProductDAOImpl.
     */
    @Test
    public void testGetProductById() {
        System.out.println("Testing getProductById...");
        
        // First get all products to find a valid ID
        List<AdminProductModel> allProducts = instance.getAllProducts();
        
        if (!allProducts.isEmpty()) {
            int productId = allProducts.get(0).getProductId();
            
            AdminProductModel result = instance.getProductById(productId);
            
            assertNotNull("Product should be found", result);
            assertEquals("Product ID should match", productId, result.getProductId());
            assertNotNull("Product name should not be null", result.getName());
        } else {
            System.out.println("No products in database to test");
        }
    }
    
    /**
     * Test of getProductById with invalid ID
     */
    @Test
    public void testGetProductByIdNotFound() {
        System.out.println("Testing getProductById with invalid ID...");
        
        int productId = 99999; // Non-existent ID
        
        AdminProductModel result = instance.getProductById(productId);
        
        assertNull("Non-existent product should return null", result);
    }

    /**
     * Test of getAllProducts method, of class AdminProductDAOImpl.
     */
    @Test
    public void testGetAllProducts() {
        System.out.println("Testing getAllProducts...");
        
        List<AdminProductModel> result = instance.getAllProducts();
        
        assertNotNull("Result should not be null", result);
        // Don't assert it's not empty - database might be empty in some test environments
        
        System.out.println("Found " + result.size() + " products");
    }

    /**
     * Test of getProductsByCategory method, of class AdminProductDAOImpl.
     */
    @Test
    public void testGetProductsByCategory() {
        System.out.println("Testing getProductsByCategory...");
        
        List<AdminProductModel> allProducts = instance.getAllProducts();
        
        if (!allProducts.isEmpty()) {
            String category = allProducts.get(0).getCategory();
            
            List<AdminProductModel> result = instance.getProductsByCategory(category);
            
            assertNotNull("Result should not be null", result);
            
            // Verify all returned products are from the requested category
            for (AdminProductModel product : result) {
                assertEquals("All products should be from category: " + category, 
                           category, product.getCategory());
            }
            
            System.out.println("Found " + result.size() + " products in category: " + category);
        }
    }
    
    /**
     * Test of getProductsByCategory with empty string
     */
    @Test
    public void testGetProductsByCategoryEmpty() {
        System.out.println("Testing getProductsByCategory with empty category...");
        
        String category = "";
        List<AdminProductModel> result = instance.getProductsByCategory(category);
        
        assertNotNull("Result should not be null", result);
    }

    /**
     * Test of getProductsByBrand method, of class AdminProductDAOImpl.
     */
    @Test
    public void testGetProductsByBrand() {
        System.out.println("Testing getProductsByBrand...");
        
        List<AdminProductModel> allProducts = instance.getAllProducts();
        
        if (!allProducts.isEmpty()) {
            String brand = allProducts.get(0).getBrand();
            
            List<AdminProductModel> result = instance.getProductsByBrand(brand);
            
            assertNotNull("Result should not be null", result);
            
            // Verify all returned products are from the requested brand
            for (AdminProductModel product : result) {
                assertEquals("All products should be from brand: " + brand, 
                           brand, product.getBrand());
            }
            
            System.out.println("Found " + result.size() + " products from brand: " + brand);
        }
    }

    /**
     * Test of searchProducts method, of class AdminProductDAOImpl.
     */
    @Test
    public void testSearchProducts() {
        System.out.println("Testing searchProducts...");
        
        String keyword = "Nike";
        
        List<AdminProductModel> result = instance.searchProducts(keyword);
        
        assertNotNull("Result should not be null", result);
        
        System.out.println("Found " + result.size() + " products matching keyword: " + keyword);
    }
    
    /**
     * Test of searchProducts with empty keyword
     */
    @Test
    public void testSearchProductsEmpty() {
        System.out.println("Testing searchProducts with empty keyword...");
        
        String keyword = "";
        
        List<AdminProductModel> result = instance.searchProducts(keyword);
        
        assertNotNull("Result should not be null", result);
    }

    /**
     * Test of updateProduct method, of class AdminProductDAOImpl.
     */
    @Test
    public void testUpdateProduct() {
        System.out.println("Testing updateProduct...");
        
        List<AdminProductModel> allProducts = instance.getAllProducts();
        
        if (!allProducts.isEmpty()) {
            AdminProductModel product = allProducts.get(0);
            String originalName = product.getName();
            
            product.setName("Updated Test Name");
            
            boolean result = instance.updateProduct(product);
            
            assertTrue("Product should be updated successfully", result);
            
            // Restore original name
            product.setName(originalName);
            instance.updateProduct(product);
        } else {
            System.out.println("Skipping update test - no products in database");
        }
    }
    
    /**
     * Test of updateProduct with null
     */
    @Test
    public void testUpdateProductWithNull() {
        System.out.println("Testing updateProduct with null...");
        
        AdminProductModel product = null;
        boolean result = instance.updateProduct(product);
        
        assertFalse("Updating null product should return false", result);
    }

    /**
     * Test of deleteProduct method, of class AdminProductDAOImpl.
     */
    @Test
    public void testDeleteProduct() {
        System.out.println("Testing deleteProduct...");
        
        // First add a product to delete
        AdminProductModel product = new AdminProductModel();
        product.setName("Product To Delete");
        product.setDescription("This will be deleted");
        product.setCategory("Test");
        product.setBrand("Test");
        product.setPrice(new BigDecimal("50.00"));
        product.setStockQuantity(5);
        product.setImageUrl("delete.jpg");
        
        boolean added = instance.addProduct(product);
        
        if (added) {
            // Get all products and find the one we just added
            List<AdminProductModel> products = instance.getAllProducts();
            AdminProductModel toDelete = null;
            
            for (AdminProductModel p : products) {
                if ("Product To Delete".equals(p.getName())) {
                    toDelete = p;
                    break;
                }
            }
            
            if (toDelete != null) {
                boolean result = instance.deleteProduct(toDelete.getProductId());
                assertTrue("Product should be deleted successfully", result);
            }
        }
    }

    /**
     * Test of updateStock method, of class AdminProductDAOImpl.
     */
    @Test
    public void testUpdateStock() {
        System.out.println("Testing updateStock...");
        
        List<AdminProductModel> allProducts = instance.getAllProducts();
        
        if (!allProducts.isEmpty()) {
            AdminProductModel product = allProducts.get(0);
            int productId = product.getProductId();
            int originalStock = product.getStockQuantity();
            int quantity = 5;
            
            boolean result = instance.updateStock(productId, quantity);
            
            assertTrue("Stock should be updated successfully", result);
            
            // Verify stock was updated
            AdminProductModel updated = instance.getProductById(productId);
            assertEquals("Stock should increase by 5", originalStock + quantity, 
                        updated.getStockQuantity());
            
            // Restore original stock
            instance.updateStock(productId, -quantity);
        } else {
            System.out.println("Skipping stock update test - no products in database");
        }
    }

    /**
     * Test of getFeaturedProducts method, of class AdminProductDAOImpl.
     */
    @Test
    public void testGetFeaturedProducts() {
        System.out.println("Testing getFeaturedProducts...");
        
        List<AdminProductModel> result = instance.getFeaturedProducts();
        
        assertNotNull("Result should not be null", result);
        assertTrue("Should return at most 8 products", result.size() <= 8);
        
        // Verify all products have stock
        for (AdminProductModel product : result) {
            assertTrue("Featured products should have stock", product.getStockQuantity() > 0);
        }
        
        System.out.println("Found " + result.size() + " featured products");
    }

    /**
     * Test of getNewArrivals method, of class AdminProductDAOImpl.
     */
    @Test
    public void testGetNewArrivals() {
        System.out.println("Testing getNewArrivals...");
        
        List<AdminProductModel> result = instance.getNewArrivals();
        
        assertNotNull("Result should not be null", result);
        assertTrue("Should return at most 10 products", result.size() <= 10);
        
        // Verify all products have stock
        for (AdminProductModel product : result) {
            assertTrue("New arrivals should have stock", product.getStockQuantity() > 0);
        }
        
        System.out.println("Found " + result.size() + " new arrivals");
    }

    /**
     * Test of getBestSellingProducts method, of class AdminProductDAOImpl.
     */
    @Test
    public void testGetBestSellingProducts() {
        System.out.println("Testing getBestSellingProducts...");
        
        int limit = 5;
        
        List<AdminProductModel> result = instance.getBestSellingProducts(limit);
        
        assertNotNull("Result should not be null", result);
        assertTrue("Should return at most " + limit + " products", result.size() <= limit);
        
        // Verify all products have stock
        for (AdminProductModel product : result) {
            assertTrue("Best selling products should have stock", product.getStockQuantity() > 0);
        }
        
        System.out.println("Found " + result.size() + " best selling products");
    }
    
    /**
     * Test of getBestSellingProducts with zero limit
     */
    @Test
    public void testGetBestSellingProductsZeroLimit() {
        System.out.println("Testing getBestSellingProducts with zero limit...");
        
        int limit = 0;
        
        List<AdminProductModel> result = instance.getBestSellingProducts(limit);
        
        assertNotNull("Result should not be null", result);
        assertTrue("Should return empty list for zero limit", result.isEmpty());
    }
}