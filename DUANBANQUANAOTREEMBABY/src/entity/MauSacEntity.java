/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author duchi
 */
public class MauSacEntity {
    private int idMauSac;
    private String tenMauSac;

    public MauSacEntity() {
    }

    public MauSacEntity(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public MauSacEntity(int idMauSac, String tenMauSac) {
        this.idMauSac = idMauSac;
        this.tenMauSac = tenMauSac;
    }

    public int getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(int idMauSac) {
        this.idMauSac = idMauSac;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }
}
