/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODAL.LoaiSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modal.SanPham;

/**
 *
 * @author Minh
 */
public class LoaiSanPhamDAO {
     public List<LoaiSanPham> getAll() {
        List<LoaiSanPham> list = new ArrayList<>();
        try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT * FROM Loai_San_Pham";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoaiSanPham sp = new LoaiSanPham();
                sp.setLoaiSanPham(rs.getInt("loaisanpham"));
                sp.setTenLoaiSanPham(rs.getString("tenloaisanpham"));
                list.add(sp);
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
     public int GetIDByName(String name)
     {
         try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT loaisanpham FROM loai_san_pham WHERE tenloaiSanPham like N'%"+name+"%'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("loaisanpham");
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
     }
     public String GetNameByID(int id)
     {
         try {
            Connection con = DBConnection.openConnection();
            String sql = "SELECT tenloaisanpham FROM loai_san_pham WHERE loaisanpham ="+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("tenloaisanpham");
            }
            rs.close(); ps.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
     }
}
