/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author duchi
 */
public class KhachHangEntity {
    private int idKhachHang;
    private String hoTen;
    private String sdt;

    public KhachHangEntity() {
    }

    public KhachHangEntity(String hoTen, String sdt) {
        this.hoTen = hoTen;
        this.sdt = sdt;
    }
    
    
    public KhachHangEntity(int idKhachHang, String hoTen, String sdt) {
        this.idKhachHang = idKhachHang;
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
