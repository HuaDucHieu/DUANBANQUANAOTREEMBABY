/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


/**
 *
 * @author Tran Tien
 */
public class HoaDonEntity {
    private int idHoaDon;
    private int idKhachHang;
    private String ngayLap;
    private double tongTien;
    private String hinhThucTT;
    private String trangThai;
    private String tenKhachHang; // thêm thuộc tính tạm

    public HoaDonEntity() {
    }

    public HoaDonEntity(int idHoaDon, int idKhachHang, String ngayLap, double tongTien, String hinhThucTT, String trangThai, String tenKhachHang) {
        this.idHoaDon = idHoaDon;
        this.idKhachHang = idKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.hinhThucTT = hinhThucTT;
        this.trangThai = trangThai;
        this.tenKhachHang = tenKhachHang;
    }

    public HoaDonEntity(int idKhachHang, String ngayLap, double tongTien, String hinhThucTT, String trangThai, String tenKhachHang) {
        this.idKhachHang = idKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.hinhThucTT = hinhThucTT;
        this.trangThai = trangThai;
        this.tenKhachHang = tenKhachHang;
    }

    

    // Getter & Setter
    public int getIdHoaDon() { return idHoaDon; }
    public void setIdHoaDon(int idHoaDon) { this.idHoaDon = idHoaDon; }

    public int getIdKhachHang() { return idKhachHang; }
    public void setIdKhachHang(int idKhachHang) { this.idKhachHang = idKhachHang; }

    public String getNgayLap() { return ngayLap; }
    public void setNgayLap(String ngayLap) { this.ngayLap = ngayLap; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public String getHinhThucTT() { return hinhThucTT; }
    public void setHinhThucTT(String hinhThucTT) { this.hinhThucTT = hinhThucTT; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    // Getter & Setter cho tên khách hàng
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
}

