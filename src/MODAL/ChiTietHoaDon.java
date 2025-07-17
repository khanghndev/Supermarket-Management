package modal;

import java.math.BigDecimal;

public class ChiTietHoaDon {
    private Integer maCthd;
    private Integer maHoaDon;
    private Integer maSanPham;
    private Integer soLuong;
    private Double gia; 
    public ChiTietHoaDon() {}

    public ChiTietHoaDon(Integer maCthd, Integer maHoaDon, Integer maSanPham, Integer soLuong,Double gia) {
        this.maCthd = maCthd;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public Integer getMaCthd() { return maCthd; }
    public void setMaCthd(Integer maCthd) { this.maCthd = maCthd; }
    public Integer getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(Integer maHoaDon) { this.maHoaDon = maHoaDon; }
    public Integer getMaSanPham() { return maSanPham; }
    public void setMaSanPham(Integer maSanPham) { this.maSanPham = maSanPham; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public Double getGia() { return gia; }
    public void setGia(Double gia) { this.gia = gia; }
}
