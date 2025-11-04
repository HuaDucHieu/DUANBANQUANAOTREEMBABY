/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.*;
import entity.ChiTietHoaDonEntity;
import entity.HoaDonEntity;
import entity.KhachHangEntity;
import entity.SanPhamEntity;
import entity.ThongTinTamHoaDon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author duchi
 */
public class QuanLyChiTietHoaDonJPanel extends javax.swing.JPanel {

    HoaDonDAO hoaDonDAO = new HoaDonDAO();
    KhachHangDAO khachHangDAO = new KhachHangDAO();
    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
//    DanhMucDAO danhMucDAO = new DanhMucDAO();
//    MauSacDAO mauSacDAO = new MauSacDAO();
//    KichThuocDAO kichThuocDAO = new KichThuocDAO();

    private Map<Integer, KhachHangEntity> khachHangTam = new HashMap<>();
    private HashMap<Integer, ThongTinTamHoaDon> hoaDonTam = new HashMap<>();
    private final String HOADON_FILE = System.getProperty("user.home") + "/hoadon.dat";

    // ✅ Khai báo biến toàn cục để lưu hóa đơn đang chọn
    private int idHoaDonDangChon = -1; // -1 nghĩa là chưa chọn hóa đơn nào
    // Biến toàn cục để lưu sản phẩm đang chọn
    private int idSanPhamDangChon = -1;  // -1 nghĩa là chưa chọn
    private String tenSanPhamDangChon = "";
    private double tienKhachDuaTam = 0;
    private double tienThoiTam = 0;
    private int idKhachHangDangChon = -1;

