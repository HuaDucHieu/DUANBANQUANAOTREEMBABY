CREATE DATABASE DUANBANQUANAOTREEMBABY;

USE DUANBANQUANAOTREEMBABY;

-- ===========================
-- BẢNG NHÂN VIÊN
-- ===========================
CREATE TABLE NhanVien (
    id_nhan_vien INT PRIMARY KEY IDENTITY(1,1),
    ho_ten NVARCHAR(100),
    email NVARCHAR(100) UNIQUE,
    mat_khau NVARCHAR(100),
    chuc_vu NVARCHAR(50)
);
Select * from NhanVien
-- ===========================
-- BẢNG KHÁCH HÀNG
-- ===========================
CREATE TABLE KhachHang (
    id_khach_hang INT PRIMARY KEY IDENTITY(1,1),
    ho_ten NVARCHAR(100),
    sdt NVARCHAR(15)
);
Select * from KhachHang
-- ===========================
-- BẢNG DANH MỤC
-- ===========================
CREATE TABLE DanhMuc (
    id_danh_muc INT PRIMARY KEY IDENTITY(1,1),
    ten_danh_muc NVARCHAR(100),
    mo_ta NVARCHAR(255)
);

-- ===========================
-- BẢNG MÀU SẮC
-- ===========================
CREATE TABLE MauSac (
    id_mau_sac INT PRIMARY KEY IDENTITY(1,1),
    ten_mau_sac NVARCHAR(50)
);

-- ===========================
-- BẢNG KÍCH THƯỚC
-- ===========================
CREATE TABLE KichThuoc (
    id_kich_thuoc INT PRIMARY KEY IDENTITY(1,1),
    ten_kich_thuoc NVARCHAR(50)
);

-- ===========================
-- BẢNG SẢN PHẨM
-- ===========================
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

-- ===========================
-- BẢNG HÓA ĐƠN
-- ===========================
CREATE TABLE HoaDon (
    id_hoa_don INT PRIMARY KEY IDENTITY(1,1),
    id_khach_hang INT,
    ngay_lap DATE,
    tong_tien DECIMAL(10,2),
    hinh_thuc_tt NVARCHAR(50),
    trang_thai NVARCHAR(50),
    FOREIGN KEY (id_khach_hang) REFERENCES KhachHang(id_khach_hang),
);

-- ===========================
-- BẢNG CHI TIẾT HÓA ĐƠN
-- ===========================
CREATE TABLE ChiTietHoaDon (
    id_chi_tiet_hoa_don INT PRIMARY KEY IDENTITY(1,1),
    id_hoa_don INT,
    id_sp INT,
    so_luong INT,
    FOREIGN KEY (id_hoa_don) REFERENCES HoaDon(id_hoa_don),
    FOREIGN KEY (id_sp) REFERENCES SanPham(id_sp)
);
-- Nhân viên
INSERT INTO NhanVien (ho_ten, email, mat_khau, chuc_vu)
VALUES
(N'Nguyễn Văn An', 'an.nguyen@babi.vn', '123456', N'Quản lý'),
(N'Trần Thị Bình', 'binh.tran@babi.vn', '123456', N'Nhân viên'),
(N'Lê Văn Cường', 'cuong.le@babi.vn', '123456', N'Nhân viên');

-- Khách hàng
INSERT INTO KhachHang (ho_ten, sdt)
VALUES
(N'Phạm Thu Hương', '0905123456'),
(N'Nguyễn Hoàng Long', '0933456789'),
(N'Lý Thị Mai', '0987765432');

-- Danh mục
INSERT INTO DanhMuc (ten_danh_muc, mo_ta)
VALUES
(N'Áo trẻ em', N'Các loại áo cho bé trai và bé gái'),
(N'Quần trẻ em', N'Các loại quần dành cho bé'),
(N'Phụ kiện', N'Nón, vớ, khăn cho bé');

-- Màu sắc
INSERT INTO MauSac (ten_mau_sac)
VALUES (N'Đỏ'), (N'Xanh dương'), (N'Hồng'), (N'Vàng');

-- Kích thước
INSERT INTO KichThuoc (ten_kich_thuoc)
VALUES (N'S'), (N'M'), (N'L'), (N'XL');

-- Sản phẩm
INSERT INTO SanPham (ten_sp, gia, so_luong, id_danh_muc, trang_thai, id_mau_sac, id_kich_thuoc)
VALUES
(N'Áo thun bé trai siêu nhân', 120000, 50, 1, N'Còn hàng', 1, 2),
(N'Áo khoác bé gái mùa đông', 250000, 30, 1, N'Còn hàng', 3, 3),
(N'Quần jean bé trai', 180000, 40, 2, N'Còn hàng', 2, 3),
(N'Vớ cotton trẻ em', 30000, 100, 3, N'Còn hàng', 4, 1);

-- Hóa đơn
INSERT INTO HoaDon (id_khach_hang, ngay_lap, tong_tien, hinh_thuc_tt, trang_thai)
VALUES
(1, '2025-10-15', 370000, N'Tiền mặt', N'Đã thanh toán'),
(2, '2025-10-16', 250000, N'Chuyển khoản', N'Đã thanh toán'),
(3, '2025-10-17', 30000, N'Tiền mặt', N'Đang xử lý');

-- Chi tiết hóa đơn
INSERT INTO ChiTietHoaDon (id_hoa_don, id_sp, so_luong)
VALUES
(1, 1, 1),
(1, 3, 1),
(2, 2, 1),
(3, 4, 1)
EXEC sp_MSforeachtable 'DROP TABLE ?';