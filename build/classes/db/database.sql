
-- 1. Tạo database và sử dụng
CREATE DATABASE quan_ly_sieu_thi 
GO
USE quan_ly_sieu_thi 
GO

-- Bảng nhân viên
CREATE TABLE nhan_vien (
    maNhanVien     INT  PRIMARY KEY,
    matKhau         VARCHAR(100)  NOT NULL,
    hoTen           NVARCHAR(255) NOT NULL,
    chucVu          NVARCHAR(100) NOT NULL,
    ngayTuyenDung  DATE          NOT NULL DEFAULT CONVERT(date, SYSUTCDATETIME()),
    soDienThoai    VARCHAR(20)   UNIQUE,
    email            NVARCHAR(255) UNIQUE,
    ngaySinh        DATE,
    gioiTinh        BIT           NOT NULL DEFAULT 0 CHECK (gioiTinh IN (0,1)),
    diaChi          NVARCHAR(500),
    anh              NVARCHAR(500),
	trangthai int
) 
GO

-- Bảng khách hàng
CREATE TABLE khach_hang (
    maKhachHang    INT PRIMARY KEY,
    hoTen           NVARCHAR(255) NOT NULL,
    soDienThoai    VARCHAR(20)   UNIQUE,
    email            NVARCHAR(255) UNIQUE,
    diemTichLuy    INT           NOT NULL DEFAULT 0,
	diaChi          NVARCHAR(500),
    ngayTao         DATETIME2     NOT NULL DEFAULT SYSUTCDATETIME()
) 
GO

-- Bảng nhà cung cấp
CREATE TABLE nha_cung_cap (
    maNhaCungCap  INT PRIMARY KEY,
    tenNcc          NVARCHAR(255) NOT NULL,
    diaChi          NVARCHAR(500),
    soDienThoai    VARCHAR(20),
    email            NVARCHAR(255),
	trangThai       NVARCHAR(50) DEFAULT N'Đang hoạt động'
) 
GO
CREATE TABLE loai_san_pham
(
	loaisanpham int identity(1,1) primary key not null,
	tenloaisanpham nvarchar(100)
)
GO
-- Bảng sản phẩm
CREATE TABLE san_pham (
    maSanPham      INT   PRIMARY KEY,
    tenSanPham     NVARCHAR(255) NOT NULL,
    moTa            NVARCHAR(MAX),
    gia              DECIMAL(10,2) NOT NULL CHECK (gia >= 0),
    loaiSanPham    INT,
    anh              NVARCHAR(500),
    slTon           INT           NOT NULL DEFAULT 0 CHECK (slTon >= 0),
    maNhaCungCap  INT           NULL,
    trangThai       NVARCHAR(50)  NOT NULL ,
	CONSTRAINT fk_sp_lsp FOREIGN KEY (loaiSanPham) REFERENCES loai_san_pham(loaiSanPham),
    CONSTRAINT fk_sp_ncc FOREIGN KEY (maNhaCungCap) REFERENCES nha_cung_cap(maNhaCungCap)
) 
GO

-- Bảng hóa đơn
CREATE TABLE hoa_don (
    maHoaDon       INT  IDENTITY(1,1) PRIMARY KEY,
    maKhachHang    INT          ,
    ngayLap         DATETIME2    DEFAULT SYSUTCDATETIME(),
    trangThai       NVARCHAR(50) DEFAULT N'Hoàn thành',
	maNhanVien     INT        ,
	CONSTRAINT fk_hd_nv FOREIGN KEY (maNhanVien) REFERENCES nhan_vien(maNhanVien),
    CONSTRAINT fk_hd_kh FOREIGN KEY (maKhachHang) REFERENCES khach_hang(maKhachHang)
) 
GO