    /**
     * Creates new form ChiTietHoaDonJPanel
     */
    public QuanLyChiTietHoaDonJPanel() {
        initComponents();
        fillTableHoaDonCho();
        fillTableKhachHang();
        fillTableSanPham();
        loadHoaDonTam();
        // 🔸 Tự động lưu khi tắt chương trình
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveHoaDonTam();
        }));

    }

    private void fillTableHoaDonCho() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonCho.getModel();
        model.setRowCount(0);
        List<HoaDonEntity> list = hoaDonDAO.getHoaDonCho();
        for (HoaDonEntity hd : list) {
            model.addRow(new Object[]{
                hd.getIdHoaDon(),
                hd.getNgayLap(),
                hd.getTrangThai()
            });
        }
    }

    private void fillTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        List<SanPhamEntity> list = sanPhamDAO.getAllWithDetails();
        for (SanPhamEntity sp : list) {
            model.addRow(new Object[]{
                sp.getIdSp(),
                sp.getTenSp(),
                sp.getTenDanhMuc(),
                sp.getTenMauSac(),
                sp.getTenKichThuoc(),
                sp.getSoLuong(),
                sp.getGia(),
                sp.getTrangThai()
            });
        }
    }

    private void fillTableKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        List<KhachHangEntity> list = khachHangDAO.getAll();
        for (KhachHangEntity kh : list) {
            model.addRow(new Object[]{
                kh.getIdKhachHang(),
                kh.getHoTen(),
                kh.getSdt()
            });
        }
    }

    private void fillTableChiTietHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);

        // Kiểm tra đã chọn hóa đơn chưa
        if (idHoaDonDangChon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước!");
            return;
        }

        // Lấy danh sách chi tiết hóa đơn theo id hóa đơn đang chọn
        List<ChiTietHoaDonEntity> list = chiTietHoaDonDAO.getByIdHoaDon(idHoaDonDangChon);

        // Tạo DecimalFormat để format số
        DecimalFormat df = new DecimalFormat("#,###");

        for (ChiTietHoaDonEntity ct : list) {
            double thanhTien = ct.getSoLuong() * ct.getDonGia();

            // Format đơn giá và thành tiền
            String donGiaStr = df.format(ct.getDonGia());
            String thanhTienStr = df.format(thanhTien);

            model.addRow(new Object[]{
                ct.getIdChiTietHoaDon(),
                ct.getTenSanPham(),
                ct.getTenDanhMuc(),
                ct.getTenMauSac(),
                ct.getTenKichThuoc(),
                ct.getSoLuong(),
                donGiaStr,
                thanhTienStr
            });
        }
    }
    // ✅ Lưu tạm tất cả hóa đơn vào file hoadon.dat

    public void saveHoaDonTam() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(System.getProperty("user.home") + "/hoadon.dat"))) {
            oos.writeObject(hoaDonTam);
            System.out.println("💾 Đã lưu hóa đơn tạm vào file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHoaDonTam() {
        File file = new File(System.getProperty("user.home") + "/hoadon.dat");
        if (!file.exists()) {
            return; // chưa có file thì thôi
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            hoaDonTam = (HashMap<Integer, ThongTinTamHoaDon>) ois.readObject();
            System.out.println("💾 Đã load hóa đơn tạm từ file!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonCho = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        txtTimKH = new javax.swing.JTextField();
        btnTimKiemKH = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnThemSanPham = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        btnXacNhan = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        txtTienKhachDua = new javax.swing.JTextField();
        txtTienHoanLai = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnXoaHoaDon = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        BtnThemKH = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setForeground(new java.awt.Color(51, 51, 51));

        jPanel7.setBackground(new java.awt.Color(255, 153, 153));

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Hóa đơn chờ");

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblHoaDonCho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID hóa đơn", "Ngày lập", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonCho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDonCho);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        btnTaoHoaDon.setBackground(new java.awt.Color(0, 204, 204));
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Danh sách Khách hàng ");

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID khách hàng", "Tên khách hàng", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachHang);

        scrollPane1.add(jScrollPane2);

        txtTimKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKHActionPerformed(evt);
            }
        });

        btnTimKiemKH.setText("Tìm kiếm");
        btnTimKiemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(54, 54, 54)
                        .addComponent(txtTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnTimKiemKH))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTimKiemKH)
                            .addComponent(txtTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 102, 102));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Danh sách sản phẩm");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID sản phẩm", "Tên sản phẩm", "Danh mục", "Màu sắc", "Kích thước ", "Số lượng ", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThemSanPham.setText("Thêm");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnTimKiem))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
                .addComponent(btnThemSanPham)
                .addGap(44, 44, 44)
                .addComponent(btnCapNhat)
                .addGap(44, 44, 44))
            .addComponent(jScrollPane3)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnThemSanPham)
                    .addComponent(btnCapNhat))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Chi tiết hóa đơn");

        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID sản phẩm", "Tên sản phẩm", "Danh mục", "Màu sắc", "Kích thước", "Số lượng bán", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblChiTietHoaDon);

        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 490, Short.MAX_VALUE)
                .addComponent(btnXacNhan)
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnXacNhan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 102, 102));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Hóa đơn");

        jLabel7.setText("Thông tin khách hàng:");

        jLabel8.setText("Tên khách hàng");

        jLabel9.setText("Số điện thoại");

        txtTenKhachHang.setFocusable(false);

        txtSoDienThoai.setFocusable(false);

        jPanel6.setBackground(new java.awt.Color(255, 102, 102));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Thông tin hóa đơn:");

        jLabel12.setText("Số lượng sp");

        jLabel13.setText("Tổng tiền");

        jLabel15.setText("Phương thức tt");

        jLabel16.setText("Tiền khách đưa");

        jLabel17.setText("Tiền hoàn lại");

        txtSoLuong.setFocusable(false);

        txtTongTien.setFocusable(false);

        txtTienKhachDua.setFocusable(false);

        txtTienHoanLai.setFocusable(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", " " }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel15))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTongTien)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienKhachDua)
                            .addComponent(txtTienHoanLai)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(45, 45, 45))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTienHoanLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnXoaHoaDon.setBackground(new java.awt.Color(0, 204, 204));
        btnXoaHoaDon.setForeground(new java.awt.Color(51, 51, 51));
        btnXoaHoaDon.setText("Xóa hóa đơn");
        btnXoaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHoaDonActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 204));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        BtnThemKH.setText("Thêm ");
        BtnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnThemKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(124, 124, 124))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(txtSoDienThoai)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(BtnThemKH)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(btnXoaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(BtnThemKH))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(btnXoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        try {
            // 🔹 Tạo đối tượng hóa đơn mới
            HoaDonEntity hd = new HoaDonEntity();

            // 🔹 Lấy thời gian hiện tại
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            String ngayLap = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            hd.setNgayLap(ngayLap);
            hd.setTrangThai("Đang xử lý"); // ✅ thay "Chờ" thành "Đang xử lý"

            // 🔹 Thêm vào DB và lấy ID vừa tạo
            int id = hoaDonDAO.insertAndGetId(hd);

            if (id != -1) {
                JOptionPane.showMessageDialog(this, "✅ Tạo hóa đơn thành công! Mã hóa đơn: " + id);
                fillTableHoaDonCho(); // ✅ cập nhật lại bảng
            } else {
                JOptionPane.showMessageDialog(this, "❌ Tạo hóa đơn thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo hóa đơn!");
        }
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnXoaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHoaDonActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblHoaDonCho.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần hủy!");
            return;
        }

        int idHoaDon = (int) tblHoaDonCho.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn hủy hóa đơn này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            HoaDonEntity hd = hoaDonDAO.getById(idHoaDon);
            if (hd != null) {
                hd.setTrangThai("Hủy");
                hoaDonDAO.update(hd);
                JOptionPane.showMessageDialog(this, "Đã hủy hóa đơn!");
                fillTableHoaDonCho();
            }
        }
    }//GEN-LAST:event_btnXoaHoaDonActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            int idKH = (int) tblKhachHang.getValueAt(row, 0);
            String ten = (String) tblKhachHang.getValueAt(row, 1);
            String sdt = (String) tblKhachHang.getValueAt(row, 2);

            txtTenKhachHang.setText(ten);
            txtSoDienThoai.setText(sdt);
            txtTenKhachHang.setEditable(false);
            txtSoDienThoai.setEditable(false);

            JOptionPane.showMessageDialog(this, "Đã chọn khách hàng: " + ten);

            // ✅ Lưu luôn id khách hàng vào hóa đơn đang xử lý
            if (idHoaDonDangChon != -1) {
                HoaDonEntity hd = hoaDonDAO.getById(idHoaDonDangChon);
                hd.setIdKhachHang(idKH);
                hoaDonDAO.update(hd);
                System.out.println("Đã cập nhật id_khach_hang cho hóa đơn " + idHoaDonDangChon);
            } else {
                JOptionPane.showMessageDialog(this, "Chưa có hóa đơn được chọn để gán khách hàng!");
            }
        }
