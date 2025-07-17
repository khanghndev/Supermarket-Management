package modal;

import java.math.BigDecimal;

public class SanPham {
    private Integer maSanPham;
    private String tenSanPham;
    private String moTa;
    private Double gia;
    private int loaiSanPham;
    private String anh;
    private Integer slTon;
    private Integer maNhaCungCap;
    private String trangThai;

    public SanPham() {}

    public SanPham(Integer maSanPham, String tenSanPham, String moTa, Double gia, int loaiSanPham, String anh, Integer slTon, Integer maNhaCungCap, String trangThai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.gia = gia;
        this.loaiSanPham = loaiSanPham;
        this.anh = anh;
        this.slTon = slTon;
        this.maNhaCungCap = maNhaCungCap;
        this.trangThai = trangThai;
    }

    public Integer getMaSanPham() { return maSanPham; }
    public void setMaSanPham(Integer maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public Double getGia() { return gia; }
    public void setGia(Double gia) { this.gia = gia; }
    public int getLoaiSanPham() { return loaiSanPham; }
    public void setLoaiSanPham(int loaiSanPham) { this.loaiSanPham = loaiSanPham; }
    public String getAnh() { return anh; }
    public void setAnh(String anh) { this.anh = anh; }
    public Integer getSlTon() { return slTon; }
    public void setSlTon(Integer slTon) { this.slTon = slTon; }
    public Integer getMaNhaCungCap() { return maNhaCungCap; }
    public void setMaNhaCungCap(Integer maNhaCungCap) { this.maNhaCungCap = maNhaCungCap; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
