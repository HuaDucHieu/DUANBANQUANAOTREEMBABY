package entity;

public class ChiTietHoaDonEntity {
    private int idChiTietHoaDon;
    private int idHoaDon;
    private int idSanPham;
    private String tenSanPham;
    private String tenDanhMuc;
    private String tenMauSac;
    private String tenKichThuoc;
    private int soLuong;
    private double donGia;

    public ChiTietHoaDonEntity() {}

    public ChiTietHoaDonEntity(int idHoaDon, int idSanPham, String tenSanPham, String tenDanhMuc, String tenMauSac, String tenKichThuoc, int soLuong, double donGia) {
        this.idHoaDon = idHoaDon;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.tenDanhMuc = tenDanhMuc;
        this.tenMauSac = tenMauSac;
        this.tenKichThuoc = tenKichThuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
    
    

    public ChiTietHoaDonEntity(int idChiTietHoaDon, int idHoaDon, int idSanPham, String tenSanPham, 
            String tenDanhMuc, String tenMauSac, String tenKichThuoc, int soLuong, double donGia) {
        this.idChiTietHoaDon = idChiTietHoaDon;
        this.idHoaDon = idHoaDon;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.tenDanhMuc = tenDanhMuc;
        this.tenMauSac = tenMauSac;
        this.tenKichThuoc = tenKichThuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public int getIdChiTietHoaDon() { return idChiTietHoaDon; }
    public void setIdChiTietHoaDon(int idChiTietHoaDon) { this.idChiTietHoaDon = idChiTietHoaDon; }

    public int getIdHoaDon() { return idHoaDon; }
    public void setIdHoaDon(int idHoaDon) { this.idHoaDon = idHoaDon; }

    public int getIdSanPham() { return idSanPham; }
    public void setIdSanPham(int idSanPham) { this.idSanPham = idSanPham; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }

    public String getTenMauSac() { return tenMauSac; }
    public void setTenMauSac(String tenMauSac) { this.tenMauSac = tenMauSac; }

    public String getTenKichThuoc() { return tenKichThuoc; }
    public void setTenKichThuoc(String tenKichThuoc) { this.tenKichThuoc = tenKichThuoc; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public double getThanhTien() {
        return soLuong * donGia;
    }
}
