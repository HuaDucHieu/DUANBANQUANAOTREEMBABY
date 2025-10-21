/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author duchi
 */
public class KichThuocEntity {
    private int idKichThuoc;
    private String tenKichThuoc;

    public KichThuocEntity() {
    }

    public KichThuocEntity(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }
    
    public KichThuocEntity(int idKichThuoc, String tenKichThuoc) {
        this.idKichThuoc = idKichThuoc;
        this.tenKichThuoc = tenKichThuoc;
    }

    public int getIdKichThuoc() {
        return idKichThuoc;
    }

    public void setIdKichThuoc(int idKichThuoc) {
        this.idKichThuoc = idKichThuoc;
    }

    public String getTenKichThuoc() {
        return tenKichThuoc;
    }

    public void setTenKichThuoc(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }
}
