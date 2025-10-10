/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.SanPhamEntity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectDB;
import static utils.ConnectDB.con;
import view.QuanLySanPhamJPanel;


/**
 *
 * @author Admin
 */
public class SanPhamDAO {
    public List<SanPhamEntity> getAll() {
        List<SanPhamEntity> list = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnect(); 
            String sql = "SELECT * FROM SanPham";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                SanPhamEntity sp = new SanPhamEntity(
                    result.getInt("MaSP"), 
                    result.getString("TenSP"),
                    result.getInt("LoaiSP"),
                    result.getInt("Gia"),
                    result.getInt("TrangThai"));
                list.add(sp);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll sản phẩm: " + e.getMessage());
        }
        return list;
    }
    
    
    public List<SanPhamEntity> getAllSP() {
    List<SanPhamEntity> list = new ArrayList<>();
    try {
        Connection con = ConnectDB.getConnect(); 
        String sql = "SELECT sp.MaSP, sp.TenSP, dm.MaDanhMuc, sp.Loai, sp.Gia, sp.TrangThai "
                   + "FROM SanPham sp "
                   + "JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            SanPhamEntity sp = new SanPhamEntity(
                   result.getInt("MaSP"), 
                    result.getString("TenSP"),
                    result.getInt("LoaiSP"),
                    result.getInt("Gia"),
                    result.getInt("TrangThai"));             
            list.add(sp);
        }
    } catch (Exception e) {
        System.out.println("Lỗi getAll SP: " + e.getMessage());
    }
    return list;
}

    public void insert(SanPhamEntity sp){
        try {
            Connection con = ConnectDB.getConnect();
            String sql = "INSERT INTO SanPham (TenSP, SoLuong, Gia, TrangThai)" + "VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, sp.getTenSP());
            statement.setInt(2, sp.getSoLuong());
            statement.setInt(3, sp.getGia());
            statement.setInt(4, sp.getTrangThai());
            
            statement.execute();
        } catch (Exception e) {
            System.out.println("Loi khi insert: " + e.getMessage());
        }
    }

    public void delete(int maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi xóa sản phẩm: " + e.getMessage());
        }
    }

    public void update(SanPhamEntity sp) {
        String sql = "UPDATE SanPham SET TenSP = ?, SoLuong = ?, Gia = ? WHERE MaSP = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getSoLuong());
            ps.setDouble(3, sp.getGia());
            ps.setInt(4, sp.getMaSP());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public SanPhamEntity getOneSP(int id) {
    try {
        Connection con = ConnectDB.getConnect(); 
        String sql = "SELECT sp.MaSP, sp.TenSP, dm.MaDanhMuc, sp.Loai, sp.Gia, sp.TrangThai "
                   + "FROM SanPham sp "
                   + "JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc where sp.MaSanPham = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            SanPhamEntity sp = new SanPhamEntity(
                   result.getInt("MaSP"), 
                    result.getString("TenSP"),
                    result.getInt("LoaiSP"),
                    result.getInt("Gia"),
                    result.getInt("TrangThai"));
            return sp;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}