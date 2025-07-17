package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modal.HoaDon;

public class HoaDonDAO {

    // Thêm hóa đơn
    public int insert(int staffID) {
        String sql = "INSERT INTO hoa_don (maKhachHang, ngayLap, trangThai, maNhanVien) VALUES (NULL, getDate(),N'Mua sắm',"+staffID+" )";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int update_makh(int idBill,int idCus) {
        String sql = "UPDATE hoa_don SET maKhachHang = ? WHERE maHoaDon = "+idBill;
        if(idCus==0)    sql = "UPDATE hoa_don SET maKhachHang = null WHERE maHoaDon = "+idBill;
        try (Connection con = DBConnection.openConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            if(idCus!=0)
            {
                ps.setInt(1, idCus);
            }
            
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Cập nhật hóa đơn
    public int update(HoaDon hoaDon,int idCus) {
        String sql = "UPDATE hoa_don SET ngayLap = getdate(), trangThai = N'Hoàn thành', maNhanVien = ? WHERE maHoaDon = ?";
        if(idCus !=-1)
            sql = "UPDATE hoa_don SET  maKhachHang = ?, ngayLap = getdate(), trangThai = N'Hoàn thành', maNhanVien = ? WHERE maHoaDon = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if(idCus !=-1)
            {
                ps.setInt(1, idCus);
                ps.setInt(2, hoaDon.getMaNhanVien());
                ps.setInt(3, hoaDon.getMaHoaDon());
            }
            else
            {
                ps.setInt(1, hoaDon.getMaNhanVien());
                ps.setInt(2, hoaDon.getMaHoaDon());
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Xóa hóa đơn
    public int delete(int maHoaDon) {
        String sql = "DELETE FROM hoa_don WHERE maHoaDon = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
// Tìm hóa đơn theo ID
    public HoaDon findById(int maHoaDon) {
        String sql = "SELECT * FROM hoa_don WHERE maHoaDon = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getInt("maHoaDon"));
                hoaDon.setMaKhachHang(rs.getInt("maKhachHang"));
                hoaDon.setNgayLap(rs.getTimestamp("ngayLap").toLocalDateTime());
                hoaDon.setTrangThai(rs.getString("trangThai"));
                hoaDon.setMaNhanVien(rs.getInt("maNhanVien"));
                return hoaDon;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Tìm hóa đơn moi tao
    public int getNew() {
        String sql = "SELECT TOP 1 * FROM hoa_don ORDER BY maHoaDon DESC;";
          try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
                if (rs.next()) { return rs.getInt("maHoaDon");}
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
          return 0;
    }
    public Double getSumPrice(int id)
    {
        String sql = "select sum(gia*soLuong) as sum from chi_tiet_hoa_don where maHoadon = "+id;
          try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
                if (rs.next()) { return rs.getDouble("sum");}
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
          return 0.0;

    }
    public int getCountBill_NoneCustomer()
    {
        String sql = "select count(mahoadon) as sum from hoa_don where makhachhang is null";
          try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
                if (rs.next()) { return rs.getInt("sum");}
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
          return 0;

    }
     public Double getAllSumPrice()
    {
        String sql = "select sum(gia*soLuong) as sum from chi_tiet_hoa_don ";
          try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
                if (rs.next()) { return rs.getDouble("sum");}
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
          return 0.0;

    }
      public Set<Integer> selectProduct(int id) {
        Set<Integer> list = new HashSet<>();
        String sql = "SELECT * FROM chi_tiet_hoa_don ct where maHoaDon = "+id;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getInt("maSanPham"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lấy tất cả hóa đơn
    public List<HoaDon> selectAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getInt("maHoaDon"));
                hoaDon.setMaKhachHang(rs.getInt("maKhachHang"));
                hoaDon.setNgayLap(rs.getTimestamp("ngayLap").toLocalDateTime());
                hoaDon.setTrangThai(rs.getString("trangThai"));
                hoaDon.setMaNhanVien(rs.getInt("maNhanVien"));
                list.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<HoaDon> selectAll_condition(String q) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don"+q;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getInt("maHoaDon"));
                hoaDon.setMaKhachHang(rs.getInt("maKhachHang"));
                hoaDon.setNgayLap(rs.getTimestamp("ngayLap").toLocalDateTime());
                hoaDon.setTrangThai(rs.getString("trangThai"));
                hoaDon.setMaNhanVien(rs.getInt("maNhanVien"));
                list.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<String> selectAll_condition_admin(String q) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT hd.maHoaDon, kh.hoTen AS kh, nv.hoTen AS nv," + 
                    "hd.ngayLap,dbo.TinhTongTienHoaDon(hd.maHoaDon) AS TongTien,hd.trangThai" +
                    
                    " FROM hoa_don hd LEFT JOIN khach_hang kh ON hd.maKhachHang = kh.maKhachHang "+
                    " JOIN nhan_vien nv ON nv.maNhanVien = hd.maNhanVien WHERE "+q;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
               String s ="";
                    s+=rs.getString("maHoaDon")+"#";
                    s+=rs.getString("kh")+"#";
                    s+=rs.getString("nv")+"#";
                    s+=rs.getString("ngayLap")+"#";
                    s+=rs.getString("TongTien")+"#";
                    s+=rs.getString("trangThai");
                    list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<HoaDon> selectAll_NonePaid() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don where trangThai = N'Mua sắm'";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getInt("maHoaDon"));
                hoaDon.setMaKhachHang(rs.getInt("maKhachHang"));
                hoaDon.setNgayLap(rs.getTimestamp("ngayLap").toLocalDateTime());
                hoaDon.setTrangThai(rs.getString("trangThai"));
                hoaDon.setMaNhanVien(rs.getInt("maNhanVien"));
                list.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<String> selectAll_procedure(String ngay) {
        List<String> list = new ArrayList<>();
        String sql = "{call sp_LietKeHoaDon(?)}";
        try (Connection con = DBConnection.openConnection();
        CallableStatement cs = con.prepareCall(sql); ) 
        {
            cs.setString(1, ngay);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String s ="";
                    s+=rs.getString("maHoaDon")+"#";
                    s+=rs.getString("kh")+"#";
                    s+=rs.getString("nv")+"#";
                    s+=rs.getString("ngayLap")+"#";
                    s+=rs.getString("TongTien")+"#";
                    s+=rs.getString("trangThai");
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return list;
    }
    
     public List<String> sales(int thang, int nam) {
    List<String> list = new ArrayList<>();
    String sql = "SELECT CAST(h.ngayLap AS DATE) AS ngay, SUM(ct.soLuong * ct.gia) AS sale " +
                 "FROM hoa_don h " +
                 "JOIN chi_tiet_hoa_don ct ON h.maHoaDon = ct.maHoaDon " +
                 "WHERE MONTH(h.ngayLap) = ? AND YEAR(h.ngayLap) = ? " +
                 "GROUP BY CAST(h.ngayLap AS DATE) " +
                 "ORDER BY ngay";

    try (Connection con = DBConnection.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, thang);
        ps.setInt(2, nam);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String temp = rs.getString("ngay") + "#" + rs.getInt("sale");
                list.add(temp);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
