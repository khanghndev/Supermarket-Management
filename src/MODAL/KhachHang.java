package modal;

public class KhachHang {
    private Integer maKhachHang;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private Integer diemTichLuy;
    private String diaChi;
    private String ngayTao;
    public KhachHang() {}

    public KhachHang(Integer maKhachHang, String hoTen, String soDienThoai, String email, Integer diemTichLuy, String diaChi,String ngayTao) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diemTichLuy = diemTichLuy;
        this.diaChi = diaChi;
        this.ngayTao = ngayTao;
    }

    public Integer getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(Integer maKhachHang) { this.maKhachHang = maKhachHang; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getDiemTichLuy() { return diemTichLuy; }
    public void setDiemTichLuy(Integer diemTichLuy) { this.diemTichLuy = diemTichLuy; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }
}
