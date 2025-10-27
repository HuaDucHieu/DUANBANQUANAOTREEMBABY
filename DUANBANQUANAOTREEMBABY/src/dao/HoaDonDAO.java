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

    public List<HoaDonEntity> getAll() {
        List<HoaDonEntity> list = new ArrayList<>();

        try {
            Connection con = ConnectDB.getConnect();
            String sql = "SELECT * FROM HoaDon";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                HoaDonEntity hoaDon = new HoaDonEntity(
                        result.getInt("id_hoa_don"),
                        result.getInt("id_khach_hang"),
                        result.getString("ngay_lap"),
                        result.getDouble("tong_tien"),
                        result.getString("hinh_thuc_tt"),
                        result.getString("trang_thai")
                );
                list.add(hoaDon);
            }

        } catch (Exception e) {
            System.out.println("Lỗi get all hóa đơn: " + e.getMessage());
        }

        return list;
    }

    public void insert(HoaDonEntity hd) {
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
        String sql = "SELECT * FROM HoaDon WHERE ngay_lap = ?";
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
                            rs.getString("trang_thai")
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
        String sql = "SELECT * FROM HoaDon WHERE CAST(id_hoa_don AS NVARCHAR) LIKE ? "
                + "OR trang_thai LIKE ? OR hinh_thuc_tt LIKE ?";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDonEntity hd = new HoaDonEntity(
                            rs.getInt("id_hoa_don"),
                            rs.getInt("id_khach_hang"),
                            rs.getString("ngay_lap"),
                            rs.getDouble("tong_tien"),
                            rs.getString("hinh_thuc_tt"),
                            rs.getString("trang_thai")
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
        String sql = "SELECT * FROM HoaDon WHERE id_hoa_don = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hd = new HoaDonEntity(
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_khach_hang"),
                        rs.getString("ngay_lap"),
                        rs.getDouble("tong_tien"),
                        rs.getString("hinh_thuc_tt"),
                        rs.getString("trang_thai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }

   

}
