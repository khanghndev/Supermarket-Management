/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Admin;
import DAO.KhachHangDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.Timer;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modal.KhachHang;
/**
 *
 * @author Minh
 */
public class customer_AdminScreen extends javax.swing.JFrame {

    private DefaultTableModel model;
    mainAdminScreen ms;
    KhachHangDAO KH = new KhachHangDAO();
    boolean isAdd= false, isEdit = false, isShow =false;

    public customer_AdminScreen(mainAdminScreen m) {
        initComponents();
        this.ms = m;
        SwingUtilities.invokeLater(() -> {
        setupTable();
        setupFilters();
        loadCustomer();
        editButton();
        editCombobox();
        loadEditation(false);
    });
         btnReload.addActionListener(e -> {
             if (isEdit) {
                 loadCustomerToForm((int)tableCustomer.getValueAt(tableCustomer.getSelectedRow(),0));
             } else {
                 resetForm();
             }
         });
         btnSave.addActionListener(e -> SaveCustomer());
         
    }
    private void Load()
    {
        System.out.print("gggg");
    }
    private void setupTable() {
        Object[] columnNames = {"Mã KH", "Họ Tên", "Điểm tích lũy","Giao dịch"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableCustomer.setModel(model);
        JTableHeader header = tableCustomer.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
                tableCustomer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                       @Override
                       public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            int sel = tableCustomer.getSelectedRow();
                            if (sel < 0) return;    
                            int id = Integer.parseInt(model.getValueAt(sel, 0).toString());
                            loadEditation(false);
                            loadCustomerToForm(id);
                        }}
                       });
                
               
