package modal;

public class NhaCungCap {
    private Integer maNhaCungCap;
    private String tenNcc;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String trangThai;

    public NhaCungCap() {}

    public NhaCungCap(Integer maNhaCungCap, String tenNcc, String diaChi, String soDienThoai, String email, String trangThai) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNcc = tenNcc;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.trangThai = trangThai;
    }

    public Integer getMaNhaCungCap() { return maNhaCungCap; }
    public void setMaNhaCungCap(Integer maNhaCungCap) { this.maNhaCungCap = maNhaCungCap; }
    public String getTenNcc() { return tenNcc; }
    public void setTenNcc(String tenNcc) { this.tenNcc = tenNcc; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
