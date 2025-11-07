/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.*;
import entity.HoaDonEntity;
import entity.HoaDonEntity;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.stream.Collectors;

/**
 *
 * @author Tran Tien
 */
public class ThongKeJPanel extends javax.swing.JPanel {

    HoaDonDAO hoaDonDAO = new HoaDonDAO();
    KhachHangDAO khachHangDAO = new KhachHangDAO();

    public ThongKeJPanel() {
        initComponents();
        dateTuNgay.setDateFormatString("yyyy-MM-dd");
        dateDenNgay.setDateFormatString("yyyy-MM-dd");
        jComboBox1.addActionListener(e -> locNhanh());
        fillTable();
        fillThongKe();

        // Hiển thị tổng doanh thu + doanh thu hôm nay ban đầu
        hienThiDoanhThuTongVaHomNay();

        // Cập nhật doanh thu hôm nay theo thời gian thực (mỗi 5s)
        new javax.swing.Timer(5000, e -> hienThiDoanhThuTongVaHomNay()).start();
    }

    // ------------------ HIỂN THỊ TỔNG & HÔM NAY ------------------
    private void hienThiDoanhThuTongVaHomNay() {
        try {
            List<HoaDonEntity> list = hoaDonDAO.getHoaDonTK();
            List<HoaDonEntity> thanhToanList = list.stream()
                    .filter(hd -> "Đã thanh toán".equalsIgnoreCase(hd.getTrangThai().trim()))
                    .collect(Collectors.toList());

            double tongDoanhThu = thanhToanList.stream()
                    .mapToDouble(HoaDonEntity::getTongTien)
                    .sum();

            lblTongDoanhThu.setText(String.format("%,.0f VND", tongDoanhThu));

            // Doanh thu hôm nay
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(new Date());

            double doanhThuHomNay = thanhToanList.stream()
                    .filter(hd -> {
                        try {
                            String ngayLap = hd.getNgayLap().substring(0, 10);
                            return ngayLap.equals(today);
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .mapToDouble(HoaDonEntity::getTongTien)
                    .sum();

            lblDoanhThuHomNay.setText(String.format("%,.0f VND", doanhThuHomNay));

            lblDoanhThuTrongKhoang.setText("0 VND"); // Mặc định khi chưa lọc

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thống kê: " + e.getMessage());
        }
    }

    // --- Fill toàn bộ hóa đơn ---
    public void fillTable() {
        List<HoaDonEntity> list = hoaDonDAO.getHoaDonTK();
        fillTable(list);
    }

    public void fillTable(List<HoaDonEntity> list) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        DecimalFormat df = new DecimalFormat("#,### VNĐ");

        for (HoaDonEntity hd : list) {
            // Chỉ hiển thị hóa đơn đã thanh toán
            if ("Đã thanh toán".equalsIgnoreCase(hd.getTrangThai().trim())) {
                Object[] row = {
                    hd.getIdHoaDon(),
                    hd.getNgayLap(),
                    hd.getIdKhachHang(),
                    df.format(hd.getTongTien()),
                    hd.getTrangThai()
                };
                model.addRow(row);
            }
        }
    }

    private void fillThongKe() {
        try {
            // --- Tổng doanh thu ---
            List<HoaDonEntity> all = hoaDonDAO.getHoaDonDaThanhToan();
            double tongDoanhThu = all.stream().mapToDouble(HoaDonEntity::getTongTien).sum();

            // --- Doanh thu hôm nay ---
            List<HoaDonEntity> homNay = hoaDonDAO.getHoaDonHomNay();
            double doanhThuHomNay = homNay.stream().mapToDouble(HoaDonEntity::getTongTien).sum();

            // --- Cập nhật hiển thị ---
            lblTongDoanhThu.setText("Tổng doanh thu: " + String.format("%,.0f VND", tongDoanhThu));
            lblDoanhThuHomNay.setText("Doanh thu hôm nay: " + String.format("%,.0f VND", doanhThuHomNay));
            lblDoanhThuTrongKhoang.setText("Doanh thu trong khoảng: 0 VND");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thống kê doanh thu: " + e.getMessage());
        }
    }

//    // --- Thống kê tổng ---
//    private void fillThongKe() {
//        List<HoaDonEntity> list = hoaDonDAO.getHoaDonTK();
//        fillThongKe(list);
//    }
//
//    // --- Thống kê từ list (chỉ tính Đã thanh toán) ---
//    public void fillThongKe(List<HoaDonEntity> list) {
//        double tongDoanhThu = 0;
//        int soHoaDon = 0;
//
//        for (HoaDonEntity hd : list) {
//            if ("Đã thanh toán".equalsIgnoreCase(hd.getTrangThai().trim())) {
//                tongDoanhThu += hd.getTongTien();
//                soHoaDon++;
//            }
//        }
//
//        lblTongDoanhThu.setText("Tổng doanh thu: " + String.format("%,.0f VND", tongDoanhThu));
//        lblDoanhThuHomNay.setText("Tổng hóa đơn: " + soHoaDon);
//        lblDoanhThuTrongKhoang.setText("Đã thanh toán: " + soHoaDon);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblDoanhThuHomNay = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblDoanhThuTrongKhoang = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        lblTuNgay = new javax.swing.JLabel();
        lblDenNgay = new javax.swing.JLabel();
        dateTuNgay = new com.toedter.calendar.JDateChooser();
        dateDenNgay = new com.toedter.calendar.JDateChooser();
        lblLocNhanh = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("THỐNG KÊ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Hóa đơn", "Ngày lập", "ID Khách hàng", "Tổng tiền", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(tblHoaDon);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Tổng doanh thu");

        lblTongDoanhThu.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongDoanhThu)
                    .addComponent(jLabel3))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel3)
                .addGap(28, 28, 28)
                .addComponent(lblTongDoanhThu)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 102, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Doanh thu hôm nay");

