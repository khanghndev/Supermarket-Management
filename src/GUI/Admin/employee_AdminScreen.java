/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Admin;

import DAO.NhanVienDAO;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.w3c.dom.events.MouseEvent;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import modal.NhanVien;

/**
 *
 * @author Minh
 */
public class employee_AdminScreen extends javax.swing.JFrame {

    private DefaultTableModel model;
    mainAdminScreen ms;
    NhanVienDAO NV = new NhanVienDAO();
    boolean isAdd= false, isEdit = false, isShow =false;
    /**
     * Creates new form productFrame
     */
    public employee_AdminScreen(mainAdminScreen m) {
        initComponents();
        setSize(new Dimension(1180, 720));
        this.ms = m;

        loadPositions();
        setupTable();
        setupFilters();
        loadEmployees();
        editButton();
        editCombobox();
        loadEditation(false);
        rdFemale2.setBackground(Color.WHITE);
        rdMale2.setBackground(Color.WHITE);
        btnShowImage.addActionListener(e -> ShowIMG());
         btnReload.addActionListener(e -> {
             if (isEdit) {
                 loadEmployeeToForm((int)tableEmployee.getValueAt(tableEmployee.getSelectedRow(),0));
             } else {
                 resetForm();
             }
         });
         btnUploadIMG2.addActionListener(e -> saveIMG());
         btnSave.addActionListener(e -> SaveEmployee());
    }

