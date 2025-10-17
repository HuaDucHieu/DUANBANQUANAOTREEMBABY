/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Admin
 */
public class NhanVienEntity {
    private int idNhanVien;
    private String hoTen;
    private String email;
    private String matKhau;
    private String chucVu;

    public NhanVienEntity() {
    }

    public NhanVienEntity(String hoTen, String email, String matKhau, String chucVu) {
        this.hoTen = hoTen;
        this.email = email;
        this.matKhau = matKhau;
        this.chucVu = chucVu;
    }
    
    
    
    public NhanVienEntity(int idNhanVien, String hoTen, String email, String matKhau, String chucVu) {
        this.idNhanVien = idNhanVien;
        this.hoTen = hoTen;
        this.email = email;
        this.matKhau = matKhau;
        this.chucVu = chucVu;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
}

