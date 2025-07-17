package modal;

import java.time.LocalDate;

public class PhieuNhap {
    private Integer maPhieuNhap;
    private Integer maNhaCungCap;
    private String ngayDat;
    private String ngayNhan;
    private Integer nguoiDat;
    private Integer nguoiNhan;
    private String trangThai;

    public PhieuNhap() {}

    public PhieuNhap(Integer maPhieuNhap, Integer maNhaCungCap, String ngayDat, String ngayNhan, Integer nguoiDat, Integer nguoiNhan, String trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.maNhaCungCap = maNhaCungCap;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.nguoiDat = nguoiDat;
        this.nguoiNhan = nguoiNhan;
        this.trangThai = trangThai;
    }

    public Integer getMaPhieuNhap() { return maPhieuNhap; }
    public void setMaPhieuNhap(Integer maPhieuNhap) { this.maPhieuNhap = maPhieuNhap; }
    public Integer getMaNhaCungCap() { return maNhaCungCap; }
    public void setMaNhaCungCap(Integer maNhaCungCap) { this.maNhaCungCap = maNhaCungCap; }
    public String getNgayDat() { return ngayDat; }
    public void setNgayDat(String ngayDat) { this.ngayDat = ngayDat; }
    public String getNgayNhan() { return ngayNhan; }
    public void setNgayNhan(String ngayNhan) { this.ngayNhan = ngayNhan; }
    public Integer getNguoiDat() { return nguoiDat; }
    public void setNguoiDat(Integer nguoiDat) { this.nguoiDat = nguoiDat; }
    public Integer getNguoiNhan() { return nguoiNhan; }
    public void setNguoiNhan(Integer nguoiNhan) { this.nguoiNhan = nguoiNhan; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
