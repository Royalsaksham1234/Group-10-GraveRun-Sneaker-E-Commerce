package view;

import controller.AdminDashboardController;
import controller.AdminUserController;
import controller.AdminOrderController;
import controller.AdminProductController;
import dao.AdminProductDAOImpl;
import dao.AdminUserDAOImpl;
import dao.AdminProductDAO;
import dao.AdminUserDAO;

/**
 * Main Admin Dashboard - Follows MVC Pattern
 * View Component - Only handles UI display and user interactions
 */
public class AdminDashboard extends javax.swing.JFrame {

    // Controllers
    private final AdminDashboardController dashboardController;
    private final AdminProductController productController;
    private final AdminUserController userController;
    private final AdminOrderController orderController;
    
    // Panel instances
    private ProductsPanel productsPanelView;
    private OrdersPanel ordersPanelView;
    private UsersPanel usersPanelView;

    public AdminDashboard() {
        // Initialize DAOs
        AdminProductDAO productDAO = new AdminProductDAOImpl();
        AdminUserDAO userDAO = new AdminUserDAOImpl();
        
        // Initialize Controllers
        this.productController = new AdminProductController(productDAO);
        this.userController = new AdminUserController(userDAO);
        this.orderController = new AdminOrderController();
        this.dashboardController = new AdminDashboardController(productDAO, userDAO);
        
        initComponents();
        initializePanels();
        updateDashboardStats();
        setupActionListeners();
    }

    /**
     * Initialize panel instances with their respective controllers
     */
    private void initializePanels() {
        // Create panel instances with controllers (following MVC)
        productsPanelView = new ProductsPanel(productController);
        ordersPanelView = new OrdersPanel(orderController);
        usersPanelView = new UsersPanel(userController);
        
        // Set bounds to match content panel
        productsPanelView.setBounds(0, 0, 1080, 660);
        ordersPanelView.setBounds(0, 0, 1080, 660);
        usersPanelView.setBounds(0, 0, 1080, 660);
        
        // Add panels to content panel
        contentPanel.add(productsPanelView);
        contentPanel.add(ordersPanelView);
        contentPanel.add(usersPanelView);
        
        // Initially hide all panels except dashboard
        productsPanelView.setVisible(false);
        ordersPanelView.setVisible(false);
        usersPanelView.setVisible(false);
        dashboardPanel.setVisible(true);
    }

    /**
     * Update dashboard statistics from controller
     */
    private void updateDashboardStats() {
        int totalProducts = dashboardController.getTotalProducts();
        int totalUsers = dashboardController.getTotalUsers();
        int totalOrders = dashboardController.getTotalOrders();
        String revenue = dashboardController.formatRevenue(dashboardController.getTotalRevenue());
        
        // Update UI labels
        ordersNumberLabel.setText(String.valueOf(totalOrders));
        usersNumberLabel.setText(String.valueOf(totalUsers));
        productNumberLabel.setText(String.valueOf(totalProducts));
        revenueNumberLabel.setText(revenue);
    }

    /**
     * Setup action listeners for sidebar buttons
     */
    private void setupActionListeners() {
        Dashboardbtn.addActionListener(evt -> showPanel("dashboard"));
        Productsbtn.addActionListener(evt -> showPanel("products"));
        Ordersbtn.addActionListener(evt -> showPanel("orders"));
        Usersbtn.addActionListener(evt -> showPanel("users"));
        Statisticsbtn.addActionListener(evt -> openStatistics());
        Logoutbtn.addActionListener(evt -> logoutAction());
    }

    /**
     * Open statistics window
     */
    private void openStatistics() {
        SalesStatistics sales = new SalesStatistics(); 
        sales.setVisible(true);
        this.dispose();
    }

    /**
     * Show selected panel and hide others
     */
    private void showPanel(String panelName) {
        // Hide all panels
        dashboardPanel.setVisible(false);
        productsPanelView.setVisible(false);
        ordersPanelView.setVisible(false);
        usersPanelView.setVisible(false);
        
        // Show selected panel and refresh its data
        switch (panelName) {
            case "dashboard":
                updateDashboardStats();
                dashboardPanel.setVisible(true);
                break;
            case "products":
                productsPanelView.refreshView(); // FIXED: Now using refreshView()
                productsPanelView.setVisible(true);
                break;
            case "orders":
                ordersPanelView.refreshView();
                ordersPanelView.setVisible(true);
                break;
            case "users":
                usersPanelView.refreshView();
                usersPanelView.setVisible(true);
                break;
        }
    }

