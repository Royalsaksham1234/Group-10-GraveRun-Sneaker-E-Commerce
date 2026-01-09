package graverun;

import com.stripe.Stripe;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Stripe Configuration
 * Manages Stripe API key initialization with multiple configuration options
 */
public class StripeConfig {
    
    private static boolean initialized = false;
    
    // Default configuration file path
    private static final String CONFIG_FILE = "stripe.properties";
    
    /**
     * Initializes Stripe with API key from environment variable or properties file
     * Priority: Environment Variable > Properties File > Hardcoded (for testing only)
     */
    public static void init() {
        if (initialized) {
            return; // Already initialized
        }
        
        String apiKey = getApiKey();
        
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                "Stripe API key not found. Please set STRIPE_SECRET_KEY environment variable " +
                "or create stripe.properties file."
            );
        }
        
        Stripe.apiKey = apiKey;
        initialized = true;
        
        System.out.println("✓ Stripe initialized successfully");
        System.out.println("  API Key: " + maskApiKey(apiKey));
    }
    
    /**
     * Gets the API key from various sources in order of priority:
     * 1. Environment variable STRIPE_SECRET_KEY
     * 2. Properties file (stripe.properties)
     * 3. Hardcoded value (for development/testing only)
     * 
     * @return Stripe API key
     */
    private static String getApiKey() {
        // Priority 1: Environment Variable (MOST SECURE)
        String apiKey = System.getenv("STRIPE_SECRET_KEY");
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            System.out.println("✓ Using Stripe API key from environment variable");
            return apiKey;
        }
        
        // Priority 2: Properties File (RECOMMENDED FOR DEVELOPMENT)
        apiKey = loadFromPropertiesFile();
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            System.out.println("✓ Using Stripe API key from properties file");
            return apiKey;
        }
        
        // Priority 3: Hardcoded (ONLY FOR TESTING - NOT RECOMMENDED)
        System.out.println("⚠️  WARNING: Using hardcoded API key. This is NOT secure!");
        System.out.println("⚠️  Please set STRIPE_SECRET_KEY environment variable or use properties file.");
        
        // TODO: Remove this hardcoded key before deploying to production
        return "sk_test_51SnJRC0XAPm4qdjnNM2RC3840XOH9Vi5h037L7xUL1raHxU0of9RyInqRdHQ1ZA5hAlPHJ242B5eXrjspJbhFX6J004qeMa7nA";
    }
    
    /**
     * Loads API key from properties file
     * 
     * @return API key or null if not found
     */
    private static String loadFromPropertiesFile() {
        Properties props = new Properties();
        
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            return props.getProperty("stripe.api.key");
        } catch (IOException e) {
            // Properties file not found or not readable
            System.out.println("ℹ️  Properties file '" + CONFIG_FILE + "' not found");
            return null;
        }
    }
    
    /**
     * Masks the API key for logging (shows only first and last few characters)
     * 
     * @param apiKey The API key to mask
     * @return Masked API key (e.g., "sk_test_51Sn...7nA")
     */
    private static String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() < 10) {
            return "***";
        }
        
        int visibleChars = 12;
        String start = apiKey.substring(0, visibleChars);
        String end = apiKey.substring(apiKey.length() - 3);
        
        return start + "..." + end;
    }
    
    /**
     * Checks if Stripe is properly initialized
     * 
     * @return true if initialized, false otherwise
     */
    public static boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Resets initialization status (useful for testing)
     */
    public static void reset() {
        initialized = false;
        Stripe.apiKey = null;
    }
    
    /**
     * Main method for testing configuration
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Stripe Configuration Test");
        System.out.println("========================================\n");
        
        try {
            init();
            System.out.println("\n✓ Stripe configuration successful!");
            System.out.println("You can now use Stripe API in your application.");
        } catch (Exception e) {
            System.err.println("\n✗ Stripe configuration failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nPlease ensure:");
            System.err.println("1. STRIPE_SECRET_KEY environment variable is set, OR");
            System.err.println("2. stripe.properties file exists with stripe.api.key property");
        }
    }
}