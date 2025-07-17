/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Admin;

import DAO.LoaiSanPhamDAO;
import DAO.NhaCungCapDAO;
import DAO.SanPhamDAO;
import MODAL.LoaiSanPham;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import modal.NhaCungCap;
import modal.SanPham;

/**
 *
 * @author Minh
 */
public class product_AdminScreen extends javax.swing.JFrame {

    private DefaultTableModel model;
    mainAdminScreen ms;
    SanPhamDAO SP = new SanPhamDAO();
    NhaCungCapDAO NCC = new NhaCungCapDAO();
    LoaiSanPhamDAO LSP = new LoaiSanPhamDAO();
    /**
     * Creates new form productFrame
     */
    public product_AdminScreen(mainAdminScreen m) {
        initComponents();
        this.ms = m;
        setSize(new Dimension(1180, 720));
        loadScreen();

    }

    private void setupTable() {
        model = new DefaultTableModel( );
        tableProduct.setModel(model);

        JTableHeader header = tableProduct.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        tableProduct.setRowSelectionAllowed(true);
        tableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupFilters() {
        cbbCategory.insertItemAt("Tất cả", 0);
        cbbCategory.setSelectedIndex(0);
        ItemListener reloadListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadProducts();
            }
        };
        cbbCategory.addItemListener(reloadListener);
        cbbPrice.addItemListener(reloadListener);
        cbbSort.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {loadProducts();}});
        tfSeacrh.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {loadProducts();}
            public void removeUpdate(DocumentEvent e) {loadProducts();}
            public void changedUpdate(DocumentEvent e) { loadProducts();}
        });
    }

    private void loadProducts() 
    {
        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        model.setRowCount(0);

        String ten = "where tenSanPham Like '%" +tfSeacrh.getText() +"%'";
        String loai="";
        String gia ="";
        String sort = "";
        if (!"Tất cả".equals(cbbCategory.getSelectedItem())) {
            loai = " AND loaiSanPham = "+LSP.GetIDByName(cbbCategory.getSelectedItem().toString());
        }
        if (cbbPrice.getSelectedIndex()==1)gia = " AND gia < 10000";
        else if (cbbPrice.getSelectedIndex()==2) gia = " AND gia BETWEEN 10000 AND 100000";
        else if (cbbPrice.getSelectedIndex()==3) gia = " AND gia BETWEEN 100000 AND 200000";
        else if (cbbPrice.getSelectedIndex()==4) gia = " AND gia > 200000";
        if(cbbSort.getSelectedIndex()==0) sort = " ORDER by maSanPham desc";
        else if(cbbSort.getSelectedIndex()==1) sort = " ORDER by tenSanPham desc";
        else if(cbbSort.getSelectedIndex()==2) sort = " ORDER by gia desc";
        else if(cbbSort.getSelectedIndex()==3) sort = " ORDER by loaiSanPham desc";
        else sort = " ORDER by slTon desc";
        List<SanPham> list = SP.searchProducts(ten+loai+gia+sort);
        model.setRowCount(0);
        for(SanPham x : list)
        {
            model.addRow(new Object[]{
                   x.getMaSanPham(),
                   x.getTenSanPham(),
                  x.getLoaiSanPham(),
                    x.getGia(),
                    x.getSlTon()
                });
        }
       tableProduct.setModel(model);
    }
    private void loadCategories() {
        cbbCategory.removeAllItems();
        cbbLoaiSP.removeAllItems();
        cbbCategory.addItem("Tất cả");
        List<LoaiSanPham> ct = LSP.getAll();
       for(LoaiSanPham x : ct) {
                cbbCategory.addItem(x.getTenLoaiSanPham());
                cbbLoaiSP.addItem(x.getTenLoaiSanPham());
            }
    }
    private void loadSupplier() {
        cbbNhaC.removeAllItems();
        List<NhaCungCap> ct = NCC.getAll();
       for(NhaCungCap x : ct) {
                cbbNhaC.addItem(x.getTenNcc());
            }
    }
    
    private void Save()
    {
        if(isAdd || isEdit)
        {
            int gia=0;
            if(tfTenSP.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Tên sản phẩm không dược trống","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            gia = tfGia.getValue();
           if(gia>=900000000)
            {
                JOptionPane.showMessageDialog(null, "Giá không hợp lệ","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(tfMoTa.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Mô tả không dược trống","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(tfMoTa.getText().trim().length()>50)
            {
                JOptionPane.showMessageDialog(null, "Mô tả không vượt quá 50 ký tự","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            SanPham s = new SanPham();
            s.setMaSanPham(Integer.parseInt(tfMaSP.getText()));
            s.setTenSanPham(tfTenSP.getText());
            s.setGia((1.0*gia));
            s.setMoTa(tfMoTa.getText());
            s.setLoaiSanPham(LSP.GetIDByName(cbbLoaiSP.getSelectedItem().toString()));
            s.setMaNhaCungCap(NCC.getIDbyName(cbbNhaC.getSelectedItem().toString()));
            s.setSlTon(Integer.parseInt(tfSLTon.getText()));
            s.setTrangThai(cbbTrangThai.getSelectedItem().toString());
            if(tfAnh.getText().trim().length()!=0)
            {
                s.setAnh(tfAnh.getText().trim());
            }
            if(isEdit)
            {
                SP.update(s);
                JOptionPane.showMessageDialog(null,"Cập nhật thông tin sản phẩm thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(isAdd)
            {
                SP.insert(s);
                JOptionPane.showMessageDialog(null,"Tạo sản phẩm mới thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            isEdit = isAdd = false;
            loadEditation(false);
            loadProducts();
        }
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
        cbbSort = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        pnCategori = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbbPrice = new javax.swing.JComboBox<>();
        tfSeacrh = new javax.swing.JTextField();
        cbbCategory = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        pnDetail = new javax.swing.JPanel();
        lbTitlleDetail = new javax.swing.JLabel();
        tfMaSP = new javax.swing.JTextField();
        tfTenSP = new javax.swing.JTextField();
        jlabel12 = new javax.swing.JLabel();
        jlabel7 = new javax.swing.JLabel();
        jlabel8 = new javax.swing.JLabel();
        jlabel24 = new javax.swing.JLabel();
        jlabel9 = new javax.swing.JLabel();
        jlabel11 = new javax.swing.JLabel();
        jlabel18 = new javax.swing.JLabel();
        tfSLTon = new javax.swing.JTextField();
        tfGia = new com.toedter.components.JSpinField();
        cbbTrangThai = new javax.swing.JComboBox<>();
        cbbNhaC = new javax.swing.JComboBox<>();
        cbbLoaiSP = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        tfMoTa = new javax.swing.JTextField();
        jlabel10 = new javax.swing.JLabel();
        tfAnh = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        btnUp = new javax.swing.JButton();
        btnShowImage = new javax.swing.JButton();

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
        lbTitleList_client.setText("SẢN PHẨM");
        pnCRUD.add(lbTitleList_client);
        lbTitleList_client.setBounds(10, 8, 120, 22);

        btnExportFIle.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnExportFIle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/file-import.png"))); // NOI18N
        btnExportFIle.setText("Xuất file");
        btnExportFIle.setBorder(null);
        pnCRUD.add(btnExportFIle);
        btnExportFIle.setBounds(1040, 4, 130, 30);

        cbbSort.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã sản phẩm", "Tên sản phẩm", "Giá tiền", "Loại sản phẩm", "Số lượng tồn" }));
        pnCRUD.add(cbbSort);
        cbbSort.setBounds(850, 3, 160, 30);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Sắp xếp theo");
        pnCRUD.add(jLabel2);
        jLabel2.setBounds(770, 10, 80, 20);

        pnMain.add(pnCRUD);
        pnCRUD.setBounds(0, 0, 1180, 40);

        pnCategori.setBackground(new java.awt.Color(255, 255, 255));
        pnCategori.setForeground(new java.awt.Color(51, 51, 51));
        pnCategori.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Tìm kiếm theo tên, loại, giá");
        pnCategori.add(jLabel1);
        jLabel1.setBounds(20, 10, 230, 30);

        cbbPrice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbPrice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Dưới 10.000đ", "10.000 - 100.000đ", "100.000 - 200.000đ", "Trên 200.000đ" }));
        pnCategori.add(cbbPrice);
        cbbPrice.setBounds(890, 10, 260, 40);

        tfSeacrh.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tfSeacrh.setVerifyInputWhenFocusTarget(false);
        tfSeacrh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSeacrhActionPerformed(evt);
            }
        });
        pnCategori.add(tfSeacrh);
        tfSeacrh.setBounds(260, 10, 350, 40);

        cbbCategory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cbbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nước uống", "Bia rượu", "Thực phẩm", "..." }));
        pnCategori.add(cbbCategory);
        cbbCategory.setBounds(620, 10, 260, 40);

        tableProduct.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        tableProduct.setRowHeight(40);
        jScrollPane1.setViewportView(tableProduct);

        pnCategori.add(jScrollPane1);
        jScrollPane1.setBounds(20, 70, 620, 540);

        pnDetail.setBackground(new java.awt.Color(255, 255, 255));
        pnDetail.setLayout(null);

        lbTitlleDetail.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbTitlleDetail.setForeground(new java.awt.Color(40, 93, 179));
        lbTitlleDetail.setText("THÔNG TIN CHI TIẾT");
        pnDetail.add(lbTitlleDetail);
        lbTitlleDetail.setBounds(6, 10, 210, 30);

        tfMaSP.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfMaSP.setText("SP001");
        tfMaSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnDetail.add(tfMaSP);
        tfMaSP.setBounds(190, 70, 300, 30);

        tfTenSP.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfTenSP.setText("C2 Chanh");
        tfTenSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnDetail.add(tfTenSP);
        tfTenSP.setBounds(190, 120, 300, 30);

        jlabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel12.setText("Loại sản phẩm");
        pnDetail.add(jlabel12);
        jlabel12.setBounds(20, 170, 150, 30);

        jlabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel7.setText("Mã sản phẩm");
        pnDetail.add(jlabel7);
        jlabel7.setBounds(20, 70, 146, 22);

        jlabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel8.setText("Tên sản phẩm");
        pnDetail.add(jlabel8);
        jlabel8.setBounds(20, 120, 150, 22);

        jlabel24.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel24.setText("Số lượng tồn");
        pnDetail.add(jlabel24);
        jlabel24.setBounds(20, 270, 150, 30);

        jlabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel9.setText("Giá bán");
        pnDetail.add(jlabel9);
        jlabel9.setBounds(20, 230, 150, 22);

        jlabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel11.setText("Trạng thái");
        pnDetail.add(jlabel11);
        jlabel11.setBounds(20, 380, 150, 20);

        jlabel18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel18.setText("Nhà cung cấp");
        pnDetail.add(jlabel18);
        jlabel18.setBounds(20, 310, 150, 50);

        tfSLTon.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfSLTon.setText("90");
        tfSLTon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnDetail.add(tfSLTon);
        tfSLTon.setBounds(190, 270, 300, 30);

        tfGia.setMinimum(0);
        tfGia.setName("0"); // NOI18N
        pnDetail.add(tfGia);
        tfGia.setBounds(190, 220, 300, 34);

        cbbTrangThai.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kinh doanh", "Ngừng bán", "Hết hàng" }));
        pnDetail.add(cbbTrangThai);
        cbbTrangThai.setBounds(190, 370, 300, 34);

        cbbNhaC.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cbbNhaC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnDetail.add(cbbNhaC);
        cbbNhaC.setBounds(190, 320, 300, 34);

        cbbLoaiSP.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnDetail.add(cbbLoaiSP);
        cbbLoaiSP.setBounds(190, 170, 300, 32);

        btnSave.setBackground(new java.awt.Color(51, 153, 0));
        btnSave.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/folder.png"))); // NOI18N
        btnSave.setText("Lưu dữ liệu");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSave.setBorderPainted(false);
        btnSave.setFocusPainted(false);
        btnSave.setFocusable(false);
        btnSave.setPreferredSize(new java.awt.Dimension(230, 30));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        pnDetail.add(btnSave);
        btnSave.setBounds(350, 510, 140, 40);

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
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        pnDetail.add(btnReload);
        btnReload.setBounds(190, 510, 150, 40);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/add.png"))); // NOI18N
        btnAdd.setBorder(null);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setFocusable(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnDetail.add(btnAdd);
        btnAdd.setBounds(450, 10, 40, 40);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/pen.png"))); // NOI18N
        btnEdit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 204, 255), 1, true));
        btnEdit.setBorderPainted(false);
        btnEdit.setFocusPainted(false);
        btnEdit.setFocusable(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnDetail.add(btnEdit);
        btnEdit.setBounds(403, 10, 40, 40);

        tfMoTa.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfMoTa.setText("C2 Chanh");
        tfMoTa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnDetail.add(tfMoTa);
        tfMoTa.setBounds(190, 420, 300, 30);

        jlabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel10.setText("Mô tả");
        pnDetail.add(jlabel10);
        jlabel10.setBounds(20, 420, 150, 30);

        tfAnh.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnDetail.add(tfAnh);
        tfAnh.setBounds(190, 470, 250, 30);

        lb.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lb.setText("Ảnh");
        pnDetail.add(lb);
        lb.setBounds(20, 470, 150, 30);

        btnUp.setBackground(new java.awt.Color(204, 204, 204));
        btnUp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnUp.setText("Tải");
        btnUp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnUp.setBorderPainted(false);
        btnUp.setFocusPainted(false);
        btnUp.setFocusable(false);
        pnDetail.add(btnUp);
        btnUp.setBounds(440, 470, 50, 30);

        btnShowImage.setBackground(new java.awt.Color(102, 204, 255));
        btnShowImage.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnShowImage.setForeground(new java.awt.Color(255, 255, 255));
        btnShowImage.setText("Xem ảnh");
        btnShowImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnShowImage.setBorderPainted(false);
        btnShowImage.setFocusPainted(false);
        btnShowImage.setFocusable(false);
        btnShowImage.setPreferredSize(new java.awt.Dimension(230, 30));
        btnShowImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowImageActionPerformed(evt);
            }
        });
        pnDetail.add(btnShowImage);
        btnShowImage.setBounds(20, 510, 160, 40);

        pnCategori.add(pnDetail);
        pnDetail.setBounds(650, 60, 510, 630);

        pnMain.add(pnCategori);
        pnCategori.setBounds(0, 50, 1160, 640);

        getContentPane().add(pnMain);
        pnMain.setBounds(0, 0, 1170, 760);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfSeacrhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSeacrhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSeacrhActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        // TODO add your handling code here:
        if(isEdit) loadProductDetail(Integer.parseInt(tableProduct.getValueAt(tableProduct.getSelectedRow(), 0).toString()));
        else if(isAdd) loadNew();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int row = tableProduct.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chọn 1 sản phẩm để sửa");
                return;
            }
        
        loadProductDetail(Integer.parseInt(tableProduct.getValueAt(row,0).toString()));
        loadEditation(true);
        isAdd = isShow = false;
        isEdit = true;
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    
        isEdit = isShow = false;
        isAdd = true;
        loadEditation(true);
        loadNew();
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        Save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnShowImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowImageActionPerformed
        // TODO add your handling code here:
        if(tfAnh.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(null,"Không có ảnh để hiển thị","",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try
        {
            JDialog dialog = new JDialog(this, "Chi tiết sản phẩm", true); // 'true' = modal
            dialog.setSize(300, 300);
            dialog.setLocationRelativeTo(this); 
            URL imageUrl = getClass().getResource(tfAnh.getText());
            ImageIcon icon = new ImageIcon(imageUrl);
            Image img = icon.getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH);
            JButton btn = new JButton("");
            btn.setIcon(new ImageIcon(img));
            btn.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            btn.setContentAreaFilled(false);
            dialog.add(btn);
            dialog.setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Dữ liệu chưa được đồng bộ! Vui lòng đăng xuất khỏi ứng dụng để đồng bộ dữ liệu ảnh","",JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_btnShowImageActionPerformed

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
            java.util.logging.Logger.getLogger(product_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(product_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(product_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(product_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new product_AdminScreen(new mainAdminScreen()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExportFIle;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnShowImage;
    private javax.swing.JButton btnUp;
    private javax.swing.JComboBox<String> cbbCategory;
    private javax.swing.JComboBox<String> cbbLoaiSP;
    private javax.swing.JComboBox<String> cbbNhaC;
    private javax.swing.JComboBox<String> cbbPrice;
    private javax.swing.JComboBox<String> cbbSort;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlabel10;
    private javax.swing.JLabel jlabel11;
    private javax.swing.JLabel jlabel12;
    private javax.swing.JLabel jlabel18;
    private javax.swing.JLabel jlabel24;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JLabel jlabel8;
    private javax.swing.JLabel jlabel9;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbTitleList_client;
    private javax.swing.JLabel lbTitlleDetail;
    private javax.swing.JPanel pnCRUD;
    private javax.swing.JPanel pnCategori;
    private javax.swing.JPanel pnDetail;
    private javax.swing.JPanel pnMain;
    private javax.swing.JTable tableProduct;
    private javax.swing.JTextField tfAnh;
    private com.toedter.components.JSpinField tfGia;
    private javax.swing.JTextField tfMaSP;
    private javax.swing.JTextField tfMoTa;
    private javax.swing.JTextField tfSLTon;
    private javax.swing.JTextField tfSeacrh;
    private javax.swing.JTextField tfTenSP;
    // End of variables declaration//GEN-END:variables

    boolean isShow = true,isEdit = false,isAdd= false;
    
    private void loadScreen() {
        setResizable(false);
        editTable();
        editButton();
        editCombobox();
        loadCategories();
        setupFilters();
        loadProducts();
        loadNew();
        loadEditation(false);
        loadSupplier();
        loadCategories();
        
    }

     private void editCombobox() {
        // Set background trắng
        cbbCategory.setBackground(Color.WHITE);
        cbbPrice.setBackground(Color.WHITE);
        cbbSort.setBackground(Color.WHITE);
        cbbLoaiSP.setBackground(Color.WHITE);
        cbbNhaC.setBackground(Color.WHITE);
        cbbTrangThai.setBackground(Color.WHITE);  
    }
    

    private void editButton() {
        btnExportFIle.setBackground(Color.WHITE);
        btnUp.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file ảnh");

            // Chỉ chấp nhận các file ảnh (.jpg, .png, .gif)
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Ảnh JPEG, JPG, PNG, GIF","jpeg", "jpg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this); // Mở hộp thoại chọn file

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile(); // Lấy file đã chọn
                tfAnh.setText(file.getAbsolutePath()); // Hiển thị đường dẫn vào JTextField
                saveIMG();
            }
        });
    }

   private void editTable() {
    Object[] columnNames = { "Mã sản phẩm", "Tên", "Loại sản phẩm", "Giá","Tồn kho"};
    model = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tableProduct.setModel(model);
    JTableHeader header = tableProduct.getTableHeader();
    header.setFont(new Font("Arial", Font.BOLD, 14));
    header.setBackground(Color.LIGHT_GRAY);
    header.setForeground(Color.BLACK);
    ((DefaultTableCellRenderer) header.getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);
 tableProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                   @Override
                   public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int sel = tableProduct.getSelectedRow();
                        if (sel < 0) return;    
                        int id = Integer.parseInt(model.getValueAt(sel, 0).toString());
                        loadEditation(false);
                        loadProductDetail(id);
                    }}
                   });
   }
   
    private void loadNew()
    {
         tfMaSP.setText(String.valueOf(SP.getNewID()));
        tfTenSP.setText("");
        tfMoTa.setText("");
        tfGia.setValue(0);
        cbbLoaiSP.setSelectedIndex(0);
        cbbNhaC.setSelectedIndex(0);
        tfSLTon.setText("0");
        cbbTrangThai.setSelectedIndex(0);
        tfAnh.setText(null);
    }
    private void loadEditation(boolean e)
    {
        tfMaSP.setEditable(false);
        tfTenSP.setEditable(e);
        tfMoTa.setEditable(e);
        tfAnh.setEditable(false);
        btnUp.setEnabled(e);
        btnShowImage.setEnabled(!e);
    }
    private void loadProductDetail(int id)
    {
        isAdd = isEdit = false;
        isShow = true;
        SanPham S = SP.findById(id);
        tfMaSP.setText(S.getMaSanPham().toString());
        tfTenSP.setText(S.getTenSanPham());
        tfMoTa.setText(S.getMoTa());
        int g = (int)(S.getGia()*1);
        tfGia.setValue(g);
        NhaCungCap ncc = NCC.findById(S.getMaNhaCungCap());
        cbbLoaiSP.setSelectedItem(LSP.GetNameByID(S.getLoaiSanPham()));
        cbbNhaC.setSelectedItem(ncc.getTenNcc());
        cbbTrangThai.setSelectedItem(S.getTrangThai());
        tfSLTon.setText(S.getSlTon().toString());
        tfGia.setFocusable(false);
        tfAnh.setText(S.getAnh());
        cbbNhaC.setFocusable(false);
       
        
    }
   private void saveIMG() {
        String filePath = tfAnh.getText();
        String maSanPham = tfMaSP.getText();
        File sourceFile = new File(filePath);

        if (!sourceFile.exists()) {
            JOptionPane.showMessageDialog(null, "File không tồn tại!");
            tfAnh.setText(null);
            return;
        }

        String projectDirectory = System.getProperty("user.dir");
        File targetDir = new File(projectDirectory+ File.separator + "src" + File.separator + "resources" + File.separator + "product");

        if (!targetDir.exists()) {
            targetDir.mkdirs(); // Tạo thư mục nếu chưa có
        }

        String extension = getFileExtension(sourceFile);
        String newFileName = maSanPham + extension;
        File targetFile = new File(targetDir, newFileName); // ✅ Đúng đường dẫn resources/product

        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tfAnh.setText("/resources/product/" + newFileName);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu file: " + ex.getMessage());
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex); // Trả về phần mở rộng (ví dụ .jpg, .png)
        }
        return ""; // Nếu không có phần mở rộng (trường hợp file không có đuôi)
    }

}
