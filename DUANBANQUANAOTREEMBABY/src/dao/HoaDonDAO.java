/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utils.ConnectDB;
import entity.HoaDonEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectDB;

/**
 *
 * @author Tran Tien
 */
public class HoaDonDAO {

    // ✅ DAO: lấy toàn bộ hóa đơn kèm tên khách hàng
    public List<HoaDonEntity> getAll() {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = """
        SELECT hd.id_hoa_don, hd.ngay_lap, hd.tong_tien, hd.trang_thai, kh.ho_ten AS ten_khach_hang
        FROM HoaDon hd
        LEFT JOIN KhachHang kh ON hd.id_khach_hang = kh.id_khach_hang
        ORDER BY hd.id_hoa_don ASC
    """;

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity();
                hd.setIdHoaDon(rs.getInt("id_hoa_don"));
                hd.setTenKhachHang(rs.getString("ten_khach_hang"));
                hd.setNgayLap(rs.getString("ngay_lap"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTrangThai(rs.getString("trang_thai"));
                list.add(hd);
            }

        } catch (Exception e) {
            System.out.println("Lỗi getHoaDon: " + e.getMessage());
        }
        return list;
    }

    public List<HoaDonEntity> getHoaDonTK() {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = "SELECT id_hoa_don, id_khach_hang, ngay_lap, tong_tien, trang_thai "
                + "FROM HoaDon ORDER BY id_hoa_don ASC";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity();
                hd.setIdHoaDon(rs.getInt("id_hoa_don"));
                hd.setIdKhachHang(rs.getInt("id_khach_hang"));
                hd.setNgayLap(rs.getString("ngay_lap")); // giữ String nhưng parse đúng format sau
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTrangThai(rs.getString("trang_thai"));
                list.add(hd);
            }

        } catch (Exception e) {
            System.out.println("Lỗi getAll HoaDonThongKe: " + e.getMessage());
        }

        return list;
    }

    public List<HoaDonEntity> getHoaDonCho() {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = "SELECT id_hoa_don, ngay_lap, trang_thai "
                + "FROM HoaDon WHERE trang_thai = N'Đang xử lý' ";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity();
                hd.setIdHoaDon(rs.getInt("id_hoa_don"));
                hd.setNgayLap(rs.getString("ngay_lap"));
                hd.setTrangThai(rs.getString("trang_thai"));
                list.add(hd);
            }

        } catch (Exception e) {
            System.out.println("Lỗi getHoaDonCho: " + e.getMessage());
        }
        return list;
    }

    public void insert2(HoaDonEntity hd) {
        String sql = "INSERT INTO HoaDon (id_khach_hang, ngay_lap, tong_tien, hinh_thuc_tt, trang_thai) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, hd.getIdKhachHang());
            ps.setString(2, hd.getNgayLap());
            ps.setDouble(3, hd.getTongTien());
            ps.setString(4, hd.getHinhThucTT());
            ps.setString(5, hd.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert HoaDon: " + e.getMessage());
        }
    }

    public void update(HoaDonEntity hd) {
        String sql = "UPDATE HoaDon SET ngay_lap=?, tong_tien=?, hinh_thuc_tt=?, trang_thai=? WHERE id_hoa_don=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hd.getNgayLap());
            ps.setDouble(2, hd.getTongTien());
            ps.setString(3, hd.getHinhThucTT());
            ps.setString(4, hd.getTrangThai());
            ps.setInt(5, hd.getIdHoaDon());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update HoaDon: " + e.getMessage());
        }
    }

    public void delete(int idHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE id_hoa_don=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHoaDon);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete HoaDon: " + e.getMessage());
        }
    }

    public double getTongDoanhThu() {
        double tong = 0;
        String sql = "SELECT SUM(tong_tien) AS tong_tien FROM HoaDon WHERE trang_thai = N'Đã thanh toán'";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                tong = rs.getDouble("tong_tien");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getTongDoanhThu: " + e.getMessage());
        }
        return tong;
    }

    public int getTongSoHoaDon() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getSoHoaDonDaThanhToan() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE trang_thai = N'Đã thanh toán'";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<HoaDonEntity> searchByDate(String ngayLap) {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = "SELECT hd.*, kh.ho_ten AS ten_khach_hang "
                + "FROM HoaDon hd "
                + "JOIN KhachHang kh ON hd.id_khach_hang = kh.id_khach_hang "
                + "WHERE hd.ngay_lap = ?";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ngayLap);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDonEntity hd = new HoaDonEntity(
                            rs.getInt("id_hoa_don"),
                            rs.getInt("id_khach_hang"),
                            rs.getString("ngay_lap"),
                            rs.getDouble("tong_tien"),
                            rs.getString("hinh_thuc_tt"),
                            rs.getString("trang_thai"),
                            rs.getString("ten_khach_hang")
                    );
                    list.add(hd);
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi searchByDate HoaDon: " + e.getMessage());
        }
        return list;
    }

    public List<HoaDonEntity> search(String keyword) {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = "SELECT hd.*, kh.ho_ten AS ten_khach_hang "
                + "FROM HoaDon hd "
                + "LEFT JOIN KhachHang kh ON hd.id_khach_hang = kh.id_khach_hang"
                + "WHERE CAST(hd.id_hoa_don AS NVARCHAR) LIKE ? "
                + "OR hd.trang_thai LIKE ? "
                + "OR hd.hinh_thuc_tt LIKE ? "
                + "OR kh.ho_ten LIKE ?";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDonEntity hd = new HoaDonEntity(
                            rs.getInt("id_hoa_don"),
                            rs.getInt("id_khach_hang"),
                            rs.getString("ngay_lap"),
                            rs.getDouble("tong_tien"),
                            rs.getString("hinh_thuc_tt"),
                            rs.getString("trang_thai"),
                            rs.getString("ten_khach_hang")
                    );
                    list.add(hd);
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi search HoaDon: " + e.getMessage());
        }
        return list;
    }

    public int insertAndGetId(HoaDonEntity hd) {
        String sql = "INSERT INTO HoaDon (ngay_lap, trang_thai) VALUES (?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, hd.getNgayLap());
            ps.setString(2, hd.getTrangThai());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateTrangThai(int idHoaDon, String trangThai) {
        String sql = "UPDATE HoaDon SET trang_thai = ? WHERE id_hoa_don = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, idHoaDon);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HoaDonEntity getById(int id) {
        HoaDonEntity hd = null;
        String sql = "SELECT hd.*, kh.ho_ten AS ten_khach_hang "
                + "FROM HoaDon hd LEFT JOIN KhachHang kh ON hd.id_khach_hang = kh.id_khach_hang "
                + "WHERE hd.id_hoa_don = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hd = new HoaDonEntity();
                hd.setIdHoaDon(rs.getInt("id_hoa_don"));
                hd.setIdKhachHang(rs.getInt("id_khach_hang"));
                hd.setTenKhachHang(rs.getString("ten_khach_hang")); // <-- quan trọng
                hd.setNgayLap(rs.getString("ngay_lap"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTrangThai(rs.getString("trang_thai"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return hd;
    }

    public List<HoaDonEntity> getByDateRange(String from, String to) {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE ngay_Lap BETWEEN ? AND ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, from);
            ps.setString(2, to);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity();
                hd.setIdHoaDon(rs.getInt("id_hoa_don"));
                hd.setIdKhachHang(rs.getInt("id_khach_hang"));
                hd.setNgayLap(rs.getString("ngay_Lap"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTrangThai(rs.getString("trang_thai"));
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete2(int idHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE id_hoa_don = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHoaDon);
            int result = ps.executeUpdate();
            return result > 0; // true nếu xóa thành công

        } catch (Exception e) {
            System.out.println("Lỗi delete HoaDon: " + e.getMessage());
            return false;
        }
    }

    public boolean insert(HoaDonEntity hd) {
        String sql = "INSERT INTO HoaDon (id_khach_hang, ngay_lap, tong_tien, trang_thai) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, hd.getIdKhachHang());
            ps.setString(2, hd.getNgayLap());
            ps.setDouble(3, hd.getTongTien());
            ps.setString(4, hd.getTrangThai());
            int affected = ps.executeUpdate();
            if (affected == 0) {
                return false;
            }
            // lấy id trả về nếu cần
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                hd.setIdHoaDon(rs.getInt(1));
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    // Cập nhật id khách hàng cho hóa đơn

    public boolean updateKhachHang(int idHoaDon, int idKhachHang) {
        String sql = "UPDATE HoaDon SET id_khach_hang = ? WHERE id_hoa_don = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idKhachHang);
            ps.setInt(2, idHoaDon);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    // Lấy hóa đơn hôm nay (đã thanh toán)

    public List<HoaDonEntity> getHoaDonHomNay() {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE trang_thai = N'Đã thanh toán' AND CAST(ngay_lap AS DATE) = CAST(GETDATE() AS DATE)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity();
                hd.setIdHoaDon(rs.getInt("id_hoa_don"));
                hd.setNgayLap(rs.getString("ngay_lap"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTrangThai(rs.getString("trang_thai"));
                // set thêm các trường khác nếu cần
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// Lấy tất cả hóa đơn đã thanh toán
    public List<HoaDonEntity> getHoaDonDaThanhToan() {
        List<HoaDonEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE trang_thai = N'Đã thanh toán'";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity();
                hd.setIdHoaDon(rs.getInt("id_hoa_don"));
                hd.setNgayLap(rs.getString("ngay_lap"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTrangThai(rs.getString("trang_thai"));
                // set thêm các trường khác nếu cần
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
