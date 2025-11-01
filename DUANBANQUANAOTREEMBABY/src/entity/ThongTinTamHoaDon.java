/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author duchi
 */
public class ThongTinTamHoaDon implements Serializable {
    private String tenKhachHang;
    private String sdt;
    private int tongSoLuong;
    private double tongTien;

    public ThongTinTamHoaDon(String tenKhachHang, String sdt, int tongSoLuong, double tongTien) {
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.tongSoLuong = tongSoLuong;
        this.tongTien = tongTien;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public double getTongTien() {
        return tongTien;
    }
}