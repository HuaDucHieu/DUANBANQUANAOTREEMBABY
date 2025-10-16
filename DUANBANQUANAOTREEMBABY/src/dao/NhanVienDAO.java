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
        try {
            Connection con = ConnectDB.getConnect(); 
            String sql = "SELECT * FROM NhanVien";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                NhanVienEntity nv = new NhanVienEntity(
                    result.getInt("MaNV"),
                    result.getString("TenNV"),
                    result.getString("GioiTinh"),
                    result.getString("NgaySinh"),
                    result.getString("ChucVu"),
                    result.getString("SDT"),
                    result.getString("MatKhau"),
                    result.getString("Email"));
                list.add(nv);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll nhân viên: " + e.getMessage());
        }
        return list;
    }

    public void insert(NhanVienEntity nv) {
        Connection con = ConnectDB.getConnect();
        String sql = "INSERT INTO NhanVien (TenNV, GioiTinh, NgaySinh, ChucVu, SDT, MatKhau, Email) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getGioiTinh());
            ps.setString(3, nv.getNgaySinh());
            ps.setString(4, nv.getChucVu());
            ps.setString(5, nv.getSdt());
            ps.setString(6, nv.getMatKhau());
            ps.setString(7, nv.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert nhân viên: " + e.getMessage());
        }
    }

    public int delete(String maNV) {
        int result = 0;
        try {
            Connection con = ConnectDB.getConnect();
            String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maNV);
            result = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public void update(NhanVienEntity nv) {
        String sql = "UPDATE NhanVien SET TenNV = ?, GioiTinh = ?, NgaySinh = ?, ChucVu = ?, SDT = ?, MatKhau = ?, Email = ? WHERE MaNV = ?";
        Connection con = ConnectDB.getConnect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getGioiTinh());
            ps.setString(3, nv.getNgaySinh());
            ps.setString(4, nv.getChucVu());
            ps.setString(5, nv.getSdt());
            ps.setString(6, nv.getMatKhau());
            ps.setString(7, nv.getEmail());
            ps.setInt(8, nv.getMaNV());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
