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
(N'Vũ Minh Khang', 'khang.vu@babi.vn', '123456', N'Kho hàng');

SELECT * FROM NhanVien;
-- DROP TABLE NhanVien;
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
(N'Phạm Thu Hương', '0905123456'),
(N'Nguyễn Hoàng Long', '0933456789'),
(N'Lý Thị Mai', '0987765432'),
(N'Đặng Hữu Phát', '0978345678'),
(N'Trần Bảo Ngọc', '0912345678');

SELECT * FROM KhachHang;
-- DROP TABLE KhachHang;
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
(N'Giày dép', N'Các loại giày dép trẻ em');

SELECT * FROM DanhMuc;
-- DROP TABLE DanhMuc;
GO

-- ============================================
-- BẢNG MÀU SẮC
-- ============================================
CREATE TABLE MauSac (
    id_mau_sac INT PRIMARY KEY IDENTITY(1,1),
    ten_mau_sac NVARCHAR(50)
);

INSERT INTO MauSac (ten_mau_sac)
VALUES (N'Đỏ'), (N'Xanh dương'), (N'Hồng'), (N'Vàng'), (N'Trắng'), (N'Đen');

SELECT * FROM MauSac;
-- DROP TABLE MauSac;
GO

-- ============================================
-- BẢNG KÍCH THƯỚC
-- ============================================
CREATE TABLE KichThuoc (
    id_kich_thuoc INT PRIMARY KEY IDENTITY(1,1),
    ten_kich_thuoc NVARCHAR(50)
);

INSERT INTO KichThuoc (ten_kich_thuoc)
VALUES (N'S'), (N'M'), (N'L'), (N'XL'), (N'XXL');

SELECT * FROM KichThuoc;
-- DROP TABLE KichThuoc;
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
(N'Áo thun bé trai siêu nhân', 120000, 50, 1, N'Còn hàng', 1, 2),
(N'Áo khoác bé gái mùa đông', 250000, 30, 1, N'Còn hàng', 3, 3),
(N'Quần jean bé trai', 180000, 40, 2, N'Còn hàng', 2, 3),
(N'Vớ cotton trẻ em', 30000, 100, 3, N'Còn hàng', 4, 1),
(N'Đồ bộ mùa hè bé trai', 150000, 60, 4, N'Còn hàng', 5, 2),
(N'Giày thể thao trẻ em', 220000, 35, 5, N'Còn hàng', 6, 3);

SELECT * FROM SanPham;
-- DROP TABLE SanPham;
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
(5, '2025-10-19', 500000, N'Chuyển khoản', N'Đang xử lý');

SELECT * FROM HoaDon;
-- DROP TABLE HoaDon;
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
(5, 3, 100, 180000);

SELECT * FROM ChiTietHoaDon;
-- DROP TABLE ChiTietHoaDon;
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
