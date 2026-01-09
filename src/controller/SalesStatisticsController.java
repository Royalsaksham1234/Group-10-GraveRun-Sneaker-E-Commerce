package controller;

import dao.SalesStatisticsDao;
import database.MySqlConnection;
import java.sql.Connection;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class SalesStatisticsController {

    private SalesStatisticsDao dao;

    public SalesStatisticsController() {
        Connection conn = new MySqlConnection().openConnection();
        dao = new SalesStatisticsDao(conn);
    }

    // PIE CHART
    public JFreeChart getPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Double> data = dao.getSalesByCategory();
        if (data.isEmpty()) dataset.setValue("No Data", 1);
        else data.forEach((k,v) -> dataset.setValue(k, v));

        return ChartFactory.createPieChart("Sales by Category", dataset, true, true, false);
    }

    // BAR CHART
    public JFreeChart getBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> data = dao.getTopProductsByRevenue();
        if (data.isEmpty()) dataset.addValue(0, "Revenue", "No Data");
        else data.forEach((k,v) -> dataset.addValue(v, "Revenue", k));

        return ChartFactory.createBarChart("Top 10 Products by Revenue", "Product", "Revenue", dataset);
    }

    // LINE CHART
    public JFreeChart getLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> data = dao.getMonthlySales();
        if (data.isEmpty()) dataset.addValue(0, "Revenue", "No Data");
        else data.forEach((k,v) -> dataset.addValue(v, "Revenue", k));

        return ChartFactory.createLineChart("Monthly Sales Trend", "Month", "Revenue", dataset);
    }
}
