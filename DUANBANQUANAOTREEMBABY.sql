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

-- ===========================
-- BẢNG KHÁCH HÀNG
-- ===========================
CREATE TABLE KhachHang (
    id_khach_hang INT PRIMARY KEY IDENTITY(1,1),
    ho_ten NVARCHAR(100),
    sdt NVARCHAR(15)
);

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
