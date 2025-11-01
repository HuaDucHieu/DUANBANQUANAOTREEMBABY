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
(N'Nguyễn Thị Lan', 'lan.nguyen@babi.vn', '123456', N'Nhân viên'),
(N'Phạm Văn Tùng', 'tung.pham@babi.vn', '123456', N'Nhân viên'),
(N'Lê Thị Hồng', 'hong.le@babi.vn', '123456', N'Thu ngân'),
(N'Vũ Hoàng Nam', 'nam.vu@babi.vn', '123456', N'Nhân viên'),
(N'Trần Thị Mai', 'mai.tran@babi.vn', '123456', N'Nhân viên'),
(N'Hoàng Văn Tài', 'tai.hoang@babi.vn', '123456', N'Kho hàng'),
(N'Lê Thị Ngọc', 'ngoc.le@babi.vn', '123456', N'Nhân viên'),
(N'Nguyễn Văn Hùng', 'hung.nguyen@babi.vn', '123456', N'Nhân viên'),
(N'Phạm Thị Thanh', 'thanh.pham@babi.vn', '123456', N'Thu ngân'),
(N'Vũ Minh Hoàng', 'hoang.vu@babi.vn', '123456', N'Nhân viên');

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
(N'Trần Bảo Ngọc', '0912345678'),
(N'Nguyễn Thị Thanh', '0911223344'),
(N'Phan Văn Bình', '0922334455'),
(N'Lê Thị Hồng', '0933445566'),
(N'Vũ Hoàng Nam', '0944556677'),
(N'Trần Thị Kim', '0955667788'),
(N'Hoàng Văn Tùng', '0966778899'),
(N'Lê Thị Mai', '0977889900'),
(N'Nguyễn Văn Hùng', '0988990011'),
(N'Phạm Thị Lan', '0999001122'),
(N'Vũ Thị Hạnh', '0900112233');

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
(N'Dồ mặc mùa hè', N'Áo, quần, váy mùa hè cho bé'),
(N'Dồ mặc mùa đông', N'Áo khoác, áo len, quần dài'),
(N'Đồ chơi', N'Đồ chơi trí tuệ và vận động'),
(N'Dụng cụ học tập', N'Sách, bút, vở, balo'),
(N'Thiết bị an toàn', N'Mũ bảo hiểm, găng tay, bảo vệ'),
(N'Phụ kiện thời trang', N'Khăn, mũ, vớ, giày dép'),
(N'Đồ ngủ', N'Đồ pijama cho bé'),
(N'Đồ bơi', N'Bikini, quần bơi, áo bơi'),
(N'Dụng cụ ăn uống', N'Bình sữa, thìa, bát, ly'),
(N'Túi xách trẻ em', N'Túi, balo cho bé');

-- ============================================
-- BẢNG MÀU SẮC
-- ============================================
CREATE TABLE MauSac (
    id_mau_sac INT PRIMARY KEY IDENTITY(1,1),
    ten_mau_sac NVARCHAR(50)
);

INSERT INTO MauSac (ten_mau_sac)
VALUES (N'Đỏ'), (N'Xanh dương'), (N'Hồng'), (N'Vàng'), (N'Trắng'), (N'Đen'),
(N'Cam'), (N'Tím'), (N'Hồng nhạt'), (N'Xanh lá cây'), (N'Be'),
       (N'Nâu'), (N'Xám'), (N'Hồng đậm'), (N'Xanh ngọc'), (N'Kem');

-- ============================================
-- BẢNG KÍCH THƯỚC
-- ============================================
CREATE TABLE KichThuoc (
    id_kich_thuoc INT PRIMARY KEY IDENTITY(1,1),
    ten_kich_thuoc NVARCHAR(50)
);

INSERT INTO KichThuoc (ten_kich_thuoc)
VALUES (N'S'), (N'M'), (N'L'), (N'XL'), (N'XXL'),
(N'3T'), (N'4T'), (N'5T'), (N'6T'), (N'7T'),
       (N'8T'), (N'9T'), (N'10T'), (N'11T'), (N'12T');

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
(N'Giày thể thao trẻ em', 220000, 35, 5, N'Còn hàng', 6, 3),
(N'Áo thun mùa hè bé trai', 120000, 50, 1, N'Còn hàng', 1, 1),
(N'Quần jean bé gái', 150000, 40, 2, N'Còn hàng', 2, 2),
(N'Váy mùa hè bé gái', 180000, 35, 1, N'Còn hàng', 3, 3),
(N'Áo khoác mùa đông', 250000, 20, 2, N'Còn hàng', 4, 4),
(N'Áo len bé trai', 200000, 25, 2, N'Còn hàng', 5, 5),
(N'Bộ đồ ngủ bé gái', 130000, 50, 7, N'Còn hàng', 6, 3),
(N'Bộ đồ bơi bé trai', 180000, 30, 8, N'Còn hàng', 7, 2),
(N'Túi đeo chéo bé gái', 50000, 60, 10, N'Còn hàng', 8, 1),
(N'Bình sữa an toàn', 300000, 40, 9, N'Còn hàng', 9, 1),
(N'Đồ chơi lego bé trai', 450000, 15, 3, N'Còn hàng', 10, 2);

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
(1, '2025-10-25', 450000, N'Tiền mặt', N'Đang xử lý'),
(2, '2025-10-26', 320000, N'Chuyển khoản', N'Đang xử lý'),
(3, '2025-10-27', 270000, N'Tiền mặt', N'Đã thanh toán'),
(4, '2025-10-28', 380000, N'Tiền mặt', N'Đang xử lý'),
(5, '2025-10-29', 420000, N'Chuyển khoản', N'Đã thanh toán'),
(6, '2025-10-20', 400000, N'Tiền mặt', N'Đang xử lý'),
(7, '2025-10-21', 350000, N'Chuyển khoản', N'Đã thanh toán'),
(8, '2025-10-22', 280000, N'Tiền mặt', N'Đang xử lý'),
(9, '2025-10-23', 500000, N'Chuyển khoản', N'Đã thanh toán'),
(10, '2025-10-24', 600000, N'Tiền mặt', N'Đã thanh toán');


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
(2, 3, 50, 180000),
(3, 2, 60, 250000),
(4, 4, 70, 30000),
(5, 5, 20, 150000),
(6, 6, 50, 220000),
(7, 3, 100, 180000),
(8, 1, 10, 120000),
(9, 7, 5, 150000),
(10, 2, 8, 250000);

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
