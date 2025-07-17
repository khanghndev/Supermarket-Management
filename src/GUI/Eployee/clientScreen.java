/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Eployee;

import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modal.HoaDon;
import modal.KhachHang;


public class clientScreen extends javax.swing.JFrame {

    private DefaultTableModel model;
    private boolean isAdding = false;
    private boolean isEditing = false;
    KhachHangDAO KH = new KhachHangDAO();
    mainScreen ms;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form productFrame
     */
    public clientScreen(mainScreen m) {
        initComponents();
        this.ms = m;
        loadScreen();
    }

    private void loadClients() {
        model = (DefaultTableModel) tableClient.getModel();
        model.setRowCount(0);
        tableClient.clearSelection();  
        List<KhachHang> DSKH = KH.findByName(tfSeacrh.getText());
        for (KhachHang x : DSKH)
            model.addRow(new Object[]{ x.getMaKhachHang(), x.getHoTen(), x.getDiemTichLuy(), x.getSoDienThoai() });
    }
    private void loadClientDetails(int id) {
        
        KhachHang k = KH.findById(id);
        tfMaKH.setText(String.valueOf(id));
        tfTenKH.setText(k.getHoTen());
        tfSDT.setText(k.getSoDienThoai());
        tfEmail.setText(k.getEmail());
        tfDiemtichluy.setText(k.getDiemTichLuy().toString());
        tfDiachi.setText(k.getDiaChi());
        String[] Ngay = k.getNgayTao().split(" ")[0].split("-");
        String dt = Ngay[2]+"/"+Ngay[1]+"/"+Ngay[0];
        tfNgaytao.setText(dt);
    }