        lblDoanhThuHomNay.setText("jLabel7");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblDoanhThuHomNay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel5)
                .addGap(28, 28, 28)
                .addComponent(lblDoanhThuHomNay)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Doanh thu trong khoảng lọc");

        lblDoanhThuTrongKhoang.setText("jLabel8");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDoanhThuTrongKhoang)
                    .addComponent(jLabel6))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel6)
                .addGap(28, 28, 28)
                .addComponent(lblDoanhThuTrongKhoang)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        btnLoc.setText("Lọc");
        btnLoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLocMouseClicked(evt);
            }
        });
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        lblTuNgay.setText("Từ ngày:");

        lblDenNgay.setText("Đến ngày:");

        lblLocNhanh.setText("Lọc nhanh:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "3 ngày gần nhất", "1 tuần", "1 tháng", "1 năm" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblLocNhanh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTuNgay)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(lblDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btnLoc))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDenNgay)
                    .addComponent(lblTuNgay)
                    .addComponent(dateTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLocNhanh)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        try {
            if (dateTuNgay.getDate() == null || dateDenNgay.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đủ Từ ngày --- Đến ngày!");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String from = sdf.format(dateTuNgay.getDate());
            String to = sdf.format(dateDenNgay.getDate());

            List<HoaDonEntity> list = hoaDonDAO.getByDateRange(from, to);
            List<HoaDonEntity> thanhToanList = list.stream()
                    .filter(hd -> "Đã thanh toán".equalsIgnoreCase(hd.getTrangThai().trim()))
                    .collect(Collectors.toList());

            fillTable(thanhToanList);

            double doanhThuKhoang = thanhToanList.stream()
                    .mapToDouble(HoaDonEntity::getTongTien)
                    .sum();

            lblDoanhThuTrongKhoang.setText(String.format("%,.0f VND", doanhThuKhoang));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc dữ liệu: " + ex.getMessage());
        }

    }

    // --- Lọc nhanh bằng ComboBox ---
    private void locNhanh() {
        try {
            String selected = (String) jComboBox1.getSelectedItem();
            if ("Tất cả".equals(selected)) {
                List<HoaDonEntity> all = hoaDonDAO.getHoaDonTK().stream()
                        .filter(hd -> "Đã thanh toán".equalsIgnoreCase(hd.getTrangThai().trim()))
                        .collect(Collectors.toList());
                fillTable(all);
                lblDoanhThuTrongKhoang.setText("0 VND");
                return;
            }

            long days = switch (selected) {
                case "3 ngày gần nhất" ->
                    3;
                case "1 tuần" ->
                    7;
                case "1 tháng" ->
                    30;
                case "1 năm" ->
                    365;
                default ->
                    0;
            };

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -(int) days);
            Date tuNgay = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<HoaDonEntity> filtered = new ArrayList<>();
            for (HoaDonEntity hd : hoaDonDAO.getHoaDonTK()) {
                Date ngayLap = sdf.parse(hd.getNgayLap());
                if (!ngayLap.before(tuNgay) && "Đã thanh toán".equalsIgnoreCase(hd.getTrangThai().trim())) {
                    filtered.add(hd);
                }
            }

            fillTable(filtered);

            double doanhThuKhoang = filtered.stream()
                    .mapToDouble(HoaDonEntity::getTongTien)
                    .sum();

            lblDoanhThuTrongKhoang.setText(String.format("%,.0f VND", doanhThuKhoang));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc nhanh: " + ex.getMessage());
        }

        // --- Không cần sửa phần initComponents --- //
        // (giữ nguyên như bạn có sẵn)

    }//GEN-LAST:event_btnLocActionPerformed

    private void btnLocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLocMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLocMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private com.toedter.calendar.JDateChooser dateDenNgay;
    private com.toedter.calendar.JDateChooser dateTuNgay;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDenNgay;
    private javax.swing.JLabel lblDoanhThuHomNay;
    private javax.swing.JLabel lblDoanhThuTrongKhoang;
    private javax.swing.JLabel lblLocNhanh;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JLabel lblTuNgay;
    private javax.swing.JTable tblHoaDon;
    // End of variables declaration//GEN-END:variables
}
