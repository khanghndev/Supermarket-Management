/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Eployee;
import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.LoaiSanPhamDAO;
import DAO.NhaCungCapDAO;
import DAO.SanPhamDAO;
import MODAL.LoaiSanPham;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import modal.ChiTietHoaDon;
import modal.HoaDon;
import modal.KhachHang;
import modal.SanPham;

/**
 *
 * @author Minh
 */
public class productScreen extends javax.swing.JFrame {

    private HoaDonDAO HD = new HoaDonDAO();
    private List<HoaDon> DS_None;
    private List<SanPham> DSSP;
    SanPhamDAO SP= new SanPhamDAO();
    NhaCungCapDAO NCC = new NhaCungCapDAO();
    ChiTietHoaDonDAO CT = new ChiTietHoaDonDAO();
    KhachHangDAO KH = new KhachHangDAO();
    LoaiSanPhamDAO LSP = new LoaiSanPhamDAO();
    int nowPage =1;
    int totalPage;
    mainScreen ms;
    List<HoaDon> DSHD ;
    /**
     * Creates new form productFrame
     */
    public void result(String json) {
        // Rất đơn giản, lọc recommendations trong JSON
        StringBuilder result = new StringBuilder();
        if (json.contains("recommendations")) {
            int start = json.indexOf("[");
            int end = json.indexOf("]", start);
            if (start != -1 && end != -1) {
                String itemsStr = json.substring(start + 1, end);
                String[] items = itemsStr.replace("\"", "").split(",");
                result.append("Gợi ý sản phẩm mua kèm:\n");
                DefaultListModel<String> model = new DefaultListModel<>();
                listSuggesstion.removeAll();
                for (String item : items) {
                    model.addElement(item.trim());
                }
                listSuggesstion.setModel(model);
            } 
        } 
    }

