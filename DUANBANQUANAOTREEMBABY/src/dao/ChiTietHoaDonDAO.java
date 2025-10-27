/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ChiTietHoaDonEntity;
import java.sql.*;
import java.util.*;
import utils.ConnectDB;

/**
 *
 * @author duchi
 */
public class ChiTietHoaDonDAO {

    public List<ChiTietHoaDonEntity> getAll() {
        List<ChiTietHoaDonEntity> list = new ArrayList<>();
        String sql = """
        SELECT cthd.*, sp.ten_sp, sp.gia, dm.ten_danh_muc, ms.ten_mau_sac, kt.ten_kich_thuoc
        FROM ChiTietHoaDon cthd
        JOIN SanPham sp ON cthd.id_sp = sp.id_sp
        JOIN DanhMuc dm ON sp.id_danh_muc = dm.id_danh_muc
        JOIN MauSac ms ON sp.id_mau_sac = ms.id_mau_sac
        JOIN KichThuoc kt ON sp.id_kich_thuoc = kt.id_kich_thuoc
    """;

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietHoaDonEntity cthd = new ChiTietHoaDonEntity(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_sp"),
                        rs.getString("ten_sp"),
                        rs.getString("ten_danh_muc"),
                        rs.getString("ten_mau_sac"),
                        rs.getString("ten_kich_thuoc"),
                        rs.getInt("so_luong"),
                        rs.getDouble("gia")
                );
                list.add(cthd);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll ChiTietHoaDon: " + e.getMessage());
        }
        return list;
    }

    public void insert(ChiTietHoaDonEntity cthd) {
        String sql = "INSERT INTO ChiTietHoaDon (id_hoa_don, id_sp, so_luong) VALUES (?, ?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cthd.getIdHoaDon());
            ps.setInt(2, cthd.getIdSanPham());
            ps.setInt(3, cthd.getSoLuong());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert ChiTietHoaDon: " + e.getMessage());
        }
    }

    public void update(ChiTietHoaDonEntity cthd) {
        String sql = "UPDATE ChiTietHoaDon SET id_hoa_don=?, id_sp=?, so_luong=? WHERE id_chi_tiet_hoa_don=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cthd.getIdHoaDon());
            ps.setInt(2, cthd.getIdSanPham());
            ps.setInt(3, cthd.getSoLuong());
            ps.setInt(4, cthd.getIdChiTietHoaDon());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update ChiTietHoaDon: " + e.getMessage());
        }
    }

    public void delete(int idChiTietHoaDon) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE id_chi_tiet_hoa_don=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idChiTietHoaDon);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete ChiTietHoaDon: " + e.getMessage());
        }
    }

    public ChiTietHoaDonEntity findById(int idChiTietHoaDon) {
        String sql = """
        SELECT cthd.*, sp.ten_sp, sp.gia, dm.ten_danh_muc, ms.ten_mau_sac, kt.ten_kich_thuoc
        FROM ChiTietHoaDon cthd
        JOIN SanPham sp ON cthd.id_sp = sp.id_sp
        JOIN DanhMuc dm ON sp.id_danh_muc = dm.id_danh_muc
        JOIN MauSac ms ON sp.id_mau_sac = ms.id_mau_sac
        JOIN KichThuoc kt ON sp.id_kich_thuoc = kt.id_kich_thuoc
        WHERE cthd.id_chi_tiet_hoa_don = ?
    """;

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idChiTietHoaDon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ChiTietHoaDonEntity(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_sp"),
                        rs.getString("ten_sp"),
                        rs.getString("ten_danh_muc"),
                        rs.getString("ten_mau_sac"),
                        rs.getString("ten_kich_thuoc"),
                        rs.getInt("so_luong"),
                        rs.getDouble("gia")
                );
            }
        } catch (Exception e) {
            System.out.println("Lỗi findById ChiTietHoaDon: " + e.getMessage());
        }
        return null;
    }

    public boolean insert2(ChiTietHoaDonEntity entity) {
        String sql = "INSERT INTO ChiTietHoaDon (id_hoa_don, id_sp, so_luong, don_gia) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entity.getIdHoaDon());
            ps.setInt(2, entity.getIdSanPham());
            ps.setInt(3, entity.getSoLuong());
            ps.setDouble(4, entity.getDonGia()); // ✅ PHẢI CÓ DÒNG NÀY

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<ChiTietHoaDonEntity> getByIdHoaDon(int idHoaDon) {
        List<ChiTietHoaDonEntity> list = new ArrayList<>();
        String sql = """
            SELECT 
                cthd.id_chi_tiet_hoa_don,
                cthd.id_hoa_don,
                sp.id_sp,
                sp.ten_sp,
                dm.ten_danh_muc,
                ms.ten_mau_sac,
                kt.ten_kich_thuoc,
                sp.gia,
                cthd.so_luong
            FROM ChiTietHoaDon cthd
            JOIN SanPham sp ON cthd.id_sp = sp.id_sp
            JOIN DanhMuc dm ON sp.id_danh_muc = dm.id_danh_muc
            JOIN MauSac ms ON sp.id_mau_sac = ms.id_mau_sac
            JOIN KichThuoc kt ON sp.id_kich_thuoc = kt.id_kich_thuoc
            WHERE cthd.id_hoa_don = ?
        """;

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHoaDon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietHoaDonEntity cthd = new ChiTietHoaDonEntity(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_sp"),
                        rs.getString("ten_sp"),
                        rs.getString("ten_danh_muc"),
                        rs.getString("ten_mau_sac"),
                        rs.getString("ten_kich_thuoc"),
                        rs.getInt("so_luong"),
                        rs.getDouble("gia")
                );
                list.add(cthd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ChiTietHoaDonEntity findByHoaDonAndSanPham(int idHoaDon, int idSanPham) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE id_hoa_don = ? AND id_sp = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idHoaDon);
            ps.setInt(2, idSanPham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChiTietHoaDonEntity ct = new ChiTietHoaDonEntity();
                ct.setIdChiTietHoaDon(rs.getInt("id_chi_tiet_hoa_don"));
                ct.setIdHoaDon(rs.getInt("id_hoa_don"));
                ct.setIdSanPham(rs.getInt("id_sp"));
                ct.setSoLuong(rs.getInt("so_luong"));
                return ct;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update2(ChiTietHoaDonEntity ct) {
        String sql = "UPDATE ChiTietHoaDon SET so_luong = ?, don_gia = ? WHERE id_hoa_don = ? AND id_sp = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ct.getSoLuong());
            ps.setDouble(2, ct.getDonGia());
            ps.setInt(3, ct.getIdHoaDon());
            ps.setInt(4, ct.getIdSanPham());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertOrUpdate(ChiTietHoaDonEntity ct) {
        ChiTietHoaDonEntity existing = findByHoaDonAndSanPham(ct.getIdHoaDon(), ct.getIdSanPham());
        if (existing != null) {
            // Nếu đã có thì cập nhật số lượng
            int newQuantity = existing.getSoLuong() + ct.getSoLuong();
            String sql = "UPDATE ChiTietHoaDon SET so_luong = ? WHERE id_chi_tiet_hoa_don = ?";
            try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, newQuantity);
                ps.setInt(2, existing.getIdChiTietHoaDon());
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Nếu chưa có thì thêm mới
            String sql = "INSERT INTO ChiTietHoaDon (id_hoa_don, id_sp, so_luong) VALUES (?, ?, ?)";
            try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, ct.getIdHoaDon());
                ps.setInt(2, ct.getIdSanPham());
                ps.setInt(3, ct.getSoLuong());
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
