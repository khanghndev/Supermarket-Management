package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modal.PhieuNhap;

public class PhieuNhapDAO {

    // Thêm Phiếu nhập mới
    public int insert(PhieuNhap pn) {
        String sql = "INSERT INTO phieu_nhap (maNhaCungCap, ngayDat, ngayNhan, nguoiDat, nguoiNhan, trangThai) VALUES (?, GETDATE(), NULL, ?, NULL, N'Chờ nhận')";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pn.getMaNhaCungCap());
            ps.setInt(2, pn.getNguoiDat());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

public int Confirm(int pn,int nn) {
        String sql = "UPDATE phieu_nhap SET trangThai = N'Đã nhận', nguoiNhan = "+nn+", ngayNhan = getDate() WHERE maPhieuNhap = "+pn;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    // Xóa Phiếu nhập
    public int delete(int maPhieuNhap) {
        String sql = "DELETE FROM phieu_nhap WHERE maPhieuNhap = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maPhieuNhap);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Tìm Phiếu nhập theo ID
    public PhieuNhap findById(int maPhieuNhap) {
        String sql = "SELECT * FROM phieu_nhap WHERE maPhieuNhap = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maPhieuNhap);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPhieuNhap(rs.getInt("maPhieuNhap"));
                pn.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                pn.setNgayDat(rs.getString("ngayDat"));
                pn.setNgayNhan(rs.getString("ngayNhan"));
                pn.setNguoiDat(rs.getInt("nguoiDat"));
                pn.setNguoiNhan(rs.getInt("nguoiNhan"));
                pn.setTrangThai(rs.getString("trangThai"));
                return pn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Lấy tất cả Phiếu nhập
    public int getNewID(){
         try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT max(maPhieuNhap) as max FROM phieu_nhap")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("max");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<PhieuNhap> selectAll_condition(String s) {
        List<PhieuNhap> list = new ArrayList<>();
        String sql = "SELECT pn.* FROM phieu_nhap pn, nha_cung_cap ncc "+s;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPhieuNhap(rs.getInt("maPhieuNhap"));
                pn.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                pn.setNgayDat(rs.getString("ngayDat"));
                pn.setNgayNhan(rs.getString("ngayNhan"));
                pn.setNguoiDat(rs.getInt("nguoiDat"));
                pn.setNguoiNhan(rs.getInt("nguoiNhan"));
                pn.setTrangThai(rs.getString("trangThai"));
                list.add(pn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<PhieuNhap> selectAll() {
        List<PhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM phieu_nhap ";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPhieuNhap(rs.getInt("maPhieuNhap"));
                pn.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                pn.setNgayDat(rs.getString("ngayDat"));
                pn.setNgayNhan(rs.getString("ngayNhan"));
                pn.setNguoiDat(rs.getInt("nguoiDat"));
                pn.setNguoiNhan(rs.getInt("nguoiNhan"));
                pn.setTrangThai(rs.getString("trangThai"));
                list.add(pn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
