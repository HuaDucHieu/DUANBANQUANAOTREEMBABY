/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import entity.HoaDonEntity;
import java.sql.*;
import java.util.*;
import utils.ConnectDB;
/**
 *
 * @author Tran Tien
 */
public class HoaDonDAO {
     public List<HoaDonEntity> getAll() {
        List<HoaDonEntity> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM HoaDon";
            PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity(
                        rs.getInt("MaHD"),
                        rs.getString("NgayTao"),
                        rs.getDouble("TongTien"),
                        rs.getString("TrangThai"),
                        rs.getString("NhanVien"),
                        rs.getString("KhachHang")
                );
                list.add(hd);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Lỗi getAll: " + e.getMessage());
        }
        return list;
    }

    public int insert(HoaDonEntity hd) {
        try {
            String sql = "INSERT INTO HoaDon (NgayTao, TongTien, TrangThai, NhanVien, KhachHang) VALUES (?,?,?,?,?)";
            PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
            ps.setString(1, hd.getNgayTao());
            ps.setDouble(2, hd.getTongTien());
            ps.setString(3, hd.getTrangThai());
            ps.setString(4, hd.getNhanVien());
            ps.setString(5, hd.getKhachHang());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert: " + e.getMessage());
        }
        return -1;
    }

    public int update(HoaDonEntity hd) {
        try {
            String sql = "UPDATE HoaDon SET NgayTao=?, TongTien=?, TrangThai=?, NhanVien=?, KhachHang=? WHERE MaHD=?";
            PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
            ps.setString(1, hd.getNgayTao());
            ps.setDouble(2, hd.getTongTien());
            ps.setString(3, hd.getTrangThai());
            ps.setString(4, hd.getNhanVien());
            ps.setString(5, hd.getKhachHang());
            ps.setInt(6, hd.getMaHD());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update: " + e.getMessage());
        }
        return -1;
    }

    public int delete(int maHD) {
        try {
            String sql = "DELETE FROM HoaDon WHERE MaHD=?";
            PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
            ps.setInt(1, maHD);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete: " + e.getMessage());
        }
        return -1;
    }
}