-- Bảng chi tiết hóa đơn
CREATE TABLE chi_tiet_hoa_don (
    maCthd          INT           IDENTITY(1,1) PRIMARY KEY,
    maHoaDon       INT           NOT NULL,
    maSanPham      INT           NOT NULL,
    soLuong         INT           NOT NULL CHECK (soLuong > 0),
    gia     DECIMAL(10,2) NOT NULL CHECK (gia >= 0),
    CONSTRAINT fk_cthd_hd FOREIGN KEY (maHoaDon)
        REFERENCES hoa_don(maHoaDon),
    CONSTRAINT fk_cthd_sp FOREIGN KEY (maSanPham)
        REFERENCES san_pham(maSanPham)
) 
GO
-- Bảng phiếu nhập
CREATE TABLE phieu_nhap (
    maPhieuNhap     INT           IDENTITY(1,1) PRIMARY KEY,
    maNhaCungCap   INT           NOT NULL,
    ngayDat          DATE          NOT NULL DEFAULT CONVERT(date, SYSUTCDATETIME()),
    ngayNhan         DATE          NULL,
    nguoiDat         INT           NOT NULL,
    nguoiNhan        INT           NULL,
    trangThai        NVARCHAR(50)  NOT NULL 
    CONSTRAINT fk_pn_ncc      FOREIGN KEY (maNhaCungCap) REFERENCES nha_cung_cap(maNhaCungCap),
    CONSTRAINT fk_pn_nguoi_dat FOREIGN KEY (nguoiDat)       REFERENCES nhan_vien(maNhanVien),
    CONSTRAINT fk_pn_nguoi_nhan FOREIGN KEY (nguoiNhan)     REFERENCES nhan_vien(maNhanVien)
)
GO

-- Bảng chi tiết phiếu nhập
CREATE TABLE chi_tiet_phieu_nhap (
    maCtpn         INT           IDENTITY(1,1) PRIMARY KEY,
    maPhieuNhap    INT           NOT NULL,
    maSanPham      INT           NOT NULL,
    soLuongDat     INT           NOT NULL CHECK (soLuongDat > 0),
    giaNhap         DECIMAL(10,2) NOT NULL CHECK (giaNhap >= 0),
    CONSTRAINT fk_ctpn_pn FOREIGN KEY (maPhieuNhap) REFERENCES phieu_nhap(maPhieuNhap),
    CONSTRAINT fk_ctpn_sp FOREIGN KEY (maSanPham)   REFERENCES san_pham(maSanPham)
)
GO

---------------------------------------------------NHẬP LIỆU----------------------------------------------------
-- 1. Khách hàng
INSERT INTO khach_hang (maKhachHang,hoTen, soDienThoai, email, diemTichLuy, diaChi) VALUES
  (1,N'Nguyễn Văn An',   '0901123456', 'an@gmail.com',   150, N'123 Lê Lợi, Hà Nội'),
  (2,N'Trần Thị Bình',   '0912233445', 'binh@yahoo.com', 200, N'45 Nguyễn Trãi, Hà Nội'),
  (3,N'Lê Văn Cường',    '0987654321', 'cuong@vnpt.vn',   75, N'78 Trần Phú, Đà Nẵng'),
  (4,N'Phạm Thị Dung',   '0977123456', 'dung@fpt.com',    50, N'56 Hùng Vương, Cần Thơ'),
  (5,N'Hoàng Văn Em',    '0965432109', 'em@vietnampost.vn',120, N'99 Phan Đình Phùng, Hải Phòng');
GO

