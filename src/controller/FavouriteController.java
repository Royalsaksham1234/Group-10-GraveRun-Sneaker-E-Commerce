package controller;

import javax.swing.JOptionPane;
import view.Favourites;
import view.CartView;
import model.FavouriteItem;
import model.Ffavourites;

public class FavouriteController {

    private final Favourites view;
    private Ffavourites model;

    public FavouriteController(Favourites view) {
        this.view = view;
        this.model = new Ffavourites();
        initController();
        loadFfavourites();
    }

    private void initController() {
        view.getProfile().addActionListener(e -> showPopupMenu());
        view.getHelpButton().addActionListener(e -> helpClicked());
        view.getLogoLabel().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoClicked();
            }
        });
        view.getAccountMenu().addActionListener(e -> accountClicked());
        view.getOutMenu().addActionListener(e -> logoutClicked());
        view.getFav().addActionListener(e -> switchToCart());
    }

    private void showPopupMenu() {
        view.getPopMenu().show(view.getProfile(), 0, view.getProfile().getHeight());
    }

    private void helpClicked() {
    HelpController helpController = new HelpController(view);
    helpController.showHelp();
    }

    private void logoClicked() {
        JOptionPane.showMessageDialog(view, "Logo clicked!");
    }

    private void accountClicked() {
        JOptionPane.showMessageDialog(view, "Account menu clicked!");
    }

    private void logoutClicked() {
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to logout?");
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void switchToCart() {
        CartView cartView = new CartView();
        new CartController(cartView);
        cartView.setVisible(true);
        view.dispose();
    }

    private void loadFfavourites() {
        view.clearFavourites();
        for (FavouriteItem item : model.getItems()) {
            view.addFavouriteItem(item);
        }
    }
}
