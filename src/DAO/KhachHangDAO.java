package DAO;

import java.sql.*;
import java.util.*;
import modal.KhachHang;

public class KhachHangDAO {

    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM khach_hang");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("maKhachHang"));
                kh.setHoTen(rs.getString("hoTen"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                kh.setDiemTichLuy(rs.getInt("diemTichLuy"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setNgayTao(rs.getString("ngayTao"));
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
public List<KhachHang> getAll_condition(String q) {
        List<KhachHang> list = new ArrayList<>();
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM khach_hang "+q);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("maKhachHang"));
                kh.setHoTen(rs.getString("hoTen"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                kh.setDiemTichLuy(rs.getInt("diemTichLuy"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setNgayTao(rs.getString("ngayTao"));
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public KhachHang findById(int id) {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM khach_hang WHERE maKhachHang = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("maKhachHang"));
                kh.setHoTen(rs.getString("hoTen"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                kh.setDiemTichLuy(rs.getInt("diemTichLuy"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setNgayTao(rs.getString("ngayTao"));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int findIDByName(String name) {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT maKhachHang FROM khach_hang WHERE hoTen = N'"+name+"'")) {
 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("maKhachHang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<KhachHang> findByName(String name) {
        List<KhachHang> list = new ArrayList<>();
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM khach_hang WHERE hoTen LIKE ?")) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("maKhachHang"));
                kh.setHoTen(rs.getString("hoTen"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                kh.setDiemTichLuy(rs.getInt("diemTichLuy"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setNgayTao(rs.getString("ngayTao"));
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(KhachHang kh) {
        String sql = "INSERT INTO khach_hang (maKhachHang,hoTen, soDienThoai, email, diemTichLuy, diaChi,ngayTao) VALUES (?,?, ?, ?, 0, ?,getdate())";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,kh.getMaKhachHang());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getEmail());
            ps.setString(5, kh.getDiaChi());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(KhachHang kh) {
        String sql = "UPDATE khach_hang SET hoTen=?, soDienThoai=?, email=?,diaChi=? WHERE maKhachHang=?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getDiaChi());
            ps.setInt(5, kh.getMaKhachHang());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updatePoint(int id,int d) {
        String sql = "UPDATE khach_hang SET diemTichLuy+="+d+" WHERE maKhachHang="+id;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(int id) {
        String sql = "delete khach_hang WHERE maKhachHang="+id;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public int generateNewClientId()
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT MAX(maKhachHang)+1 as maKhachHang FROM khach_hang")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maKhachHang");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
    public int checkEmail(String email,int id)
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM khach_hang where maKhachHang != '"+id+"' and email ='"+email+"'")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("maKhachHang");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
    public int checkPhone(String phone,int id)
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM khach_hang where maKhachHang != '"+id+"' and soDienThoai ='"+phone+"'")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                 System.out.println(rs.getInt("maKhachHang"));
                return rs.getInt("maKhachHang");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }

    public List<String> getSaleByID(int id) {
        List<String> list = new ArrayList<>();
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT ct.maSanPham,sp.tenSanPham, ct.soLuong FROM chi_tiet_hoa_don ct, san_pham sp,hoa_don hd where ct.maSanPham = sp.maSanPham and ct.maHoaDon = hd.maHoaDon and hd.maKhachHang = "+id);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String k =rs.getString("maSanPham")+"#";
                k+= rs.getString("tenSanPham")+"#";
                k+= rs.getInt("soLuong");
                list.add(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
