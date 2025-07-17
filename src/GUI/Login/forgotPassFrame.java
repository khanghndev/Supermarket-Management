package GUI.Login;

import DAO.NhanVienDAO;
import GUI.Eployee.mainScreen;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
public class forgotPassFrame extends javax.swing.JFrame {

    private boolean isOtpSent = false;
    private String generatedOtp;

    public forgotPassFrame() {
        initComponents();
        setSize(new Dimension(800, 525));
        setTitle("Khôi phục mật khẩu – KMT Supermarket");
        setIconImage(new ImageIcon(getClass().getResource("/resources/login/login_img.png")).getImage());
        SetUp_Panel_Left();
        setLocationRelativeTo(null);
        tfCapchaValue.setText(generateCaptcha(5));
        tfCapchaValue.setEditable(false);

    }

    /**
     * Sinh mã ngẫu nhiên gồm các chữ số, độ dài = length
     */
    private String generateCaptcha(int length) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * Sinh OTP
     */
    private String generateOtp(int length) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * Tạo nội dung HTML cho email OTP
     */
    private String createOtpEmailTemplate(String otp) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Mã OTP Khôi Phục Mật Khẩu</title>\n"
                + "</head>\n"
                + "<body style=\"margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;\">\n"
                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff;\">\n"
                + "        <tr>\n"
                + "            <td align=\"center\" bgcolor=\"#004444\" style=\"padding: 40px 0; color: #ffffff;\">\n"
                + "                <h1 style=\"margin: 0; font-size: 24px;\">KMT Supermarket</h1>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td style=\"padding: 40px 30px;\">\n"
                + "                <h2 style=\"color: #004444; margin-top: 0;\">Khôi Phục Mật Khẩu</h2>\n"
                + "                <p style=\"color: #555555; line-height: 1.6;\">Xin chào,</p>\n"
                + "                <p style=\"color: #555555; line-height: 1.6;\">Chúng tôi đã nhận được yêu cầu khôi phục mật khẩu từ tài khoản của bạn. Để hoàn tất quá trình này, vui lòng sử dụng mã OTP sau:</p>\n"
                + "                <div style=\"background-color: #f8f8f8; border: 1px solid #dddddd; padding: 20px; text-align: center; margin: 30px 0;\">\n"
                + "                    <h2 style=\"font-family: monospace; letter-spacing: 5px; color: #004444; margin: 0;\">" + otp + "</h2>\n"
                + "                </div>\n"
                + "                <p style=\"color: #555555; line-height: 1.6;\">Mã OTP này có giá trị trong vòng 5 phút. Vui lòng không chia sẻ mã này với bất kỳ ai.</p>\n"
                + "                <p style=\"color: #555555; line-height: 1.6;\">Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này và mật khẩu hiện tại của bạn vẫn giữ nguyên.</p>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td bgcolor=\"#FAF6ED\" style=\"padding: 30px; color: #555555; font-size: 14px; text-align: center;\">\n"
                + "                <p style=\"margin: 0;\">© 2025 KMT Supermarket. Tất cả các quyền được bảo lưu.</p>\n"
                + "                <p style=\"margin: 10px 0 0;\">Đây là email tự động, vui lòng không trả lời.</p>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "    </table>\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * Gửi mail qua SMTP (ví dụ Gmail) với nội dung HTML
     */
    private void sendEmail(String to, String subject, String body) throws MessagingException {
        final String username = "noreply.kmtsupermarket@gmail.com";
        final String password = "zzcj qvlm ooas zcys";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(username, "KMT Supermarket"));
        } catch (UnsupportedEncodingException e) {
            msg.setFrom(new InternetAddress(username));
        }
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);

        msg.setContent(body, "text/html; charset=utf-8");

        Transport.send(msg);
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
        lbPassword = new javax.swing.JLabel();
        btnconfirm = new javax.swing.JButton();
        lbStoreTitleBottom1 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        lbPassword1 = new javax.swing.JLabel();
        tfCapcha = new javax.swing.JTextField();
        tfOTP = new javax.swing.JTextField();
        lbOTP = new javax.swing.JLabel();
        tfCapchaValue = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        btnSend1 = new javax.swing.JButton();

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
                .addContainerGap()
                .addComponent(lbStoreName, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnLeftLayout.setVerticalGroup(
            pnLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLeftLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(lbStoreIMG, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLeftLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbStoreName)
                .addGap(93, 93, 93))
        );

        getContentPane().add(pnLeft);
        pnLeft.setBounds(0, 0, 400, 500);

        pnRight.setBackground(new java.awt.Color(255, 255, 255));
        pnRight.setForeground(new java.awt.Color(255, 204, 0));
        pnRight.setAlignmentX(0.0F);
        pnRight.setAlignmentY(0.0F);
        pnRight.setPreferredSize(new java.awt.Dimension(400, 600));

        lbStoreTitleTop.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbStoreTitleTop.setForeground(new java.awt.Color(0, 68, 68));
        lbStoreTitleTop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStoreTitleTop.setText("Quên mật khẩu?");
        lbStoreTitleTop.setToolTipText("");
        lbStoreTitleTop.setAlignmentY(0.0F);

        lbPassword.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbPassword.setText("Email");

        btnconfirm.setBackground(new java.awt.Color(0, 68, 68));
        btnconfirm.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnconfirm.setForeground(new java.awt.Color(255, 255, 255));
        btnconfirm.setText("Xác nhận");
        btnconfirm.setBorderPainted(false);
        btnconfirm.setFocusPainted(false);
        btnconfirm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnconfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmActionPerformed(evt);
            }
        });

        lbStoreTitleBottom1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lbStoreTitleBottom1.setForeground(new java.awt.Color(0, 68, 68));
        lbStoreTitleBottom1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStoreTitleBottom1.setText("© 2025 KMT Supermarket. All rights reserved");
        lbStoreTitleBottom1.setToolTipText("");
        lbStoreTitleBottom1.setAlignmentY(0.0F);

        tfEmail.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N

        lbPassword1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbPassword1.setText("Nhập mã capcha");

        tfCapcha.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N

        tfOTP.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N

        lbOTP.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbOTP.setText("Nhập mã OTP");

        tfCapchaValue.setBackground(new java.awt.Color(102, 102, 102));
        tfCapchaValue.setFont(new java.awt.Font("Segoe Print", 1, 20)); // NOI18N
        tfCapchaValue.setForeground(new java.awt.Color(255, 204, 0));
        tfCapchaValue.setText("J9K1");

        btnExit.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnExit.setForeground(new java.awt.Color(51, 51, 51));
        btnExit.setText("Thoát");
        btnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExit.setFocusPainted(false);
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitLogin_Event(evt);
            }
        });

        btnSend1.setBackground(new java.awt.Color(0, 68, 68));
        btnSend1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnSend1.setForeground(new java.awt.Color(255, 255, 255));
        btnSend1.setText("Nhận mã");
        btnSend1.setBorderPainted(false);
        btnSend1.setFocusPainted(false);
        btnSend1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSend1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSend1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnRightLayout = new javax.swing.GroupLayout(pnRight);
        pnRight.setLayout(pnRightLayout);
        pnRightLayout.setHorizontalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRightLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStoreTitleTop, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPassword)
                    .addComponent(lbPassword1)
                    .addComponent(lbOTP)
                    .addGroup(pnRightLayout.createSequentialGroup()
                        .addComponent(tfCapcha, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCapchaValue, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnRightLayout.createSequentialGroup()
                        .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSend1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31))
            .addGroup(pnRightLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(lbStoreTitleBottom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnRightLayout.setVerticalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbStoreTitleTop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbPassword1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfCapcha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCapchaValue, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbOTP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfOTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend1))
                .addGap(45, 45, 45)
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(lbStoreTitleBottom1)
                .addGap(25, 25, 25))
        );

        getContentPane().add(pnRight);
        pnRight.setBounds(400, 0, 400, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitLogin_Event(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitLogin_Event
        // TODO add your handling code here:
        dispose();
        new loginFrame().setVisible(true);
    }//GEN-LAST:event_btnExitLogin_Event

    private void btnconfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmActionPerformed
        // TODO add your handling code here:
        if (!isOtpSent) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhấn \"Nhận mã\" trước.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String otpIn = tfOTP.getText().trim();
        if (otpIn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã OTP.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!otpIn.equals(generatedOtp)) {
            JOptionPane.showMessageDialog(this,
                    "OTP không đúng. Vui lòng kiểm tra lại.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPasswordField pf = new JPasswordField();
        pf.setColumns(20);
        JPasswordField confirmPf = new JPasswordField();
        confirmPf.setColumns(20);

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.add(new javax.swing.JLabel("Nhập mật khẩu mới:"));
        panel.add(pf);
        panel.add(javax.swing.Box.createVerticalStrut(15)); // khoảng cách giữa các field
        panel.add(new javax.swing.JLabel("Xác nhận mật khẩu mới:"));
        panel.add(confirmPf);

        int ok = JOptionPane.showConfirmDialog(this, panel, "Đặt mật khẩu mới",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (ok != JOptionPane.OK_OPTION) {
            return;
        }

        String newPass = new String(pf.getPassword()).trim();
        String confirmPass = new String(confirmPf.getPassword()).trim();

        if (newPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        NhanVienDAO NV = new NhanVienDAO();
        int id = NV.getIDbyEmail(tfEmail.getText());
        NV.changePass(id, newPass);
        JOptionPane.showMessageDialog(this,"Đổi mật khẩu thành công! Bạn có thể đăng nhập với mật khẩu mới.","Thành công", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new loginFrame().setVisible(true);
    }//GEN-LAST:event_btnconfirmActionPerformed

    private void btnSend1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSend1ActionPerformed
        // TODO add your handling code here:
        String email = tfEmail.getText().trim();
        String captchaIn = tfCapcha.getText().trim();
        String captchaVal = tfCapchaValue.getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!captchaIn.equals(captchaVal)) {
            JOptionPane.showMessageDialog(this, "Mã captcha không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tfCapchaValue.setText(generateCaptcha(5));
            return;
        }
        NhanVienDAO NV = new NhanVienDAO();
        if (NV.getIDbyEmail(email)==0) {
                    JOptionPane.showMessageDialog(this, "Email này không tồn tại trong hệ thống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    tfCapchaValue.setText(generateCaptcha(5));
                    return;
        }

        try {
            generatedOtp = generateOtp(6);
            String emailContent = createOtpEmailTemplate(generatedOtp);
            JOptionPane optionPane = new JOptionPane("Đang gửi email...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            final javax.swing.JDialog dialog = new javax.swing.JDialog();
            dialog.setTitle("Thông báo");
            dialog.setContentPane(optionPane);
            dialog.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
            dialog.pack();
            dialog.setLocationRelativeTo(this);

            new Thread(() -> {
                try {
                    dialog.setVisible(true);
                    sendEmail(email, "KMT Supermarket – Mã OTP khôi phục mật khẩu", emailContent);
                    dialog.dispose();
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        isOtpSent = true;
                        lbOTP.setVisible(true);
                        tfOTP.setVisible(true);
                        btnSend1.setText("Gửi lại mã OTP");
                        JOptionPane.showMessageDialog(this, "OTP đã được gửi đến email của bạn.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    });
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                    dialog.dispose();
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Gửi mail thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    if (dialog.isVisible()) {
                        dialog.dispose();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSend1ActionPerformed

    private void SetUp_Panel_Left() {
        ImageIcon icon = new ImageIcon("src/resources/login/login_img.png");
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
                new forgotPassFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.ButtonGroup btnGroup_Role;
    private javax.swing.JButton btnSend1;
    private javax.swing.JButton btnconfirm;
    private javax.swing.JLabel lbOTP;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbPassword1;
    private javax.swing.JLabel lbStoreIMG;
    private javax.swing.JLabel lbStoreName;
    private javax.swing.JLabel lbStoreTitleBottom1;
    private javax.swing.JLabel lbStoreTitleTop;
    private javax.swing.JPanel pnLeft;
    private javax.swing.JPanel pnRight;
    private javax.swing.JTextField tfCapcha;
    private javax.swing.JTextField tfCapchaValue;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfOTP;
    // End of variables declaration//GEN-END:variables

    boolean isGetOTP = false;
}
