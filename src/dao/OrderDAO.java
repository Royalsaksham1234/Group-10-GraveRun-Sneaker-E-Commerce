package dao;

import database.MySqlConnection;
import model.CartItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAO {

    private final int userId;
    private final MySqlConnection db;

    public OrderDAO(int userId) {
        this.userId = userId;
        this.db = new MySqlConnection();
    }

    // 🔹 Create order for multiple cart items
    public int createOrder(List<CartItem> cartItems, String address, String paymentMethod, String stripeSessionId) {
        String insertOrder = "INSERT INTO orders (user_id, total_price, shipping_address, payment_method, stripe_session_id, status) " +
                             "VALUES (?, ?, ?, ?, ?, ?)";

        String insertOrderItem = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.openConnection()) {
            conn.setAutoCommit(false);

            // 1️⃣ Calculate total
            BigDecimal total = BigDecimal.ZERO;
            for (CartItem item : cartItems) {
                total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            // 2️⃣ Insert order
            try (PreparedStatement ps = conn.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setBigDecimal(2, total);
                ps.setString(3, address);
                ps.setString(4, paymentMethod);
                ps.setString(5, stripeSessionId);
                ps.setString(6, "pending"); // initial status
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    // 3️⃣ Insert order items
                    try (PreparedStatement psItem = conn.prepareStatement(insertOrderItem)) {
                        for (CartItem item : cartItems) {
                            psItem.setInt(1, orderId);
                            psItem.setInt(2, item.getProduct().getProductId());
                            psItem.setInt(3, item.getQuantity());
                            psItem.setBigDecimal(4, item.getProduct().getPrice());
                            psItem.addBatch();
                        }
                        psItem.executeBatch();
                    }

                    // 4️⃣ Clear cart
                    String clearCart = "DELETE FROM cart_items WHERE user_id = ?";
                    try (PreparedStatement psClear = conn.prepareStatement(clearCart)) {
                        psClear.setInt(1, userId);
                        psClear.executeUpdate();
                    }

                    conn.commit();
                    return orderId;
                }

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // failed
    }

    // 🔹 Create order for single product (like OrderConfirm)
    public int createSingleOrder(CartItem item, String address, String paymentMethod, String stripeSessionId) {
        return createOrder(List.of(item), address, paymentMethod, stripeSessionId);
    }

    // 🔹 Update order status after Stripe payment
    public boolean updateOrderStatus(int orderId, String status) {
        String updateStatus = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(updateStatus)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Optional: Fetch orders for user
    public ResultSet getUserOrders() {
        String query = "SELECT * FROM orders WHERE user_id = ?";
        try {
            Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
