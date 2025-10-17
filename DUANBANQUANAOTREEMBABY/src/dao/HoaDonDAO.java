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
        String sql = "SELECT * FROM HoaDon";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        } catch (Exception e) {
            System.out.println("Lỗi getAll HoaDon: " + e.getMessage());
        }
        return list;
    }

    public void insert(HoaDonEntity hd) {
        String sql = "INSERT INTO HoaDon (id_khach_hang, ngay_lap, tong_tien, hinh_thuc_tt, trang_thai) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

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
        String sql = "UPDATE HoaDon SET id_khach_hang=?, ngay_lap=?, tong_tien=?, hinh_thuc_tt=?, trang_thai=? WHERE id_hoa_don=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, hd.getIdKhachHang());
            ps.setString(2, hd.getNgayLap());
            ps.setDouble(3, hd.getTongTien());
            ps.setString(4, hd.getHinhThucTT());
            ps.setString(5, hd.getTrangThai());
            ps.setInt(6, hd.getIdHoaDon());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update HoaDon: " + e.getMessage());
        }
    }

    public void delete(int idHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE id_hoa_don=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHoaDon);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete HoaDon: " + e.getMessage());
        }
    }
}
