package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modal.ChiTietHoaDon;

public class ChiTietHoaDonDAO {

    public int insert(int maHoaDon,int maSanPham,int soLuong,double gia) {
        String sql = "INSERT INTO chi_tiet_hoa_don (maHoaDon, maSanPham, soLuong,gia) VALUES (?, ?, ?,?)";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ps.setInt(2, maSanPham);
            ps.setInt(3, soLuong);
            ps.setDouble(4,gia);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(int maHoaDon,int maSanPham,int soLuong) {
        String sql = "UPDATE chi_tiet_hoa_don SET soLuong = ? WHERE maHoaDon = ? and maSanPham = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maHoaDon);
            ps.setInt(3, maSanPham);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int update_quantity_product(int maSanPham,int soLuong) {
        String sql = "UPDATE san_pham SET SlTon += ? WHERE maSanPham = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maSanPham);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int maHoaDon,int maSanPham) {
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE maHoaDon = ? and maSanPham = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ps.setInt(2, maSanPham);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int delete_all(int maHoaDon) {
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE maHoaDon = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ChiTietHoaDon findById(int maCthd) {
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE maCthd = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maCthd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setMaCthd(rs.getInt("maCthd"));
                ct.setMaHoaDon(rs.getInt("maHoaDon"));
                ct.setMaSanPham(rs.getInt("maSanPham"));
                ct.setSoLuong(rs.getInt("soLuong"));
                return ct;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChiTietHoaDon> findByMaHoaDon(int maHoaDon) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE maHoaDon = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setMaCthd(rs.getInt("maCthd"));
                ct.setMaHoaDon(rs.getInt("maHoaDon"));
                ct.setMaSanPham(rs.getInt("maSanPham"));
                ct.setSoLuong(rs.getInt("soLuong"));
                ct.setGia(rs.getDouble("gia"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChiTietHoaDon> selectAll() {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_hoa_don";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setMaCthd(rs.getInt("maCthd"));
                ct.setMaHoaDon(rs.getInt("maHoaDon"));
                ct.setMaSanPham(rs.getInt("maSanPham"));
                ct.setSoLuong(rs.getInt("soLuong"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<String> selectAll_procedure(int mahd) {
    List<String> list = new ArrayList<>();
    String sql = "{call sp_LietKeChiTietHoaDon(?)}";
    try (Connection con = DBConnection.openConnection();
        CallableStatement cs = con.prepareCall(sql); ) 
    {
        cs.setInt(1, mahd);
        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                String s = "";
                s += rs.getString("maSanPham") + "#";
                s += rs.getString("tenSanPham") + "#";
                s += rs.getString("soLuong") + "#";
                s += String.format("%,.0f VND",rs.getDouble("gia")) + "#";
                s += String.format("%,.0f VND",rs.getDouble("TongTien"));
                list.add(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
