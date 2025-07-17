/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package GUI.Eployee;
import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import modal.ChiTietHoaDon;
import modal.HoaDon;
import modal.KhachHang;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author Minh
 */
public class paidScreen extends javax.swing.JFrame {

    private double sumPrice =0;
    int sum=0;
    double discount = 0;
    boolean isConvert = false;
    mainScreen ms;
    HoaDonDAO HD = new HoaDonDAO();
    public paidScreen(mainScreen m) {
        initComponents();
        ms =m;
        setLocationRelativeTo(null);
         setLocationRelativeTo(null);
        editTable();
        loadDetailBill();
        loadStaffInfo();
        int cus = -1;
        if(HD.findById(ms.currentInvoiceId).getMaKhachHang()!=0) cus = HD.findById(ms.currentInvoiceId).getMaKhachHang();
        loadCustomer(cus);
        btnPaid.addActionListener(e -> doPayment());
        btnConvertPoint.addActionListener(e -> convertPoint());
    }

    private void convertPoint()
    {
        if(!lbIDClient.getText().equals("Trống"))
        {
            int p = Integer.parseInt(lbNowPoint.getText());
            if(p<1000)
            {
                JOptionPane.showMessageDialog(null,"Số điểm hiện có cần lớn hơn 1000 để quy đổi","",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            btnConvertPoint.setFont(new java.awt.Font("Times New Roman", 1, 14));
            if(!isConvert)
            {
                lbDiscount.setText(String.format("%,.0f₫", p*1.0));
                lbSumPrice.setText(String.format("%,.0f₫", sumPrice));
                lbTotalPrice.setText(String.format("%,.0f₫", (sumPrice - p)));
                btnConvertPoint.setText("Hủy bỏ sử dụng điểm");
                btnConvertPoint.setBackground(new java.awt.Color(153,0,0));
            }
            else
            {
                lbDiscount.setText(String.format("%,.0f₫",0.0));
                lbSumPrice.setText(String.format("%,.0f₫", sumPrice));
                lbTotalPrice.setText(String.format("%,.0f₫", (sumPrice - p)));
                btnConvertPoint.setText("Sử dụng điểm");
                btnConvertPoint.setBackground(new java.awt.Color(51, 153, 255));
            }
            isConvert = !isConvert;
        }
        else JOptionPane.showMessageDialog(null,"Vui lòng chọn khách hàng trước","",JOptionPane.WARNING_MESSAGE);
    }
    
    private void editTable()
    {
        JTableHeader header = tableBill.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableBill.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        Object[][] data = {};
        Object[] columnNames = {"Sản phẩm","Giá bán", "Số lượng","Thành tiền"};
        TableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableBill.setModel(model);
    }
    private void loadDetailBill() {
        DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
        model.setRowCount(0);
        sumPrice = 0;
        ChiTietHoaDonDAO ct =new ChiTietHoaDonDAO();
        List<ChiTietHoaDon> list = ct.findByMaHoaDon(ms.currentInvoiceId);
        for(ChiTietHoaDon x : list)
        {
            String name = new SanPhamDAO().findById(x.getMaSanPham()).getTenSanPham();
            double price = x.getGia();
            int qty = x.getSoLuong();
            double tot = x.getGia()*x.getSoLuong();
            sumPrice += tot;
            
            model.addRow(new Object[]{name, price, qty, tot});
        }
        lbSumPrice.setText(String.format("%,.0f₫", sumPrice));
        lbTotalPrice.setText(String.format("%,.0f₫", (sumPrice - discount)));
    }
    private void loadStaffInfo() {
        lbIDStaff.setText(String.valueOf(ms.staffId));
        lbNameStaff.setText(new NhanVienDAO().getnameByID(ms.staffId));
    }
    private void loadCustomer(int id)
    {
        if(id==-1)
        {
            lbIDClient.setText("Trống");
            lbNameClient.setText("Trống");
            lbNowPoint.setText("0");
            lbNewPoint.setText("0");
            lbDiscount.setText(String.format("%,.0f₫",discount));
            lbSumPrice.setText(String.format("%,.0f₫",sumPrice));
            return;
        }
        KhachHangDAO kh = new KhachHangDAO();
        KhachHang k = kh.findById(id);
        lbIDClient.setText(k.getMaKhachHang().toString());
        lbNameClient.setText(k.getHoTen());
        lbNowPoint.setText(k.getDiemTichLuy().toString());
        int s = (int)sumPrice / 100;
        lbNewPoint.setText(String.valueOf(s));
    }
    private void loadSum()
    {
        double s=0;
        for(int i=0;i<tableBill.getRowCount();i++)
        {
            double g = Double.parseDouble(tableBill.getValueAt(i,3).toString());
            s+= g;
        }
        sumPrice = s;
        
    }
    public void appendDataToExcel() {
        try {
            File file = new File("src/resources/GiaoDich_SanPham_TiengViet.xlsx");
            XSSFWorkbook workbook;
            XSSFSheet sheet;
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                // Nếu chưa tồn tại, tạo mới
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("GiaoDich_SanPham_TiengViet");
                XSSFRow headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Itemname");
            }
            String s="";
            for(int i=0;i<tableBill.getRowCount()-1;i++)
                s+=tableBill.getValueAt(i,0)+",";
            s+=tableBill.getValueAt(tableBill.getRowCount()-1,0);
            int soDongHienCo = sheet.getLastRowNum();
            XSSFRow row = sheet.createRow(++soDongHienCo);
            row.createCell(0).setCellValue(s);
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
        fos.close();
        workbook.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đọc hoặc ghi tệp: " + ex.getMessage());
        }
    }
    private void doPayment() {
        HoaDon h = new HoaDon();
        h.setMaHoaDon(ms.currentInvoiceId);
        h.setMaNhanVien(ms.staffId);
        int idCus =-1;
        if(!lbIDClient.getText().equals("Trống"))
        {
            KhachHangDAO kh = new KhachHangDAO();
            idCus = Integer.parseInt(lbIDClient.getText());
            kh.updatePoint(idCus,Integer.parseInt(lbNewPoint.getText()));
        }
        HD.update(h, idCus);
        JOptionPane.showMessageDialog(this,
                "Thanh toán thành công!\nHoá đơn #" + ms.currentInvoiceId
                + "\nTổng: " + lbSumPrice.getText(),
                "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
        if(HD.selectAll_NonePaid().size()==0)
            HD.insert(ms.staffId);
        appendDataToExcel();
        ms.openInternalFrame("SẢN PHẨM");
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
        btnPaid = new javax.swing.JButton();
        pnDetailBill = new javax.swing.JPanel();
        ScrollTable = new javax.swing.JScrollPane();
        tableBill = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lbDiscount = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbSumPrice = new javax.swing.JLabel();
        pnTotal = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbTotalPrice = new javax.swing.JLabel();
        pnInfo = new javax.swing.JPanel();
        lbTitleList_client = new javax.swing.JLabel();
        jlabel1 = new javax.swing.JLabel();
        jlabel2 = new javax.swing.JLabel();
        jlabel3 = new javax.swing.JLabel();
        jlabel4 = new javax.swing.JLabel();
        lbIDStaff = new javax.swing.JLabel();
        lbNameStaff = new javax.swing.JLabel();
        lbIDClient = new javax.swing.JLabel();
        lbTitleList_client1 = new javax.swing.JLabel();
        lbNameClient = new javax.swing.JLabel();
        jlabel6 = new javax.swing.JLabel();
        jlabel7 = new javax.swing.JLabel();
        lbNowPoint = new javax.swing.JLabel();
        lbNewPoint = new javax.swing.JLabel();
        jlabel8 = new javax.swing.JLabel();
        jlabel9 = new javax.swing.JLabel();
        btnConvertPoint = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 247, 250));
        setResizable(false);
        setSize(new java.awt.Dimension(1180, 720));
        getContentPane().setLayout(null);

        pnMain.setBackground(new java.awt.Color(245, 247, 250));
        pnMain.setPreferredSize(new java.awt.Dimension(1180, 720));
        pnMain.setLayout(null);

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
        btnPaid.setBounds(0, 660, 490, 40);

        pnDetailBill.setBackground(new java.awt.Color(249, 250, 251));

        tableBill.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tableBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Sản phẩm", "Giá bán", "Số lượng", "Thành tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableBill.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableBill.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableBill.setFillsViewportHeight(true);
        tableBill.setGridColor(new java.awt.Color(0, 0, 0));
        tableBill.setMinimumSize(new java.awt.Dimension(60, 75));
        tableBill.setPreferredSize(new java.awt.Dimension(298, 75));
        tableBill.setRowHeight(40);
        tableBill.setShowGrid(true);
        tableBill.setShowHorizontalLines(false);
        ScrollTable.setViewportView(tableBill);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("Giảm giá:");

        lbDiscount.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbDiscount.setForeground(new java.awt.Color(204, 0, 0));
        lbDiscount.setText("0.000đ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Tổng cộng:");

        lbSumPrice.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbSumPrice.setText("0.000đ");

        pnTotal.setBackground(new java.awt.Color(239, 246, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Thành tiền:");

        lbTotalPrice.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbTotalPrice.setText("0.000đ");

        javax.swing.GroupLayout pnTotalLayout = new javax.swing.GroupLayout(pnTotal);
        pnTotal.setLayout(pnTotalLayout);
        pnTotalLayout.setHorizontalGroup(
            pnTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTotalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(82, 82, 82)
                .addComponent(lbTotalPrice)
                .addGap(34, 34, 34))
        );
        pnTotalLayout.setVerticalGroup(
            pnTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTotalLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(pnTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTotalPrice)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnDetailBillLayout = new javax.swing.GroupLayout(pnDetailBill);
        pnDetailBill.setLayout(pnDetailBillLayout);
        pnDetailBillLayout.setHorizontalGroup(
            pnDetailBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDetailBillLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnDetailBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDetailBillLayout.createSequentialGroup()
                        .addComponent(ScrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailBillLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnDetailBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addGap(86, 86, 86)
                        .addGroup(pnDetailBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbSumPrice)
                            .addComponent(lbDiscount))
                        .addGap(33, 33, 33))))
            .addComponent(pnTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnDetailBillLayout.setVerticalGroup(
            pnDetailBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDetailBillLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnDetailBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbSumPrice))
                .addGap(18, 18, 18)
                .addGroup(pnDetailBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbDiscount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnMain.add(pnDetailBill);
        pnDetailBill.setBounds(0, 0, 490, 660);

        pnInfo.setBackground(new java.awt.Color(255, 255, 255));

        lbTitleList_client.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitleList_client.setForeground(new java.awt.Color(40, 93, 179));
        lbTitleList_client.setText("THÔNG TIN NHÂN VIÊN");

        jlabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel1.setForeground(new java.awt.Color(153, 0, 0));
        jlabel1.setText("Hóa đơn có giá trị đổi trả trong cùng ngày");

        jlabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel2.setText("Mã nhân viên:");

        jlabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel3.setText("Họ tên nhân viên thanh toán:");

        jlabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel4.setText("140 Lê Trọng Tấn, P.Hòa Thạnh, Q.Tân Phú, Tp.Hồ Chí Minh");

        lbIDStaff.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbIDStaff.setText("1");

        lbNameStaff.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbNameStaff.setText("Minh");

        lbIDClient.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbIDClient.setText("1");

        lbTitleList_client1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitleList_client1.setForeground(new java.awt.Color(40, 93, 179));
        lbTitleList_client1.setText("THÔNG TIN KHÁCH HÀNG");

        lbNameClient.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbNameClient.setText("Minh");

        jlabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel6.setText("Mã khách hàng:");

        jlabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel7.setText("Họ tên khách hàng:");

        lbNowPoint.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbNowPoint.setText("1");

        lbNewPoint.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbNewPoint.setText("1");

        jlabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel8.setText("Điểm tích lũy đang có:");

        jlabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel9.setText("Điểm quy đổi từ thanh toán:");

        btnConvertPoint.setBackground(new java.awt.Color(51, 153, 255));
        btnConvertPoint.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnConvertPoint.setForeground(new java.awt.Color(255, 255, 255));
        btnConvertPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/employee/convert.png"))); // NOI18N
        btnConvertPoint.setText("SỬ DỤNG ĐIỂM");
        btnConvertPoint.setBorder(null);
        btnConvertPoint.setBorderPainted(false);
        btnConvertPoint.setFocusPainted(false);
        btnConvertPoint.setPreferredSize(new java.awt.Dimension(180, 95));

        javax.swing.GroupLayout pnInfoLayout = new javax.swing.GroupLayout(pnInfo);
        pnInfo.setLayout(pnInfoLayout);
        pnInfoLayout.setHorizontalGroup(
            pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInfoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnInfoLayout.createSequentialGroup()
                        .addComponent(lbTitleList_client, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 380, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnInfoLayout.createSequentialGroup()
                        .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnInfoLayout.createSequentialGroup()
                                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbTitleList_client1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnInfoLayout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jlabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jlabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lbNewPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbNowPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lbNameClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbIDClient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lbNameStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbIDStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(btnConvertPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInfoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInfoLayout.createSequentialGroup()
                        .addComponent(jlabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInfoLayout.createSequentialGroup()
                        .addComponent(jlabel1)
                        .addGap(142, 142, 142))))
        );
        pnInfoLayout.setVerticalGroup(
            pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInfoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lbTitleList_client, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnInfoLayout.createSequentialGroup()
                        .addComponent(jlabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnInfoLayout.createSequentialGroup()
                        .addComponent(lbIDStaff)
                        .addGap(18, 18, 18)
                        .addComponent(lbNameStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lbTitleList_client1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel6)
                    .addComponent(lbIDClient))
                .addGap(18, 18, 18)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNameClient, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel8)
                    .addComponent(lbNowPoint))
                .addGap(18, 18, 18)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNewPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnConvertPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                .addComponent(jlabel1)
                .addGap(18, 18, 18)
                .addComponent(jlabel4)
                .addContainerGap())
        );

        pnMain.add(pnInfo);
        pnInfo.setBounds(500, 0, 670, 700);

        getContentPane().add(pnMain);
        pnMain.setBounds(0, 0, 1180, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(paidScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(paidScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(paidScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(paidScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new paidScreen(new mainScreen()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollTable;
    private javax.swing.JButton btnConvertPoint;
    private javax.swing.JButton btnPaid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JLabel jlabel2;
    private javax.swing.JLabel jlabel3;
    private javax.swing.JLabel jlabel4;
    private javax.swing.JLabel jlabel6;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JLabel jlabel8;
    private javax.swing.JLabel jlabel9;
    private javax.swing.JLabel lbDiscount;
    private javax.swing.JLabel lbIDClient;
    private javax.swing.JLabel lbIDStaff;
    private javax.swing.JLabel lbNameClient;
    private javax.swing.JLabel lbNameStaff;
    private javax.swing.JLabel lbNewPoint;
    private javax.swing.JLabel lbNowPoint;
    private javax.swing.JLabel lbSumPrice;
    private javax.swing.JLabel lbTitleList_client;
    private javax.swing.JLabel lbTitleList_client1;
    private javax.swing.JLabel lbTotalPrice;
    private javax.swing.JPanel pnDetailBill;
    private javax.swing.JPanel pnInfo;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnTotal;
    private javax.swing.JTable tableBill;
    // End of variables declaration//GEN-END:variables

    

}