-- 2. Nhà cung cấp
INSERT INTO nha_cung_cap (maNhaCungCap,tenNcc, diaChi, soDienThoai, email, trangThai) VALUES
  (1,N'Công ty ABC Food',   N'10 Trần Hưng Đạo, Hà Nội','0247123456','contact@abcfood.vn',   N'Hợp tác'),
  (2,N'Công ty XYZ Drink',   N'5 Lê Duẩn, Đà Nẵng',     '0236378901','sales@xyzdrink.vn',    N'Hợp tác'),
  (3,N'Công ty FreshFarm',   N'12 Nguyễn Huệ, Hồ Chí Minh','0283912345','info@freshfarm.vn',   N'Hợp tác'),
  (4,N'Công ty GreenVeg',    N'20 Trần Quang Khải, Huế','0234387654','veggy@greenvg.vn',       N'Tạm ngưng'),
  (5,N'Công ty OceanSea',    N'3 Nguyễn Thị Minh Khai, Nha Trang','0258381726','sea@oceansea.vn',N'Hợp tác');
GO

-- 3. Nhân viên
INSERT INTO nhan_vien (maNhanVien,matKhau, hoTen, chucVu, ngayTuyenDung, soDienThoai, email, ngaySinh, gioiTinh, diaChi, anh,trangthai) VALUES
  (1,'1', N'Nguyễn Thị Hoa',     N'Quản lý',    '2019-01-15','0901000111','hoa@cty.vn','1985-03-20',0,N'123 Lý Thường Kiệt, Hà Nội',NULL,1),
  (2,'1',  N'Trần Văn Minh',      N'Thu ngân',   '2020-06-10','0912000222','minh@cty.vn','1990-07-05',1,N'45 Hai Bà Trưng, Đà Nẵng',NULL,1),
  (3,'1',  N'Lê Thị Lan',         N'Kho hàng',   '2021-03-25','0987000333','lan@cty.vn', '1992-11-11',0,N'78 Nguyễn Huệ, Hồ Chí Minh',NULL,1),
  (4,'1',  N'Phạm Văn Hưng',      N'Nhân viên',  '2022-09-01','0977000444','hung@cty.vn','1988-05-22',1,N'56 Trần Phú, Cần Thơ',NULL,1),
  (5,'1',  N'Hoàng Thị Mai',      N'Thu ngân',   '2023-02-14','0966000555','mai@cty.vn', '1995-12-30',0,N'99 Phan Đình Phùng, Hải Phòng',NULL,1);