//        saveHoaDonTam();
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void BtnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnThemKHActionPerformed
        // TODO add your handling code here:
        String ten = JOptionPane.showInputDialog(this, "Nhập tên khách hàng:");

// ✅ Nếu bấm nút X → thoát luôn, không báo lỗi
        if (ten == null) {
            return;
        }

// ⚠️ Nếu để trống → báo lỗi
        if (ten.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống!");
            return;
        }

        String sdt = JOptionPane.showInputDialog(this, "Nhập số điện thoại:");

// ✅ Nếu bấm nút X → thoát luôn, không báo lỗi
        if (sdt == null) {
            return;
        }

// ⚠️ Nếu để trống → báo lỗi
        if (sdt.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "SĐT không được để trống!");
            return;
        }

// ✅ Tạo mới entity và lưu vào DB
        KhachHangEntity kh = new KhachHangEntity();
        kh.setHoTen(ten);
        kh.setSdt(sdt);
        khachHangDAO.insert(kh);

// ✅ Cập nhật lại bảng khách hàng
        fillTableKhachHang();

// ✅ Lưu tạm khách hàng cho hóa đơn đang chọn (nếu có)
//        if (idHoaDonDangChon != -1) {
//            if (khachHangTam == null) {
//                khachHangTam = new HashMap<>();
//            }
//            khachHangTam.put(idHoaDonDangChon, kh);
//        }
// ✅ Tự động hiển thị khách vừa thêm
        txtTenKhachHang.setText(ten);
        txtSoDienThoai.setText(sdt);

