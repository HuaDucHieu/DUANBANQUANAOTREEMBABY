-- ============================================
-- TẠO MỚI DATABASE
-- ============================================
DROP DATABASE IF EXISTS DUANBANQUANAOTREEMBABY;
GO

CREATE DATABASE DUANBANQUANAOTREEMBABY;
GO

USE DUANBANQUANAOTREEMBABY;
GO

-- ============================================
-- BẢNG NHÂN VIÊN
-- ============================================
CREATE TABLE NhanVien (
    id_nhan_vien INT PRIMARY KEY IDENTITY(1,1),
    ho_ten NVARCHAR(100),
    email NVARCHAR(100) UNIQUE,
    mat_khau NVARCHAR(100),
    chuc_vu NVARCHAR(50)
);

INSERT INTO NhanVien (ho_ten, email, mat_khau, chuc_vu)
VALUES
(N'Nguyễn Văn An', 'an.nguyen@babi.vn', '123456', N'Quản lý'),
(N'Trần Thị Bình', 'binh.tran@babi.vn', '123456', N'Nhân viên'),
(N'Lê Văn Cường', 'cuong.le@babi.vn', '123456', N'Nhân viên'),
(N'Phạm Thị Hồng', 'hong.pham@babi.vn', '123456', N'Thu ngân'),
(N'Vũ Minh Khang', 'khang.vu@babi.vn', '123456', N'Kho hàng'),
(N'Nguyễn Thị Thanh', 'thanh.nguyen@babi.vn', '123456', N'Nhân viên'),
(N'Lê Hoàng Phúc', 'phuc.le@babi.vn', '123456', N'Thu ngân'),
(N'Phan Thị Yến', 'yen.phan@babi.vn', '123456', N'Kho hàng'),
(N'Đinh Văn Tùng', 'tung.dinh@babi.vn', '123456', N'Nhân viên'),
(N'Hoàng Minh Anh', 'minh.anh@babi.vn', '123456', N'Quản lý');
GO

-- ============================================
-- BẢNG KHÁCH HÀNG
-- ============================================
CREATE TABLE KhachHang (
    id_khach_hang INT PRIMARY KEY IDENTITY(1,1),
    ho_ten NVARCHAR(100),
    sdt NVARCHAR(15)
);

INSERT INTO KhachHang (ho_ten, sdt)
VALUES
(N'Nguyễn Minh Tuấn', '0911223344'),
(N'Hoàng Thị Lan', '0922334455'),
(N'Phạm Văn Duy', '0933445566'),
(N'Lê Thị Hồng Nhung', '0944556677'),
(N'Trần Văn Nam', '0955667788'),
(N'Đỗ Thị Mai', '0966778899'),
(N'Ngô Hoàng Sơn', '0977889900'),
(N'Vũ Thị Hạnh', '0988990011'),
(N'Bùi Văn Long', '0999001122'),
(N'Nguyễn Thị Kim', '0900112233');
GO

-- ============================================
-- BẢNG DANH MỤC
-- ============================================
CREATE TABLE DanhMuc (
    id_danh_muc INT PRIMARY KEY IDENTITY(1,1),
    ten_danh_muc NVARCHAR(100),
    mo_ta NVARCHAR(255)
);

INSERT INTO DanhMuc (ten_danh_muc, mo_ta)
VALUES
(N'Áo trẻ em', N'Các loại áo cho bé trai và bé gái'),
(N'Quần trẻ em', N'Các loại quần dành cho bé'),
(N'Phụ kiện', N'Nón, vớ, khăn cho bé'),
(N'Đồ bộ', N'Bộ đồ cho bé mặc ở nhà'),
(N'Giày dép', N'Các loại giày dép trẻ em'),
(N'Áo khoác mùa đông', N'Áo khoác ấm áp cho bé trai và bé gái'),
(N'Quần short', N'Quần short trẻ em mùa hè'),
(N'Balo & Túi xách', N'Túi xách, balo cho bé đi học'),
(N'Nón & Mũ', N'Nón, mũ chống nắng cho trẻ em'),
(N'Dép & Giày sandal', N'Dép, sandal cho bé đi chơi và đi học');
GO

-- ============================================
-- BẢNG MÀU SẮC
-- ============================================
CREATE TABLE MauSac (
    id_mau_sac INT PRIMARY KEY IDENTITY(1,1),
    ten_mau_sac NVARCHAR(50)
);

INSERT INTO MauSac (ten_mau_sac)
VALUES 
(N'Đỏ'), (N'Xanh dương'), (N'Hồng'), (N'Vàng'), (N'Trắng'), (N'Đen'),
(N'Tím'), (N'Cam'), (N'Xanh lá'), (N'Hồng nhạt'), (N'Nâu'), (N'Kem');
GO

-- ============================================
-- BẢNG KÍCH THƯỚC
-- ============================================
CREATE TABLE KichThuoc (
    id_kich_thuoc INT PRIMARY KEY IDENTITY(1,1),
    ten_kich_thuoc NVARCHAR(50)
);

INSERT INTO KichThuoc (ten_kich_thuoc)
VALUES 
(N'S'), (N'M'), (N'L'), (N'XL'), (N'XXL'),
(N'XXS'), (N'XXL+'), (N'3T'), (N'4T'), (N'5T'), (N'6T');
GO