GO
INSERT loai_san_pham VALUES
( N'Thực phẩm'),
(N'Nước giải khát'),
( N'Ngũ cốc'),
( N'Gia vị'),
( N'Sản phẩm sữa'),
(N'Món tráng miệng')
GO
INSERT INTO san_pham (maSanPham, tenSanPham, moTa, gia, loaiSanPham, anh, slTon, maNhaCungCap, trangThai) VALUES
(1, N'Bánh quy', N'Bánh quy giòn', 8000, 1, NULL, 150, 1, 1),
(2, N'Bột dinh dưỡng Bournvita', N'Bột dinh dưỡng pha uống', 40000, 2, NULL, 60, 1, 1),
(3, N'Bánh mì', N'Bánh mì tươi giòn', 5000,1, NULL, 100, 1, 1),
(4, N'Nước ngọt có ga', N'Nước ngọt có ga', 12000, 2, NULL, 110, 1, 1),
(5, N'Cà phê', N'Cà phê hòa tan', 18000,2, NULL, 100, 1, 1),
(6, N'Ngũ cốc Cornflakes', N'Ngũ cốc ăn sáng', 25000,3, NULL, 80, 1, 1),
(7, N'Mứt', N'Mứt trái cây', 15000, 3, NULL, 50, 1, 1),
(8, N'Mì ăn liền Maggi', N'Mì ăn liền', 6000,1, NULL, 120, 1, 1),
(9, N'Sữa tươi', N'Sữa tươi tiệt trùng', 12000,5, NULL, 200, 1, 1),
(10, N'Đường', N'Đường tinh luyện', 10000, 4, NULL, 130, 1, 1),
(11, N'Trà túi lọc', N'Trà túi lọc', 10000, 2, NULL, 90, 1, 1),
(12, N'Bơ', N'Bơ sữa tươi', 22000, 1, NULL, 70, 1, 1),
(13, N'Mật ong', N'Mật ong nguyên chất', 35000,1, NULL, 60, 1, 1),
(14, N'Phô mai', N'Phô mai lát Cheddar', 30000, 5, NULL, 90, 1, 1),
(15, N'Sữa chua', N'Sữa chua tự nhiên', 15000,5, NULL, 80, 1, 1),
(16, N'Sô cô la', N'Thanh sô cô la sữa', 20000, 6, NULL, 100, 1, 1),
(17, N'Kem', N'Kem vị vani', 45000, 6, NULL, 50, 1, 1),
(18, N'Trà xanh', N'Trà xanh hữu cơ', 12000,2, NULL, 100, 1, 1),
(19, N'Yến mạch', N'Yến mạch nguyên hạt', 28000, 3, NULL, 70, 1, 1),
(20, N'Bơ đậu phộng', N'Bơ đậu phộng mịn', 40000, 4, NULL, 60, 1, 1),
(21, N'Nước soda', N'Nước soda có gas', 10000, 2, NULL, 100, 1, 1),
(22, N'Khoai tây chiên', N'Khoai tây chiên giòn', 15000, 6, NULL, 120, 1, 1),
(23, N'Nước mắm', N'Nước mắm cao cấp', 18000, 5, NULL, 80, 1, 1),
(24, N'Nước tương', N'Nước tương nhạt', 16000,5, NULL, 90, 1, 1),
(25, N'Sốt Mayonnaise', N'Sốt mayonnaise béo ngậy', 25000, 4, NULL, 70, 1, 1),
(26, N'Sốt cà chua', N'Chai sốt cà chua', 18000, 4, NULL, 85, 1, 1),
(27, N'Trà đá', N'Trà chanh đá', 12000,2, NULL, 100, 1, 1),
(28, N'Dầu ăn thực vật', N'Dầu ăn tinh luyện', 45000, 1, NULL, 60, 1, 1),
(29, N'Tiêu', N'Tiêu xay nhuyễn', 12000, 4, NULL, 95, 1, 1),
(30, N'Sốt cà đặc', N'Sốt cà chua cô đặc', 20000, 4, NULL, 70, 1, 1);



