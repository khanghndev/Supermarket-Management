package modal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDon {
    private Integer maHoaDon;
    private Integer maKhachHang;
    private LocalDateTime ngayLap;
    private String trangThai;
    private Integer maNhanVien;

    public HoaDon() {}

    public HoaDon(Integer maHoaDon, Integer maKhachHang, LocalDateTime ngayLap, BigDecimal tongTien, String trangThai, Integer maNhanVien) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.ngayLap = ngayLap;
        this.trangThai = trangThai;
        this.maNhanVien = maNhanVien;
    }

    public Integer getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(Integer maHoaDon) { this.maHoaDon = maHoaDon; }
    public Integer getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(Integer maKhachHang) { this.maKhachHang = maKhachHang; }
    public LocalDateTime getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDateTime ngayLap) { this.ngayLap = ngayLap; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public Integer getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(Integer maNhanVien) { this.maNhanVien = maNhanVien; }
}
