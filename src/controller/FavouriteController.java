package controller;

import dao.FavoriteDAO;
import javax.swing.JOptionPane;
import model.FavouriteItem;
import model.ProductModel;
import view.Favourites;
import view.ProductFavPanel;

public class FavouriteController {

    private final Favourites view;
    private final FavoriteDAO dao;

    // ================= CONSTRUCTOR =================
    public FavouriteController(Favourites view) {
        this.view = view;
        this.dao = new FavoriteDAO(); // ✅ no args, internally handles user

        initController();
        loadFavourites();
    }

    // ================= INIT =================
    private void initController() {

        view.getProfileButton().addActionListener(e -> showPopupMenu());
        view.getHelpButton().addActionListener(e -> helpClicked());

        view.getLogoLabel().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoClicked();
            }
        });

        view.getAccountMenu().addActionListener(e -> accountClicked());
        view.getOUTMenu().addActionListener(e -> logoutClicked());
    }

    // ================= UI ACTIONS =================
    private void showPopupMenu() {
        view.getPopupMenu().show(
                view.getProfileButton(),
                0,
                view.getProfileButton().getHeight()
        );
    }

    private void helpClicked() {
        new HelpController(view).showHelp();
    }

    private void logoClicked() {
        JOptionPane.showMessageDialog(view, "Logo clicked!");
    }

    private void accountClicked() {
        JOptionPane.showMessageDialog(view, "Account clicked!");
    }

    private void logoutClicked() {
        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ================= FAVOURITES (DB) =================
    private void loadFavourites() {

        view.getProductContainer().removeAll();

        for (FavouriteItem item : dao.getItems()) {

            ProductFavPanel panel = new ProductFavPanel();

            panel.setProductId(item.getProductId());
            panel.setProductName(item.getProductName());
            panel.setUnitPrice(item.getPrice());
            panel.setProductImage(item.getImageUrl());

            panel.addDeleteListener(() -> {
                dao.remove(item.getProductId()); // ✅ DB delete
                loadFavourites();                // ✅ UI refresh
            });

            view.addFavouritePanel(panel);
        }
    }

    // ================= CALLED FROM DASHBOARD =================
    public void addToFavourites(ProductModel product) {

        if (dao.isFavourite(product.getProductId())) {
            JOptionPane.showMessageDialog(view, "Already in favourites!");
            return;
        }

        dao.add(product);  // ✅ DB insert
        loadFavourites();
    }
}
