/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.NhanVienEntity;
import java.sql.*;
import java.util.*;
import utils.ConnectDB;

/**
 *
 * @author Admin
 */
public class NhanVienDAO {

    // Lấy tất cả nhân viên
    public List<NhanVienEntity> getAll() {
        List<NhanVienEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVienEntity nv = new NhanVienEntity();
                nv.setIdNhanVien(rs.getInt("id_nhan_vien"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setChucVu(rs.getString("chuc_vu"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setEmail(rs.getString("email"));
                list.add(nv);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getAll: " + e.getMessage());
        }
        return list;
    }

    // Thêm nhân viên
    public int insert(NhanVienEntity nv) {
        String sql = "INSERT INTO NhanVien(ho_ten, chuc_vu, mat_khau, email) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getEmail());
            return ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Lỗi insert: " + e.getMessage());
        }
        return -1;
    }

    // Cập nhật nhân viên
    public int update(NhanVienEntity nv) {
        String sql = "UPDATE NhanVien SET ho_ten=?, chuc_vu=?, mat_khau=?, email=? WHERE id_nhan_vien=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getEmail());
            ps.setInt(5, nv.getIdNhanVien());

            return ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Lỗi update: " + e.getMessage());
        }
        return -1;
    }

    // Xóa nhân viên theo ID
    public int delete(int id) {
        String sql = "DELETE FROM NhanVien WHERE id_nhan_vien=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Lỗi delete: " + e.getMessage());
        }
        return -1;
    }

    // Tìm kiếm nhân viên theo ID hoặc họ tên
    public List<NhanVienEntity> search(String keyword) {
        List<NhanVienEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE CAST(id_nhan_vien AS VARCHAR) LIKE ? OR ho_ten LIKE ?";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NhanVienEntity nv = new NhanVienEntity();
                    nv.setIdNhanVien(rs.getInt("id_nhan_vien"));
                    nv.setHoTen(rs.getString("ho_ten"));
                    nv.setChucVu(rs.getString("chuc_vu"));
                    nv.setMatKhau(rs.getString("mat_khau"));
                    nv.setEmail(rs.getString("email"));
                    list.add(nv);
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm NhanVien: " + e.getMessage());
        }

        return list;
    }

    public NhanVienEntity getById(int id) {
        String sql = "SELECT * FROM NhanVien WHERE id_nhan_vien = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                NhanVienEntity nv = new NhanVienEntity();
                nv.setIdNhanVien(rs.getInt("id_nhan_vien"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setChucVu(rs.getString("chuc_vu"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setEmail(rs.getString("email"));
                return nv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
