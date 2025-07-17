package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modal.LichSuHoatDong;

public class LichSuHoatDongDAO {

    // Thêm lịch sử hoạt động
    public int insert(LichSuHoatDong lshd) {
        String sql = "INSERT INTO LichSuHoatDong (maNhanVien, moTa, thoiGian) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, lshd.getMaNhanVien());
            ps.setString(2, lshd.getMoTa());
            ps.setTimestamp(3, Timestamp.valueOf(lshd.getThoiGian()));

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Cập nhật lịch sử hoạt động
    public int update(LichSuHoatDong lshd) {
        String sql = "UPDATE LichSuHoatDong SET maNhanVien = ?, moTa = ?, thoiGian = ? WHERE maLs = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, lshd.getMaNhanVien());
            ps.setString(2, lshd.getMoTa());
            ps.setTimestamp(3, Timestamp.valueOf(lshd.getThoiGian()));
            ps.setInt(4, lshd.getMaLs());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Xóa lịch sử hoạt động theo maLs
    public int delete(int maLs) {
        String sql = "DELETE FROM LichSuHoatDong WHERE maLs = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maLs);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Tìm lịch sử hoạt động theo ID
    public LichSuHoatDong findById(int maLs) {
        String sql = "SELECT * FROM LichSuHoatDong WHERE maLs = ?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, maLs);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LichSuHoatDong lshd = new LichSuHoatDong();
                lshd.setMaLs(rs.getInt("maLs"));
                lshd.setMaNhanVien(rs.getInt("maNhanVien"));
                lshd.setMoTa(rs.getString("moTa"));
                lshd.setThoiGian(rs.getTimestamp("thoiGian").toLocalDateTime());
                return lshd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả lịch sử hoạt động
    public List<LichSuHoatDong> selectAll() {
        List<LichSuHoatDong> list = new ArrayList<>();
        String sql = "SELECT * FROM LichSuHoatDong";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LichSuHoatDong lshd = new LichSuHoatDong();
                lshd.setMaLs(rs.getInt("maLs"));
                lshd.setMaNhanVien(rs.getInt("maNhanVien"));
                lshd.setMoTa(rs.getString("moTa"));
                lshd.setThoiGian(rs.getTimestamp("thoiGian").toLocalDateTime());
                list.add(lshd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
