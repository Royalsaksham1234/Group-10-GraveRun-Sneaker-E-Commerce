/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import dao.UserDao; 
import javax.swing.*;


/**
 *
 * @author Samsung
 */
public class GraveRunForgotPassword extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GraveRunForgotPassword.class.getName());

    /**
     * Creates new form GraveRunForgotPassword
     */
    public GraveRunForgotPassword() {
        initComponents();
        
// --- Button action ---
SendResetCode.addActionListener(evt -> {
    String email = Email.getText().trim();

    if (email.isEmpty() || email.equals("Email") || email.equals("                               Email")) {
        JOptionPane.showMessageDialog(GraveRunForgotPassword.this, "Please enter your email.");
    } else {
        UserDao userDao = new UserDao();
        if (userDao.checkUserExists("", email)) {
            // Generate and save reset code
            String resetCode = userDao.generateResetCode();
            if (userDao.saveResetCode(email, resetCode)) {
                JOptionPane.showMessageDialog(
                    GraveRunForgotPassword.this,
                    "Reset code sent to your email: " + resetCode
                );
                dispose(); // close ForgotPassword window
                new GraveRunOTP(email).setVisible(true); // ✅ go to OTP screen
            } else {
                JOptionPane.showMessageDialog(
                    GraveRunForgotPassword.this,
                    "Error saving reset code. Please try again."
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                GraveRunForgotPassword.this,
                "No account found with that email."
            );
        }
    }
});


        // --- Placeholder logic for Email field ---
        Email.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (Email.getText().trim().equals("Email")
                    || Email.getText().trim().equals("                               Email")) {
                    Email.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (Email.getText().trim().isEmpty()) {
                    Email.setText("                               Email");
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        GRAVERUN = new javax.swing.JLabel();
        logan = new javax.swing.JLabel();
        ForgetYourPassword = new javax.swing.JLabel();
        enteremail = new javax.swing.JLabel();
        Email = new javax.swing.JTextField();
        SendResetCode = new javax.swing.JButton();
        logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        GRAVERUN.setFont(new java.awt.Font("Bookman Old Style", 1, 24));
        GRAVERUN.setForeground(new java.awt.Color(204, 204, 204));
        GRAVERUN.setText("  GRAVERUN");
        GRAVERUN.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 0)));

        logan.setForeground(new java.awt.Color(255, 255, 255));
        logan.setText("RUN BEYOND THE GRAVE");

        ForgetYourPassword.setFont(new java.awt.Font("Segoe UI Light", 0, 18));
        ForgetYourPassword.setForeground(new java.awt.Color(204, 204, 204));
        ForgetYourPassword.setText("       Forget Your Password?");

        enteremail.setFont(new java.awt.Font("Segoe UI Light", 0, 18));
        enteremail.setForeground(new java.awt.Color(204, 204, 204));
        enteremail.setText("Enter your E-mail to receive a password reset code.");

        Email.setBackground(new java.awt.Color(0, 0, 0));
        Email.setFont(new java.awt.Font("Segoe UI", 0, 18));
        Email.setForeground(new java.awt.Color(204, 204, 204));
        Email.setText("                               Email");
        Email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));

        SendResetCode.setBackground(new java.awt.Color(204, 0, 0));
        SendResetCode.setFont(new java.awt.Font("Georgia", 0, 14));
        SendResetCode.setForeground(new java.awt.Color(255, 255, 255));
        SendResetCode.setText("Send Reset Code");

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo.png.png")));
        logo.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(431, 431, 431)
                        .addComponent(enteremail, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(501, 501, 501)
                        .addComponent(ForgetYourPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(453, 453, 453)
                        .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(logan, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GRAVERUN, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(569, 569, 569)
                        .addComponent(SendResetCode)))
                .addContainerGap(444, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(GRAVERUN, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logan, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addComponent(ForgetYourPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enteremail)
                .addGap(35, 35, 35)
                .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(SendResetCode, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

  


   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        GRAVERUN = new javax.swing.JLabel();
        logan = new javax.swing.JLabel();
        ForgetYourPassword = new javax.swing.JLabel();
        enteremail = new javax.swing.JLabel();
        Email = new javax.swing.JTextField();
        SendResetCode = new javax.swing.JButton();
        logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        GRAVERUN.setFont(new java.awt.Font("Bookman Old Style", 1, 24)); // NOI18N
        GRAVERUN.setForeground(new java.awt.Color(204, 204, 204));
        GRAVERUN.setText("  GRAVERUN");
        GRAVERUN.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 0)));

        logan.setForeground(new java.awt.Color(255, 255, 255));
        logan.setText("RUN BEYOND THE GRAVE");

        ForgetYourPassword.setBackground(new java.awt.Color(0, 0, 0));
        ForgetYourPassword.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        ForgetYourPassword.setForeground(new java.awt.Color(204, 204, 204));
        ForgetYourPassword.setText("       Forget Your Password?");

        enteremail.setBackground(new java.awt.Color(0, 0, 0));
        enteremail.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        enteremail.setForeground(new java.awt.Color(204, 204, 204));
        enteremail.setText("Enter your E-mail to receive a password reset code.");

        Email.setBackground(new java.awt.Color(0, 0, 0));
        Email.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Email.setForeground(new java.awt.Color(204, 204, 204));
        Email.setText("                               Email");
        Email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        Email.addActionListener(this::EmailActionPerformed);

        SendResetCode.setBackground(new java.awt.Color(204, 0, 0));
        SendResetCode.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SendResetCode.setForeground(new java.awt.Color(255, 255, 255));
        SendResetCode.setText("Send Reset Code");
        SendResetCode.addActionListener(this::SendResetCodeActionPerformed);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo.png.png"))); // NOI18N
        logo.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(431, 431, 431)
                        .addComponent(enteremail, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(501, 501, 501)
                        .addComponent(ForgetYourPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(453, 453, 453)
                        .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(logan, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GRAVERUN, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(551, 551, 551)
                        .addComponent(SendResetCode)))
                .addContainerGap(444, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(GRAVERUN, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logan, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addComponent(ForgetYourPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enteremail)
                .addGap(35, 35, 35)
                .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(SendResetCode, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailActionPerformed

    private void SendResetCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendResetCodeActionPerformed
      
        // TODO add your handling code here:
    }//GEN-LAST:event_SendResetCodeActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new GraveRunForgotPassword().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Email;
    private javax.swing.JLabel ForgetYourPassword;
    private javax.swing.JLabel GRAVERUN;
    private javax.swing.JButton SendResetCode;
    private javax.swing.JLabel enteremail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel logan;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
