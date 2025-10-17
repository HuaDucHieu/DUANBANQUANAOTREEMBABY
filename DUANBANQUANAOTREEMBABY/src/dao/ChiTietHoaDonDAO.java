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
        String sql = "SELECT * FROM ChiTietHoaDon";
        
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ChiTietHoaDonEntity cthd = new ChiTietHoaDonEntity(
                    rs.getInt("id_chi_tiet_hoa_don"),
                    rs.getInt("id_hoa_don"),
                    rs.getInt("id_sp"),
                    rs.getInt("so_luong")
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
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
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
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
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
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idChiTietHoaDon);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi delete ChiTietHoaDon: " + e.getMessage());
        }
    }

    public ChiTietHoaDonEntity findById(int idChiTietHoaDon) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE id_chi_tiet_hoa_don=?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idChiTietHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ChiTietHoaDonEntity(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_sp"),
                        rs.getInt("so_luong")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi findById ChiTietHoaDon: " + e.getMessage());
        }
        return null;
    }
}
