/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utils.ConnectDB;
import entity.MauSacEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duchi
 */
public class MauSacDAO {

    public List<MauSacEntity> getAll() {
        List<MauSacEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM MauSac";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MauSacEntity ms = new MauSacEntity(
                        rs.getInt("id_mau_sac"),
                        rs.getString("ten_mau_sac")
                );
                list.add(ms);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll MauSac: " + e.getMessage());
        }
        return list;
    }

    public void insert(MauSacEntity ms) {
        String sql = "INSERT INTO MauSac (ten_mau_sac) VALUES (?)";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ms.getTenMauSac());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert MauSac: " + e.getMessage());
        }
    }

    public void update(MauSacEntity ms) {
        String sql = "UPDATE MauSac SET ten_mau_sac=? WHERE id_mau_sac=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ms.getTenMauSac());
            ps.setInt(2, ms.getIdMauSac());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update MauSac: " + e.getMessage());
        }
    }

    public void delete(int idMauSac) {
        String sql = "DELETE FROM MauSac WHERE id_mau_sac=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMauSac);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete MauSac: " + e.getMessage());
        }
    }

    public int findIdByName(String tenMauSac) {
        String sql = "SELECT id_mau_sac FROM MauSac WHERE ten_mau_sac = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenMauSac);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_mau_sac");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // không tìm thấy
    }
}
