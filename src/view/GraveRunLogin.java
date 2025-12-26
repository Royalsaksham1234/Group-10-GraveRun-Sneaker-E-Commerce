/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import controller.LoginController;
import view.dashboard;



/**
 *
 * @author Samsung
 */
public class GraveRunLogin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GraveRunLogin.class.getName());

    /**
     * Creates new form GraveRunLogin
     */
    public GraveRunLogin() {
    initComponents();
    
    // SIGNUP link
    signuplink.setEditable(false); // make it non-editable
    signuplink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // show hand cursor
    signuplink.setForeground(new java.awt.Color(204, 0, 0)); // red color for link-like feel
    signuplink.setBorder(null); // optional: remove border

    signuplink.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        new GraveRunSignup().setVisible(true); // open signup JFrame
        dispose(); // close current login JFrame
    }
});
 // FORGOT PASSWORD link
    Forgotpassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // hand cursor
    Forgotpassword.setForeground(new java.awt.Color(0, 102, 204)); // blue like a link
    Forgotpassword.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            new GraveRunForgotPassword().setVisible(true); // open Forgot Password JFrame
            dispose(); // close current login JFrame
        }
    });
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        screen = new javax.swing.JPanel();
        Loginfront = new javax.swing.JPanel();
        Graverun = new javax.swing.JLabel();
        Donthaveanaccount = new javax.swing.JLabel();
        emailF = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        Login = new javax.swing.JButton();
        logan = new javax.swing.JLabel();
        signuplink = new javax.swing.JTextField();
        Forgotpassword = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Loginfront.setBackground(new java.awt.Color(0, 0, 0));
        Loginfront.setForeground(new java.awt.Color(204, 204, 204));
        Loginfront.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 14)); // NOI18N

        Graverun.setFont(new java.awt.Font("Book Antiqua", 1, 36)); // NOI18N
        Graverun.setForeground(new java.awt.Color(204, 204, 204));
        Graverun.setText(" GRAVERUN");
        Graverun.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 0)));

        Donthaveanaccount.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 18)); // NOI18N
        Donthaveanaccount.setForeground(new java.awt.Color(255, 255, 255));
        Donthaveanaccount.setText("Don't have an account");

        emailF.setBackground(new java.awt.Color(0, 0, 0));
        emailF.setFont(new java.awt.Font("Rockwell", 0, 18)); // NOI18N
        emailF.setForeground(new java.awt.Color(255, 255, 255));
        emailF.setText("Email");
        emailF.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        emailF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                emailFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailFFocusLost(evt);
            }
        });
        emailF.addActionListener(this::emailFActionPerformed);

        password.setBackground(new java.awt.Color(0, 0, 0));
        password.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 18)); // NOI18N
        password.setForeground(new java.awt.Color(255, 255, 255));
        password.setText("PASSWORD");
        password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordFocusLost(evt);
            }
        });
        password.addActionListener(this::passwordActionPerformed);

        Login.setBackground(new java.awt.Color(153, 0, 0));
        Login.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        Login.setForeground(new java.awt.Color(255, 255, 255));
        Login.setText("LOGIN");
        Login.addActionListener(this::LoginActionPerformed);

        logan.setBackground(new java.awt.Color(0, 0, 0));
        logan.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        logan.setForeground(new java.awt.Color(255, 255, 255));
        logan.setText("RUN BEYOND THE GRAVE");

        signuplink.setBackground(new java.awt.Color(0, 0, 0));
        signuplink.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        signuplink.setForeground(new java.awt.Color(255, 255, 255));
        signuplink.setText("  SIGNUP");
        signuplink.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 0, 0)));
        signuplink.addActionListener(this::signuplinkActionPerformed);

        Forgotpassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Forgotpassword.setForeground(new java.awt.Color(204, 204, 204));
        Forgotpassword.setText("      Forgot Password?");

        javax.swing.GroupLayout LoginfrontLayout = new javax.swing.GroupLayout(Loginfront);
        Loginfront.setLayout(LoginfrontLayout);
        LoginfrontLayout.setHorizontalGroup(
            LoginfrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginfrontLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(LoginfrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginfrontLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(logan))
                    .addGroup(LoginfrontLayout.createSequentialGroup()
                        .addComponent(Donthaveanaccount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(signuplink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(509, 509, 509))
            .addGroup(LoginfrontLayout.createSequentialGroup()
                .addGap(484, 484, 484)
                .addGroup(LoginfrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailF, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(LoginfrontLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(Graverun)))
                .addContainerGap(465, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginfrontLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(LoginfrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginfrontLayout.createSequentialGroup()
                        .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(570, 570, 570))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginfrontLayout.createSequentialGroup()
                        .addComponent(Forgotpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(531, 531, 531))))
        );
        LoginfrontLayout.setVerticalGroup(
            LoginfrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginfrontLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(Graverun, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logan)
                .addGap(106, 106, 106)
                .addComponent(emailF, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(Forgotpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LoginfrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginfrontLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(signuplink, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Donthaveanaccount, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65))
        );

        javax.swing.GroupLayout screenLayout = new javax.swing.GroupLayout(screen);
        screen.setLayout(screenLayout);
        screenLayout.setHorizontalGroup(
            screenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(screenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Loginfront, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        screenLayout.setVerticalGroup(
            screenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, screenLayout.createSequentialGroup()
                .addComponent(Loginfront, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(screen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(screen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emailFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailFFocusGained

    }//GEN-LAST:event_emailFFocusGained

    private void emailFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailFFocusLost

    }//GEN-LAST:event_emailFFocusLost

    private void emailFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFActionPerformed

    }//GEN-LAST:event_emailFActionPerformed

    private void passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFocusGained

    private void passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFocusLost

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed

    }//GEN-LAST:event_passwordActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
                                     
    String email = emailF.getText();
    String passwordText = new String(password.getPassword());

    LoginController loginController = new LoginController();
    boolean success = loginController.login(email, passwordText);

    if (success) {
        new dashboard().setVisible(true);
        this.dispose(); // close login window
    }


    }//GEN-LAST:event_LoginActionPerformed

    private void signuplinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signuplinkActionPerformed

    }//GEN-LAST:event_signuplinkActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GraveRunLogin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Donthaveanaccount;
    private javax.swing.JLabel Forgotpassword;
    private javax.swing.JLabel Graverun;
    private javax.swing.JButton Login;
    private javax.swing.JPanel Loginfront;
    private javax.swing.JTextField emailF;
    private javax.swing.JLabel logan;
    private javax.swing.JPasswordField password;
    private javax.swing.JPanel screen;
    private javax.swing.JTextField signuplink;
    // End of variables declaration//GEN-END:variables

    public Object getLoginButton() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
