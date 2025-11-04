/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

// ======= Class ThongTinTamHoaDon.java =======
import java.io.Serializable;

public class ThongTinTamHoaDon implements Serializable {

    private String tenKhachHang;
    private String sdt;
    private int tongSoLuong;
    private double tongTien;
    private double tienKhachDua; // ✅ thêm trường lưu tiền khách đưa

    public ThongTinTamHoaDon(String tenKhachHang, String sdt, int tongSoLuong, double tongTien, double tienKhachDua) {
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.tongSoLuong = tongSoLuong;
        this.tongTien = tongTien;
        this.tienKhachDua = tienKhachDua;
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

    public double getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(double tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }
}
