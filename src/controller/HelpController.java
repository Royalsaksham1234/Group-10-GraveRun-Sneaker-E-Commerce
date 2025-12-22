package controller;

import view.HelpPage;
import javax.swing.JFrame;

public class HelpController {

    private HelpPage helpView;
    private JFrame previousPage;

    public HelpController(JFrame previousPage) {
        this.previousPage = previousPage;
        this.helpView = new HelpPage();

        initController();
    }

    private void initController() {
        helpView.getBackButton().addActionListener(e -> goBack());
    }

    private void goBack() {
        previousPage.setVisible(true);
        helpView.dispose();
    }

    public void showHelp() {
        helpView.setVisible(true);
        previousPage.setVisible(false);
    }
}