    private void setupTable() {
        Object[] columnNames = {"Mã NV", "Họ Tên", "Chức vụ", "Khôi phục mật khẩu","Tài khoản"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEmployee.setModel(model);
        JTableHeader header = tableEmployee.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
                tableEmployee.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                       @Override
                       public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            int sel = tableEmployee.getSelectedRow();
                            if (sel < 0) return;    
                            int id = Integer.parseInt(model.getValueAt(sel, 0).toString());
                            loadEditation(false);
                            loadEmployeeToForm(id);
                        }}
                       });
                
               
      
        DefaultTableCellRenderer resetRenderer = new DefaultTableCellRenderer();
        resetRenderer.setHorizontalAlignment(JLabel.CENTER);
        resetRenderer.setForeground(Color.RED);
        resetRenderer.setFont(new Font("Arial", Font.BOLD, 12));
        tableEmployee.getColumn("Khôi phục mật khẩu").setCellRenderer(resetRenderer);
        tableEmployee.getColumn("Tài khoản").setCellRenderer(resetRenderer);
        
        resetRenderer = new DefaultTableCellRenderer();
        resetRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableEmployee.getColumn("Mã NV").setCellRenderer(resetRenderer);
        tableEmployee.getColumn("Chức vụ").setCellRenderer(resetRenderer);


    }
    
    private void setupFilters() {
        // sort combo
        cbbSort.removeAllItems();
        cbbSort.addItem("Mã nhân viên");
        cbbSort.addItem("Họ và tên");
        cbbSort.addItem("Chức vụ");
        ItemListener reloadListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadEmployees();
            }
        };
        cbbSort.addItemListener(reloadListener);
        tfSeacrh.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {loadEmployees();}
            public void removeUpdate(DocumentEvent e) {loadEmployees();}
            public void changedUpdate(DocumentEvent e) { loadEmployees();}
        });
    }
    
    private void loadEmployees() {
        model.setRowCount(0);
        String orderCol =" order by chucVu asc";
        if(cbbSort.getSelectedIndex()==0) orderCol =" order by maNhanVien asc";
        else if(cbbSort.getSelectedIndex()==1) orderCol =" order by hoTen asc ";
        String sql = " where chucVu != N'Quản lý' and hoTen Like N'%"+tfSeacrh.getText()+"%' "+orderCol;
        for(NhanVien x : NV.getAll(sql))
        {
            String taikhoan = "Khóa tài khoản";
            if(x.getTrangThai()==0) taikhoan="Mở khóa";
            model.addRow(new Object[]{
                        x.getMaNhanVien(),
                        x.getHoTen(),
                        x.getChucVu(),
                        "Cấp lại",
                        taikhoan
                    });
        }
        tableEmployee.setModel(model);
    }

    private void resetPassword(int empId) {
        String newPwd = String.valueOf(empId);
        int id = (int)tableEmployee.getValueAt(tableEmployee.getSelectedRow(),0);
        NV.changePass(id, newPwd);
        JOptionPane.showMessageDialog(this,"Reset mật khẩu về: " + newPwd,"Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadPositions() {
        cbbPosition2.removeAllItems();
        for(String x : NV.getPosition())
            cbbPosition2.addItem(x);
    }

    private void loadEmployeeToForm(int empId) {
        NhanVien nv = NV.getOne(empId);
        tfID2.setText(String.valueOf(empId));
        tfName2.setText(nv.getHoTen());
        cbbPosition2.setSelectedItem(nv.getChucVu());
        String[] a = nv.getNgaySinh().split("-");
        LocalDate dt = LocalDate.of(
            Integer.parseInt(a[0]),
            Integer.parseInt(a[1]),
            Integer.parseInt(a[2])
        );
        Date date = Date.from(dt.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dtpk2.setDate(date);
        boolean male = nv.getGioiTinh();
        rdMale2.setSelected(male);
        rdFemale2.setSelected(!male);
        tfPhone2.setText(nv.getSoDienThoai());
        tfEmail2.setText(nv.getEmail());
        tfAdress2.setText(nv.getDiaChi());
        tfIMG2.setText(nv.getAnh());
        LocalDateTime now = LocalDateTime.now();
        a = nv.getNgayTuyenDung().split("-");
        tfNgayVaoLAm.setText(a[2]+"/"+a[1]+"/"+a[0]);
    }

    private void resetForm() {
        tfID2.setText(String.valueOf(NV.getNewId()));
        tfName2.setText("");
        cbbPosition2.setSelectedIndex(0);
        dtpk2.setDate(null);
        rdMale2.setSelected(true);
        tfPhone2.setText("");
        tfEmail2.setText("");
        tfAdress2.setText("");
        tfIMG2.setText("");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfNgayVaoLAm.setText(now.format(formatter));
        LocalDate dt = LocalDate.now();
        Date date = Date.from(dt.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dtpk2.setDate(date);
    }

    private void ShowIMG()
    {
        if(tfIMG2.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(null,"Không có ảnh để hiển thị","",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try
        {
            JDialog dialog = new JDialog(this, "Chi tiết nhân viên", true); // 'true' = modal
            dialog.setSize(300, 300);
            dialog.setLocationRelativeTo(this);
            URL imageUrl = getClass().getResource(tfIMG2.getText());
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
        
    }
    private void loadEditation(boolean e)
    {
        tfID2.setEditable(false);
        tfName2.setEditable(e);
        tfEmail2.setEditable(e);
        tfPhone2.setEditable(e);
        tfAdress2.setEditable(e);
        tfIMG2.setEnabled(false);
        tfNgayVaoLAm.setEditable(e);
        cbbPosition2.setEnabled(e);
        dtpk2.setEnabled(e);
        btnShowImage.setEnabled(!e);
        btnSave.setEnabled(e);
        btnReload.setEnabled(e);
        btnUploadIMG2.setEnabled(e);
    }
    private boolean checkAge(JDateChooser d)
    {
        Date selectedDate = d.getDate();
        if (selectedDate == null) return false;
        LocalDate birthDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears(); 
        return  age >= 18 && age<100  ;
    }
    private void SaveEmployee()
    {
        if(isAdd || isEdit)
        {
            if(tfName2.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Họ và tên không dược trống","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!checkAge(dtpk2))
            {
                JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lê","",JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            String rePhone = "^(03|05|07|08|09)\\d{8}$";
            String phone = tfPhone2.getText().trim();
            if (!phone.matches(rePhone)) {
                JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(NV.checkPhone(phone,Integer.parseInt(tfID2.getText()))!=0)
            {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại trong dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String reEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
            String email = tfEmail2.getText().trim();
            if (!email.matches(reEmail)) {
                JOptionPane.showMessageDialog(null, "Email không đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(NV.checkEmail(email,Integer.parseInt(tfID2.getText()))!=0)
            {
                JOptionPane.showMessageDialog(null, "Địa chỉ email đã tồn tại trong dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(tfAdress2.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Địa chỉ không dược trống","",JOptionPane.ERROR_MESSAGE);
                return;
            }
         
            NhanVien s = new NhanVien();
            s.setMaNhanVien(Integer.parseInt(tfID2.getText()));
            s.setHoTen(tfName2.getText());
            s.setEmail(tfEmail2.getText());
            s.setSoDienThoai(tfPhone2.getText());
            s.setDiaChi(tfAdress2.getText());
            s.setChucVu(cbbPosition2.getSelectedItem().toString());
            Date dob = dtpk2.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String a = sdf.format(dob);
            s.setNgaySinh(a);
            s.setTrangThai(1);
            s.setMatKhau(tfID2.getText());
            s.setGioiTinh(false);
            if(rdMale2.isSelected()) s.setGioiTinh(true);
            s.setAnh("");
            if(tfIMG2.getText().trim().length()!=0)
            {
                s.setAnh(tfIMG2.getText().trim());
            }
            if(isEdit)
            {
                NV.update(s);
                JOptionPane.showMessageDialog(null,"Cập nhật thông tin nhân viên thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(isAdd)
            {
                LocalDate now = LocalDate.now();
                Date date = java.sql.Date.valueOf(now);  // chuyển LocalDate thành Date
                s.setNgayTuyenDung(sdf.format(date));
     
                NV.insert(s);
                JOptionPane.showMessageDialog(null,"Thêm nhân viên mới thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            loadEmployees();
        }
    }
    
    private void saveIMG() {
        String filePath = tfIMG2.getText();
        String maSanPham = tfID2.getText();
        File sourceFile = new File(filePath);

        if (!sourceFile.exists()) {
            tfIMG2.setText(null);
            return;
        }

        String projectDirectory = System.getProperty("user.dir");
        File targetDir = new File(projectDirectory+ File.separator + "src" + File.separator + "resources" + File.separator + "avatar");

        if (!targetDir.exists()) {
            targetDir.mkdirs(); // Tạo thư mục nếu chưa có
        }

        String extension = getFileExtension(sourceFile);
        String newFileName = maSanPham + extension;
        File targetFile = new File(targetDir, newFileName); // ✅ Đúng đường dẫn resources/product

        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tfIMG2.setText("/resources/avatar/" + newFileName);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnMain = new javax.swing.JPanel();
        pnCRUD = new javax.swing.JPanel();
        lbTitleList_client = new javax.swing.JLabel();
        btnExportFIle = new javax.swing.JButton();
        pnCategori = new javax.swing.JPanel();
        tfSeacrh = new javax.swing.JTextField();
        ScrollPane = new javax.swing.JScrollPane();
        tableEmployee = new javax.swing.JTable();
        pnCEmployeeEdit2 = new javax.swing.JPanel();
        lbTitleList_client3 = new javax.swing.JLabel();
        jlabel27 = new javax.swing.JLabel();
        tfAdress2 = new javax.swing.JTextField();
        jlabel28 = new javax.swing.JLabel();
        jlabel29 = new javax.swing.JLabel();
        jlabel30 = new javax.swing.JLabel();
        tfID2 = new javax.swing.JTextField();
        tfName2 = new javax.swing.JTextField();
        tfPhone2 = new javax.swing.JTextField();
        tfEmail2 = new javax.swing.JTextField();
        jlabel31 = new javax.swing.JLabel();
        jlabel32 = new javax.swing.JLabel();
        cbbPosition2 = new javax.swing.JComboBox<>();
        jlabel33 = new javax.swing.JLabel();
        jlabel34 = new javax.swing.JLabel();
        dtpk2 = new com.toedter.calendar.JDateChooser();
        rdMale2 = new javax.swing.JRadioButton();
        rdFemale2 = new javax.swing.JRadioButton();
        tfIMG2 = new javax.swing.JTextField();
        jlabel35 = new javax.swing.JLabel();
        btnUploadIMG2 = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnShowImage = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jlabel36 = new javax.swing.JLabel();
        tfNgayVaoLAm = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbbSort = new javax.swing.JComboBox<>();

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
        lbTitleList_client.setText("NHÂN VIÊN");
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

        tfSeacrh.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        tfSeacrh.setVerifyInputWhenFocusTarget(false);
        pnCategori.add(tfSeacrh);
        tfSeacrh.setBounds(10, 10, 390, 30);

        tableEmployee.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tableEmployee.setModel(new javax.swing.table.DefaultTableModel(
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
        tableEmployee.setRowHeight(40);
        tableEmployee.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEmployeeMouseClicked(evt);
            }
        });
        ScrollPane.setViewportView(tableEmployee);

        pnCategori.add(ScrollPane);
        ScrollPane.setBounds(10, 50, 720, 560);

        pnCEmployeeEdit2.setBackground(new java.awt.Color(255, 255, 255));
        pnCEmployeeEdit2.setLayout(null);

        lbTitleList_client3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lbTitleList_client3.setForeground(new java.awt.Color(40, 93, 179));
        lbTitleList_client3.setText("THÔNG TIN NHÂN VIÊN");
        pnCEmployeeEdit2.add(lbTitleList_client3);
        lbTitleList_client3.setBounds(0, 10, 220, 40);

        jlabel27.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel27.setText("Địa chỉ:");
        pnCEmployeeEdit2.add(jlabel27);
        jlabel27.setBounds(0, 480, 70, 30);

        tfAdress2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnCEmployeeEdit2.add(tfAdress2);
        tfAdress2.setBounds(80, 470, 320, 36);
        tfAdress2.getAccessibleContext().setAccessibleName("");

        jlabel28.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel28.setText("Mã NV:");
        pnCEmployeeEdit2.add(jlabel28);
        jlabel28.setBounds(0, 80, 170, 20);

        jlabel29.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel29.setText("Họ tên:");
        pnCEmployeeEdit2.add(jlabel29);
        jlabel29.setBounds(0, 120, 200, 30);

        jlabel30.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel30.setText("SĐT:");
        pnCEmployeeEdit2.add(jlabel30);
        jlabel30.setBounds(0, 380, 62, 22);

        tfID2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tfID2.setText("KH001");
        pnCEmployeeEdit2.add(tfID2);
        tfID2.setBounds(110, 70, 290, 40);

        tfName2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnCEmployeeEdit2.add(tfName2);
        tfName2.setBounds(110, 120, 290, 36);

        tfPhone2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnCEmployeeEdit2.add(tfPhone2);
        tfPhone2.setBounds(80, 370, 320, 36);

        tfEmail2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnCEmployeeEdit2.add(tfEmail2);
        tfEmail2.setBounds(80, 420, 320, 36);

        jlabel31.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel31.setText("Ảnh:");
        pnCEmployeeEdit2.add(jlabel31);
        jlabel31.setBounds(0, 540, 84, 22);

        jlabel32.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel32.setText("Chức vụ:");
        pnCEmployeeEdit2.add(jlabel32);
        jlabel32.setBounds(0, 180, 90, 22);

        cbbPosition2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cbbPosition2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnCEmployeeEdit2.add(cbbPosition2);
        cbbPosition2.setBounds(110, 170, 290, 36);

        jlabel33.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel33.setText("Giới tính");
        pnCEmployeeEdit2.add(jlabel33);
        jlabel33.setBounds(0, 330, 114, 22);

        jlabel34.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel34.setText("Ngày sinh");
        pnCEmployeeEdit2.add(jlabel34);
        jlabel34.setBounds(0, 230, 100, 22);

        dtpk2.setBackground(new java.awt.Color(255, 255, 255));
        dtpk2.setDateFormatString("dd/MM/yyyy");
        dtpk2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnCEmployeeEdit2.add(dtpk2);
        dtpk2.setBounds(110, 220, 290, 36);

        buttonGroup1.add(rdMale2);
        rdMale2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        rdMale2.setText("Nam");
        pnCEmployeeEdit2.add(rdMale2);
        rdMale2.setBounds(110, 330, 80, 26);

        buttonGroup1.add(rdFemale2);
        rdFemale2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        rdFemale2.setText("Nữ");
        pnCEmployeeEdit2.add(rdFemale2);
        rdFemale2.setBounds(220, 330, 80, 26);

        tfIMG2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnCEmployeeEdit2.add(tfIMG2);
        tfIMG2.setBounds(80, 530, 240, 31);

        jlabel35.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel35.setText("Email:");
        pnCEmployeeEdit2.add(jlabel35);
        jlabel35.setBounds(0, 430, 84, 22);

        btnUploadIMG2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUploadIMG2.setText("Tải");
        pnCEmployeeEdit2.add(btnUploadIMG2);
        btnUploadIMG2.setBounds(320, 530, 80, 30);

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
        pnCEmployeeEdit2.add(btnReload);
        btnReload.setBounds(130, 580, 140, 35);

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
        pnCEmployeeEdit2.add(btnSave);
        btnSave.setBounds(280, 580, 120, 35);

        btnShowImage.setBackground(new java.awt.Color(102, 204, 255));
        btnShowImage.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnShowImage.setForeground(new java.awt.Color(255, 255, 255));
        btnShowImage.setText("Xem ảnh");
        btnShowImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnShowImage.setBorderPainted(false);
        btnShowImage.setFocusPainted(false);
        btnShowImage.setFocusable(false);
        btnShowImage.setPreferredSize(new java.awt.Dimension(230, 30));
        pnCEmployeeEdit2.add(btnShowImage);
        btnShowImage.setBounds(0, 580, 120, 35);

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
        pnCEmployeeEdit2.add(btnAdd);
        btnAdd.setBounds(360, 10, 42, 42);

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
        pnCEmployeeEdit2.add(btnEdit);
        btnEdit.setBounds(320, 10, 38, 38);

        jlabel36.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlabel36.setText("Vào làm");
        pnCEmployeeEdit2.add(jlabel36);
        jlabel36.setBounds(0, 270, 100, 40);

        tfNgayVaoLAm.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        pnCEmployeeEdit2.add(tfNgayVaoLAm);
        tfNgayVaoLAm.setBounds(110, 270, 290, 35);

        pnCategori.add(pnCEmployeeEdit2);
        pnCEmployeeEdit2.setBounds(760, 0, 410, 620);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Sắp xếp theo");
        pnCategori.add(jLabel1);
        jLabel1.setBounds(470, 10, 90, 30);

        cbbSort.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã nhân viên", "Họ và tên", "Chức vụ", " " }));
        pnCategori.add(cbbSort);
        cbbSort.setBounds(570, 10, 154, 30);

        pnMain.add(pnCategori);
        pnCategori.setBounds(0, 50, 1200, 640);

        getContentPane().add(pnMain);
        pnMain.setBounds(0, 0, 1180, 690);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:

        isEdit = isShow = false;
        isAdd = true;
        loadEditation(true);
        resetForm();

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int row = tableEmployee.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 nhân viên để sửa");
            return;
        }
        isAdd = isShow = false;
        isEdit = true;

        loadEmployeeToForm(Integer.parseInt(tableEmployee.getValueAt(tableEmployee.getSelectedRow(),0).toString()));
        loadEditation(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void tableEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEmployeeMouseClicked
        // TODO add your handling code here:
        if(tableEmployee.getSelectedColumn()==3 && tableEmployee.getSelectedRow()>=0)
        {
            int maNV = Integer.parseInt(tableEmployee.getValueAt(tableEmployee.getSelectedRow(), 0).toString());
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc muốn khôi phục mật khẩu cho nhân viên có mã " + maNV + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                NV.changePass(maNV,tfID2.getText());
                JOptionPane.showMessageDialog(null, "Khôi phục mật khẩu thành công!");
            }
        }
        else if(tableEmployee.getSelectedColumn()==4 && tableEmployee.getSelectedRow()>=0)
        {
            int maNV = Integer.parseInt(tableEmployee.getValueAt(tableEmployee.getSelectedRow(), 0).toString());
            if(tableEmployee.getValueAt(tableEmployee.getSelectedRow(), 4).toString().trim().equals("Khóa tài khoản"))
            {
                int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc muốn khóa tài khoản nhân viên có mã " + maNV + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    NV.update_status(maNV,0);
                    JOptionPane.showMessageDialog(null, "Khóa tài khoản thành công!");
                }
            }
            else
            {
                int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc muốn mở khóa tài khoản nhân viên có mã " + maNV + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    NV.update_status(maNV,1);
                    JOptionPane.showMessageDialog(null, "Mở khóa tài khoản thành công!");
                }
            }
            loadEmployees();
        }
    }//GEN-LAST:event_tableEmployeeMouseClicked

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
            java.util.logging.Logger.getLogger(employee_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(employee_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(employee_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(employee_AdminScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new employee_AdminScreen(new mainAdminScreen()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExportFIle;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnShowImage;
    private javax.swing.JButton btnUploadIMG2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbPosition2;
    private javax.swing.JComboBox<String> cbbSort;
    private com.toedter.calendar.JDateChooser dtpk2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jlabel27;
    private javax.swing.JLabel jlabel28;
    private javax.swing.JLabel jlabel29;
    private javax.swing.JLabel jlabel30;
    private javax.swing.JLabel jlabel31;
    private javax.swing.JLabel jlabel32;
    private javax.swing.JLabel jlabel33;
    private javax.swing.JLabel jlabel34;
    private javax.swing.JLabel jlabel35;
    private javax.swing.JLabel jlabel36;
    private javax.swing.JLabel lbTitleList_client;
    private javax.swing.JLabel lbTitleList_client3;
    private javax.swing.JPanel pnCEmployeeEdit2;
    private javax.swing.JPanel pnCRUD;
    private javax.swing.JPanel pnCategori;
    private javax.swing.JPanel pnMain;
    private javax.swing.JRadioButton rdFemale2;
    private javax.swing.JRadioButton rdMale2;
    private javax.swing.JTable tableEmployee;
    private javax.swing.JTextField tfAdress2;
    private javax.swing.JTextField tfEmail2;
    private javax.swing.JTextField tfID2;
    private javax.swing.JTextField tfIMG2;
    private javax.swing.JTextField tfName2;
    private javax.swing.JTextField tfNgayVaoLAm;
    private javax.swing.JTextField tfPhone2;
    private javax.swing.JTextField tfSeacrh;
    // End of variables declaration//GEN-END:variables


    private void editCombobox() {
        cbbSort.setBackground(Color.WHITE);
    }

    private void editButton() {
        btnExportFIle.setBackground(Color.WHITE);
        btnAdd.setBackground(Color.WHITE);
        btnEdit.setBackground(Color.WHITE);
        btnUploadIMG2.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file ảnh");

            // Chỉ chấp nhận các file ảnh (.jpg, .png, .gif)
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Ảnh JPEG, JPG, PNG, GIF","jpeg", "jpg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this); // Mở hộp thoại chọn file

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile(); // Lấy file đã chọn
                tfIMG2.setText(file.getAbsolutePath()); // Hiển thị đường dẫn vào JTextField
                saveIMG();
            }
        });
    }


}
