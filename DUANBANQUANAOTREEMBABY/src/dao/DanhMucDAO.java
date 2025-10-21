/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import entity.DanhMucEntity;
import java.sql.*;
import java.util.*;
import utils.ConnectDB;
/**
 *
 * @author Tran Tien
 */
public class DanhMucDAO {
      public List<DanhMucEntity> getAll() {
        List<DanhMucEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhMuc";
        
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                DanhMucEntity dm = new DanhMucEntity(
                    rs.getInt("id_danh_muc"),
                    rs.getString("ten_danh_muc"),
                    rs.getString("mo_ta")
                );
                list.add(dm);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll DanhMuc: " + e.getMessage());
        }
        return list;
    }

    public void insert(DanhMucEntity dm) {
        String sql = "INSERT INTO DanhMuc (ten_danh_muc, mo_ta) VALUES (?, ?)";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, dm.getTenDanhMuc());
            ps.setString(2, dm.getMoTa());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert DanhMuc: " + e.getMessage());
        }
    }

    public void update(DanhMucEntity dm) {
        String sql = "UPDATE DanhMuc SET ten_danh_muc=?, mo_ta=? WHERE id_danh_muc=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, dm.getTenDanhMuc());
            ps.setString(2, dm.getMoTa());
            ps.setInt(3, dm.getIdDanhMuc());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update DanhMuc: " + e.getMessage());
        }
    }

    public void delete(int idDanhMuc) {
        String sql = "DELETE FROM DanhMuc WHERE id_danh_muc=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idDanhMuc);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete DanhMuc: " + e.getMessage());
        }
    }

    public DanhMucEntity findById(int idDanhMuc) {
        String sql = "SELECT * FROM DanhMuc WHERE id_danh_muc=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idDanhMuc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DanhMucEntity(
                        rs.getInt("id_danh_muc"),
                        rs.getString("ten_danh_muc"),
                        rs.getString("mo_ta")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi findById DanhMuc: " + e.getMessage());
        }
        return null;
    }
}
