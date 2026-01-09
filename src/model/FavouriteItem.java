package model;

import java.math.BigDecimal;

public class FavouriteItem {

    private int productId;
    private String productName;
    private BigDecimal price;
    private String imageUrl;

    public FavouriteItem(int productId, String productName,
                         BigDecimal price, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