// ✅ Thông báo
        JOptionPane.showMessageDialog(this, "Đã thêm khách hàng mới thành công!");
    }//GEN-LAST:event_BtnThemKHActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String input = txtTimKiem.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm!");
            return;
        }

        try {
            int idSanPham = Integer.parseInt(input);
            SanPhamEntity sp = sanPhamDAO.findById(idSanPham);

            if (sp == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có mã: " + idSanPham);
                return;
            }

            // Xóa bảng cũ, hiển thị sản phẩm tìm được
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0);
            model.addRow(new Object[]{
                sp.getIdSp(),
                sp.getTenSp(),
                sp.getTenDanhMuc(),
                sp.getTenMauSac(),
                sp.getTenKichThuoc(),
                sp.getGia(),
                sp.getSoLuong(),
                sp.getTrangThai()
            });

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm phải là số!");
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        int row = tblSanPham.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
            return;
        }

        // Kiểm tra xem đã chọn hóa đơn chưa
        if (idHoaDonDangChon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước!");
            return;
        }

        // Lấy hóa đơn và kiểm tra trạng thái
        HoaDonEntity hd = hoaDonDAO.getById(idHoaDonDangChon);
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Hóa đơn không tồn tại!");
            return;
        }

        if ("Đã thanh toán".equalsIgnoreCase(hd.getTrangThai())) {
            JOptionPane.showMessageDialog(this, "⚠️ Hóa đơn này đã thanh toán, không thể thêm sản phẩm!");
            return;
        }

        if ("Hủy".equalsIgnoreCase(hd.getTrangThai())) {
            JOptionPane.showMessageDialog(this, "⚠️ Hóa đơn này đã bị hủy, không thể thêm sản phẩm!");
            return;
        }

        // Lấy thông tin sản phẩm được chọn
        int idSanPham = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
        String tenSanPham = tblSanPham.getValueAt(row, 1).toString();
        double gia = Double.parseDouble(tblSanPham.getValueAt(row, 6).toString());

        // Hỏi người dùng nhập số lượng
        String soLuongStr = JOptionPane.showInputDialog(this, "Nhập số lượng cho sản phẩm: " + tenSanPham);
        if (soLuongStr == null || soLuongStr.trim().isEmpty()) {
            return;
        }

        int soLuong;
        try {
            soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
            return;
        }

        // Lấy sản phẩm từ DB để kiểm tra tồn kho
        SanPhamEntity sp = sanPhamDAO.findById(idSanPham);
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm trong cơ sở dữ liệu!");
            return;
        }

        // Kiểm tra xem sản phẩm đã tồn tại trong chi tiết hóa đơn chưa
        ChiTietHoaDonEntity existingCT = chiTietHoaDonDAO.findByHoaDonAndSanPham(idHoaDonDangChon, idSanPham);

        boolean success = false;
        if (existingCT != null) {
            // Cộng dồn số lượng
            int soLuongMoi = existingCT.getSoLuong() + soLuong;

            // Kiểm tra tồn kho
            if (soLuongMoi > sp.getSoLuong()) {
                JOptionPane.showMessageDialog(this,
                        "Số lượng vượt quá tồn kho! Chỉ còn " + sp.getSoLuong() + " sản phẩm.");
                return;
            }

            existingCT.setSoLuong(soLuongMoi);
            existingCT.setDonGia(gia);

            success = chiTietHoaDonDAO.update2(existingCT);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Đã cập nhật số lượng sản phẩm trong hóa đơn!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Cập nhật sản phẩm thất bại!");
            }

        } else {
            // Nếu chưa có → thêm mới
            if (soLuong > sp.getSoLuong()) {
                JOptionPane.showMessageDialog(this,
                        "Số lượng vượt quá tồn kho! Chỉ còn " + sp.getSoLuong() + " sản phẩm.");
                return;
            }

            ChiTietHoaDonEntity ct = new ChiTietHoaDonEntity();
            ct.setIdHoaDon(idHoaDonDangChon);
            ct.setIdSanPham(idSanPham);
            ct.setSoLuong(soLuong);
            ct.setDonGia(gia);

            success = chiTietHoaDonDAO.insert2(ct);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Đã thêm sản phẩm mới vào hóa đơn!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Thêm sản phẩm thất bại!");
            }
        }

        // Cập nhật lại bảng chi tiết hóa đơn
        fillTableChiTietHoaDon();
