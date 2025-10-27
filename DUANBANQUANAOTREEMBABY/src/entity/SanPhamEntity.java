package entity;

public class SanPhamEntity {
    private int idSanPham;
    private String tenSp;
    private double gia;
    private int soLuong;
    private int idDanhMuc;
    private String trangThai;
    private int idMauSac;
    private int idKichThuoc;

    // 🔹 Thêm 3 trường mới để hiển thị JOIN
    private String tenDanhMuc;
    private String tenMauSac;
    private String tenKichThuoc;

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

    public SanPhamEntity(int idSanPham, String tenSp, double gia, int soLuong, int idDanhMuc, int idMauSac, int idKichThuoc, String tenDanhMuc, String tenMauSac, String tenKichThuoc) {
        this.idSanPham = idSanPham;
        this.tenSp = tenSp;
        this.gia = gia;
        this.soLuong = soLuong;
        this.idDanhMuc = idDanhMuc;
        this.idMauSac = idMauSac;
        this.idKichThuoc = idKichThuoc;
        this.tenDanhMuc = tenDanhMuc;
        this.tenMauSac = tenMauSac;
        this.tenKichThuoc = tenKichThuoc;
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

    // --- Getter & Setter ---
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

    // --- Getter & Setter cho 3 trường mới ---
    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public String getTenKichThuoc() {
        return tenKichThuoc;
    }

    public void setTenKichThuoc(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }
}
