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

    // ✅ Lấy tất cả danh mục từ database
public List<DanhMucEntity> getAll() {
    // Tạo một danh sách rỗng để chứa các danh mục lấy được từ CSDL
    List<DanhMucEntity> list = new ArrayList<>();
    try {
        // 1️⃣ Lấy kết nối đến SQL Server thông qua lớp ConnectDB
        Connection con = ConnectDB.getConnect();

        // 2️⃣ Câu lệnh SQL để truy vấn tất cả danh mục
        String sql = "SELECT * FROM DanhMuc";

        // 3️⃣ Tạo đối tượng PreparedStatement để thực thi câu SQL
        PreparedStatement statement = con.prepareStatement(sql);

        // 4️⃣ Thực thi truy vấn, trả về ResultSet chứa kết quả
        ResultSet result = statement.executeQuery();

        // 5️⃣ Duyệt qua từng dòng kết quả trong ResultSet
        while (result.next()) {
            // Tạo một đối tượng DanhMucEntity từ dữ liệu trong dòng đó
            DanhMucEntity danhMuc = new DanhMucEntity(
                result.getInt("MaDanhMuc"),   // Lấy giá trị cột MaDanhMuc
                result.getString("TenDanhMuc"), // Lấy giá trị cột TenDanhMuc
                result.getString("MoTa")        // ✅ Lấy thêm giá trị cột MoTa
            );

            // 6️⃣ Thêm đối tượng này vào danh sách
            list.add(danhMuc);
        }
    } catch (Exception e) {
        // Nếu có lỗi, in ra console
        System.out.println("Lỗi get all danh mục: " + e.getMessage());
    }
    // 7️⃣ Trả về danh sách danh mục lấy được
    return list;
}


    // ✅ Thêm một danh mục mới vào database
public void insert(DanhMucEntity danhMuc) {
    try {
        // 1️⃣ Lấy kết nối database
        Connection con = ConnectDB.getConnect();

        // 2️⃣ Viết câu lệnh SQL thêm dữ liệu (chỉ có Tên và Mô tả)
        String sql = "INSERT INTO DanhMuc (TenDanhMuc, MoTa) VALUES (?, ?)";

        // 3️⃣ Chuẩn bị câu lệnh
        PreparedStatement statement = con.prepareStatement(sql);

        // 4️⃣ Truyền giá trị từ đối tượng danhMuc vào dấu “?”
        statement.setString(1, danhMuc.getTenDanhMuc());
        statement.setString(2, danhMuc.getMoTa()); // ✅ thêm mô tả

        // 5️⃣ Thực thi câu lệnh INSERT
        statement.executeUpdate();

    } catch (Exception e) {
        // Nếu lỗi, in ra thông báo
        System.out.println("Lỗi insert danh mục: " + e.getMessage());
    }
}

    // ✅ Cập nhật danh mục trong database
public void update(DanhMucEntity danhMuc) {
    try {
        // 1️⃣ Kết nối đến CSDL
        Connection con = ConnectDB.getConnect();

        // 2️⃣ Viết câu SQL để cập nhật tên và mô tả theo mã danh mục
        String sql = "UPDATE DanhMuc SET TenDanhMuc = ?, MoTa = ? WHERE MaDanhMuc = ?";

        // 3️⃣ Tạo PreparedStatement
        PreparedStatement statement = con.prepareStatement(sql);

        // 4️⃣ Gán giá trị vào các dấu “?”
        statement.setString(1, danhMuc.getTenDanhMuc());
        statement.setString(2, danhMuc.getMoTa());
        statement.setInt(3, danhMuc.getMaDanhMuc());

        // 5️⃣ Thực thi câu lệnh UPDATE
        statement.executeUpdate();

    } catch (Exception e) {
        // In lỗi nếu có
        System.out.println("Lỗi update danh mục: " + e.getMessage());
    }
}

    // ✅ Xóa danh mục theo mã
public void delete(int maDanhMuc) {
    try {
        // 1️⃣ Lấy kết nối đến database
        Connection con = ConnectDB.getConnect();

        // 2️⃣ Câu SQL xóa theo khóa chính (MaDanhMuc)
        String sql = "DELETE FROM DanhMuc WHERE MaDanhMuc = ?";

        // 3️⃣ Chuẩn bị câu lệnh
        PreparedStatement statement = con.prepareStatement(sql);

        // 4️⃣ Gán giá trị mã danh mục vào dấu “?”
        statement.setInt(1, maDanhMuc);

        // 5️⃣ Thực thi lệnh DELETE
        statement.executeUpdate();

    } catch (Exception e) {
        // Báo lỗi nếu có
        System.out.println("Lỗi delete danh mục: " + e.getMessage());
    }
}

}