//        saveHoaDonTam();
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void tblHoaDonChoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChoMouseClicked
        int row = tblHoaDonCho.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn!");
            return;
        }

        // Lấy id hóa đơn từ JTable
        int idHoaDon = Integer.parseInt(tblHoaDonCho.getValueAt(row, 0).toString());
        idHoaDonDangChon = idHoaDon;

        try {
            HoaDonEntity hd = hoaDonDAO.getById(idHoaDonDangChon);
            if (hd == null) {
                JOptionPane.showMessageDialog(this, "Hóa đơn này không tồn tại trong DB!");
                return;
            }

            DecimalFormat df = new DecimalFormat("0"); // hiển thị toàn số

            // Nếu đã có thông tin tạm lưu
            if (hoaDonTam.containsKey(idHoaDonDangChon)) {
                ThongTinTamHoaDon thongTin = hoaDonTam.get(idHoaDonDangChon);
                txtTenKhachHang.setText(thongTin.getTenKhachHang());
                txtSoDienThoai.setText(thongTin.getSdt());
                txtSoLuong.setText(String.valueOf(thongTin.getTongSoLuong()));
                txtTongTien.setText(df.format(thongTin.getTongTien()));
                txtTienKhachDua.setText(thongTin.getTienKhachDua() > 0 ? df.format(thongTin.getTienKhachDua()) : "");
            } else {
                // Lấy thông tin khách hàng từ DB nếu có
                if (hd.getIdKhachHang() > 0) {
                    KhachHangEntity kh = khachHangDAO.getById(hd.getIdKhachHang());
                    if (kh != null) {
                        txtTenKhachHang.setText(kh.getHoTen());
                        txtSoDienThoai.setText(kh.getSdt());
                    } else {
                        txtTenKhachHang.setText("Chưa chọn");
                        txtSoDienThoai.setText("");
                    }
                } else {
                    txtTenKhachHang.setText("Chưa chọn");
                    txtSoDienThoai.setText("");
                }

                // Tính tổng số lượng và tổng tiền từ chi tiết hóa đơn
                List<ChiTietHoaDonEntity> listCT = chiTietHoaDonDAO.getByIdHoaDon(idHoaDonDangChon);
                int tongSoLuong = 0;
                double tongTien = 0;
                for (ChiTietHoaDonEntity ct : listCT) {
                    tongSoLuong += ct.getSoLuong();
                    tongTien += ct.getSoLuong() * ct.getDonGia();
                }

                txtSoLuong.setText(String.valueOf(tongSoLuong));
                txtTongTien.setText(df.format(tongTien));
                txtTienKhachDua.setText(""); // chưa nhập, giữ trống
            }

            // Reset tiền thối và chọn phương thức thanh toán
            txtTienHoanLai.setText("");
            jComboBox1.setSelectedIndex(0);

            // Load chi tiết hóa đơn lên JTable
            fillTableChiTietHoaDon();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin hóa đơn!");
        }


    }//GEN-LAST:event_tblHoaDonChoMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int row = tblSanPham.getSelectedRow();
        if (row >= 0) {

            // Kiểm tra đã chọn hóa đơn chưa
            if (idHoaDonDangChon == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi chọn sản phẩm!");
                return;
            }

            // Kiểm tra đã chọn khách hàng chưa (nếu cần)
            if (txtTenKhachHang.getText() == null || txtTenKhachHang.getText().trim().isEmpty() || "Chưa chọn".equals(txtTenKhachHang.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi chọn sản phẩm!");
                return;
            }

            // Lấy id và tên sản phẩm
            int idSanPhamDangChon = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
            String tenSanPham = tblSanPham.getValueAt(row, 1).toString();

            // Lưu vào biến toàn cục
            this.idSanPhamDangChon = idSanPhamDangChon;
            this.tenSanPhamDangChon = tenSanPham;

            // Hiển thị thông báo
            JOptionPane.showMessageDialog(this, "Đã chọn sản phẩm: " + tenSanPham);
            System.out.println("Sản phẩm đang chọn: " + idSanPhamDangChon + " - " + tenSanPham);

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm!");
        }

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
//        int row = tblSanPham.getSelectedRow();
//        if (row < 0) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để cập nhật!");
//            return;
//        }
//
//        try {
//            // Lấy thông tin từ JTable
//            int idSanPham = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
//            String tenSanPham = tblSanPham.getValueAt(row, 1).toString();
//            String tenDanhMuc = tblSanPham.getValueAt(row, 2).toString();
//            String tenMauSac = tblSanPham.getValueAt(row, 3).toString();
//            String tenKichThuoc = tblSanPham.getValueAt(row, 4).toString();
//            double donGia = Double.parseDouble(tblSanPham.getValueAt(row, 5).toString());
//
//            // Nếu bạn dùng ID cho danh mục, màu sắc, kích thước, thì cần tra ID từ tên
//            int idDanhMuc = danhMucDAO.findIdByName(tenDanhMuc);
//            int idMauSac = mauSacDAO.findIdByName(tenMauSac);
//            int idKichThuoc = kichThuocDAO.findIdByName(tenKichThuoc);
//
//            // Tạo entity sản phẩm
//            SanPhamEntity sp = new SanPhamEntity();
//            sp.setIdSp(idSanPham);
//            sp.setTenSp(tenSanPham);
//            sp.setIdDanhMuc(idDanhMuc);
//            sp.setIdMauSac(idMauSac);
//            sp.setIdKichThuoc(idKichThuoc);
//            sp.setGia(donGia);
//
//            // Cập nhật vào DB
//            boolean success = sanPhamDAO.update2(sp);
//            if (success) {
//                JOptionPane.showMessageDialog(this, "✅ Cập nhật sản phẩm thành công!");
//                fillTableSanPham(); // Load lại bảng
//            } else {
//                JOptionPane.showMessageDialog(this, "❌ Cập nhật thất bại!");
//            }
//
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật sản phẩm!");
//        }
        try {
            fillTableSanPham(); // Gọi hàm load lại toàn bộ sản phẩm
            JOptionPane.showMessageDialog(this, "🔄 Đã làm mới danh sách sản phẩm!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Có lỗi khi làm mới bảng sản phẩm!");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        if (idHoaDonDangChon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước!");
            return;
        }

        HoaDonEntity hd = hoaDonDAO.getById(idHoaDonDangChon);
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Hóa đơn không tồn tại!");
            return;
        }

        if (txtTenKhachHang.getText().equals("Chưa chọn") || txtTenKhachHang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi thanh toán!");
            return;
        }

        KhachHangEntity kh = khachHangDAO.findByName(txtTenKhachHang.getText());
        if (kh == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
            return;
        }

        hd.setIdKhachHang(kh.getIdKhachHang());
        hoaDonDAO.updateKhachHang(idHoaDonDangChon, kh.getIdKhachHang());

        List<ChiTietHoaDonEntity> listCT = chiTietHoaDonDAO.getByIdHoaDon(idHoaDonDangChon);
        if (listCT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm!");
            return;
        }

        double tongTien = 0;
        int tongSoLuong = 0;
        for (ChiTietHoaDonEntity ct : listCT) {
            tongTien += ct.getSoLuong() * ct.getDonGia();
            tongSoLuong += ct.getSoLuong();
        }
        txtSoLuong.setText(String.valueOf(tongSoLuong));
        txtTongTien.setText(String.valueOf(tongTien));

        // Nhập tiền khách đưa
        double tienKhachDua = 0;
        if (hoaDonTam.containsKey(idHoaDonDangChon)) {
            tienKhachDua = hoaDonTam.get(idHoaDonDangChon).getTienKhachDua();
        }

        String strTien = JOptionPane.showInputDialog(this,
                "Nhập tiền khách đưa:", tienKhachDua > 0 ? tienKhachDua : "");
        if (strTien == null) {
            return;
        }

        try {
            tienKhachDua = Double.parseDouble(strTien);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ!");
            return;
        }

        if (tienKhachDua < tongTien) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không đủ!");
            return;
        }

        double tienThoi = tienKhachDua - tongTien;

        // Lưu tiền khách đưa vào HashMap
        ThongTinTamHoaDon thongTinTam = new ThongTinTamHoaDon(
                txtTenKhachHang.getText(),
                txtSoDienThoai.getText(),
                tongSoLuong,
                tongTien,
                tienKhachDua
        );
        hoaDonTam.put(idHoaDonDangChon, thongTinTam);

        // Cập nhật hóa đơn
        hd.setHinhThucTT(jComboBox1.getSelectedItem().toString());
        hd.setTongTien(tongTien);
        hd.setTrangThai("Đã thanh toán");
        hoaDonDAO.update(hd);

        // Cập nhật tồn kho
        for (ChiTietHoaDonEntity ct : listCT) {
            SanPhamEntity sp = sanPhamDAO.findById(ct.getIdSanPham());
            sp.setSoLuong(sp.getSoLuong() - ct.getSoLuong());
            sanPhamDAO.update2(sp);
        }

        JOptionPane.showMessageDialog(this,
                "✅ Thanh toán thành công!\n"
                + "Tổng tiền: " + tongTien + " VNĐ\n"
                + "Tiền khách đưa: " + tienKhachDua + " VNĐ\n"
                + "Tiền thối lại: " + tienThoi + " VNĐ");

        // Reset form
        txtTenKhachHang.setText("Chưa chọn");
        txtSoDienThoai.setText("");
        txtSoLuong.setText("");
        txtTongTien.setText("");
        txtTienKhachDua.setText("");
        txtTienHoanLai.setText("");
        jComboBox1.setSelectedIndex(0);

        fillTableChiTietHoaDon();
        fillTableSanPham();
        fillTableHoaDonCho();

        hoaDonTam.remove(idHoaDonDangChon);
        idHoaDonDangChon = -1;
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTimKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKHActionPerformed

    private void btnTimKiemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKHActionPerformed
        String keyword = txtTimKH.getText().trim(); // 👈 lấy nội dung trong ô nhập

        // 1️⃣ Kiểm tra trống
        if (keyword == null || keyword.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng!");
            return;
        }

        // 2️⃣ Kiểm tra có phải là số không
        if (!keyword.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng phải là số!");
            return;
        }

        // 3️⃣ Chuyển sang số và tìm
        int idKhachHang = Integer.parseInt(keyword);
        KhachHangEntity kh = khachHangDAO.getById(idKhachHang);

        // 4️⃣ Đổ dữ liệu ra bảng
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);

        if (kh != null) {
            model.addRow(new Object[]{
                kh.getIdKhachHang(),
                kh.getHoTen(),
                kh.getSdt()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng có mã: " + idKhachHang);
            fillTableKhachHang(); // Làm mới lại bảng
        }

    }//GEN-LAST:event_btnTimKiemKHActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (idHoaDonDangChon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước!");
            return;
        }

        HoaDonEntity hd = hoaDonDAO.getById(idHoaDonDangChon);
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Hóa đơn không tồn tại!");
            return;
        }

        // Kiểm tra trạng thái
        if ("Đã thanh toán".equalsIgnoreCase(hd.getTrangThai())) {
            JOptionPane.showMessageDialog(this, "⚠️ Hóa đơn này đã được thanh toán trước đó!");
            return;
        }
        if ("Hủy".equalsIgnoreCase(hd.getTrangThai())) {
            JOptionPane.showMessageDialog(this, "⚠️ Hóa đơn này đã bị hủy, không thể xác nhận!");
            return;
        }

        if (txtTenKhachHang.getText().equals("Chưa chọn") || txtTenKhachHang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi xác nhận!");
            return;
        }

        List<ChiTietHoaDonEntity> listCT = chiTietHoaDonDAO.getByIdHoaDon(idHoaDonDangChon);
        if (listCT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm!");
            return;
        }

        double tongTien = 0;
        int tongSoLuong = 0;
        for (ChiTietHoaDonEntity ct : listCT) {
            tongTien += ct.getSoLuong() * ct.getDonGia();
            tongSoLuong += ct.getSoLuong();
        }

        txtSoLuong.setText(String.valueOf(tongSoLuong));
        txtTongTien.setText(String.valueOf(tongTien));

        // Lưu tạm vào HashMap (tiền khách đưa giữ nguyên nếu đã nhập)
        ThongTinTamHoaDon thongTinTam = new ThongTinTamHoaDon(
                txtTenKhachHang.getText(),
                txtSoDienThoai.getText(),
                tongSoLuong,
                tongTien,
                hoaDonTam.containsKey(idHoaDonDangChon)
                ? hoaDonTam.get(idHoaDonDangChon).getTienKhachDua()
                : 0
        );
        hoaDonTam.put(idHoaDonDangChon, thongTinTam);

        // Cập nhật trạng thái sang "Đang xử lý"
        hd.setTongTien(tongTien);
        hd.setTrangThai("Đang xử lý");
        hoaDonDAO.update(hd);

        JOptionPane.showMessageDialog(this,
                "✅ Đơn hàng đã được xác nhận!\n"
                + "Tổng tiền: " + tongTien + " VNĐ\n"
                + "Số lượng: " + tongSoLuong + "\n"
                + "Thông tin khách: " + txtTenKhachHang.getText());

        fillTableHoaDonCho(); // cập nhật danh sách hóa đơn chờ
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void tblKhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKhachHangMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnThemKH;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTimKiemKH;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaHoaDon;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblHoaDonCho;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienHoanLai;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimKH;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
