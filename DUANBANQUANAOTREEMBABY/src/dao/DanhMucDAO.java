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

    // 📌 LẤY TẤT CẢ DANH MỤC TỪ CSDL
    public List<DanhMucEntity> getAll() {
        List<DanhMucEntity> list = new ArrayList<>(); // Tạo danh sách rỗng để chứa kết quả
        String sql = "SELECT * FROM DanhMuc"; // Câu lệnh SQL lấy toàn bộ dữ liệu

        try (Connection con = ConnectDB.getConnect(); // Kết nối tới CSDL
                 PreparedStatement ps = con.prepareStatement(sql); // Chuẩn bị lệnh SQL
                 ResultSet rs = ps.executeQuery()) { // Thực thi và nhận kết quả

            // Duyệt từng dòng kết quả và gán vào đối tượng DanhMucEntity
            while (rs.next()) {
                DanhMucEntity dm = new DanhMucEntity(
                        rs.getInt("id_danh_muc"),
                        rs.getString("ten_danh_muc"),
                        rs.getString("mo_ta")
                );
                list.add(dm); // Thêm vào danh sách
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAll DanhMuc: " + e.getMessage());
        }
        return list; // Trả về danh sách danh mục
    }

    // 📌 THÊM DANH MỤC MỚI
    public void insert(DanhMucEntity dm) {
        String sql = "INSERT INTO DanhMuc (ten_danh_muc, mo_ta) VALUES ( ?, ?)"; // Câu lệnh thêm có id
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dm.getTenDanhMuc());   // Gán tên danh mục
            ps.setString(2, dm.getMoTa());         // Gán mô tả

            ps.executeUpdate(); // Thực thi lệnh thêm
        } catch (Exception e) {
            System.out.println("Lỗi insert DanhMuc: " + e.getMessage());
        }
    }

    public int updateDanhMuc_DoiTen(DanhMucEntity dmCu, DanhMucEntity dmMoi) {
        String sql = "UPDATE DanhMuc SET ten_danh_muc = ?, mo_ta = ? WHERE id_danh_muc = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dmMoi.getTenDanhMuc());   // tên mới
            ps.setString(2, dmMoi.getMoTa());         // mô tả mới
            ps.setInt(3, dmCu.getIdDanhMuc());        // ID danh mục cần cập nhật

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Không tìm thấy danh mục có ID: " + dmCu.getIdDanhMuc());
            } else {
                System.out.println("✅ Cập nhật thành công danh mục ID: " + dmCu.getIdDanhMuc()
                        + " → Tên mới: " + dmMoi.getTenDanhMuc());
            }

            return rows;
        } catch (Exception e) {
            System.out.println("❌ Lỗi update danh mục: " + e.getMessage());
            return 0;
        }
    }

    // 📌 XÓA DANH MỤC THEO TÊN
    public void deleteByTenDanhMuc(String tenDanhMuc) {
        String sql = "DELETE FROM DanhMuc WHERE ten_danh_muc = ?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenDanhMuc); // Gán tên danh mục cần xóa
            int rows = ps.executeUpdate(); // Thực thi lệnh xóa

            if (rows == 0) {
                System.out.println("Không tìm thấy danh mục có tên: " + tenDanhMuc);
            }
        } catch (Exception e) {
            System.out.println("Lỗi delete DanhMuc theo tên: " + e.getMessage());
        }
    }

    // 📌 TÌM DANH MỤC THEO ID
    public DanhMucEntity findById(int idDanhMuc) {
        String sql = "SELECT * FROM DanhMuc WHERE id_danh_muc=?";
        try (Connection con = ConnectDB.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDanhMuc); // Gán ID cần tìm

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Nếu tìm thấy → tạo đối tượng và trả về
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
        return null; // Không tìm thấy
    }

    // 📌 TÌM KIẾM DANH MỤC THEO TỪ KHÓA
    public List<DanhMucEntity> search(String keyword) {
        List<DanhMucEntity> list = new ArrayList<>();

        try {
            // Kết nối đến database
            Connection con = ConnectDB.getConnect();

            // Chuyển chuỗi người dùng nhập thành số nguyên (ID)
            int id = Integer.parseInt(keyword);

            // Câu lệnh SQL tìm danh mục theo ID
            String sql = "SELECT * FROM DanhMuc WHERE id_danh_muc = ?";

            // Chuẩn bị câu lệnh
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            // Thực thi câu lệnh
            ResultSet rs = ps.executeQuery();

            // Duyệt kết quả trả về
            while (rs.next()) {
                DanhMucEntity dm = new DanhMucEntity();
                dm.setIdDanhMuc(rs.getInt("id_danh_muc"));
                dm.setTenDanhMuc(rs.getString("ten_danh_muc"));
                dm.setMoTa(rs.getString("mo_ta"));

                // Thêm vào danh sách kết quả
                list.add(dm);
            }

            // Đóng kết nối
            rs.close();
            ps.close();
            con.close();

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Vui lòng nhập ID là số nguyên!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi tìm kiếm DanhMuc: " + e.getMessage());
        }

        return list;
    }

    public DanhMucEntity findByName(String tenDM) {
        String sql = "SELECT * FROM DanhMuc WHERE ten_danh_muc = ?";
        try (Connection conn = ConnectDB.getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DanhMucEntity dm = new DanhMucEntity();
                dm.setIdDanhMuc(rs.getInt("id_danh_muc"));
                dm.setTenDanhMuc(rs.getString("ten_danh_muc"));
                dm.setMoTa(rs.getString("mo_ta"));
                return dm;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
