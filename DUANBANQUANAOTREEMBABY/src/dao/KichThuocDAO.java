/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utils.ConnectDB;
import entity.KichThuocEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duchi
 */
public class KichThuocDAO {

    public List<KichThuocEntity> getAll() {
        List<KichThuocEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM KichThuoc";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KichThuocEntity kt = new KichThuocEntity(
                        rs.getInt("id_kich_thuoc"),
                        rs.getString("ten_kich_thuoc")
                );
                list.add(kt);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll KichThuoc: " + e.getMessage());
        }
        return list;
    }

    public void insert(KichThuocEntity kt) {
        String sql = "INSERT INTO KichThuoc (ten_kich_thuoc) VALUES (?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kt.getTenKichThuoc());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert KichThuoc: " + e.getMessage());
        }
    }

    public void update(KichThuocEntity kt) {
        String sql = "UPDATE KichThuoc SET ten_kich_thuoc=? WHERE id_kich_thuoc=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kt.getTenKichThuoc());
            ps.setInt(2, kt.getIdKichThuoc());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update KichThuoc: " + e.getMessage());
        }
    }

    public void delete(int idKichThuoc) {
        String sql = "DELETE FROM KichThuoc WHERE id_kich_thuoc=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idKichThuoc);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete KichThuoc: " + e.getMessage());
        }
    }

    public int findIdByName(String tenKichThuoc) {
        String sql = "SELECT id_kich_thuoc FROM KichThuoc WHERE ten_kich_thuoc = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenKichThuoc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_kich_thuoc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // không tìm thấy
    }
}
