/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import entity.NhanVienEntity;
/**
 *
 * @author Tran Tien
 */
public class Global {
        public static NhanVienEntity nhanVienDangNhap;
    public static String matKhauDangNhap;

    public static void setSession(NhanVienEntity nv, String mk) {
        nhanVienDangNhap = nv;
        matKhauDangNhap = mk;
    }
}
