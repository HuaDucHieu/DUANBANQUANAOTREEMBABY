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
            String sql = "SELECT * FROM NhanVien";
            PreparedStatement ps = ConnectDB.getConnect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream

                NhanVienEntity nv = new NhanVienEntity(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ho_ten"),
                        rs.getString("chuc_vu"),
                        rs.getString("mat_khau"),
                        rs.getString("email")
                );

=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
                NhanVienEntity nv = new NhanVienEntity();
                nv.setIdNhanVien(rs.getInt("id_nhan_vien"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setChucVu(rs.getString("chuc_vu"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setEmail(rs.getString("email"));
                
                
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
                list.add(nv);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getAll: " + e.getMessage());
        }
        return list;
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        public int insert(NhanVienEntity nv) {
            int result = 0;
            try {
                String sql = "INSERT INTO NhanVien (ho_ten, chuc_vu, mat_khau, email) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
                ps.setString(1, nv.getHoTen());
                ps.setString(2, nv.getChucVu());
                ps.setString(3, nv.getMatKhau());
                ps.setString(4, nv.getEmail());
                result = ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Lỗi insert: " + e.getMessage());
            }
            return result;
        }

        public int update(NhanVienEntity nv) {
            int result = 0;
            try {
                String sql = "UPDATE NhanVien SET ho_ten=?, email=?, mat_khau=?, chuc_vu=? WHERE id_nhan_vien=?";
                PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
                ps.setString(1, nv.getHoTen());
                ps.setString(2, nv.getEmail());
                ps.setString(3, nv.getMatKhau());
                ps.setString(4, nv.getChucVu());
                ps.setInt(5, nv.getIdNhanVien());
                result = ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Lỗi update: " + e.getMessage());
            }
            return result;
        }

    public int delete(int id) {
        int kq = 0;
        try {
            String sql = "DELETE FROM NhanVien WHERE id_nhan_vien = ?";
            PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
            ps.setInt(1, id);
            kq = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    public List<NhanVienEntity> search(String keyword) {
        List<NhanVienEntity> list = new ArrayList<>();

        try {
            // Kết nối đến database
            Connection con = ConnectDB.getConnect();

            // Chuyển chuỗi người dùng nhập thành số nguyên (ID)
            int id = Integer.parseInt(keyword);

            // Câu lệnh SQL tìm nhân viên theo ID
            String sql = "SELECT * FROM NhanVien WHERE id_nhan_vien = ?";

            // Chuẩn bị câu lệnh
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            // Thực thi câu lệnh
            ResultSet rs = ps.executeQuery();

            // Duyệt kết quả trả về
            while (rs.next()) {
                NhanVienEntity nv = new NhanVienEntity();
                nv.setIdNhanVien(rs.getInt("id_nhan_vien"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setChucVu(rs.getString("chuc_vu"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setEmail(rs.getString("email"));

                // Thêm vào danh sách kết quả
                list.add(nv);
            }

            // Đóng kết nối
            rs.close();
            ps.close();
            con.close();

        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập ID là số nguyên!");
        } catch (Exception e) {
            System.out.println("Lỗi tìm kiếm nhân viên: " + e.getMessage());
        }

=======
    public int insert(NhanVienEntity nv) {
        String sql = "INSERT INTO NhanVien(ho_ten, chuc_vu, mat_khau, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConnectDB.getConnect().prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getEmail());
            
            

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert: " + e.getMessage());
        }
        return -1;
    }

    public int update(NhanVienEntity nv) {
        try {
            String sql = "UPDATE NhanVien SET ho_ten=?, chuc_vu=?, mat_khau=? WHERE email=?";
            PreparedStatement ps = ConnectDB.getConnect().prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());          
            ps.setString(4, nv.getEmail());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update: " + e.getMessage());
        }
        return -1;
    }

=======
    public int insert(NhanVienEntity nv) {
        String sql = "INSERT INTO NhanVien(ho_ten, chuc_vu, mat_khau, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConnectDB.getConnect().prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getEmail());
            
            

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert: " + e.getMessage());
        }
        return -1;
    }

    public int update(NhanVienEntity nv) {
        try {
            String sql = "UPDATE NhanVien SET ho_ten=?, chuc_vu=?, mat_khau=? WHERE email=?";
            PreparedStatement ps = ConnectDB.getConnect().prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());          
            ps.setString(4, nv.getEmail());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update: " + e.getMessage());
        }
        return -1;
    }

>>>>>>> Stashed changes
=======
    public int insert(NhanVienEntity nv) {
        String sql = "INSERT INTO NhanVien(ho_ten, chuc_vu, mat_khau, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConnectDB.getConnect().prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getEmail());
            
            

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi insert: " + e.getMessage());
        }
        return -1;
    }

    public int update(NhanVienEntity nv) {
        try {
            String sql = "UPDATE NhanVien SET ho_ten=?, chuc_vu=?, mat_khau=? WHERE email=?";
            PreparedStatement ps = ConnectDB.getConnect().prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getChucVu());
            ps.setString(3, nv.getMatKhau());          
            ps.setString(4, nv.getEmail());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi update: " + e.getMessage());
        }
        return -1;
    }

>>>>>>> Stashed changes
    public int delete(int id) {
        int kq = 0;
        try {
            String sql = "DELETE FROM NhanVien WHERE id_nhan_vien = ?";
            PreparedStatement ps = ConnectDB.con.prepareStatement(sql);
            ps.setInt(1, id);
            kq = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    public List<NhanVienEntity> search(String keyword) {
        List<NhanVienEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE id_nhan_vien LIKE ? OR ho_ten LIKE ?";
        try (Connection con = ConnectDB.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVienEntity nv = new NhanVienEntity();
                nv.setIdNhanVien(rs.getInt("id_nhan_vien"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setChucVu(rs.getString("chuc_vu"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setEmail(rs.getString("email"));
                
                
                list.add(nv);
            }

        } catch (Exception e) {
            System.out.println("Lỗi tìm kiếm NhanVien: " + e.getMessage());
        }
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        return list;
    }
}