    /**
     * Handle logout action
     */
    private void logoutAction() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose();
            // Show login window if you have one
            // new LoginWindow().setVisible(true);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidebarPanel = new javax.swing.JPanel();
        logo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        Dashboardbtn = new javax.swing.JButton();
        Ordersbtn = new javax.swing.JButton();
        Usersbtn = new javax.swing.JButton();
        Statisticsbtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        Logoutbtn = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        Productsbtn = new javax.swing.JButton();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        dashboardPanel = new javax.swing.JPanel();
        ordersPanel = new javax.swing.JPanel();
        ordersHeaderLabel = new javax.swing.JLabel();
        ordersNumberLabel = new javax.swing.JLabel();
        usersPanel = new javax.swing.JPanel();
        usersHeaderLabel = new javax.swing.JLabel();
        usersNumberLabel = new javax.swing.JLabel();
        productPanel = new javax.swing.JPanel();
        productHeaderLabel = new javax.swing.JLabel();
        productNumberLabel = new javax.swing.JLabel();
        revenuePanel = new javax.swing.JPanel();
        revenueHeaderLabel = new javax.swing.JLabel();
        revenueNumberLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(null);

        sidebarPanel.setBackground(new java.awt.Color(28, 28, 28));
        sidebarPanel.setPreferredSize(new java.awt.Dimension(200, 0));
        sidebarPanel.setLayout(null);

        logo.setBackground(new java.awt.Color(28, 28, 28));
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo.png_1_185x85.png"))); // NOI18N
        logo.setBorder(null);
        sidebarPanel.add(logo);
        logo.setBounds(0, 0, 185, 85);
        sidebarPanel.add(jSeparator1);
        jSeparator1.setBounds(0, 85, 200, 3);

        Dashboardbtn.setBackground(new java.awt.Color(43, 43, 43));
        Dashboardbtn.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        Dashboardbtn.setForeground(new java.awt.Color(255, 255, 255));
        Dashboardbtn.setText("Dashboard");
        Dashboardbtn.setMaximumSize(new java.awt.Dimension(200, 40));
        Dashboardbtn.setMinimumSize(new java.awt.Dimension(200, 40));
        sidebarPanel.add(Dashboardbtn);
        Dashboardbtn.setBounds(0, 88, 200, 40);

        Ordersbtn.setBackground(new java.awt.Color(43, 43, 43));
        Ordersbtn.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        Ordersbtn.setForeground(new java.awt.Color(255, 255, 255));
        Ordersbtn.setText("Orders");
        Ordersbtn.setMaximumSize(new java.awt.Dimension(200, 40));
        Ordersbtn.setMinimumSize(new java.awt.Dimension(200, 40));
        sidebarPanel.add(Ordersbtn);
        Ordersbtn.setBounds(0, 128, 200, 40);

        Usersbtn.setBackground(new java.awt.Color(43, 43, 43));
        Usersbtn.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        Usersbtn.setForeground(new java.awt.Color(255, 255, 255));
        Usersbtn.setText("Users");
        Usersbtn.setMaximumSize(new java.awt.Dimension(200, 40));
        sidebarPanel.add(Usersbtn);
        Usersbtn.setBounds(0, 168, 200, 40);

        Statisticsbtn.setBackground(new java.awt.Color(43, 43, 43));
        Statisticsbtn.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        Statisticsbtn.setForeground(new java.awt.Color(255, 255, 255));
        Statisticsbtn.setText("Statistics");
        Statisticsbtn.setMaximumSize(new java.awt.Dimension(200, 40));
        Statisticsbtn.addActionListener(this::StatisticsbtnActionPerformed);
        sidebarPanel.add(Statisticsbtn);
        Statisticsbtn.setBounds(0, 250, 200, 40);
        sidebarPanel.add(jSeparator2);
        jSeparator2.setBounds(0, 288, 200, 10);

        Logoutbtn.setBackground(new java.awt.Color(43, 43, 43));
        Logoutbtn.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        Logoutbtn.setForeground(new java.awt.Color(255, 255, 255));
        Logoutbtn.setText("Logout");
        Logoutbtn.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Logoutbtn.addActionListener(this::LogoutbtnActionPerformed);
        sidebarPanel.add(Logoutbtn);
        Logoutbtn.setBounds(0, 560, 90, 30);
        sidebarPanel.add(jSeparator3);
        jSeparator3.setBounds(0, 400, 200, 10);

        Productsbtn.setBackground(new java.awt.Color(43, 43, 43));
        Productsbtn.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        Productsbtn.setForeground(new java.awt.Color(255, 255, 255));
        Productsbtn.setText("Products");
        Productsbtn.addActionListener(this::ProductsbtnActionPerformed);
        sidebarPanel.add(Productsbtn);
        Productsbtn.setBounds(0, 210, 200, 40);

        getContentPane().add(sidebarPanel);
        sidebarPanel.setBounds(0, 60, 200, 660);

        headerPanel.setBackground(new java.awt.Color(18, 18, 18));
        headerPanel.setPreferredSize(new java.awt.Dimension(0, 60));
        headerPanel.setLayout(null);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Admin Dashboard");
        headerPanel.add(jLabel1);
        jLabel1.setBounds(0, 0, 134, 60);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Admin");
        headerPanel.add(jLabel2);
        jLabel2.setBounds(1210, 0, 49, 60);

        getContentPane().add(headerPanel);
        headerPanel.setBounds(0, 0, 1280, 60);