    private void setDetailEnabled(boolean en) {
        tfMaKH.setEnabled(false);
        tfTenKH.setEditable(en);
        tfSDT.setEditable(en);
        tfEmail.setEditable(en);
        tfDiemtichluy.setEditable(false);
        tfDiachi.setEditable(en);
        tfNgaytao.setEnabled(false);
        btnSave.setEnabled(en);
        btnReload.setEnabled(en);
    }
    private void addCustomer()
    {
        isAdding = true;
        isEditing = false;
        String newId = String.valueOf(KH.generateNewClientId());
        tfMaKH.setText(newId);
        tfTenKH.setText("");
        tfSDT.setText("");
        tfEmail.setText("");
        tfDiachi.setText("");
        tfNgaytao.setText(sdf.format(new Date()));
        tfDiemtichluy.setText("0");
        setDetailEnabled(true);
        tfTenKH.requestFocus();
    }
    private void editCustomer()
    {
        int row = tableClient.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chọn 1 khách hàng để sửa");
                return;
            }
            isAdding = false;
            isEditing = true;
            setDetailEnabled(true);
    }
    private void saveCustomer()
    {
        int idC = Integer.parseInt(tfMaKH.getText().trim());
        if(check(idC))
        {
            KhachHang k = new KhachHang();
            k.setMaKhachHang(idC);
            k.setHoTen(tfTenKH.getText());
            k.setEmail(tfEmail.getText());
            k.setSoDienThoai(tfSDT.getText());
            k.setDiaChi(tfDiachi.getText());
            if (isAdding) 
            {
               KH.insert(k);
               JOptionPane.showMessageDialog(null,"Thêm thành công");
                } 
            else if (isEditing) {
                    KH.update(k);
                    JOptionPane.showMessageDialog(null,"Chỉnh sửa thành công");
                }
            isAdding = isEditing = false;
            setDetailEnabled(false);
            loadClientDetails(idC);
            loadClients();
        }
    }
    private void removeCustomer(int idC)
    {
        if(!isAdding && !isEditing)
        {
            HoaDonDAO HD = new HoaDonDAO();
            List<HoaDon> ds = HD.selectAll();
            for(HoaDon x : ds)
            {
                if(x.getMaKhachHang()==idC)
                {
                    JOptionPane.showMessageDialog(null,"Không thể thực hiện xóa do khách hàng đã từng thực hiện giao dịch","",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (JOptionPane.showConfirmDialog(this,"Xóa thông tin khách hàng?","Xác nhận",JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
            KH.delete(idC);
            JOptionPane.showMessageDialog(null,"Xóa thành công","",JOptionPane.INFORMATION_MESSAGE);
            loadClients();
            addCustomer();
            setDetailEnabled(false);
            
        }
        else JOptionPane.showMessageDialog(null,"Vui lòng thoát khỏi chỉnh sửa hoặc thêm để thực hiện xóa","",JOptionPane.WARNING_MESSAGE);
    }
    private boolean check(int idC)
    {
        String name = tfTenKH.getText().trim();
        if(name.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Tên không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String rePhone = "^(03|05|07|08|09)\\d{8}$";
        String phone = tfSDT.getText().trim();
        if (!phone.matches(rePhone)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(KH.checkPhone(phone,idC)!=0)
        {
            JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại trong dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String reEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        String email = tfEmail.getText().trim();
        if (!email.matches(reEmail)) {
            JOptionPane.showMessageDialog(null, "Email không đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(KH.checkEmail(email,idC)!=0)
        {
            JOptionPane.showMessageDialog(null, "Địa chỉ email đã tồn tại trong dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
        lbTitle = new javax.swing.JLabel();
        pnSearch = new javax.swing.JPanel();
        tfSeacrh = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        pnClient = new javax.swing.JPanel();
        lbTitleList_client = new javax.swing.JLabel();
        pnDetail = new javax.swing.JPanel();
        lbTitlleDetail = new javax.swing.JLabel();
        tfMaKH = new javax.swing.JTextField();
        tfDiemtichluy = new javax.swing.JTextField();
        tfTenKH = new javax.swing.JTextField();
        tfEmail = new javax.swing.JTextField();
        jlabel12 = new javax.swing.JLabel();
        jlabel7 = new javax.swing.JLabel();
        jlabel8 = new javax.swing.JLabel();
        jlabel24 = new javax.swing.JLabel();
        jlabel9 = new javax.swing.JLabel();
        jlabel11 = new javax.swing.JLabel();
        tfDiachi = new javax.swing.JTextField();
        btnIMG = new javax.swing.JButton();
        tfSDT = new javax.swing.JTextField();
        lbSuggest = new javax.swing.JLabel();
        jlabel13 = new javax.swing.JLabel();
        tfNgaytao = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        lbAnhdaidien = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableClient = new javax.swing.JTable();

        setResizable(false);
        setSize(new java.awt.Dimension(1400, 830));
        getContentPane().setLayout(null);

        pnMain.setBackground(new java.awt.Color(245, 247, 250));
        pnMain.setPreferredSize(new java.awt.Dimension(1180, 720));
        pnMain.setLayout(null);

        pnCRUD.setBackground(new java.awt.Color(255, 255, 255));
        pnCRUD.setForeground(new java.awt.Color(51, 51, 51));
        pnCRUD.setLayout(null);

        lbTitle.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(40, 93, 179));
        lbTitle.setText("TÌM KIẾM THEO TÊN");
        pnCRUD.add(lbTitle);
        lbTitle.setBounds(20, 10, 190, 40);

        pnSearch.setBackground(new java.awt.Color(255, 255, 255));
        pnSearch.setLayout(null);

        tfSeacrh.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tfSeacrh.setPreferredSize(new java.awt.Dimension(500, 26));
        tfSeacrh.setVerifyInputWhenFocusTarget(false);
        pnSearch.add(tfSeacrh);
        tfSeacrh.setBounds(50, 0, 670, 40);

        pnCRUD.add(pnSearch);
        pnSearch.setBounds(160, 10, 760, 40);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/bin.png"))); // NOI18N
        btnDelete.setBorder(null);
        btnDelete.setBorderPainted(false);
        btnDelete.setFocusPainted(false);
        btnDelete.setFocusable(false);
        pnCRUD.add(btnDelete);
        btnDelete.setBounds(1100, 10, 40, 40);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/add.png"))); // NOI18N
        btnAdd.setBorder(null);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setFocusable(false);
        pnCRUD.add(btnAdd);
        btnAdd.setBounds(1050, 10, 40, 40);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/pen.png"))); // NOI18N
        btnEdit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 204, 255), 1, true));
        btnEdit.setBorderPainted(false);
        btnEdit.setFocusPainted(false);
        btnEdit.setFocusable(false);
        pnCRUD.add(btnEdit);
        btnEdit.setBounds(1000, 10, 40, 40);

        pnMain.add(pnCRUD);
        pnCRUD.setBounds(0, 0, 1160, 60);

        pnClient.setBackground(new java.awt.Color(255, 255, 255));
        pnClient.setLayout(null);

        lbTitleList_client.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitleList_client.setForeground(new java.awt.Color(40, 93, 179));
        lbTitleList_client.setText("DANH SÁCH KHÁCH HÀNG");
        pnClient.add(lbTitleList_client);
        lbTitleList_client.setBounds(20, 10, 270, 40);

        pnDetail.setBackground(new java.awt.Color(235, 249, 248));

        lbTitlleDetail.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbTitlleDetail.setForeground(new java.awt.Color(40, 93, 179));
        lbTitlleDetail.setText("THÔNG TIN CHI TIẾT");

        tfMaKH.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfMaKH.setBorder(null);

        tfDiemtichluy.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfDiemtichluy.setForeground(new java.awt.Color(153, 0, 0));
        tfDiemtichluy.setBorder(null);

        tfTenKH.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfTenKH.setBorder(null);

        tfEmail.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfEmail.setBorder(null);

        jlabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel12.setText("Số điện thoại");

        jlabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel7.setText("Mã khách hàng");

        jlabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel8.setText("Tên khách hàng");

        jlabel24.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel24.setText("Điểm tích lũy");

        jlabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel9.setText("Email");

        jlabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel11.setText("Địa chỉ");

        tfDiachi.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfDiachi.setBorder(null);

        btnIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/user.png"))); // NOI18N
        btnIMG.setToolTipText("");

        tfSDT.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfSDT.setBorder(null);

        lbSuggest.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        lbSuggest.setForeground(new java.awt.Color(153, 0, 51));

        jlabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel13.setText("Ngày tạo");

        tfNgaytao.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfNgaytao.setBorder(null);

        btnSave.setBackground(new java.awt.Color(51, 153, 0));
        btnSave.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/folder.png"))); // NOI18N
        btnSave.setText("Lưu dữ liệu");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSave.setBorderPainted(false);
        btnSave.setEnabled(false);
        btnSave.setFocusPainted(false);
        btnSave.setFocusable(false);
        btnSave.setPreferredSize(new java.awt.Dimension(230, 30));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReload.setBackground(new java.awt.Color(255, 204, 0));
        btnReload.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnReload.setForeground(new java.awt.Color(255, 255, 255));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/reload.png"))); // NOI18N
        btnReload.setText("Khôi phục");
        btnReload.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReload.setBorderPainted(false);
        btnReload.setEnabled(false);
        btnReload.setFocusPainted(false);
        btnReload.setFocusable(false);
        btnReload.setPreferredSize(new java.awt.Dimension(230, 30));

        lbAnhdaidien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbAnhdaidien.setForeground(new java.awt.Color(51, 51, 255));
        lbAnhdaidien.setText("Ảnh đại diện");

        javax.swing.GroupLayout pnDetailLayout = new javax.swing.GroupLayout(pnDetail);
        pnDetail.setLayout(pnDetailLayout);
        pnDetailLayout.setHorizontalGroup(
            pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDetailLayout.createSequentialGroup()
                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnDetailLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(lbTitlleDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnDetailLayout.createSequentialGroup()
                            .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnDetailLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jlabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(pnDetailLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jlabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(18, 18, 18)
                            .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfDiachi, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(tfTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(tfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(tfDiemtichluy, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(tfSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(tfMaKH)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnDetailLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jlabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnDetailLayout.createSequentialGroup()
                                    .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbSuggest)
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                    .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(tfNgaytao))))
                    .addGroup(pnDetailLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(btnIMG, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDetailLayout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(lbAnhdaidien)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pnDetailLayout.setVerticalGroup(
            pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDetailLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbTitlleDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIMG, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAnhdaidien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createSequentialGroup()
                        .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel7)
                            .addComponent(tfMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel8)
                            .addComponent(tfTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel9)
                            .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel24)
                            .addComponent(tfDiemtichluy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel11)
                            .addComponent(tfDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel13)
                            .addComponent(tfNgaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addComponent(lbSuggest)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pnClient.add(pnDetail);
        pnDetail.setBounds(660, 30, 500, 620);

        tableClient.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tableClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableClient.setRowHeight(40);
        jScrollPane1.setViewportView(tableClient);

        pnClient.add(jScrollPane1);
        jScrollPane1.setBounds(30, 52, 590, 550);

        pnMain.add(pnClient);
        pnClient.setBounds(0, 80, 1160, 620);

        getContentPane().add(pnMain);
        pnMain.setBounds(0, 0, 1180, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(clientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(clientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(clientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(clientScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new clientScreen(new mainScreen()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnIMG;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlabel11;
    private javax.swing.JLabel jlabel12;
    private javax.swing.JLabel jlabel13;
    private javax.swing.JLabel jlabel24;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JLabel jlabel8;
    private javax.swing.JLabel jlabel9;
    private javax.swing.JLabel lbAnhdaidien;
    private javax.swing.JLabel lbSuggest;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTitleList_client;
    private javax.swing.JLabel lbTitlleDetail;
    private javax.swing.JPanel pnCRUD;
    private javax.swing.JPanel pnClient;
    private javax.swing.JPanel pnDetail;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnSearch;
    private javax.swing.JTable tableClient;
    private javax.swing.JTextField tfDiachi;
    private javax.swing.JTextField tfDiemtichluy;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfMaKH;
    private javax.swing.JTextField tfNgaytao;
    private javax.swing.JTextField tfSDT;
    private javax.swing.JTextField tfSeacrh;
    private javax.swing.JTextField tfTenKH;
    // End of variables declaration//GEN-END:variables


    private void loadScreen() {
        editTable();
        loadClients();
        setDetailEnabled(false);
        tableClient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                   @Override
                   public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int sel = tableClient.getSelectedRow();
                        if (sel < 0) return;   
                        int id = Integer.parseInt(model.getValueAt(sel, 0).toString());
                        loadClientDetails(id);
                    }}
                   });
        btnAdd.addActionListener(e -> {addCustomer();});
        btnEdit.addActionListener(e -> {editCustomer();});
        btnSave.addActionListener(e -> {saveCustomer();});
        btnDelete.addActionListener(e -> {removeCustomer(Integer.parseInt(tableClient.getValueAt(tableClient.getSelectedRow(), 0).toString()));});
        btnReload.addActionListener(e -> {
            if (isAdding) addCustomer();
            else if (isEditing) loadClientDetails(Integer.parseInt(tfMaKH.getText()));
        });
        tfSeacrh.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {loadClients();}
            public void removeUpdate(DocumentEvent e) {loadClients();}
            public void changedUpdate(DocumentEvent e) { loadClients();}
        });

    }

    private void editTable() {
         JTableHeader header = tableClient.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableClient.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        Object[][] data = {};
        Object[] columnNames = {"Mã","Họ tên", "Điểm","SĐT"};
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableClient.setModel(model);
    }

}
