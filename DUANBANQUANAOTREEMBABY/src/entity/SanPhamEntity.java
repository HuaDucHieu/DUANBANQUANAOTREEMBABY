/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Admin
 */
public class SanPhamEntity {
    private int idSanPham;
    private String tenSp;
    private double gia;
    private int soLuong;
    private int idDanhMuc;
    private String trangThai;
    private int idMauSac;
    private int idKichThuoc;

    public SanPhamEntity() {
    }

    public SanPhamEntity(int idSanPham, String tenSp, double gia, int soLuong, int idDanhMuc, String trangThai, int idMauSac, int idKichThuoc) {
        this.idSanPham = idSanPham;
        this.tenSp = tenSp;
        this.gia = gia;
        this.soLuong = soLuong;
        this.idDanhMuc = idDanhMuc;
        this.trangThai = trangThai;
        this.idMauSac = idMauSac;
        this.idKichThuoc = idKichThuoc;
    }

    public SanPhamEntity(String tenSp, double gia, int soLuong, int idDanhMuc, String trangThai, int idMauSac, int idKichThuoc) {
        this.tenSp = tenSp;
        this.gia = gia;
        this.soLuong = soLuong;
        this.idDanhMuc = idDanhMuc;
        this.trangThai = trangThai;
        this.idMauSac = idMauSac;
        this.idKichThuoc = idKichThuoc;
    }
    
    public int getIdSp() {
        return idSanPham;
    }

    public void setIdSp(int idSp) {
        this.idSanPham = idSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(int idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(int idMauSac) {
        this.idMauSac = idMauSac;
    }

    public int getIdKichThuoc() {
        return idKichThuoc;
    }

    public void setIdKichThuoc(int idKichThuoc) {
        this.idKichThuoc = idKichThuoc;
    }
}
