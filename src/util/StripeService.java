package util;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import graverun.StripeConfig;
import java.math.BigDecimal;
import model.CartItem;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Stripe Payment Service
 * Handles checkout session creation for single products and shopping carts
 */
public class StripeService {
    
    // Your application URLs - UPDATE THESE to match your actual setup
    private static final String SUCCESS_URL = "http://localhost:8080/success";
    private static final String CANCEL_URL = "http://localhost:8080/cancel";
    
    /**
     * Converts NPR amount to Stripe's smallest currency unit (paisa)
     * Stripe requires amounts in the smallest currency unit (e.g., cents, paisa)
     * 
     * @param amountInNPR Amount in NPR (e.g., 269.90)
     * @return Amount in paisa (e.g., 26990)
     */
    private static long toStripeAmount(BigDecimal amountInNPR) {
        return amountInNPR.multiply(BigDecimal.valueOf(100)).longValueExact();
    }
    
    /**
     * Creates a Stripe checkout session for a single product
     * 
     * @param productName Name of the product
     * @param priceNPR Price in NPR (e.g., 269.90)
     * @return Stripe checkout URL
     * @throws StripeException If Stripe API call fails
     */
    public static String checkoutSingle(String productName, BigDecimal priceNPR) 
            throws StripeException {
        
        // Validate input
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        
        if (priceNPR == null || priceNPR.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        
        try {
            // Initialize Stripe with API key
            StripeConfig.init();
            
            // Convert NPR to paisa (smallest unit)
            long amountInPaisa = toStripeAmount(priceNPR);
            
            // Log for debugging
            System.out.println("Creating Stripe checkout for: " + productName);
            System.out.println("Price: NPR " + priceNPR + " = " + amountInPaisa + " paisa");
            
            // Build checkout session parameters
            SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(SUCCESS_URL)
                .setCancelUrl(CANCEL_URL)
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("npr")
                                .setUnitAmount(amountInPaisa)
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(productName)
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .build();
            
            // Create the checkout session
            Session session = Session.create(params);
            
            System.out.println("✓ Checkout session created: " + session.getId());
            System.out.println("✓ Checkout URL: " + session.getUrl());
            
            return session.getUrl();
            
        } catch (StripeException e) {
            System.err.println("✗ Stripe API Error: " + e.getMessage());
            System.err.println("Error Code: " + e.getCode());
            System.err.println("Status Code: " + e.getStatusCode());
            throw e;
        } catch (Exception e) {
            System.err.println("✗ Unexpected error during checkout: " + e.getMessage());
            e.printStackTrace();
            throw new StripeException("Failed to create checkout session: " + e.getMessage(), 
                                     null, null, 0) {};
        }
    }
    
    /**
     * Creates a Stripe checkout session for multiple cart items
     * 
     * @param cartItems List of items in the shopping cart
     * @return Stripe checkout URL
     * @throws StripeException If Stripe API call fails
     */
    public static String checkoutCart(List<CartItem> cartItems) throws StripeException {
        
        // Validate input
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        
        try {
            // Initialize Stripe with API key
            StripeConfig.init();
            
            System.out.println("Creating Stripe checkout for cart with " + 
                             cartItems.size() + " items");
            
            // Build session parameters
            var builder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(SUCCESS_URL)
                .setCancelUrl(CANCEL_URL);
            
            // Add each cart item as a line item
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (CartItem item : cartItems) {
                long quantity = (long) item.getQuantity();
                BigDecimal price = item.getProduct().getPrice();
                String name = item.getProduct().getName();
                
                // Calculate item total
                BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));
                totalAmount = totalAmount.add(itemTotal);
                
                System.out.println("  - " + name + " x" + quantity + 
                                 " = NPR " + itemTotal);
                
                builder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(quantity)
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("npr")
                                .setUnitAmount(toStripeAmount(price))
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(name)
                                        .build()
                                )
                                .build()
                        )
                        .build()
                );
            }
            
            System.out.println("Total Amount: NPR " + totalAmount);
            
            // Create the checkout session
            Session session = Session.create(builder.build());
            
            System.out.println("✓ Cart checkout session created: " + session.getId());
            System.out.println("✓ Checkout URL: " + session.getUrl());
            
            return session.getUrl();
            
        } catch (StripeException e) {
            System.err.println("✗ Stripe API Error: " + e.getMessage());
            System.err.println("Error Code: " + e.getCode());
            System.err.println("Status Code: " + e.getStatusCode());
            throw e;
        } catch (Exception e) {
            System.err.println("✗ Unexpected error during cart checkout: " + e.getMessage());
            e.printStackTrace();
            throw new StripeException("Failed to create checkout session: " + e.getMessage(), 
                                     null, null, 0) {};
        }
    }
    
    /**
     * Opens the Stripe checkout URL in the default browser
     * 
     * @param checkoutUrl The Stripe checkout URL
     */
    public static void openCheckoutInBrowser(String checkoutUrl) {
        if (checkoutUrl == null || checkoutUrl.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to generate checkout URL",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        try {
            // Open URL in default browser
            java.awt.Desktop.getDesktop().browse(new java.net.URI(checkoutUrl));
            
            System.out.println("✓ Opened checkout in browser");
            
        } catch (Exception e) {
            System.err.println("✗ Failed to open browser: " + e.getMessage());
            
            // Show URL in dialog for manual copy
            JOptionPane.showMessageDialog(
                null,
                "Please copy this URL and paste in your browser:\n\n" + checkoutUrl,
                "Checkout URL",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    /**
     * Handles the complete checkout flow: create session and open in browser
     * 
     * @param productName Product name
     * @param priceNPR Price in NPR
     */
    public static void handleSingleProductCheckout(String productName, BigDecimal priceNPR) {
        try {
            // Create checkout session
            String checkoutUrl = checkoutSingle(productName, priceNPR);
            
            // Open in browser
            openCheckoutInBrowser(checkoutUrl);
            
        } catch (StripeException e) {
            handleStripeError(e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to process checkout: " + e.getMessage(),
                "Checkout Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    
    /**
     * Handles the complete cart checkout flow: create session and open in browser
     * 
     * @param cartItems Cart items
     */
    public static void handleCartCheckout(List<CartItem> cartItems) {
        try {
            // Create checkout session
            String checkoutUrl = checkoutCart(cartItems);
            
            // Open in browser
            openCheckoutInBrowser(checkoutUrl);
            
        } catch (StripeException e) {
            handleStripeError(e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to process checkout: " + e.getMessage(),
                "Checkout Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    
    /**
     * Handles Stripe errors with user-friendly messages
     * 
     * @param e StripeException
     */
    private static void handleStripeError(StripeException e) {
        String message;
        
        // Provide user-friendly error messages based on error code
        switch (e.getCode() != null ? e.getCode() : "") {
            case "invalid_request_error":
                message = "Invalid payment request. Please try again.";
                break;
            case "api_key_expired":
                message = "Payment system configuration error. Please contact support.";
                break;
            case "rate_limit":
                message = "Too many requests. Please wait a moment and try again.";
                break;
            default:
                message = "Payment processing failed: " + e.getMessage();
        }
        
        JOptionPane.showMessageDialog(
            null,
            message,
            "Payment Error",
            JOptionPane.ERROR_MESSAGE
        );
        
        System.err.println("Stripe Error Details:");
        System.err.println("  Code: " + e.getCode());
        System.err.println("  Message: " + e.getMessage());
        System.err.println("  Status: " + e.getStatusCode());
    }
}