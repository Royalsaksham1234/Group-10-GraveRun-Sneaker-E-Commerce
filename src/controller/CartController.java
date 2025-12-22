package controller;

import view.CartView;
import java.awt.event.*;
import view.Favourites;
import model.Cart;
import model.CartItem;

public class CartController {

    private CartView view;
    private Cart cart;

    public CartController(CartView view) {
        this.view = view;
        this.cart = new Cart();
        initController();
        loadCart();
    }

    private void initController() {
        view.getProfile().addActionListener(e -> showProfileMenu());
        view.getFav().addActionListener(e -> openFavourites());
        view.getHelpButton().addActionListener(e -> openHelpPage());

        view.getLogoLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openDashboard();
            }
        });

        view.getSearchField().addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (view.getSearchField().getText().equals("Search sneakers or brands")) {
                    view.getSearchField().setText("");
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (view.getSearchField().getText().trim().isEmpty()) {
                    view.getSearchField().setText("Search sneakers or brands");
                }
            }
        });

        view.getAccountMenu().addActionListener(e -> openAccount());
        view.getOrderMenu().addActionListener(e -> openOrders());
        view.getOutMenu().addActionListener(e -> logout());
    }

    private void showProfileMenu() {
        view.getPopMenu().show(view.getProfile(), 0, view.getProfile().getHeight());
    }

    private void openFavourites() {

    Favourites favourites = new Favourites();
    FavouriteController favouriteController = new FavouriteController(favourites);
    favourites.setVisible(true);
    view.dispose();
}

    

    private void openHelpPage() {
        HelpController helpController = new HelpController(view);
        helpController.showHelp();
    }

    private void openDashboard() {
        try {
            Class<?> homeClass = Class.forName("view.dashboard");
            javax.swing.JFrame home = (javax.swing.JFrame) homeClass.getDeclaredConstructor().newInstance();
            home.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(
                view,
                "Home page is not available yet.",
                "Navigation Error",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void openAccount() {
        System.out.println("Account menu clicked");
    }

    private void openOrders() {
        System.out.println("Order status menu clicked");
    }

    private void logout() {
        System.out.println("Log out menu clicked");
    }
    
private void switchToFavourites() {
    Favourites favourites = new Favourites();             
    FavouriteController favouriteController = new FavouriteController(favourites);  
    favourites.setVisible(true);                           
    view.dispose();                                       
}

private void loadCart() {
    view.clearCart();

    for (CartItem item : cart.getItems()) {
        view.addCartItem(item);
    }
    view.setTotal(cart.getCartTotal());
}


}

