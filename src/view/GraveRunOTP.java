/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.OTPController;
import javax.swing.JOptionPane;

/**
 *
 * @author Samsung
 */
public class GraveRunOTP extends javax.swing.JFrame {

 private final OTPController otpController;
 private final String email;
 
public GraveRunOTP(String email) {
    initComponents();
    this.email = email;
    this.otpController = new OTPController(this, email);
    




        pin3.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a)
                    throws javax.swing.text.BadLocationException {
                if (str == null) return;
                String currentText = getText(0, getLength());
                String newText = currentText.substring(0, offs) + str + currentText.substring(offs);
                if (newText.length() <= 6 && str.matches("\\d+")) {
                    super.insertString(offs, str, a);
                }
            }
        });


        changepassword.addActionListener(e -> onChangePassword());
}

    @SuppressWarnings("unchecked")
private void onChangePassword() {
    String enteredOTP = pin3.getText().trim();

    if (enteredOTP.length() != 6) {
        JOptionPane.showMessageDialog(this, "Please enter all 6 digits.", "Incomplete OTP", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Call OTPController to verify
    boolean verified = otpController.verifyOTP(enteredOTP);

    if (verified) {
        // OTP is correct — proceed to Change Password frame
        new GraveRunConfirmPassword(email).setVisible(true); // make sure you have this frame
        this.dispose();
    }
    // If not verified, otpController already shows "Invalid OTP" message
}

    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OTPpanel = new javax.swing.JPanel();
        GRAVERUN = new javax.swing.JLabel();
        logan = new javax.swing.JLabel();
        ForgetYourPassword = new javax.swing.JLabel();
        enteremail = new javax.swing.JLabel();
        changepassword = new java.awt.Button();
        logo = new javax.swing.JLabel();
        pin3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        OTPpanel.setBackground(new java.awt.Color(0, 0, 0));

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
        enteremail.setText("Enter the OTP sent to your E-mail to reset your password.");

        changepassword.setBackground(new java.awt.Color(204, 0, 0));
        changepassword.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        changepassword.setForeground(new java.awt.Color(255, 255, 255));
        changepassword.setLabel("Change Password");

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo.png.png"))); // NOI18N
        logo.setText("jLabel1");

        pin3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        pin3.addActionListener(this::pin3ActionPerformed);

        javax.swing.GroupLayout OTPpanelLayout = new javax.swing.GroupLayout(OTPpanel);
        OTPpanel.setLayout(OTPpanelLayout);
        OTPpanelLayout.setHorizontalGroup(
            OTPpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OTPpanelLayout.createSequentialGroup()
                .addGroup(OTPpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OTPpanelLayout.createSequentialGroup()
                        .addGap(483, 483, 483)
                        .addComponent(ForgetYourPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OTPpanelLayout.createSequentialGroup()
                        .addGap(367, 367, 367)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(OTPpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GRAVERUN, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(OTPpanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(logan, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(OTPpanelLayout.createSequentialGroup()
                        .addGap(527, 527, 527)
                        .addComponent(changepassword, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OTPpanelLayout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addComponent(enteremail, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OTPpanelLayout.createSequentialGroup()
                        .addGap(470, 470, 470)
                        .addComponent(pin3, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(445, Short.MAX_VALUE))
        );
        OTPpanelLayout.setVerticalGroup(
            OTPpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OTPpanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(OTPpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OTPpanelLayout.createSequentialGroup()
                        .addComponent(GRAVERUN, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logan, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108)
                .addComponent(ForgetYourPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(enteremail)
                .addGap(39, 39, 39)
                .addComponent(pin3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(changepassword, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
        );

        changepassword.getAccessibleContext().setAccessibleName(" Change Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OTPpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OTPpanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pin3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pin3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pin3ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ForgetYourPassword;
    private javax.swing.JLabel GRAVERUN;
    private javax.swing.JPanel OTPpanel;
    private java.awt.Button changepassword;
    private javax.swing.JLabel enteremail;
    private javax.swing.JLabel logan;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField pin3;
    // End of variables declaration//GEN-END:variables

}