//      
        DefaultTableCellRenderer resetRenderer = new DefaultTableCellRenderer();
        resetRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableCustomer.getColumn("Mã KH").setCellRenderer(resetRenderer);
        tableCustomer.getColumn("Điểm tích lũy").setCellRenderer(resetRenderer);
        
        resetRenderer = new DefaultTableCellRenderer();
        resetRenderer.setHorizontalAlignment(JLabel.CENTER);
        resetRenderer.setForeground(Color.RED);
        resetRenderer.setFont(new Font("Arial", Font.BOLD, 12));
        tableCustomer.getColumn("Giao dịch").setCellRenderer(resetRenderer);
        
    }
    
    private void setupFilters() {
        // sort combo
        cbbSort.removeAllItems();
        cbbSort.addItem("Mã khách hàng");
        cbbSort.addItem("Họ và tên");
        cbbSort.addItem("Điểm tích lũy");
        ItemListener reloadListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadCustomer();
            }
        };
        cbbSort.addItemListener(reloadListener);
        tfSeacrh.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {loadCustomer();}
            public void removeUpdate(DocumentEvent e) {loadCustomer();}
            public void changedUpdate(DocumentEvent e) { loadCustomer();}
        });
    }
    
    private void loadCustomer() {
        model.setRowCount(0);
        String orderCol =" order by diemTichLuy asc";
        if(cbbSort.getSelectedIndex()==0) orderCol =" order by maKhachHang asc";
        else if(cbbSort.getSelectedIndex()==1) orderCol =" order by hoTen asc ";
        String sql = " where hoTen Like N'%"+tfSeacrh.getText()+"%' "+orderCol;
        for(KhachHang x : KH.getAll_condition(sql))
        {
            model.addRow(new Object[]{
                        x.getMaKhachHang(),
                        x.getHoTen(),
                        x.getDiemTichLuy(),
                        "Xem"
                    });
        }
        tableCustomer.updateUI();
    }

    private void loadCustomerToForm(int cusID) {
        KhachHang k = KH.findById(cusID);
        tfID.setText(String.valueOf(cusID));
        tfName.setText(k.getHoTen());
        
        String[] dt = k.getNgayTao().split(" ")[0].split("-");
        tfCreateDate.setText(dt[2]+"/"+dt[1]+"/"+dt[0]);
        tfPhone.setText(k.getSoDienThoai());
        tfEmail.setText(k.getEmail());
        tfAdress.setText(k.getDiaChi());
        tfPoint.setText(k.getDiemTichLuy().toString());
    }

    private void resetForm() {
        tfID.setText(String.valueOf(KH.generateNewClientId()));
        tfName.setText("");
        tfEmail.setText("");
        tfAdress.setText("");
        tfPhone.setText("");
        tfPoint.setText("0");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfCreateDate.setText(now.format(formatter));
    }
    private void loadEditation(boolean e)
    {
        tfID.setEditable(false);
        tfName.setEditable(e);
        tfEmail.setEditable(e);
        tfPhone.setEditable(e);
        tfAdress.setEditable(e);
        tfCreateDate.setEditable(false);
        tfPoint.setEditable(false);
        btnSave.setEnabled(e);
        btnReload.setEnabled(e);
        
    }
    private void SaveCustomer()
    {
        if(isAdd || isEdit)
        {
            if(tfName.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Họ và tên không dược trống","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String rePhone = "^(03|05|07|08|09)\\d{8}$";
            String phone = tfPhone.getText().trim();
            if (!phone.matches(rePhone)) {
                JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(KH.checkPhone(phone,Integer.parseInt(tfID.getText()))!=0)
            {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại trong dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String reEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
            String email = tfEmail.getText().trim();
            if (!email.matches(reEmail)) {
                JOptionPane.showMessageDialog(null, "Email không đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(KH.checkEmail(email,Integer.parseInt(tfID.getText()))!=0)
            {
                JOptionPane.showMessageDialog(null, "Địa chỉ email đã tồn tại trong dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(tfAdress.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Địa chỉ không dược trống","",JOptionPane.ERROR_MESSAGE);
                return;
            }
         
            KhachHang s = new KhachHang();
            s.setMaKhachHang(Integer.parseInt(tfID.getText()));
            s.setHoTen(tfName.getText());
            s.setEmail(tfEmail.getText());
            s.setSoDienThoai(tfPhone.getText());
            s.setDiaChi(tfAdress.getText());
            s.setDiemTichLuy(0);
            if(isEdit)
            {
                KH.update(s);
                JOptionPane.showMessageDialog(null,"Cập nhật thông tin khách hàng thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(isAdd)
            {
                KH.insert(s);
                JOptionPane.showMessageDialog(null,"Thêm khách hàng mới thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            loadCustomer();
        }
    }
   private void ShowBill(int id) {

        JDialog dialog = new JDialog(this, "Chi tiết giao dịch", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        String[] columnNames = {"Ngày", "Sản phẩm", "Số lượng"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String x : KH.getSaleByID(id)) {
            model.addRow(new Object[]{x.split("#")[0],x.split("#")[1],x.split("#")[2]});
        }

        JTable table = new JTable(model);
        table.setRowHeight(40);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMain = new javax.swing.JPanel();
        pnCRUD = new javax.swing.JPanel();
        lbTitleList_client = new javax.swing.JLabel();
        btnExportFIle = new javax.swing.JButton();
        pnCategori = new javax.swing.JPanel();
        cbbSort = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        tfSeacrh = new javax.swing.JTextField();
        ScrollPane = new javax.swing.JScrollPane();
        tableCustomer = new javax.swing.JTable();
        pnClientEdit = new javax.swing.JPanel();
        lbTitleList_client1 = new javax.swing.JLabel();
        jlabel6 = new javax.swing.JLabel();
        tfAdress = new javax.swing.JTextField();
        jlabel7 = new javax.swing.JLabel();
        jlabel8 = new javax.swing.JLabel();
        jlabel9 = new javax.swing.JLabel();
        tfID = new javax.swing.JTextField();
        tfName = new javax.swing.JTextField();
        tfPhone = new javax.swing.JTextField();
        tfEmail = new javax.swing.JTextField();
        jlabel10 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jlabel11 = new javax.swing.JLabel();
        tfCreateDate = new javax.swing.JTextField();
        tfPoint = new javax.swing.JTextField();
        jlabel12 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();

        setResizable(false);
        setSize(new java.awt.Dimension(1400, 830));
        getContentPane().setLayout(null);

        pnMain.setBackground(new java.awt.Color(245, 247, 250));
        pnMain.setPreferredSize(new java.awt.Dimension(1180, 720));
        pnMain.setLayout(null);

        pnCRUD.setBackground(new java.awt.Color(255, 255, 255));
        pnCRUD.setForeground(new java.awt.Color(51, 51, 51));
        pnCRUD.setLayout(null);

        lbTitleList_client.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitleList_client.setForeground(new java.awt.Color(153, 0, 153));
        lbTitleList_client.setText("KHÁCH HÀNG");
        pnCRUD.add(lbTitleList_client);
        lbTitleList_client.setBounds(10, 8, 140, 22);

        btnExportFIle.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnExportFIle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/file-import.png"))); // NOI18N
        btnExportFIle.setText("Xuất file");
        btnExportFIle.setBorder(null);
        pnCRUD.add(btnExportFIle);
        btnExportFIle.setBounds(1030, 4, 130, 30);

        pnMain.add(pnCRUD);
        pnCRUD.setBounds(0, 0, 1180, 40);

        pnCategori.setBackground(new java.awt.Color(255, 255, 255));
        pnCategori.setForeground(new java.awt.Color(51, 51, 51));
        pnCategori.setLayout(null);

        cbbSort.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã khách hàng", "Họ và tên", "Điểm tich lũy", " " }));
        pnCategori.add(cbbSort);
        cbbSort.setBounds(500, 10, 160, 30);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Sắp xếp theo");
        pnCategori.add(jLabel1);
        jLabel1.setBounds(410, 10, 80, 30);

        tfSeacrh.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tfSeacrh.setVerifyInputWhenFocusTarget(false);
        tfSeacrh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSeacrhActionPerformed(evt);
            }
        });
        pnCategori.add(tfSeacrh);
        tfSeacrh.setBounds(10, 10, 330, 30);

        tableCustomer.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tableCustomer.setRowHeight(40);
        tableCustomer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCustomerMouseClicked(evt);
            }
        });
        ScrollPane.setViewportView(tableCustomer);

        pnCategori.add(ScrollPane);
        ScrollPane.setBounds(10, 60, 650, 560);

        pnClientEdit.setBackground(new java.awt.Color(255, 255, 255));
        pnClientEdit.setLayout(null);

        lbTitleList_client1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitleList_client1.setForeground(new java.awt.Color(40, 93, 179));
        lbTitleList_client1.setText("THÔNG TIN KHÁCH HÀNG");
        pnClientEdit.add(lbTitleList_client1);
        lbTitleList_client1.setBounds(120, 0, 270, 40);

        jlabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel6.setText("Địa chỉ:");
        pnClientEdit.add(jlabel6);
        jlabel6.setBounds(20, 360, 170, 22);

        tfAdress.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnClientEdit.add(tfAdress);
        tfAdress.setBounds(20, 380, 440, 40);

        jlabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel7.setText("Mã khách hàng:");
        pnClientEdit.add(jlabel7);
        jlabel7.setBounds(20, 50, 170, 22);

        jlabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel8.setText("Họ tên khách hàng:");
        pnClientEdit.add(jlabel8);
        jlabel8.setBounds(20, 130, 200, 22);

        jlabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel9.setText("Điện thoại liên lạc:");
        pnClientEdit.add(jlabel9);
        jlabel9.setBounds(20, 210, 170, 22);

        tfID.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfID.setText("KH001");
        tfID.setEnabled(false);
        pnClientEdit.add(tfID);
        tfID.setBounds(20, 70, 440, 40);

        tfName.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnClientEdit.add(tfName);
        tfName.setBounds(20, 150, 440, 40);

        tfPhone.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnClientEdit.add(tfPhone);
        tfPhone.setBounds(20, 230, 440, 40);

        tfEmail.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnClientEdit.add(tfEmail);
        tfEmail.setBounds(20, 310, 440, 40);

        jlabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel10.setText("Email:");
        pnClientEdit.add(jlabel10);
        jlabel10.setBounds(20, 290, 170, 22);

        btnSave.setBackground(new java.awt.Color(51, 153, 0));
        btnSave.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/folder.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSave.setBorderPainted(false);
        btnSave.setFocusPainted(false);
        btnSave.setFocusable(false);
        btnSave.setPreferredSize(new java.awt.Dimension(230, 30));
        pnClientEdit.add(btnSave);
        btnSave.setBounds(350, 570, 110, 40);

        btnReload.setBackground(new java.awt.Color(255, 204, 0));
        btnReload.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnReload.setForeground(new java.awt.Color(255, 255, 255));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/reload.png"))); // NOI18N
        btnReload.setText("Làm mới");
        btnReload.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReload.setBorderPainted(false);
        btnReload.setFocusPainted(false);
        btnReload.setFocusable(false);
        btnReload.setPreferredSize(new java.awt.Dimension(230, 30));
        pnClientEdit.add(btnReload);
        btnReload.setBounds(240, 570, 100, 40);

        btnAdd.setBackground(new java.awt.Color(153, 204, 255));
        btnAdd.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/role.png"))); // NOI18N
        btnAdd.setText("Tạo");
        btnAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setFocusable(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(230, 30));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnClientEdit.add(btnAdd);
        btnAdd.setBounds(20, 570, 100, 40);

        jlabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel11.setText("Ngày tạo");
        pnClientEdit.add(jlabel11);
        jlabel11.setBounds(20, 500, 170, 22);

        tfCreateDate.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnClientEdit.add(tfCreateDate);
        tfCreateDate.setBounds(20, 520, 440, 40);

        tfPoint.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnClientEdit.add(tfPoint);
        tfPoint.setBounds(20, 450, 440, 40);

        jlabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel12.setText("Điểm tích lũy");
        pnClientEdit.add(jlabel12);
        jlabel12.setBounds(20, 430, 170, 22);

        btnEdit.setBackground(new java.awt.Color(255, 51, 51));
        btnEdit.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/friends.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnEdit.setBorderPainted(false);
        btnEdit.setFocusPainted(false);
        btnEdit.setFocusable(false);
        btnEdit.setPreferredSize(new java.awt.Dimension(230, 30));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnClientEdit.add(btnEdit);
        btnEdit.setBounds(130, 570, 100, 40);

        pnCategori.add(pnClientEdit);
        pnClientEdit.setBounds(680, 10, 480, 620);

        pnMain.add(pnCategori);
        pnCategori.setBounds(0, 50, 1180, 630);

        getContentPane().add(pnMain);
        pnMain.setBounds(0, 0, 1180, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfSeacrhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSeacrhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSeacrhActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        isEdit = isShow = false;
        isAdd = true;
        loadEditation(true);
        resetForm();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int row = tableCustomer.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 nhân viên để sửa");
            return;
        }
        isAdd = isShow = false;
        isEdit = true;

        loadCustomerToForm(Integer.parseInt(tableCustomer.getValueAt(tableCustomer.getSelectedRow(),0).toString()));
        loadEditation(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void tableCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCustomerMouseClicked
        // TODO add your handling code here:
        if(tableCustomer.getSelectedColumn()==3 && tableCustomer.getSelectedRow()>=0)
        {
            int maKH = Integer.parseInt(tableCustomer.getValueAt(tableCustomer.getSelectedRow(), 0).toString());
            ShowBill(maKH);
        }
    }//GEN-LAST:event_tableCustomerMouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(customer_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(customer_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(customer_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(customer_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new customer_AdminScreen(new mainAdminScreen()).setVisible(true);
            }
        });
    }
    void LoadScreen()
    {
        loadCustomer();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExportFIle;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbbSort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jlabel10;
    private javax.swing.JLabel jlabel11;
    private javax.swing.JLabel jlabel12;
    private javax.swing.JLabel jlabel6;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JLabel jlabel8;
    private javax.swing.JLabel jlabel9;
    private javax.swing.JLabel lbTitleList_client;
    private javax.swing.JLabel lbTitleList_client1;
    private javax.swing.JPanel pnCRUD;
    private javax.swing.JPanel pnCategori;
    private javax.swing.JPanel pnClientEdit;
    private javax.swing.JPanel pnMain;
    private javax.swing.JTable tableCustomer;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfCreateDate;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfID;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextField tfPoint;
    private javax.swing.JTextField tfSeacrh;
    // End of variables declaration//GEN-END:variables

     private void editCombobox() {
        cbbSort.setBackground(Color.WHITE);
    }

    private void editButton() {
        btnExportFIle.setBackground(Color.WHITE);
    }

}
