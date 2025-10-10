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
                    rs.getInt("MaDanhMuc"),
                    rs.getString("TenDanhMuc"),
                    rs.getString("MoTa"),
                    rs.getString("TrangThai")
                );
                list.add(dm);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll DanhMuc: " + e.getMessage());
        }
        return list;
    }
    
    public void insert(DanhMucEntity dm) {
        String sql = "INSERT INTO DanhMuc (TenDanhMuc, MoTa, TrangThai) VALUES (?, ?, ?)";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, dm.getTenDanhMuc());
            ps.setString(2, dm.getMoTa());
            ps.setString(3, dm.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert DanhMuc: " + e.getMessage());
        }
    }
    
    public void update(DanhMucEntity dm) {
        String sql = "UPDATE DanhMuc SET TenDanhMuc=?, MoTa=?, TrangThai=? WHERE MaDanhMuc=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, dm.getTenDanhMuc());
            ps.setString(2, dm.getMoTa());
            ps.setString(3, dm.getTrangThai());
            ps.setInt(4, dm.getMaDanhMuc());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update DanhMuc: " + e.getMessage());
        }
    }
    
    public void delete(int maDanhMuc) {
        String sql = "DELETE FROM DanhMuc WHERE MaDanhMuc=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, maDanhMuc);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete DanhMuc: " + e.getMessage());
        }
    }
}
