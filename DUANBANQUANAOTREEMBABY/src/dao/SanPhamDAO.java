/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.SanPhamEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectDB;



/**
 *
 * @author Admin
 */
public class SanPhamDAO {
    public List<SanPhamEntity> getAll() {
        List<SanPhamEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPhamEntity sp = new SanPhamEntity(
                        rs.getInt("id_sp"),
                        rs.getString("ten_sp"),
                        rs.getDouble("gia"),
                        rs.getInt("so_luong"),
                        rs.getInt("id_danh_muc"),
                        rs.getString("trang_thai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_kich_thuoc")
                );
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll SanPham: " + e.getMessage());
        }
        return list;
    }

    public void insert(SanPhamEntity sp) {
        String sql = "INSERT INTO SanPham (ten_sp, gia, so_luong, id_danh_muc, trang_thai, id_mau_sac, id_kich_thuoc) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sp.getTenSp());
            ps.setDouble(2, sp.getGia());
            ps.setInt(3, sp.getSoLuong());
            ps.setInt(4, sp.getIdDanhMuc());
            ps.setString(5, sp.getTrangThai());
            ps.setInt(6, sp.getIdMauSac());
            ps.setInt(7, sp.getIdKichThuoc());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert SanPham: " + e.getMessage());
        }
    }

    public void update(SanPhamEntity sp) {
        String sql = "UPDATE SanPham SET ten_sp=?, gia=?, so_luong=?, id_danh_muc=?, trang_thai=?, id_mau_sac=?, id_kich_thuoc=? WHERE id_sp=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sp.getTenSp());
            ps.setDouble(2, sp.getGia());
            ps.setInt(3, sp.getSoLuong());
            ps.setInt(4, sp.getIdDanhMuc());
            ps.setString(5, sp.getTrangThai());
            ps.setInt(6, sp.getIdMauSac());
            ps.setInt(7, sp.getIdKichThuoc());
            ps.setInt(8, sp.getIdSp());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update SanPham: " + e.getMessage());
        }
    }

    public void delete(int idSp) {
        String sql = "DELETE FROM SanPham WHERE id_sp=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSp);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete SanPham: " + e.getMessage());
        }
    }
}