GO
INSERT INTO hoa_don (maKhachHang, ngayLap, trangThai, maNhanVien) VALUES
(1,    '2025-05-01 09:00', N'Hoàn thành', 2),
(2,    '2025-05-01 10:00', N'Hoàn thành', 3),
(3,    '2025-05-02 11:00', N'Hoàn thành', 4),
(4,    '2025-05-03 12:00', N'Hoàn thành', 2),
(5,    '2025-05-04 09:00', N'Hoàn thành', 3),
(NULL, '2025-05-05 10:00', N'Hoàn thành', 4),
(1,    '2025-05-05 11:00', N'Hoàn thành', 2),
(2,    '2025-05-05 12:00', N'Hoàn thành', 3),
(3,    '2025-05-05 09:00', N'Hoàn thành', 4),
(4,    '2025-05-06 10:00', N'Hoàn thành', 2),
(5,    '2025-05-07 11:00', N'Hoàn thành', 3),
(NULL, '2025-05-07 12:00', N'Hoàn thành', 4),
(1,    '2025-05-08 09:00', N'Hoàn thành', 2),
(2,    '2025-05-08 10:00', N'Hoàn thành', 3),
(3,    '2025-05-09 11:00', N'Hoàn thành', 4),
(1,    '2025-05-09 09:00', N'Hoàn thành', 2),
(2,    '2025-05-09 10:00', N'Hoàn thành', 3),
(3,    '2025-05-10 11:00', N'Hoàn thành', 4),
(1,    '2025-05-11 09:00', N'Hoàn thành', 2),
(1,    '2025-05-12 09:00', N'Hoàn thành', 2),
(1,    '2025-05-12 09:00', N'Hoàn thành', 2),
(2,    '2025-05-12 10:00', N'Hoàn thành', 3),
(3,    '2025-05-12 11:00', N'Hoàn thành', 4),
(4,    '2025-05-12 12:00', N'Hoàn thành', 2),
(5,    '2025-05-12 09:00', N'Hoàn thành', 3),
(NULL, '2025-05-12 10:00', N'Hoàn thành', 4),
(1,    '2025-05-13 11:00', N'Hoàn thành', 2),
(2,    '2025-05-13 12:00', N'Hoàn thành', 3),
(3,    '2025-05-13 09:00', N'Hoàn thành', 4),
(4,    '2025-05-13 10:00', N'Hoàn thành', 2),
(5,    '2025-05-14 11:00', N'Hoàn thành', 3),
(NULL, '2025-05-14 12:00', N'Hoàn thành', 4),
(1,    '2025-05-14 09:00', N'Hoàn thành', 2),
(2,    '2025-05-15 10:00', N'Hoàn thành', 3),
(3,    '2025-05-15 11:00', N'Hoàn thành', 4),
(1,    '2025-05-15 09:00', N'Hoàn thành', 2),
(2,    '2025-05-16 10:00', N'Hoàn thành', 3),
(3,    '2025-05-16 11:00', N'Hoàn thành', 4),
(1,    '2025-05-17 09:00', N'Hoàn thành', 2),
(1,    '2025-05-17 09:00', N'Hoàn thành', 2),
(5,    '2025-05-17 11:00', N'Hoàn thành', 3),
(NULL, '2025-05-17 12:00', N'Hoàn thành', 4),
(1,    '2025-05-17 09:00', N'Hoàn thành', 2),
(2,    '2025-05-17 10:00', N'Hoàn thành', 3),
(3,    '2025-05-17 11:00', N'Hoàn thành', 4),
(1,    '2025-05-17 09:00', N'Hoàn thành', 2),
(2,    '2025-05-17 10:00', N'Hoàn thành', 3),
(3,    '2025-05-17 11:00', N'Hoàn thành', 4),
(1,    '2025-05-17 09:00', N'Hoàn thành', 2),
(1,    '2025-05-17 09:00', N'Hoàn thành', 2);
GO
INSERT INTO chi_tiet_hoa_don (maHoaDon, maSanPham, soLuong, gia) VALUES
(1, 27, 1, 12000),
(1, 1, 1, 8000),
(1, 15, 1, 15000),
(1, 8, 1, 6000),
(1, 29, 1, 12000),
(2, 4, 1, 12000),
(2, 3, 1, 5000),
(2, 9, 1, 12000),
(3, 1, 1, 8000),
(3, 28, 1, 45000),
(4, 2, 1, 40000),
(4, 25, 1, 25000),
(4, 17, 1, 45000),
(5, 15, 1, 15000),
(5, 25, 1, 25000),
(5, 3, 1, 5000),
(5, 22, 1, 15000),
(6, 6, 1, 25000),
(6, 3, 1, 5000),
(6, 13, 1, 35000),
(6, 10, 1, 10000),
(7, 10, 1, 10000),
(7, 9, 1, 12000),
(7, 3, 1, 5000),
(8, 20, 1, 40000),
(8, 7, 1, 15000),
(9, 26, 1, 18000),
(9, 29, 1, 12000),
(9, 18, 1, 12000),
(10, 17, 1, 45000),
(10, 23, 1, 18000),
(10, 18, 1, 12000),
(10, 5, 1, 18000),
(11, 20, 1, 40000),
(11, 27, 1, 12000),
(11, 22, 1, 15000),
(11, 10, 1, 10000),
(11, 23, 1, 18000),
(12, 27, 1, 12000),
(12, 3, 1, 5000),
(13, 10, 1, 10000),
(13, 19, 1, 28000),
(13, 1, 1, 8000),
(14, 30, 1, 20000),
(14, 11, 1, 10000),
(14, 7, 1, 15000),
(15, 11, 1, 10000),
(15, 12, 1, 22000),
(15, 1, 1, 8000),
(16, 12, 1, 22000),
(16, 18, 1, 12000),
(17, 7, 1, 15000),
(17, 2, 1, 40000),
(17, 18, 1, 12000),
(17, 16, 1, 20000),
(17, 13, 1, 35000),
(18, 2, 1, 40000),
(18, 19, 1, 28000),
(18, 20, 1, 40000),
(18, 5, 1, 18000),
(19, 16, 1, 20000),
(19, 23, 1, 18000),
(19, 30, 1, 20000),
(20, 27, 1, 12000),
(20, 23, 1, 18000),
(20, 26, 1, 18000),
(21, 8, 1, 6000),
(21, 20, 1, 40000),
(22, 12, 1, 22000),
(22, 15, 1, 15000),
(22, 26, 1, 18000),
(22, 6, 1, 25000),
(23, 14, 1, 30000),
(23, 18, 1, 12000),
(23, 8, 1, 6000),
(23, 13, 1, 35000),
(23, 10, 1, 10000),
(24, 2, 1, 40000),
(24, 23, 1, 18000),
(24, 24, 1, 16000),
(24, 19, 1, 28000),
(25, 12, 1, 22000),
(25, 6, 1, 25000),
(25, 11, 1, 10000),
(26, 19, 1, 28000),
(26, 15, 1, 15000),
(26, 14, 1, 30000),
(26, 26, 1, 18000),
(26, 8, 1, 6000),
(27, 19, 1, 28000),
(27, 17, 1, 45000),
(27, 13, 1, 35000),
(27, 2, 1, 40000),
(28, 1, 1, 8000),
(28, 10, 1, 10000),
(29, 1, 1, 8000),
(29, 29, 1, 12000),
(29, 26, 1, 18000),
(30, 12, 1, 22000),
(30, 28, 1, 45000),
(30, 29, 1, 12000),
(30, 18, 1, 12000),
(30, 23, 1, 18000),
(31, 8, 1, 6000),
(31, 11, 1, 10000),
(32, 21, 1, 10000),
(32, 28, 1, 45000),
(32, 2, 1, 40000),
(32, 27, 1, 12000),
(33, 17, 1, 45000),
(33, 3, 1, 5000),
(34, 28, 1, 45000),
(34, 13, 1, 35000),
(34, 16, 1, 20000),
(35, 5, 1, 18000),
(35, 13, 1, 35000),
(35, 18, 1, 12000),
(35, 26, 1, 18000),
(35, 15, 1, 15000),
(36, 12, 1, 22000),
(36, 18, 1, 12000),
(37, 15, 1, 15000),
(37, 16, 1, 20000),
(38, 28, 1, 45000),
(38, 26, 1, 18000),
(38, 4, 1, 12000),
(39, 29, 1, 12000),
(39, 20, 1, 40000),
(39, 30, 1, 20000),
(39, 26, 1, 18000),
(39, 16, 1, 20000),
(40, 8, 1, 6000),
(40, 13, 1, 35000),
(40, 9, 1, 12000),
(40, 2, 1, 40000),
(40, 5, 1, 18000),
(41, 9, 1, 12000),
(41, 11, 1, 10000),
(41, 14, 1, 30000),
(42, 28, 1, 45000),
(42, 20, 1, 40000),
(43, 18, 1, 12000),
(43, 28, 1, 45000),
(43, 5, 1, 18000),
(43, 29, 1, 12000),
(44, 5, 1, 18000),
(44, 27, 1, 12000),
(44, 21, 1, 10000),
(45, 20, 1, 40000),
(45, 8, 1, 6000),
(45, 2, 1, 40000),
(46, 30, 1, 20000),
(46, 4, 1, 12000),
(46, 8, 1, 6000),
(46, 21, 1, 10000),
(47, 14, 1, 30000),
(47, 22, 1, 15000),
(47, 27, 1, 12000),
(48, 8, 1, 6000),
(48, 25, 1, 25000),
(48, 7, 1, 15000),
(48, 5, 1, 18000),
(49, 6, 1, 25000),
(49, 15, 1, 15000),
(49, 29, 1, 12000),
(50, 1, 1, 8000),
(50, 11, 1, 10000),
(50, 12, 1, 22000),
(50, 20, 1, 40000);
GO
INSERT INTO phieu_nhap VALUES
  (1, '2025-03-25', '2025-03-28', 1, 2, N'Đã nhận'),
  (2, '2025-03-26', '2025-03-29', 1, 3, N'Đã nhận'),
  (3, '2025-03-27', NULL,       2, NULL, N'Chờ nhận');
