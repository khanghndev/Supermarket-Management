package modal;

import java.time.LocalDateTime;

public class LichSuHoatDong {
    private Integer maLs;
    private Integer maNhanVien;
    private String moTa;
    private LocalDateTime thoiGian;

    public LichSuHoatDong() {}

    public LichSuHoatDong(Integer maLs, Integer maNhanVien, String moTa, LocalDateTime thoiGian) {
        this.maLs = maLs;
        this.maNhanVien = maNhanVien;
        this.moTa = moTa;
        this.thoiGian = thoiGian;
    }

    public Integer getMaLs() { return maLs; }
    public void setMaLs(Integer maLs) { this.maLs = maLs; }
    public Integer getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(Integer maNhanVien) { this.maNhanVien = maNhanVien; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public LocalDateTime getThoiGian() { return thoiGian; }
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }
}
