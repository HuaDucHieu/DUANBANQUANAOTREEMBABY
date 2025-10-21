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

    public List<NhanVienEntity> getAll() {
        List<NhanVienEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVienEntity nv = new NhanVienEntity(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ho_ten"),
                        rs.getString("email"),
                        rs.getString("mat_khau"),
                        rs.getString("chuc_vu")
                );
                list.add(nv);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll NhanVien: " + e.getMessage());
        }
        return list;
    }

    public void insert(NhanVienEntity nv) {
        String sql = "INSERT INTO NhanVien (ho_ten, email, mat_khau, chuc_vu) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getEmail());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getChucVu());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert NhanVien: " + e.getMessage());
        }
    }

    public void update(NhanVienEntity nv) {
        String sql = "UPDATE NhanVien SET ho_ten=?, email=?, mat_khau=?, chuc_vu=? WHERE id_nhan_vien=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getEmail());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getChucVu());
            ps.setInt(5, nv.getIdNhanVien());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update NhanVien: " + e.getMessage());
        }
    }

    public void delete(int idNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE id_nhan_vien=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNhanVien);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete NhanVien: " + e.getMessage());
        }
    }
}
