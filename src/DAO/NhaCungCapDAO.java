package DAO;

import java.sql.*;
import java.util.*;
import modal.NhaCungCap;

public class NhaCungCapDAO {

    public List<NhaCungCap> getAll() {
        List<NhaCungCap> list = new ArrayList<>();
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nha_cung_cap");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                ncc.setTenNcc(rs.getString("tenNcc"));
                ncc.setDiaChi(rs.getString("diaChi"));
                ncc.setSoDienThoai(rs.getString("soDienThoai"));
                ncc.setEmail(rs.getString("email"));
                ncc.setTrangThai(rs.getString("trangThai"));
                list.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<NhaCungCap> getAll_condition(String s) {
        List<NhaCungCap> list = new ArrayList<>();
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nha_cung_cap "+s);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                ncc.setTenNcc(rs.getString("tenNcc"));
                ncc.setDiaChi(rs.getString("diaChi"));
                ncc.setSoDienThoai(rs.getString("soDienThoai"));
                ncc.setEmail(rs.getString("email"));
                ncc.setTrangThai(rs.getString("trangThai"));
                list.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getIDbyName(String name) {
        String sql = "SELECT maNhaCungCap FROM nha_cung_cap WHERE tenNcc = N'"+name+"'";
        System.out.println(sql);
        try (Connection con = DBConnection.openConnection();
          
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maNhaCungCap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
                }
    public NhaCungCap findById(int id) {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nha_cung_cap WHERE maNhaCungCap = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                ncc.setTenNcc(rs.getString("tenNcc"));
                ncc.setDiaChi(rs.getString("diaChi"));
                ncc.setSoDienThoai(rs.getString("soDienThoai"));
                ncc.setEmail(rs.getString("email"));
                ncc.setTrangThai(rs.getString("trangThai"));
                return ncc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhaCungCap> findByName(String name) {
        List<NhaCungCap> list = new ArrayList<>();
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nha_cung_cap WHERE tenNcc LIKE ?")) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                ncc.setTenNcc(rs.getString("tenNcc"));
                ncc.setDiaChi(rs.getString("diaChi"));
                ncc.setSoDienThoai(rs.getString("soDienThoai"));
                ncc.setEmail(rs.getString("email"));
                ncc.setTrangThai(rs.getString("trangThai"));
                list.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getNewID() {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT max(maNhaCungCap)+1 as max FROM nha_cung_cap ")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("max");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    public boolean insert(NhaCungCap ncc) {
        String sql = "INSERT INTO nha_cung_cap (maNhaCungCap,tenNcc, diaChi, soDienThoai, email, trangThai) VALUES (?, ?, ?, ?, ?,N'Hợp tác')";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ncc.getMaNhaCungCap());
            ps.setString(2, ncc.getTenNcc());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getSoDienThoai());
            ps.setString(5, ncc.getEmail());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhaCungCap ncc) {
        String sql = "UPDATE nha_cung_cap SET tenNcc=?, diaChi=?, soDienThoai=?, email=?, trangThai=? WHERE maNhaCungCap=?";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNcc());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getEmail());
            ps.setString(5, ncc.getTrangThai());
            ps.setInt(6, ncc.getMaNhaCungCap());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public int checkEmail(String email,int id)
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nha_cung_cap where maNhaCungCap != '"+id+"' and email ='"+email+"'")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               
                return rs.getInt("maNhaCungCap");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
    public int checkPhone(String phone,int id)
    {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM nha_cung_cap where maNhaCungCap != '"+id+"' and soDienThoai ='"+phone+"'")) {
             ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maNhaCungCap");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return 0;
    }
}
