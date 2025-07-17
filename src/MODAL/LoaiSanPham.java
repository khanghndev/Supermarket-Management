/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODAL;

/**
 *
 * @author Minh
 */
public class LoaiSanPham {
    private Integer loaisanpham;
    private String tenloaisanpham;

    public LoaiSanPham() {}

    public LoaiSanPham(Integer ma, String ten) {
        this.loaisanpham = ma;
        this.tenloaisanpham = ten;
    }

    public Integer getLoaiSanPham() { return loaisanpham; }
    public void setLoaiSanPham(Integer maSanPham) { this.loaisanpham = maSanPham; }
    public String getTenLoaiSanPham() { return tenloaisanpham; }
    public void setTenLoaiSanPham(String tenSanPham) { this.tenloaisanpham = tenSanPham; }
}
