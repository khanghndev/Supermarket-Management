package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modal.ChiTietPhieuNhap;

public class ChiTietPhieuNhapDAO {

    public int insert(ChiTietPhieuNhap ct) {
        String sql = "INSERT INTO chi_tiet_phieu_nhap (maPhieuNhap, maSanPham, soLuongDat, giaNhap) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ct.getMaPhieuNhap());
            ps.setInt(2, ct.getMaSanPham());
            ps.setInt(3, ct.getSoLuongDat());
            ps.setInt(4, ct.getGiaNhap());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(ChiTietPhieuNhap ct) {
        String sql = "UPDATE chi_tiet_phieu_nhap SET maPhieuNhap = ?, maSanPham = ?, soLuongDat = ?, giaNhap = ? WHERE maCtPn = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ct.getMaPhieuNhap());
            ps.setInt(2, ct.getMaSanPham());
            ps.setInt(3, ct.getSoLuongDat());
            ps.setInt(4, ct.getGiaNhap());
            ps.setInt(5, ct.getMaCtPn());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int maCtPn) {
        String sql = "DELETE FROM chi_tiet_phieu_nhap WHERE maCtPn = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maCtPn);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
public int delete_all(int maPn) {
        String sql = "DELETE FROM chi_tiet_phieu_nhap WHERE maPhieuNhap = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maPn);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public ChiTietPhieuNhap findById(int maCtPn) {
        String sql = "SELECT * FROM chi_tiet_phieu_nhap WHERE maCtPn = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maCtPn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                ct.setMaCtPn(rs.getInt("maCtPn"));
                ct.setMaPhieuNhap(rs.getInt("maPhieuNhap"));
                ct.setMaSanPham(rs.getInt("maSanPham"));
                ct.setSoLuongDat(rs.getInt("soLuongDat"));
                ct.setGiaNhap(rs.getInt("giaNhap"));
                return ct;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
public List<ChiTietPhieuNhap> getListByID(int maCtPn) {
    List<ChiTietPhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_phieu_nhap WHERE maCtPn = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maCtPn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                ct.setMaCtPn(rs.getInt("maCtPn"));
                ct.setMaPhieuNhap(rs.getInt("maPhieuNhap"));
                ct.setMaSanPham(rs.getInt("maSanPham"));
                ct.setSoLuongDat(rs.getInt("soLuongDat"));
                ct.setGiaNhap(rs.getInt("giaNhap"));
                list.add(ct);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<ChiTietPhieuNhap> findByMaPhieuNhap(int maPhieuNhap) {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_phieu_nhap WHERE maPhieuNhap = "+maPhieuNhap;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                ct.setMaCtPn(rs.getInt("maCtPn"));
                ct.setMaPhieuNhap(rs.getInt("maPhieuNhap"));
                ct.setMaSanPham(rs.getInt("maSanPham"));
                ct.setSoLuongDat(rs.getInt("soLuongDat"));
                ct.setGiaNhap(rs.getInt("giaNhap"));
                list.add(ct);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChiTietPhieuNhap> selectAll() {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_phieu_nhap";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                ct.setMaCtPn(rs.getInt("maCtPn"));
                ct.setMaPhieuNhap(rs.getInt("maPhieuNhap"));
                ct.setMaSanPham(rs.getInt("maSanPham"));
                ct.setSoLuongDat(rs.getInt("soLuongDat"));
                ct.setGiaNhap(rs.getInt("giaNhap"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
