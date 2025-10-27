/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.KhachHangEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectDB;

/**
 *
 * @author duchi
 */
public class KhachHangDAO {

    public List<KhachHangEntity> getAll() {
        List<KhachHangEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhachHangEntity kh = new KhachHangEntity(
                        rs.getInt("id_khach_hang"),
                        rs.getString("ho_ten"),
                        rs.getString("sdt")
                );
                list.add(kh);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll KhachHang: " + e.getMessage());
        }
        return list;
    }

    public void insert(KhachHangEntity kh) {
        String sql = "INSERT INTO KhachHang (ho_ten, sdt) VALUES (?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSdt());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert KhachHang: " + e.getMessage());
        }
    }

    public void update(KhachHangEntity kh) {
        String sql = "UPDATE KhachHang SET ho_ten=?, sdt=? WHERE id_khach_hang=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSdt());
            ps.setInt(3, kh.getIdKhachHang());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update KhachHang: " + e.getMessage());
        }
    }

    public void delete(int idKhachHang) {
        String sql = "DELETE FROM KhachHang WHERE id_khach_hang=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idKhachHang);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete KhachHang: " + e.getMessage());
        }
    }

    public KhachHangEntity getById(int idKhachHang) {
        KhachHangEntity kh = null;
        String sql = "SELECT * FROM KhachHang WHERE id_khach_hang = ?";

        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idKhachHang);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                kh = new KhachHangEntity();
                kh.setIdKhachHang(rs.getInt("id_khach_hang"));
                kh.setHoTen(rs.getString("ho_ten"));
                kh.setSdt(rs.getString("sdt"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }
}
