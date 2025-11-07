/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.KhachHangEntity;
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

        // SQL JOIN để lấy luôn tên danh mục, màu sắc, kích thước
        String sql = "SELECT sp.id_sp, sp.ten_sp, sp.gia, sp.so_luong, sp.id_danh_muc, sp.id_mau_sac, sp.id_kich_thuoc, sp.trang_thai, "
                + "dm.ten_danh_muc, ms.ten_mau_sac, kt.ten_kich_thuoc "
                + "FROM SanPham sp "
                + "LEFT JOIN DanhMuc dm ON sp.id_danh_muc = dm.id_danh_muc "
                + "LEFT JOIN MauSac ms ON sp.id_mau_sac = ms.id_mau_sac "
                + "LEFT JOIN KichThuoc kt ON sp.id_kich_thuoc = kt.id_kich_thuoc";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPhamEntity sp = new SanPhamEntity(
                        rs.getInt("id_sp"),
                        rs.getString("ten_sp"),
                        rs.getDouble("gia"),
                        rs.getInt("so_luong"),
                        rs.getInt("id_danh_muc"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_kich_thuoc"),
                        rs.getString("ten_danh_muc"),
                        rs.getString("ten_mau_sac"),
                        rs.getString("ten_kich_thuoc"),
                        rs.getString("trang_thai")
                );
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll SanPham: " + e.getMessage());
        }
        return list;
    }

    // --- Insert ---
    public void insert(SanPhamEntity sp) {
        String sql = "INSERT INTO SanPham (ten_sp, gia, so_luong, id_danh_muc, trang_thai, id_mau_sac, id_kich_thuoc) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Lấy ID từ tên
            int idDanhMuc = new DanhMucDAO().findByName(sp.getTenDanhMuc()).getIdDanhMuc();
            int idMauSac = new MauSacDAO().findByName(sp.getTenMauSac()).getIdMauSac();
            int idKichThuoc = new KichThuocDAO().findByName(sp.getTenKichThuoc()).getIdKichThuoc();

            ps.setString(1, sp.getTenSp());
            ps.setDouble(2, sp.getGia());
            ps.setInt(3, sp.getSoLuong());
            ps.setInt(4, idDanhMuc);
            ps.setString(5, sp.getTrangThai());
            ps.setInt(6, idMauSac);
            ps.setInt(7, idKichThuoc);

            ps.executeUpdate();
            System.out.println("Thêm sản phẩm thành công: " + sp.getTenSp());
        } catch (Exception e) {
            System.out.println("Lỗi insert SanPham: " + e.getMessage());
        }
    }

// --- Update ---
    public void update(SanPhamEntity sp) {
        String sql = "UPDATE SanPham SET ten_sp=?, gia=?, so_luong=?, id_danh_muc=?, trang_thai=?, id_mau_sac=?, id_kich_thuoc=? "
                + "WHERE id_sp=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            int idDanhMuc = new DanhMucDAO().findByName(sp.getTenDanhMuc()).getIdDanhMuc();
            int idMauSac = new MauSacDAO().findByName(sp.getTenMauSac()).getIdMauSac();
            int idKichThuoc = new KichThuocDAO().findByName(sp.getTenKichThuoc()).getIdKichThuoc();

            ps.setString(1, sp.getTenSp());
            ps.setDouble(2, sp.getGia());
            ps.setInt(3, sp.getSoLuong());
            ps.setInt(4, idDanhMuc);
            ps.setString(5, sp.getTrangThai());
            ps.setInt(6, idMauSac);
            ps.setInt(7, idKichThuoc);
            ps.setInt(8, sp.getIdSanPham());

            ps.executeUpdate();
            System.out.println("Cập nhật sản phẩm thành công: " + sp.getTenSp());
        } catch (Exception e) {
            System.out.println("Lỗi update SanPham: " + e.getMessage());
        }
    }

// --- Delete ---
    public void delete(int idSp) {
        String sql = "DELETE FROM SanPham WHERE id_sp=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSp);
            ps.executeUpdate();
            System.out.println("Xóa sản phẩm thành công, ID: " + idSp);
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
                sp.setIdSanPham(rs.getInt("id_sp"));
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

    public List<SanPhamEntity> search(String keyword) {
        List<SanPhamEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE id_sp = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Chuyển keyword thành số (id)
            ps.setInt(1, Integer.parseInt(keyword));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamEntity sp = new SanPhamEntity();
                sp.setIdSanPham(rs.getInt("id_sp"));
                sp.setTenSp(rs.getString("ten_sp"));
                sp.setGia(rs.getDouble("gia"));
                sp.setSoLuong(rs.getInt("so_luong"));
                sp.setIdDanhMuc(rs.getInt("id_danh_muc"));
                sp.setTrangThai(rs.getString("trang_thai"));
                sp.setIdMauSac(rs.getInt("id_mau_sac"));
                sp.setIdKichThuoc(rs.getInt("id_kich_thuoc"));
                list.add(sp);
            }

        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ (không phải số): " + keyword);
        } catch (Exception e) {
            System.out.println("Lỗi tìm kiếm SanPham: " + e.getMessage());
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
                    sp.setIdSanPham(rs.getInt("id_sp"));
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
            ps.setInt(8, sp.getIdSanPham());

            int rows = ps.executeUpdate();
            return rows > 0; // trả về true nếu có ít nhất 1 dòng bị cập nhật

        } catch (Exception e) {
            System.out.println("Lỗi update SanPham: " + e.getMessage());
            return false;
        }
    }

    public List<SanPhamEntity> getSanPhamByDanhMuc(int idDanhMuc) {
        List<SanPhamEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE id_danh_muc = ?";
        try (Connection conn = ConnectDB.getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDanhMuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamEntity sp = new SanPhamEntity();
                sp.setIdSanPham(rs.getInt("id_sp"));
                sp.setTenSp(rs.getString("ten_sp"));
                sp.setGia(rs.getDouble("gia"));
                sp.setSoLuong(rs.getInt("so_luong"));
                sp.setIdDanhMuc(rs.getInt("id_danh_muc"));
                sp.setTrangThai(rs.getString("trang_thai"));
                sp.setIdMauSac(rs.getInt("id_mau_sac"));
                sp.setIdKichThuoc(rs.getInt("id_kich_thuoc"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
