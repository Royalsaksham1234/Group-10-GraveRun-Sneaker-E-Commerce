package model;

/**
 * Product model class
 */
public class ProductModel {
    private int productId;
    private String name;
    private String brand;
    private String category;
    private String description;
    private double price;
    private String imagePath;
    private int stock;
    private int salesCount; // For tracking best sellers
    
    // Constructors
    public ProductModel() {
    }
    
    public ProductModel(int productId, String name, String brand, String category, 
                   String description, double price, String imagePath, int stock) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.stock = stock;
    }
    
    public ProductModel(int productId, String name, String brand, String category, 
                   String description, double price, String imagePath, int stock, int salesCount) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.stock = stock;
        this.salesCount = salesCount;
    }
    
    // Getters and Setters
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public int getSalesCount() {
        return salesCount;
    }
    
    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", salesCount=" + salesCount +
                '}';
    }
}