        contentPanel.setBackground(new java.awt.Color(18, 18, 18));
        contentPanel.setLayout(null);

        dashboardPanel.setBackground(new java.awt.Color(18, 18, 18));
        dashboardPanel.setLayout(null);

        ordersPanel.setBackground(new java.awt.Color(43, 43, 43));
        ordersPanel.setForeground(new java.awt.Color(255, 255, 255));
        ordersPanel.setLayout(null);

        ordersHeaderLabel.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        ordersHeaderLabel.setForeground(new java.awt.Color(242, 242, 242));
        ordersHeaderLabel.setText("Orders");
        ordersPanel.add(ordersHeaderLabel);
        ordersHeaderLabel.setBounds(80, 10, 50, 21);

        ordersNumberLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        ordersNumberLabel.setForeground(new java.awt.Color(242, 242, 242));
        ordersNumberLabel.setText("80");
        ordersPanel.add(ordersNumberLabel);
        ordersNumberLabel.setBounds(90, 70, 37, 24);

        dashboardPanel.add(ordersPanel);
        ordersPanel.setBounds(210, 110, 220, 120);

        usersPanel.setBackground(new java.awt.Color(43, 43, 43));
        usersPanel.setForeground(new java.awt.Color(255, 255, 255));
        usersPanel.setLayout(null);

        usersHeaderLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        usersHeaderLabel.setForeground(new java.awt.Color(242, 242, 242));
        usersHeaderLabel.setText("Users");
        usersPanel.add(usersHeaderLabel);
        usersHeaderLabel.setBounds(80, 10, 60, 24);

        usersNumberLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        usersNumberLabel.setForeground(new java.awt.Color(242, 242, 242));
        usersNumberLabel.setText("120");
        usersPanel.add(usersNumberLabel);
        usersNumberLabel.setBounds(90, 70, 37, 24);

        dashboardPanel.add(usersPanel);
        usersPanel.setBounds(210, 320, 220, 120);

        productPanel.setBackground(new java.awt.Color(43, 43, 43));
        productPanel.setForeground(new java.awt.Color(255, 255, 255));
        productPanel.setLayout(null);

        productHeaderLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        productHeaderLabel.setForeground(new java.awt.Color(242, 242, 242));
        productHeaderLabel.setText("Products");
        productPanel.add(productHeaderLabel);
        productHeaderLabel.setBounds(80, 10, 80, 24);

        productNumberLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        productNumberLabel.setForeground(new java.awt.Color(242, 242, 242));
        productNumberLabel.setText("120");
        productPanel.add(productNumberLabel);
        productNumberLabel.setBounds(90, 70, 37, 24);

        dashboardPanel.add(productPanel);
        productPanel.setBounds(690, 110, 220, 120);

        revenuePanel.setBackground(new java.awt.Color(43, 43, 43));
        revenuePanel.setForeground(new java.awt.Color(255, 255, 255));
        revenuePanel.setLayout(null);

        revenueHeaderLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        revenueHeaderLabel.setForeground(new java.awt.Color(242, 242, 242));
        revenueHeaderLabel.setText("Revenue");
        revenuePanel.add(revenueHeaderLabel);
        revenueHeaderLabel.setBounds(80, 10, 80, 24);

        revenueNumberLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        revenueNumberLabel.setForeground(new java.awt.Color(242, 242, 242));
        revenueNumberLabel.setText("250k");
        revenuePanel.add(revenueNumberLabel);
        revenueNumberLabel.setBounds(90, 70, 50, 24);

        dashboardPanel.add(revenuePanel);
        revenuePanel.setBounds(690, 320, 220, 120);

        contentPanel.add(dashboardPanel);
        dashboardPanel.setBounds(0, 0, 1080, 660);

        getContentPane().add(contentPanel);
        contentPanel.setBounds(200, 60, 1080, 660);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void StatisticsbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatisticsbtnActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_StatisticsbtnActionPerformed

    private void LogoutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LogoutbtnActionPerformed

    private void ProductsbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductsbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductsbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dashboardbtn;
    private javax.swing.JButton Logoutbtn;
    private javax.swing.JButton Ordersbtn;
    private javax.swing.JButton Productsbtn;
    private javax.swing.JButton Statisticsbtn;
    private javax.swing.JButton Usersbtn;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton logo;
    private javax.swing.JLabel ordersHeaderLabel;
    private javax.swing.JLabel ordersNumberLabel;
    private javax.swing.JPanel ordersPanel;
    private javax.swing.JLabel productHeaderLabel;
    private javax.swing.JLabel productNumberLabel;
    private javax.swing.JPanel productPanel;
    private javax.swing.JLabel revenueHeaderLabel;
    private javax.swing.JLabel revenueNumberLabel;
    private javax.swing.JPanel revenuePanel;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JLabel usersHeaderLabel;
    private javax.swing.JLabel usersNumberLabel;
    private javax.swing.JPanel usersPanel;
    // End of variables declaration//GEN-END:variables
}