GO

INSERT INTO phieu_nhap VALUES
  (1, '2025-03-25', '2025-03-28', 1, 2, N'Đã nhận'),
  (2, '2025-03-26', '2025-03-29', 1, 3, N'Đã nhận'),
  (3, '2025-03-27', NULL,       2, NULL, N'Chờ nhận');
GO

-- 13. Chi tiết phiếu nhập
INSERT INTO chi_tiet_phieu_nhap VALUES
(1, 3, 10, 10000),
(1, 2, 10, 15000),
(1, 1, 10, 30000),
(2, 3, 10, 10000),
(2, 4, 10, 40000),
(2, 2, 10, 15000),
(3, 5, 10, 12000),
(3, 6, 10, 35000),
(3, 7, 10, 18000);
GO

CREATE FUNCTION dbo.TinhTongTienHoaDon(@maHoaDon INT)
RETURNS DECIMAL(18, 2)
AS
BEGIN
    DECLARE @tong DECIMAL(18, 2)

    SELECT @tong = SUM(ct.gia * ct.soLuong)
    FROM hoa_don hd
    JOIN chi_tiet_hoa_don ct ON hd.maHoaDon = ct.maHoaDon
    WHERE hd.maHoaDon = @maHoaDon

    RETURN ISNULL(@tong, 0)
