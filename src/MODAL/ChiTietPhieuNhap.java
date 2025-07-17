package modal;

import java.math.BigDecimal;

public class ChiTietPhieuNhap {
    private Integer maCtPn;
    private Integer maPhieuNhap;
    private Integer maSanPham;
    private Integer soLuongDat;
    private Integer giaNhap;

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(Integer maCtPn, Integer maPhieuNhap, Integer maSanPham, Integer soLuongDat, Integer giaNhap) {
        this.maCtPn = maCtPn;
        this.maPhieuNhap = maPhieuNhap;
        this.maSanPham = maSanPham;
        this.soLuongDat = soLuongDat;
        this.giaNhap = giaNhap;
    }

    public Integer getMaCtPn() { return maCtPn; }
    public void setMaCtPn(Integer maCtPn) { this.maCtPn = maCtPn; }
    public Integer getMaPhieuNhap() { return maPhieuNhap; }
    public void setMaPhieuNhap(Integer maPhieuNhap) { this.maPhieuNhap = maPhieuNhap; }
    public Integer getMaSanPham() { return maSanPham; }
    public void setMaSanPham(Integer maSanPham) { this.maSanPham = maSanPham; }
    public Integer getSoLuongDat() { return soLuongDat; }
    public void setSoLuongDat(Integer soLuongDat) { this.soLuongDat = soLuongDat; }
    public Integer getGiaNhap() { return giaNhap; }
    public void setGiaNhap(Integer giaNhap) { this.giaNhap = giaNhap; }
}
