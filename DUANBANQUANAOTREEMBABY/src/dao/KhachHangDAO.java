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

    public boolean insert2(KhachHangEntity kh) {
        String sql = "INSERT INTO KhachHang (ho_ten, sdt) VALUES (?, ?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSdt());
            int result = ps.executeUpdate();
            return result > 0; // trả true nếu có dòng được thêm
        } catch (Exception e) {
            System.out.println("Lỗi insert KhachHang: " + e.getMessage());
            return false;
        }
    }

    public boolean update2(KhachHangEntity kh) {
        String sql = "UPDATE KhachHang SET ho_ten=?, sdt=? WHERE id_khach_hang=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSdt());
            ps.setInt(3, kh.getIdKhachHang());
            int result = ps.executeUpdate();
            return result > 0; // trả true nếu có dòng được cập nhật
        } catch (Exception e) {
            System.out.println("Lỗi update KhachHang: " + e.getMessage());
            return false;
        }
    }

    public boolean delete2(int idKhachHang) {
        String sql = "DELETE FROM KhachHang WHERE id_khach_hang=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idKhachHang);
            int result = ps.executeUpdate();
            return result > 0; // trả true nếu có dòng bị xóa
        } catch (Exception e) {
            System.out.println("Lỗi delete KhachHang: " + e.getMessage());
            return false;
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

    public KhachHangEntity findBySdt(String sdt) {
        String sql = "SELECT * FROM KhachHang WHERE sdt = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                KhachHangEntity kh = new KhachHangEntity();
                kh.setIdKhachHang(rs.getInt("id_khach_hang"));
                kh.setHoTen(rs.getString("ho_ten"));
                kh.setSdt(rs.getString("sdt"));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHangEntity> search(String keyword) {
        List<KhachHangEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE ho_ten LIKE ? OR sdt LIKE ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_khach_hang");
                String hoTen = rs.getString("ho_ten");
                String sdt = rs.getString("sdt");
                list.add(new KhachHangEntity(id, hoTen, sdt));
            }
        } catch (Exception e) {
            System.out.println("Lỗi search: " + e.getMessage());
        }
        return list;
    }

}