END
go

CREATE FUNCTION dbo.TinhTongTienPhieuNhap(@maPhieuNhap INT)
RETURNS DECIMAL(18, 2)
AS
BEGIN
    DECLARE @tong DECIMAL(18, 2)

    SELECT @tong = SUM(giaNhap * soLuongDat)
    FROM chi_tiet_phieu_nhap 
    WHERE maPhieuNhap = @maPhieuNhap

    RETURN ISNULL(@tong, 0)
END

go

CREATE PROCEDURE sp_LietKeHoaDon
    @ngayLap DATE = NULL
AS
BEGIN
    SELECT 
        hd.maHoaDon, 
        kh.hoTen AS kh, 
        nv.hoTen AS nv,
        hd.ngayLap,
        dbo.TinhTongTienHoaDon(hd.maHoaDon) AS TongTien,
        hd.trangThai
    FROM hoa_don hd
    LEFT JOIN khach_hang kh ON hd.maKhachHang = kh.maKhachHang
    JOIN nhan_vien nv ON nv.maNhanVien = hd.maNhanVien
    WHERE 
        (@ngayLap IS NULL OR CAST(hd.ngayLap AS DATE) = @ngayLap) 
END

GO
CREATE PROCEDURE sp_LietKeChiTietHoaDon
    @maHD INT
AS
BEGIN
    SELECT 
        sp.maSanPham,
        sp.tenSanPham,
        ct.soLuong,
        ct.gia,
        ct.gia * ct.soLuong AS TongTien
    FROM chi_tiet_hoa_don ct
    JOIN san_pham sp ON ct.maSanPham = sp.maSanPham
    JOIN hoa_don hd ON ct.maHoaDon = hd.maHoaDon
    WHERE ct.maHoaDon = @maHD
END
GO

  