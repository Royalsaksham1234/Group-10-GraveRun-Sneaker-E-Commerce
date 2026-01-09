package graverun;

import view.Order_Tracking;
import controller.OrderTrackingController;
import javax.swing.SwingUtilities;

public class MainLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Order_Tracking view = new Order_Tracking();
                OrderTrackingController controller =
                        new OrderTrackingController(view);

                // MUST match DB exactly
                controller.loadOrder("#2482012");

                controller.showScreen();
                System.out.println("Order Tracking System Started!");

            } catch (Exception e) {
                System.err.println("Failed to start application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
