package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modal.SanPham;

public class SanPhamDAO {
     public int getNewID() {
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement("SELECT max(maSanPham)+1 as max FROM san_pham")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("max");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
                }
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM San_Pham";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("maSanPham"));
                sp.setTenSanPham(rs.getString("tenSanPham"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGia(rs.getDouble("gia"));
                sp.setLoaiSanPham(rs.getInt("loaiSanPham"));
                sp.setAnh(rs.getString("anh"));
                sp.setSlTon(rs.getInt("slTon"));
                sp.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                sp.setTrangThai(rs.getString("trangThai"));
                list.add(sp);
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<SanPham> getAll_Sale() {
        List<SanPham> list = new ArrayList<>();
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM San_Pham where trangThai != N'Ngừng bán'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("maSanPham"));
                sp.setTenSanPham(rs.getString("tenSanPham"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGia(rs.getDouble("gia"));
                sp.setLoaiSanPham(rs.getInt("loaiSanPham"));
                sp.setAnh(rs.getString("anh"));
                sp.setSlTon(rs.getInt("slTon"));
                sp.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                sp.setTrangThai(rs.getString("trangThai"));
                list.add(sp);
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public SanPham findById(int id) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM San_Pham WHERE maSanPham = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("maSanPham"));
                sp.setTenSanPham(rs.getString("tenSanPham"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGia(rs.getDouble("gia"));
                sp.setLoaiSanPham(rs.getInt("loaiSanPham"));
                sp.setAnh(rs.getString("anh"));
                sp.setSlTon(rs.getInt("slTon"));
                sp.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                sp.setTrangThai(rs.getString("trangThai"));
                return sp;
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getIdByName(String name) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT maSanPham FROM San_Pham WHERE tenSanPham like N'%"+name+"%'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maSanPham");
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
public List<SanPham> findByCategori(String name) {
        List<SanPham> list = new ArrayList<>();
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM San_Pham WHERE loaiSanPham =N'"+name+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("maSanPham"));
                sp.setTenSanPham(rs.getString("tenSanPham"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGia(rs.getDouble("gia"));
                sp.setLoaiSanPham(rs.getInt("loaiSanPham"));
                sp.setAnh(rs.getString("anh"));
                sp.setSlTon(rs.getInt("slTon"));
                sp.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                sp.setTrangThai(rs.getString("trangThai"));
                list.add(sp);
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
public List<SanPham> findBySupplier(int id) {
        List<SanPham> list = new ArrayList<>();
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM San_Pham WHERE maNhaCungCap ="+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("maSanPham"));
                sp.setTenSanPham(rs.getString("tenSanPham"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGia(rs.getDouble("gia"));
                sp.setLoaiSanPham(rs.getInt("loaiSanPham"));
                sp.setAnh(rs.getString("anh"));
                sp.setSlTon(rs.getInt("slTon"));
                sp.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                sp.setTrangThai(rs.getString("trangThai"));
                list.add(sp);
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int insert(SanPham sp) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "INSERT INTO san_pham (maSanPham,tenSanPham, moTa, gia, loaiSanPham, slTon, maNhaCungCap, trangThai,anh) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
            if(sp.getAnh()==null) 
                sql = "INSERT INTO san_pham (maSanPham,tenSanPham, moTa, gia, loaiSanPham, slTon, maNhaCungCap, trangThai,anh) VALUES (?, ?, ?, ?,?, ?, ?, ?, NULL)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sp.getMaSanPham());
            ps.setString(2, sp.getTenSanPham());
            ps.setString(3, sp.getMoTa());
            ps.setDouble(4, sp.getGia());
            ps.setInt(5, sp.getLoaiSanPham());
            ps.setInt(6, sp.getSlTon());
            ps.setInt(7, sp.getMaNhaCungCap());
            ps.setString(8, sp.getTrangThai());
            if(sp.getAnh()!=null) ps.setString(9, sp.getAnh());
            int result = ps.executeUpdate();
            ps.close(); con.close();
            return result;
        } 
        catch (Exception e) {
            e.printStackTrace();
            
        }
        return -1;
    }

    public int update(SanPham sp) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "UPDATE san_pham SET tenSanPham=?, moTa=?, gia=?, loaiSanPham=?, slTon=?, maNhaCungCap=?, trangThai=?,anh=? WHERE maSanPham="+sp.getMaSanPham();
            if(sp.getAnh()==null)   sql = "UPDATE san_pham SET tenSanPham=?, moTa=?, gia=?, loaiSanPham=?, slTon=?, maNhaCungCap=?, trangThai=?,anh=NULL WHERE maSanPham="+sp.getMaSanPham();;
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sp.getTenSanPham());
            ps.setString(2, sp.getMoTa());
            ps.setDouble(3, sp.getGia());
            ps.setInt(4, sp.getLoaiSanPham());
            ps.setInt(5, sp.getSlTon());
            ps.setInt(6, sp.getMaNhaCungCap());
            ps.setString(7, sp.getTrangThai());
            if(sp.getAnh()!= null)    ps.setString(8,sp.getAnh());
            int result = ps.executeUpdate();
            ps.close(); con.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
public int update_quantity(int masp,int qty) {
        try {
            Connection con = DBConnection.openConnection();
            String sql = "UPDATE san_pham SET slTon+= ? WHERE maSanPham="+masp;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, qty);
            int result = ps.executeUpdate();
            ps.close(); con.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<SanPham> searchProducts(String s) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM san_pham "+s;
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
   
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("maSanPham"));
                sp.setTenSanPham(rs.getString("tenSanPham"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGia(rs.getDouble("gia"));
                sp.setLoaiSanPham(rs.getInt("loaiSanPham"));
                sp.setAnh(rs.getString("anh"));
                sp.setSlTon(rs.getInt("slTon"));
                sp.setMaNhaCungCap(rs.getInt("maNhaCungCap"));
                sp.setTrangThai(rs.getString("trangThai"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return list;
    }
     public List<String> sales() {
        List<String> list = new ArrayList<>();
        String sql = "select masanpham, count(*) as sale from chi_tiet_hoa_don group by masanpham order by sale desc";
        try (Connection con = DBConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
   
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String temp = rs.getInt("maSanPham")+"#"+rs.getInt("sale");
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return list;
    }
}