-- ============================================
-- BẢNG SẢN PHẨM
-- ============================================
CREATE TABLE SanPham (
    id_sp INT PRIMARY KEY IDENTITY(1,1),
    ten_sp NVARCHAR(100),
    gia DECIMAL(10,2),
    so_luong INT,
    id_danh_muc INT,
    trang_thai NVARCHAR(50),
    id_mau_sac INT,
    id_kich_thuoc INT,
    FOREIGN KEY (id_danh_muc) REFERENCES DanhMuc(id_danh_muc),
    FOREIGN KEY (id_mau_sac) REFERENCES MauSac(id_mau_sac),
    FOREIGN KEY (id_kich_thuoc) REFERENCES KichThuoc(id_kich_thuoc)
);

INSERT INTO SanPham (ten_sp, gia, so_luong, id_danh_muc, trang_thai, id_mau_sac, id_kich_thuoc)
VALUES
(N'Áo len bé gái', 180000, 40, 1, N'Còn hàng', 3, 2),
(N'Quần legging bé gái', 150000, 60, 2, N'Còn hàng', 3, 3),
(N'Nón len trẻ em', 50000, 100, 3, N'Còn hàng', 4, 1),
(N'Bộ đồ thu bé trai', 220000, 35, 4, N'Còn hàng', 1, 2),
(N'Giày búp bê bé gái', 250000, 25, 5, N'Còn hàng', 3, 3),
(N'Áo khoác chống nắng', 200000, 45, 1, N'Còn hàng', 2, 3),
(N'Quần short bé trai', 120000, 50, 2, N'Còn hàng', 2, 2),
(N'Khăn len cho bé', 40000, 150, 3, N'Còn hàng', 4, 1),
(N'Bộ đồ mùa đông bé gái', 300000, 30, 4, N'Còn hàng', 3, 3),
(N'Giày thể thao bé trai', 280000, 20, 5, N'Còn hàng', 2, 3);
GO

-- ============================================
-- BẢNG HÓA ĐƠN
-- ============================================
CREATE TABLE HoaDon (
    id_hoa_don INT PRIMARY KEY IDENTITY(1,1),
    id_khach_hang INT,
    ngay_lap DATE,
    tong_tien DECIMAL(10,2),
    hinh_thuc_tt NVARCHAR(50),
    trang_thai NVARCHAR(50),
    FOREIGN KEY (id_khach_hang) REFERENCES KhachHang(id_khach_hang)
);

INSERT INTO HoaDon (id_khach_hang, ngay_lap, tong_tien, hinh_thuc_tt, trang_thai)
VALUES
(1, '2025-10-15', 370000, N'Tiền mặt', N'Đã thanh toán'),
(2, '2025-10-16', 250000, N'Chuyển khoản', N'Đã thanh toán'),
(3, '2025-10-17', 30000, N'Tiền mặt', N'Đang xử lý'),
(4, '2025-10-18', 700000, N'Tiền mặt', N'Đã thanh toán'),
(5, '2025-10-19', 500000, N'Chuyển khoản', N'Đang xử lý'),
(6, '2025-10-20', 380000, N'Tiền mặt', N'Đang xử lý'),
(7, '2025-10-21', 250000, N'Chuyển khoản', N'Đang xử lý'),
(8, '2025-10-22', 450000, N'Tiền mặt', N'Đã thanh toán'),
(9, '2025-10-23', 300000, N'Chuyển khoản', N'Đang xử lý'),
(10, '2025-10-24', 500000, N'Tiền mặt', N'Đã thanh toán');
GO

-- ============================================
-- BẢNG CHI TIẾT HÓA ĐƠN
-- ============================================
CREATE TABLE ChiTietHoaDon (
    id_chi_tiet_hoa_don INT IDENTITY(1,1) PRIMARY KEY,
    id_hoa_don INT NOT NULL,
    id_sp INT NOT NULL,
    so_luong INT NOT NULL,
    don_gia DECIMAL(18,2) NOT NULL,
    thanh_tien AS (so_luong * don_gia) PERSISTED,
    FOREIGN KEY (id_hoa_don) REFERENCES HoaDon(id_hoa_don),
    FOREIGN KEY (id_sp) REFERENCES SanPham(id_sp)
);

INSERT INTO ChiTietHoaDon (id_hoa_don, id_sp, so_luong, don_gia)
VALUES
(1, 1, 30, 120000),
(1, 3, 50, 180000),
(2, 2, 60, 250000),
(3, 4, 70, 30000),
(4, 5, 20, 150000),
(4, 6, 50, 220000),
(5, 3, 100, 180000),
(6, 7, 2, 120000),
(6, 9, 1, 300000),
(7, 2, 1, 150000),
(7, 3, 2, 50000),
(8, 5, 2, 250000),
(9, 1, 1, 180000),
(9, 4, 1, 120000),
(10, 6, 1, 200000),
(10, 8, 3, 40000),
(10, 10, 1, 280000);
GO

-- ============================================
-- KIỂM TRA TOÀN BỘ DỮ LIỆU
-- ============================================
SELECT * FROM NhanVien;
SELECT * FROM KhachHang;
SELECT * FROM DanhMuc;
SELECT * FROM MauSac;
SELECT * FROM KichThuoc;
SELECT * FROM SanPham;
SELECT * FROM HoaDon;
SELECT * FROM ChiTietHoaDon;
GO