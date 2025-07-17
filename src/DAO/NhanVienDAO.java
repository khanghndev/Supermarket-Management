package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modal.NhanVien;

public class NhanVienDAO {

    public String Login(String username,String pass)
    {
        String result="";
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT maNhanVien, hoTen, chucVu FROM nhan_vien WHERE email=? AND matKhau=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username); 
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery(); 
            while (!rs.next()) {
                return result;
            }
            result = rs.getString("MaNhanVien")+"-"+rs.getString("HoTen")+"-"+rs.getString("ChucVu");
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public int findStatusByEmail(String Email) {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT trangthai FROM nhan_vien WHERE email = N'"+Email+"'")) {
 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("trangthai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int findIDByName(String name) {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT maNhanVien FROM nhan_vien WHERE hoTen = N'"+name+"'")) {
 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("maNhanVien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<String> getPosition() {
        List<String> list = new ArrayList<>();

        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT Distinct(chucVu) FROM nhan_vien";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("chucVu"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    //
    public String getnameByID(int id)
    {
        String result="";
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT hoTen FROM nhan_vien WHERE maNhanVien="+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); 
            while(rs.next())
                result = rs.getString("HoTen");
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    // Lấy tất cả nhân viên
    public List<NhanVien> getAll(String s) {
        List<NhanVien> list = new ArrayList<>();

        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM nhan_vien"+s;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getInt("maNhanVien"));
                nv.setMatKhau(rs.getString("matKhau"));
                nv.setHoTen(rs.getString("hoTen"));
                nv.setChucVu(rs.getString("chucVu"));
                nv.setNgayTuyenDung(rs.getString("ngayTuyenDung"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                nv.setNgaySinh(rs.getString("ngaySinh"));
                nv.setGioiTinh(rs.getBoolean("gioiTinh"));
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setAnh(rs.getString("anh"));
                nv.setTrangThai(rs.getInt("trangthai"));
                list.add(nv);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm nhân viên
    public int insert(NhanVien nv) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "INSERT INTO nhan_vien (maNhanVien,matKhau, hoTen, ChucVu, NgayTuyenDung, SoDienThoai, Email, NgaySinh, GioiTinh, DiaChi, Anh,trangthai) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,1)";
            if(nv.getAnh()==null)
                sql = "INSERT INTO nhan_vien (maNhanVien,matKhau, hoTen, ChucVu, NgayTuyenDung, SoDienThoai, Email, NgaySinh, GioiTinh, DiaChi, Anh,trangthai) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, NULL,1)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,nv.getMaNhanVien());
            ps.setString(2, nv.getMatKhau());
            ps.setString(3, nv.getHoTen());
            ps.setString(4, nv.getChucVu());
            ps.setString(5, nv.getNgayTuyenDung());
            ps.setString(6, nv.getSoDienThoai());
            ps.setString(7, nv.getEmail());
            ps.setString(8, nv.getNgaySinh());
            ps.setBoolean(9, nv.getGioiTinh());
            ps.setString(10, nv.getDiaChi());
            if(nv.getAnh()!=null) ps.setString(11, nv.getAnh());

            int result = ps.executeUpdate();

            ps.close();
            con.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Cập nhật nhân viên
    public int update(NhanVien nv) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "UPDATE nhan_vien SET hoTen = ?, chucVu =?,  soDienThoai = ?, email = ?,gioiTinh = ?, diaChi = ? ,ngaySinh = ?, anh = ? WHERE maNhanVien = "+nv.getMaNhanVien();
            if(nv.getAnh() == null)
                sql = "UPDATE nhan_vien SET hoTen = ?, chucVu =?,  soDienThoai = ?, email = ?,gioiTinh = ?, diaChi = ? ,ngaySinh = ?, anh = NULL WHERE maNhanVien = "+nv.getMaNhanVien();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getSoDienThoai());
            ps.setString(4, nv.getEmail());
            ps.setBoolean(5, nv.getGioiTinh());
            ps.setString(6, nv.getDiaChi());
            ps.setString(7, nv.getNgaySinh());
            if(nv.getAnh() != null) ps.setString(8, nv.getAnh());

            int result = ps.executeUpdate();

            ps.close();
            con.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
public int update_status(int nv,int status) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "UPDATE nhan_vien SET trangthai = ? WHERE maNhanVien = "+nv;
    
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,status);
           

            int result = ps.executeUpdate();

            ps.close();
            con.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
 
    public NhanVien getOne(int id) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM nhan_vien where maNhanVien = "+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getInt("MaNhanVien"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setNgayTuyenDung(rs.getString("NgayTuyenDung"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setAnh(rs.getString("Anh"));
                return nv;
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public int changePass(int id,String newpass) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "UPDATE nhan_vien SET matKhau = ? WHERE MaNhanVien = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newpass);
            ps.setInt(2, id);
            int result = ps.executeUpdate();

            ps.close();
            con.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int getNewId()
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT MAX(maNhanVien)+1 as max FROM nhan_vien")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("max");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
    public int checkEmail(String email,int id)
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nhan_vien where maNhanVien != '"+id+"' and email ='"+email+"'")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("maNhanVien");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
    public int checkPhone(String phone,int id)
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nhan_vien where maNhanVien != '"+id+"' and soDienThoai ='"+phone+"'")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maNhanVien");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
    public int getIDbyEmail(String email)
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT maNhanVien FROM nhan_vien where email ='"+email+"'")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("maNhanVien");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
}
