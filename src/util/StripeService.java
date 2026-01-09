package util;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import graverun.StripeConfig;
import java.math.BigDecimal;
import model.CartItem;

import java.util.List;

public class StripeService {

    private static long toStripeAmount(BigDecimal amountInNPR) {
        return amountInNPR.multiply(BigDecimal.valueOf(100)).longValueExact();
    }

public static String checkoutSingle(
    String productName,
    BigDecimal priceNPR          // ← change to BigDecimal
) throws StripeException {
    StripeConfig.init();
    
    SessionCreateParams params = SessionCreateParams.builder()
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("http://localhost:8080/success")   // ← better to use real port
        .setCancelUrl("http://localhost:8080/cancel")
        .addLineItem(
            SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("npr")
                        .setUnitAmount(
                            priceNPR
                                .multiply(BigDecimal.valueOf(100))
                                .longValueExact()     // ← 26990 → 2,699,000
                        )
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

    Session session = Session.create(params);
    return session.getUrl();
}

    public static String checkoutCart(List<CartItem> cartItems) throws StripeException {
        StripeConfig.init();
        var builder = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost/success")
            .setCancelUrl("http://localhost/cancel");

        for (CartItem item : cartItems) {
            builder.addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity((long) item.getQuantity())
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("npr")
                            .setUnitAmount(toStripeAmount(item.getProduct().getPrice()))
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(item.getProduct().getName())
                                    .build()
                            )
                            .build()
                    )
                    .build()
            );
        }
        return Session.create(builder.build()).getUrl();
    }
}