     private void loadSuggestions(String productID) {
         try {
            String urlStr = "http://127.0.0.1:5000/recommend?product=" + java.net.URLEncoder.encode(productID, "UTF-8");
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                JOptionPane.showMessageDialog(null, "Lỗi khi gọi API");
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            result(response.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi gọi API");
        }
    }
    public productScreen(mainScreen m) {
        initComponents();
        ms = m;
        DSHD = HD.selectAll();
        DS_None = HD.selectAll_NonePaid();
        DSSP = new ArrayList<SanPham>();
        setSize(new Dimension(1180,730));
        setLocationRelativeTo(null);
        loadCategories();
        loadScreen();
    }
    private void loadCategories() {
        List<LoaiSanPham> ct = LSP.getAll();
        cbbCategory.removeAllItems();
        cbbCategory.addItem("Tất cả");
       for(LoaiSanPham x : ct) {
                cbbCategory.addItem(x.getTenLoaiSanPham());
            }
       cbbCategory.setSelectedIndex(0);
       cbbCustomer.removeAllItems();
       List<KhachHang> k = KH.getAll();
        cbbCustomer.addItem("Trống");
       for(KhachHang x : k) {
                cbbCustomer.addItem(x.getHoTen());
            }
       cbbCustomer.setSelectedIndex(0);
    }
    private void loadCustomerToBill()
    {
        if(cbbCustomer.getSelectedIndex()==0)
        {
            HD.update_makh(Integer.parseInt(cbbBill.getSelectedItem().toString()),0);
        }
        else 
        {
            int makh = KH.findIDByName(cbbCustomer.getSelectedItem().toString());
            HD.update_makh(Integer.parseInt(cbbBill.getSelectedItem().toString()),makh);
        }
    }
    private void loadListProductByCategory()
    {
        nowPage=1;
        String s="";
        if(cbbCategory.getSelectedIndex()!=0) s+="where loaiSanPham ="+LSP.GetIDByName(cbbCategory.getSelectedItem().toString());
        DSSP = SP.searchProducts(s);
        totalPage = (DSSP.size()%9==0) ? DSSP.size()/9 : (DSSP.size()/9)+1;
        if(totalPage<1) totalPage=1;
        lbPage.setText("1/"+totalPage);
        loadProductCards();
    }
    private void loadProductCards() {
        pnProductCard.removeAll();
        int count = (nowPage<totalPage) ? (nowPage*9) : DSSP.size();
        for(int i=(nowPage-1)*9;i<count;i++)
        {
            int id = DSSP.get(i).getMaSanPham();
            String name =DSSP.get(i).getTenSanPham();
            Double price = DSSP.get(i).getGia();
            String img = DSSP.get(i).getAnh();
            int qty = DSSP.get(i).getSlTon();
            pnProductCard.add(createProductCard(id, name,qty, price, img));
        }
        pnProductCard.revalidate();
        pnProductCard.repaint();
        lbPage.setText(nowPage+"/"+totalPage);
    }
    private JPanel createProductCard(int id, String Name,int qty, double Price, String imgPath) 
    {
        JPanel pn = new JPanel();
        pn.putClientProperty("id", id); 
         pn.setBackground(new java.awt.Color(255, 255, 255));
         pn.setPreferredSize(new java.awt.Dimension(150, 155));
         pn.setRequestFocusEnabled(false);
         pn.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent evt) {
                //loadSuggestions(id);
                loadSuggestions(Name);
                JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
                int option = JOptionPane.showOptionDialog(
                        null,spinner,
                        "Nhập số lượng: " ,JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,new String[]{"Đặt", "Hủy"},"Đặt" );
                if (option != JOptionPane.OK_OPTION) {return;}
                int qantity = (Integer) spinner.getValue();
                if(qty>=qantity){ 
                    
                    addProductToInvoice(id,qantity, Price);
                    
                }
                else JOptionPane.showMessageDialog(null, "Kho không còn đủ số lượng để cung cấp. Tra cứu số lượng tồn ở mục tìm kiếm","",JOptionPane.ERROR_MESSAGE);
             }
         });
         pn.setLayout(null);
         JLabel img = new JLabel();
         JLabel price = new JLabel();
         JLabel name = new JLabel();
         img.setText(String.valueOf(id));
         URL imageUrl = getClass().getResource("/resources/product/empty-img.png");
         if(imgPath != null)  imageUrl = getClass().getResource(imgPath);
         ImageIcon icon = new ImageIcon(imageUrl);
         Image i = icon.getImage().getScaledInstance(120, 130, Image.SCALE_SMOOTH);
         img.setIcon(new ImageIcon(i));
         img.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
         pn.add(img);
         img.setBounds(10, 10, 130, 90);
         
         
         price.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
         price.setForeground(new java.awt.Color(153, 0, 51));
         price.setText(String.format("%,.0f VND", Price));
         pn.add(price);
         price.setBounds(10, 124, 100, 30);
        
        
         name.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
         name.setText(Name);
         pn.add(name);
         name.setBounds(10, 105, 140, 20);
        return pn;
    }
    private void addProductToInvoice(int maSanPham,int soLuong,Double price) {
        if (cbbBill.getSelectedIndex()<0) {
            JOptionPane.showMessageDialog(this,"Vui lòng tạo hoặc chọn hóa đơn trước.","Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int invoiceId = Integer.parseInt(cbbBill.getSelectedItem().toString());
        DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
        int existingRow = -1;
        for (int r = 0; r < tableBill.getRowCount(); r++) {
            if (Integer.parseInt(tableBill.getValueAt(r, 0).toString()) == maSanPham) {
                existingRow = r;
                break;
            }
        }
        if (existingRow >= 0) {
            int old = Integer.parseInt(tableBill.getValueAt(existingRow, 3).toString());
            CT.update(invoiceId,maSanPham,soLuong+old);
            double total = (soLuong+old) * price;
            model.setValueAt(soLuong+old, existingRow, 3);
            model.setValueAt(total, existingRow, 4);
        } else {
            CT.insert(invoiceId, maSanPham, soLuong,price);
            Object[] row = {maSanPham, new SanPhamDAO().findById(maSanPham).getTenSanPham(),price,soLuong,price*soLuong};
            model.addRow(row);
        }
        CT.update_quantity_product(maSanPham,-soLuong);
        System.out.println("Cap nhat so luong san pham 3");
        JOptionPane.showMessageDialog(this, "Đã chọn sản phẩm [" + new SanPhamDAO().findById(maSanPham).getTenSanPham() + "]");
        loadProductCards();
    }
    private void updateInvoiceQuantity(int prodId, int row, int newQty) {
        int invoiceId = Integer.parseInt(cbbBill.getSelectedItem().toString());
        double unitPrice = (Double) tableBill.getModel().getValueAt(row, 2);
        CT.update_quantity_product(prodId,Integer.parseInt(tableBill.getValueAt(row, 3).toString()));
        CT.update(invoiceId,Integer.parseInt(tableBill.getValueAt(row, 0).toString()),newQty);
        CT.update_quantity_product(prodId,-newQty);
        DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
        double total = newQty * unitPrice;
        model.setValueAt(newQty, row, 3);
        model.setValueAt(total, row, 4);
        JOptionPane.showMessageDialog(this,"Đã cập nhật");
    }
    private void deleteProductFromInvoice(int prodId, int row) {
        if (cbbBill.getSelectedIndex()<0)  return;
        int invoiceId = Integer.parseInt(cbbBill.getSelectedItem().toString()); 
        if (JOptionPane.showConfirmDialog(this,"Xóa sản phẩm này khỏi hoá đơn?","Xác nhận",JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        CT.update_quantity_product(prodId,Integer.parseInt(tableBill.getValueAt(row, 3).toString()));
        CT.delete(invoiceId,Integer.parseInt(tableBill.getValueAt(row, 0).toString()));
        DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
        model.removeRow(row);
        JOptionPane.showMessageDialog(this,"Đã xóa sản phẩm khỏi hoá đơn.");
        
    }
    private void loadBillCombo() {
        cbbBill.removeAllItems();
        if(DS_None.size()==0)   createEmptyInvoice();
        for(HoaDon x : DS_None)
            cbbBill.addItem(String.valueOf(x.getMaHoaDon()));
        String sel = (String) cbbBill.getSelectedItem();
            if (sel != null) {
                
                int id = Integer.parseInt(sel);
                loadInvoiceDetails();
            }
    }
    private void createEmptyInvoice() {
        int staffID = ms.staffId;
        HD.insert(staffID);
        int newID = HD.getNew();
        ms.currentInvoiceId = newID;
        JOptionPane.showMessageDialog(this,"Tạo hóa đơn mới thành công");
        DS_None.add(HD.findById(newID));
        loadBillCombo();
        cbbBill.setSelectedItem(String.valueOf(newID));
    }    
    private void loadInvoiceDetails() {
        if (cbbBill.getSelectedIndex()<0)  return;
        int invoiceId = Integer.parseInt(cbbBill.getSelectedItem().toString());
        KhachHang k = KH.findById(HD.findById(invoiceId).getMaKhachHang());
        if(k==null) cbbCustomer.setSelectedIndex(0);
        else cbbCustomer.setSelectedItem(k.getHoTen());
        DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
        model.setRowCount(0); 
        List<ChiTietHoaDon> list = CT.findByMaHoaDon(invoiceId);
        for(ChiTietHoaDon x : list)
        {
            SanPhamDAO sp = new SanPhamDAO();
                    Object[] row = {
                        x.getMaSanPham(),
                        sp.findById(x.getMaSanPham()).getTenSanPham(),
                        x.getGia(),
                        x.getSoLuong(),
                        x.getGia()* x.getSoLuong()};
                    model.addRow(row);
                }
    }
    private void deleteCurrentInvoice() {
        if (cbbBill.getSelectedIndex()<0)  return;
        int invoiceId = Integer.parseInt(cbbBill.getSelectedItem().toString());
        for(int i=0;i<tableBill.getRowCount();i++)
            CT.update_quantity_product(Integer.parseInt(tableBill.getValueAt(i, 0).toString()),Integer.parseInt(tableBill.getValueAt(i, 3).toString()));
        if (JOptionPane.showConfirmDialog(this,"Xóa hóa đơn #" + invoiceId + " ?","Xác nhận",JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        CT.delete_all(invoiceId);
        HD.delete(invoiceId);
        JOptionPane.showMessageDialog(this,"Đã xóa hóa đơn #" + invoiceId);
        DS_None.remove(cbbBill.getSelectedIndex());
        loadBillCombo();
        loadProductCards();
    }  
    private void paid()
    {
        if(tableBill.getRowCount()==0)
        {
            JOptionPane.showMessageDialog(null,"Vui lòng chọn ít nhất 1 sản phẩm để thanh toán","Thông báo",JOptionPane.WARNING_MESSAGE);
            return;
        }
        ms.currentInvoiceId = Integer.parseInt(cbbBill.getSelectedItem().toString());
        ms.openInternalFrame("THANH TOÁN");
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
        pnSuggesdtion = new javax.swing.JPanel();
        lbTitleCategory1 = new javax.swing.JLabel();
        cbbCategory = new javax.swing.JComboBox<>();
        pnProductCard = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        lbPage = new javax.swing.JLabel();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        pnCRUD = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        lbTitleBill = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        cbbBill = new javax.swing.JComboBox<>();
        cbbCustomer = new javax.swing.JComboBox<>();
        lbTitleBill1 = new javax.swing.JLabel();
        lbTitleBill2 = new javax.swing.JLabel();
        pnSuggesdtion1 = new javax.swing.JPanel();
        lbTitleCategory2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listSuggesstion = new javax.swing.JList<>();
        btnPaid = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBill = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 247, 250));
        setResizable(false);
        setSize(new java.awt.Dimension(1180, 720));
        getContentPane().setLayout(null);

        pnMain.setBackground(new java.awt.Color(245, 247, 250));
        pnMain.setPreferredSize(new java.awt.Dimension(1180, 720));
        pnMain.setLayout(null);

        pnSuggesdtion.setBackground(new java.awt.Color(255, 255, 255));
        pnSuggesdtion.setLayout(null);

        lbTitleCategory1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbTitleCategory1.setForeground(new java.awt.Color(40, 93, 179));
        lbTitleCategory1.setText("DANH SÁCH SẢN PHẨM");
        pnSuggesdtion.add(lbTitleCategory1);
        lbTitleCategory1.setBounds(10, 10, 220, 30);

        cbbCategory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cbbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nước uống", "Bia rượu", "Thực phẩm", "..." }));
        pnSuggesdtion.add(cbbCategory);
        cbbCategory.setBounds(230, 10, 290, 30);
        pnSuggesdtion.add(pnProductCard);
        pnProductCard.setBounds(10, 50, 510, 530);

        btnNext.setText("Sau");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnSuggesdtion.add(btnNext);
        btnNext.setBounds(370, 590, 70, 40);

        btnPrevious.setText("Trước");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });
        pnSuggesdtion.add(btnPrevious);
        btnPrevious.setBounds(90, 590, 70, 40);

        lbPage.setText("jLabel1");
        pnSuggesdtion.add(lbPage);
        lbPage.setBounds(260, 610, 37, 16);

        btnLast.setText("Cuối");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnSuggesdtion.add(btnLast);
        btnLast.setBounds(450, 590, 70, 40);

        btnFirst.setText("Đầu");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnSuggesdtion.add(btnFirst);
        btnFirst.setBounds(10, 590, 70, 40);

        pnMain.add(pnSuggesdtion);
        pnSuggesdtion.setBounds(620, 50, 530, 660);

        pnCRUD.setBackground(new java.awt.Color(153, 204, 255));
        pnCRUD.setForeground(new java.awt.Color(255, 255, 255));
        pnCRUD.setRequestFocusEnabled(false);
        pnCRUD.setLayout(null);

        btnDelete.setBackground(new java.awt.Color(153, 204, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/bin.png"))); // NOI18N
        btnDelete.setBorder(null);
        btnDelete.setBorderPainted(false);
        btnDelete.setFocusPainted(false);
        btnDelete.setFocusable(false);
        pnCRUD.add(btnDelete);
        btnDelete.setBounds(1110, 5, 40, 40);

        lbTitleBill.setBackground(new java.awt.Color(102, 102, 102));
        lbTitleBill.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbTitleBill.setForeground(new java.awt.Color(102, 102, 102));
        lbTitleBill.setText("Khách hàng");
        pnCRUD.add(lbTitleBill);
        lbTitleBill.setBounds(630, 10, 100, 30);

        btnAdd.setBackground(new java.awt.Color(153, 204, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/add.png"))); // NOI18N
        btnAdd.setBorder(null);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setFocusable(false);
        pnCRUD.add(btnAdd);
        btnAdd.setBounds(1060, 5, 40, 40);

        cbbBill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbBill.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HD1", "HD2", "HD3" }));
        pnCRUD.add(cbbBill);
        cbbBill.setBounds(500, 10, 110, 30);

        cbbCustomer.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pnCRUD.add(cbbCustomer);
        cbbCustomer.setBounds(720, 10, 180, 30);

        lbTitleBill1.setBackground(new java.awt.Color(102, 102, 102));
        lbTitleBill1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitleBill1.setForeground(new java.awt.Color(102, 102, 102));
        lbTitleBill1.setText("HÓA ĐƠN MUA HÀNG");
        pnCRUD.add(lbTitleBill1);
        lbTitleBill1.setBounds(10, 10, 200, 30);

        lbTitleBill2.setBackground(new java.awt.Color(102, 102, 102));
        lbTitleBill2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbTitleBill2.setForeground(new java.awt.Color(102, 102, 102));
        lbTitleBill2.setText("Hóa đơn");
        pnCRUD.add(lbTitleBill2);
        lbTitleBill2.setBounds(440, 10, 60, 30);

        pnMain.add(pnCRUD);
        pnCRUD.setBounds(0, 0, 1160, 50);

        pnSuggesdtion1.setBackground(new java.awt.Color(255, 255, 255));
        pnSuggesdtion1.setLayout(null);

        lbTitleCategory2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbTitleCategory2.setForeground(new java.awt.Color(40, 93, 179));
        lbTitleCategory2.setText("SẢN PHẨM GỢI Ý");
        pnSuggesdtion1.add(lbTitleCategory2);
        lbTitleCategory2.setBounds(10, 10, 180, 30);

        listSuggesstion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(listSuggesstion);

        pnSuggesdtion1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 46, 580, 230);

        pnMain.add(pnSuggesdtion1);
        pnSuggesdtion1.setBounds(0, 420, 610, 290);

        btnPaid.setBackground(new java.awt.Color(76, 175, 80));
        btnPaid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnPaid.setForeground(new java.awt.Color(255, 255, 255));
        btnPaid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/money.png"))); // NOI18N
        btnPaid.setText("THANH TOÁN");
        btnPaid.setBorder(null);
        btnPaid.setBorderPainted(false);
        btnPaid.setFocusPainted(false);
        btnPaid.setPreferredSize(new java.awt.Dimension(180, 95));
        pnMain.add(btnPaid);
        btnPaid.setBounds(0, 380, 610, 40);

        tableBill.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableBill);

        pnMain.add(jScrollPane1);
        jScrollPane1.setBounds(2, 50, 610, 330);

        getContentPane().add(pnMain);
        pnMain.setBounds(0, 0, 1160, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
        if(nowPage>1)
        {
            nowPage-=1;
            loadProductCards();
}
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
   
         if(nowPage<totalPage)
        {
            nowPage+=1;
            loadProductCards();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
       nowPage=totalPage;
       loadProductCards();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        nowPage=1;
        loadProductCards();
    }//GEN-LAST:event_btnFirstActionPerformed
    
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
            java.util.logging.Logger.getLogger(productScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(productScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(productScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(productScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new productScreen(new mainScreen()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPaid;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JComboBox<String> cbbBill;
    private javax.swing.JComboBox<String> cbbCategory;
    private javax.swing.JComboBox<String> cbbCustomer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbPage;
    private javax.swing.JLabel lbTitleBill;
    private javax.swing.JLabel lbTitleBill1;
    private javax.swing.JLabel lbTitleBill2;
    private javax.swing.JLabel lbTitleCategory1;
    private javax.swing.JLabel lbTitleCategory2;
    private javax.swing.JList<String> listSuggesstion;
    private javax.swing.JPanel pnCRUD;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnProductCard;
    private javax.swing.JPanel pnSuggesdtion;
    private javax.swing.JPanel pnSuggesdtion1;
    private javax.swing.JTable tableBill;
    // End of variables declaration//GEN-END:variables

    private void loadScreen() {
        editCombobox();
        editTable();
        pnProductCard.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        cbbCategory.addActionListener(e -> {loadListProductByCategory();});
        cbbBill.addActionListener(e -> loadInvoiceDetails());
        cbbCustomer.addActionListener(e -> loadCustomerToBill());
        DSSP = SP.getAll();
        btnAdd.addActionListener(e -> createEmptyInvoice());
        btnDelete.addActionListener(e -> deleteCurrentInvoice());
        btnPaid.addActionListener(e -> paid());
        loadListProductByCategory();
        loadBillCombo();
        loadInvoiceDetails();
    }

    private void editTable() {
        JTableHeader header = tableBill.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableBill.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        Object[][] data = {};
        Object[] columnNames = {"Mã sản phẩm", "Sản phẩm", "Giá bán", "Số lượng", "Thành tiền"};
        TableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableBill.setModel(model);
        tableBill.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableBill.rowAtPoint(e.getPoint());
                if (row < 0) {return; }
                DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
                int prodId = (Integer) model.getValueAt(row, 0);
                String name = (String) model.getValueAt(row, 1);
                double unitPrice = (Double) model.getValueAt(row, 2);
                int currentQty = (Integer) model.getValueAt(row, 3);

                JSpinner spinner = new JSpinner( new SpinnerNumberModel(currentQty, 1, 1000, 1));

                Object[] options = {"Cập nhật", "Xóa", "Hủy"};
                int choice = JOptionPane.showOptionDialog(null,spinner,"Sản phẩm: " + name,JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options, options[0]);

                if (choice == 0) {
                    int newQty = (Integer) spinner.getValue();
                    updateInvoiceQuantity(prodId, row, newQty);
                } else if (choice == 1) {
                    deleteProductFromInvoice(prodId, row);
                }
                loadProductCards();
            }
        });
    }

    private void editCombobox() {
        cbbCategory.setBackground(Color.WHITE);
        cbbBill.setBackground(Color.WHITE);
        cbbCustomer.setBackground(Color.WHITE);
    }
}
