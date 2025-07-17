package modal;

import java.time.LocalDate;

public class NhanVien {
    private Integer maNhanVien;
    private String matKhau;
    private String hoTen;
    private String chucVu;
    private String ngayTuyenDung;
    private String soDienThoai;
    private String email;
    private String ngaySinh;
    private Boolean gioiTinh;
    private String diaChi;
    private String anh;
private Integer trangthai;
    // No-arg constructor
    public NhanVien() {}

    // All-arg constructor
    public NhanVien(Integer maNhanVien, String matKhau, String hoTen, String chucVu, String ngayTuyenDung,
                    String soDienThoai, String email, String ngaySinh, Boolean gioiTinh, String diaChi, String anh,Integer trangthai) {
        this.maNhanVien = maNhanVien;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.ngayTuyenDung = ngayTuyenDung;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.anh = anh;
        this.trangthai = trangthai;
    }

    public Integer getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(Integer maNhanVien) { this.maNhanVien = maNhanVien; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
    public String getNgayTuyenDung() { return ngayTuyenDung; }
    public void setNgayTuyenDung(String ngayTuyenDung) { this.ngayTuyenDung = ngayTuyenDung; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public Boolean getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(Boolean gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getAnh() { return anh; }
    public void setAnh(String anh) { this.anh = anh; }
    public Integer getTrangThai() { return trangthai; }
    public void setTrangThai(Integer trangThai) { this.trangthai = trangThai; }
}
