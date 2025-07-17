package GUI.Login;

import DAO.NhanVienDAO;
import GUI.Admin.mainAdminScreen;
import GUI.Eployee.mainScreen;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class loginFrame extends javax.swing.JFrame {

    NhanVienDAO NV = new NhanVienDAO();
    public loginFrame() {
        initComponents();

        setSize(new Dimension(800, 525));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hệ thống quản lý hàng hóa siêu thị KMT");
        setIconImage(new ImageIcon(getClass().getResource("/resources/login/login_img.png")).getImage());
        SetUp_Panel_Left();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup_Role = new javax.swing.ButtonGroup();
        pnLeft = new javax.swing.JPanel();
        lbStoreIMG = new javax.swing.JLabel();
        lbStoreName = new javax.swing.JLabel();
        pnRight = new javax.swing.JPanel();
        lbStoreTitleTop = new javax.swing.JLabel();
        lbStoreTitleBottom = new javax.swing.JLabel();
        lbUserName = new javax.swing.JLabel();
        tfUserName = new javax.swing.JTextField();
        lbPassword = new javax.swing.JLabel();
        tfPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        lbStoreTitleBottom1 = new javax.swing.JLabel();
        lbForgotPass = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(800, 525));
        getContentPane().setLayout(null);

        pnLeft.setBackground(new java.awt.Color(250, 246, 237));
        pnLeft.setAlignmentX(0.0F);
        pnLeft.setAlignmentY(0.0F);
        pnLeft.setPreferredSize(new java.awt.Dimension(400, 500));

        lbStoreName.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        lbStoreName.setForeground(new java.awt.Color(0, 68, 68));
        lbStoreName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbStoreName.setText("KMT Supermarket");
        lbStoreName.setToolTipText("");
        lbStoreName.setAlignmentY(0.0F);

        javax.swing.GroupLayout pnLeftLayout = new javax.swing.GroupLayout(pnLeft);
        pnLeft.setLayout(pnLeftLayout);
        pnLeftLayout.setHorizontalGroup(
            pnLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLeftLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbStoreIMG, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(pnLeftLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lbStoreName, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        pnLeftLayout.setVerticalGroup(
            pnLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLeftLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(lbStoreIMG, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbStoreName)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        getContentPane().add(pnLeft);
        pnLeft.setBounds(0, 0, 400, 500);

        pnRight.setBackground(new java.awt.Color(255, 255, 255));
        pnRight.setAlignmentX(0.0F);
        pnRight.setAlignmentY(0.0F);
        pnRight.setPreferredSize(new java.awt.Dimension(400, 600));

        lbStoreTitleTop.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbStoreTitleTop.setForeground(new java.awt.Color(0, 68, 68));
        lbStoreTitleTop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStoreTitleTop.setText("Vui lòng đăng nhập tài khoản");
        lbStoreTitleTop.setToolTipText("");
        lbStoreTitleTop.setAlignmentY(0.0F);

        lbStoreTitleBottom.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbStoreTitleBottom.setForeground(new java.awt.Color(0, 68, 68));
        lbStoreTitleBottom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStoreTitleBottom.setText("trước khi truy cập vào hệ thống");
        lbStoreTitleBottom.setToolTipText("");
        lbStoreTitleBottom.setAlignmentY(0.0F);

        lbUserName.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbUserName.setText("Tên đăng nhập");

        tfUserName.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        tfUserName.setText("hung@cty.vn");

        lbPassword.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbPassword.setText("Mật khẩu");

        tfPassword.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        tfPassword.setText("1");

        btnLogin.setBackground(new java.awt.Color(0, 68, 68));
        btnLogin.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login_Event(evt);
            }
        });

        lbStoreTitleBottom1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lbStoreTitleBottom1.setForeground(new java.awt.Color(0, 68, 68));
        lbStoreTitleBottom1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStoreTitleBottom1.setText("© 2025 KMT Supermarket. All rights reserved");
        lbStoreTitleBottom1.setToolTipText("");
        lbStoreTitleBottom1.setAlignmentY(0.0F);

        lbForgotPass.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbForgotPass.setForeground(new java.awt.Color(0, 51, 255));
        lbForgotPass.setText("Quên mật khẩu?");
        lbForgotPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbForgotPassMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnRightLayout = new javax.swing.GroupLayout(pnRight);
        pnRight.setLayout(pnRightLayout);
        pnRightLayout.setHorizontalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRightLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnRightLayout.createSequentialGroup()
                        .addComponent(lbStoreTitleBottom1, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnRightLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPassword)
                            .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbUserName)
                                .addComponent(lbStoreTitleBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                                .addComponent(lbStoreTitleTop, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfUserName)
                                .addComponent(tfPassword))
                            .addComponent(lbForgotPass, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43))))
        );
        pnRightLayout.setVerticalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRightLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lbStoreTitleTop, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbStoreTitleBottom)
                .addGap(48, 48, 48)
                .addComponent(lbUserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbForgotPass)
                .addGap(18, 18, 18)
                .addComponent(lbStoreTitleBottom1)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        getContentPane().add(pnRight);
        pnRight.setBounds(400, 0, 400, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Login_Event(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login_Event
        String email = tfUserName.getText().trim();
        String pass = new String(tfPassword.getPassword()).trim();

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Email và Mật khẩu.", "Lỗi đăng nhập", JOptionPane.WARNING_MESSAGE);
            return;
        }
        NhanVienDAO nv = new NhanVienDAO();
        String loginStr = nv.Login(email, pass).trim();
        if (loginStr.trim().length()==0) {
            JOptionPane.showMessageDialog(this, "Sai Email hoặc Mật khẩu.", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(NV.findStatusByEmail(email)==0)
        {
            JOptionPane.showMessageDialog(this, "Tài khoản bạn đã bị khóa. Liên hệ admin để mở khóa.", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int staffId = Integer.parseInt(loginStr.split("-")[0]);
        String hoTen = loginStr.split("-")[1];
        String chucVu = loginStr.split("-")[2];
        if ("Quản lý".equalsIgnoreCase(chucVu)) {
            JOptionPane.showMessageDialog(this, "Xin chào Quản lý " + hoTen + "!", "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new mainAdminScreen(staffId).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Xin chào " + hoTen + "!", "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new mainScreen(staffId).setVisible(true);
        }
    }//GEN-LAST:event_Login_Event

    private void lbForgotPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbForgotPassMouseClicked
        // TODO add your handling code here:
        setVisible(false);
        new forgotPassFrame().setVisible(true);
    }//GEN-LAST:event_lbForgotPassMouseClicked

    private void SetUp_Panel_Left() {
        ImageIcon icon = new ImageIcon("src\\resources\\login\\login_img.png");
        Image img = icon.getImage().getScaledInstance(lbStoreIMG.getWidth(), lbStoreIMG.getHeight(), Image.SCALE_SMOOTH);
        lbStoreIMG.setIcon(new ImageIcon(img));
    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroup_Role;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel lbForgotPass;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbStoreIMG;
    private javax.swing.JLabel lbStoreName;
    private javax.swing.JLabel lbStoreTitleBottom;
    private javax.swing.JLabel lbStoreTitleBottom1;
    private javax.swing.JLabel lbStoreTitleTop;
    private javax.swing.JLabel lbUserName;
    private javax.swing.JPanel pnLeft;
    private javax.swing.JPanel pnRight;
    private javax.swing.JPasswordField tfPassword;
    private javax.swing.JTextField tfUserName;
    // End of variables declaration//GEN-END:variables
}
