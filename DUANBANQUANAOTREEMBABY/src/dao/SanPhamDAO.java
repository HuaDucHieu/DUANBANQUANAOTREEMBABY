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
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

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
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

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
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSp);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete SanPham: " + e.getMessage());
        }
    }

    public List<SanPhamEntity> getAllWithDetails() {
        List<SanPhamEntity> list = new ArrayList<>();
        String sql = """
            SELECT 
                sp.id_sp,
                sp.ten_sp,
                sp.id_danh_muc,
                dm.ten_danh_muc,
                ms.ten_mau_sac,
                kt.ten_kich_thuoc,
                sp.so_luong,
                sp.gia,
                sp.trang_thai
            FROM SanPham sp
            JOIN DanhMuc dm ON sp.id_danh_muc = dm.id_danh_muc
            JOIN MauSac ms ON sp.id_mau_sac = ms.id_mau_sac
            JOIN KichThuoc kt ON sp.id_kich_thuoc = kt.id_kich_thuoc
        """;

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPhamEntity sp = new SanPhamEntity();
                sp.setIdSp(rs.getInt("id_sp"));
                sp.setTenSp(rs.getString("ten_sp"));
                sp.setIdDanhMuc(rs.getInt("id_danh_muc"));
                sp.setTenDanhMuc(rs.getString("ten_danh_muc"));
                sp.setTenMauSac(rs.getString("ten_mau_sac"));
                sp.setTenKichThuoc(rs.getString("ten_kich_thuoc"));
                sp.setSoLuong(rs.getInt("so_luong"));
                sp.setGia(rs.getDouble("gia"));
                sp.setTrangThai(rs.getString("trang_thai"));
                list.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public SanPhamEntity findById(int idSanPham) {
        String sql = """
        SELECT sp.*, 
               dm.ten_danh_muc, 
               ms.ten_mau_sac, 
               kt.ten_kich_thuoc
        FROM SanPham sp
        JOIN DanhMuc dm ON sp.id_danh_muc = dm.id_danh_muc
        JOIN MauSac ms ON sp.id_mau_sac = ms.id_mau_sac
        JOIN KichThuoc kt ON sp.id_kich_thuoc = kt.id_kich_thuoc
        WHERE sp.id_sp = ?
    """;

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSanPham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SanPhamEntity sp = new SanPhamEntity();
                    sp.setIdSp(rs.getInt("id_sp"));
                    sp.setTenSp(rs.getString("ten_sp"));
                    sp.setGia(rs.getDouble("gia"));
                    sp.setSoLuong(rs.getInt("so_luong"));
                    sp.setTrangThai(rs.getString("trang_thai"));
                    sp.setIdDanhMuc(rs.getInt("id_danh_muc"));
                    sp.setIdMauSac(rs.getInt("id_mau_sac"));
                    sp.setIdKichThuoc(rs.getInt("id_kich_thuoc"));

                    // Lấy thêm thông tin tên
                    sp.setTenDanhMuc(rs.getString("ten_danh_muc"));
                    sp.setTenMauSac(rs.getString("ten_mau_sac"));
                    sp.setTenKichThuoc(rs.getString("ten_kich_thuoc"));

                    return sp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateSoLuong(int idSanPham, int soLuongMoi) {
        String sql = "UPDATE SanPham SET so_luong = ? WHERE id_sp = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, soLuongMoi);
            ps.setInt(2, idSanPham);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean update2(SanPhamEntity sp) {
        String sql = "UPDATE SanPham SET ten_sp=?, gia=?, so_luong=?, id_danh_muc=?, trang_thai=?, id_mau_sac=?, id_kich_thuoc=? WHERE id_sp=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sp.getTenSp());
            ps.setDouble(2, sp.getGia());
            ps.setInt(3, sp.getSoLuong());
            ps.setInt(4, sp.getIdDanhMuc());
            ps.setString(5, sp.getTrangThai());
            ps.setInt(6, sp.getIdMauSac());
            ps.setInt(7, sp.getIdKichThuoc());
            ps.setInt(8, sp.getIdSp());

            int rows = ps.executeUpdate();
            return rows > 0; // trả về true nếu có ít nhất 1 dòng bị cập nhật

        } catch (Exception e) {
            System.out.println("Lỗi update SanPham: " + e.getMessage());
            return false;
        }
    }

}
