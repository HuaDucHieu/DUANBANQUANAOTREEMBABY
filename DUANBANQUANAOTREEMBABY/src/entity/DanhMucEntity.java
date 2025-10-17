/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Tran Tien
 */
public class DanhMucEntity {
    private int idDanhMuc;
    private String tenDanhMuc;
    private String moTa;

    public DanhMucEntity() {
    }
    
    

    public DanhMucEntity(int idDanhMuc, String tenDanhMuc, String moTa) {
        this.idDanhMuc = idDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.moTa = moTa;
    }

    public DanhMucEntity(String tenDanhMuc, String moTa) {
        this.tenDanhMuc = tenDanhMuc;
        this.moTa = moTa;
    }

    public int getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(int idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    
}
   
