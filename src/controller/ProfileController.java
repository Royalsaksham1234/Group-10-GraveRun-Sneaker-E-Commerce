package controller;

import dao.OrderDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import view.ProfilePage;

public class ProfileController {

    private ProfilePage view;
    private OrderDao orderDao;

    public ProfileController(ProfilePage view, java.sql.Connection conn) {
        this.view = view;
        this.orderDao = new OrderDao(conn);
    }

    public void loadUserOrders(int userId) {
        ResultSet rs = null;
        try {
            rs = orderDao.getOrdersByUserId(userId);
            DefaultTableModel model = (DefaultTableModel) view.getOrdersTable().getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("order_date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }
}
