package view;
import controller.DoiMatKhauController;
import controller.NhanVienController;
import controller.PhieuMuonController;
import controller.PhieuTraController;
import controller.SachController;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Sach;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JOptionPane;
import model.NhanVien;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import model.PhieuMuon;
import model.Session;

/**
 *
 * @author Bao Quyen
 */
public class NhanVienForm extends javax.swing.JFrame {

    /**
     * Creates new form NhanVienForm
     */
    private int maNhanVienHienTai;

    // Tìm kiếm Sách
    private void loadAllSach() {
        SachController controller = new SachController();
        List<Sach> list = controller.loadAll();

        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);

        for (Sach s : list) {
            model.addRow(new Object[]{
                s.getMaSach(),
                s.getTenSach(),
                s.getTacGia(),
                s.getTheLoai(),
                s.getNhaXuatBan(),
                s.getTinhTrang()
            });
        }
    }

    private void timKiemRealtime() {

        String keyword = txtTimKiem.getText().trim();
        String cot = "";

        if (rdMaSach.isSelected()) {
            cot = "MaSach";
        } else if (rdTenSach.isSelected()) {
            cot = "TenSach";
        } else if (rdTacGia.isSelected()) {
            cot = "TacGia";
        } else if (rdTheLoai.isSelected()) {
            cot = "TheLoai";
        } else if (rdNXB.isSelected()) {
            cot = "NhaXuatBan";
        } else if (rdTinhTrang.isSelected()) {
            cot = "TinhTrang";
        }

        if (cot.isEmpty()) {
            return;
        }

        SachController controller = new SachController();
        List<Sach> list = controller.timKiem(cot, keyword);

        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);

        for (Sach s : list) {
            model.addRow(new Object[]{
                s.getMaSach(),
                s.getTenSach(),
                s.getTacGia(),
                s.getTheLoai(),
                s.getNhaXuatBan(),
                s.getTinhTrang()
            });
        }
    }

// Thay đổi thông tin
    private void loadThongTinNhanVien() {

        if (Session.currentNhanVien == null) {
            //  JOptionPane.showMessageDialog(this, "Chưa có session nhân viên!");
            return;
        }

        NhanVien nv = Session.currentNhanVien;

        txtMaNhanVien.setText(String.valueOf(nv.getMaNhanVien()));
        txtTenDangNhap.setText(nv.getTenDangNhap());
        txtHoTen.setText(nv.getHoTen());
        txtDiaChi.setText(nv.getDiaChi());

        if ("Nam".equalsIgnoreCase(nv.getGioiTinh())) {
            radNam.setSelected(true);
        } else {
            radNu.setSelected(true);
        }

        if (nv.getNgaySinh() != null) {
            dateNgaySinh.setDate(nv.getNgaySinh());
        }

        txtMaNhanVien.setEnabled(false);
        txtTenDangNhap.setEnabled(false);
    }

    private void capNhatThongTinNhanVien() {

        if (Session.currentNhanVien == null) {
            return;
        }

        NhanVien nv = new NhanVien();

        nv.setMaNhanVien(Session.currentNhanVien.getMaNhanVien());
        nv.setTenDangNhap(Session.currentNhanVien.getTenDangNhap());
        nv.setHoTen(txtHoTen.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setGioiTinh(radNam.isSelected() ? "Nam" : "Nữ");

        Date ns = dateNgaySinh.getDate();
        if (ns == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn ngày sinh!");
            return;
        }

        nv.setNgaySinh(ns);

        NhanVienController controller = new NhanVienController();

        if (controller.capNhatThongTin(nv)) {
            JOptionPane.showMessageDialog(this, "✅ Cập nhật thành công!");
            Session.currentNhanVien = nv;
        } else {
            JOptionPane.showMessageDialog(this, "❌ Cập nhật thất bại!");
        }
    }

    private void loadBangPhieuMuon() {
        PhieuMuonController controller = new PhieuMuonController();
        List<PhieuMuon> list = controller.layDanhSachPhieuMuon();

        DefaultTableModel model = (DefaultTableModel) tblPhieuMuon.getModel();
        model.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (PhieuMuon pm : list) {
            model.addRow(new Object[]{
                pm.getMaPhieu(),
                pm.getMaDocGia(),
                pm.getHoTen(),
                pm.getMaSach(),
                pm.getTenSach(),
                sdf.format(pm.getNgayMuon()),
                sdf.format(pm.getNgayPhaiTra())
            });
        }
    }

    public void loadPhieuTraLenTable() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuTra.getModel();
        model.setRowCount(0);

        PhieuTraController controller = new PhieuTraController();
        ResultSet rs = controller.loadPhieuTra();

        try {
            while (rs != null && rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("MaPhieu"),
                    rs.getInt("MaDocGia"),
                    rs.getString("HoTen"),
                    rs.getInt("MaSach"),
                    rs.getString("TenSach"),
                    rs.getDate("NgayTra")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtMaPhieu.setText("");
        txtMaDocGia.setText("");
        txtMaSach.setText("");
        dateNgayMuon.setDate(null);
        dateNgayPhaiTra.setDate(null);
    }

    public void clearFormPhieuTra() {
        txtMaPhieuTra.setText("");
        txtMaDocGiaPhieuTra.setText("");
        txtMaSachPhieuTra.setText("");
        dateNgayTra.setDate(null);

        // Bỏ chọn trong bảng
        tblPhieuTra.clearSelection();

        // Focus về ô đầu tiên
        txtMaDocGiaPhieuTra.requestFocus();
    }

    public NhanVienForm(int maNhanVien) {
        initComponents();
        this.maNhanVienHienTai = maNhanVien;
        this.setResizable(false);

    }

    public NhanVienForm() {
        initComponents();
        setTitle("Tài khoản nhân viên");
        setLocationRelativeTo(null);
        this.setResizable(false);
        loadAllSach();
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemRealtime();
            }
        });

        loadThongTinNhanVien();

        loadBangPhieuMuon();
        loadPhieuTraLenTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        rdMaSach = new javax.swing.JRadioButton();
        rdTacGia = new javax.swing.JRadioButton();
        rdTenSach = new javax.swing.JRadioButton();
        rdTinhTrang = new javax.swing.JRadioButton();
        rdTheLoai = new javax.swing.JRadioButton();
        rdNXB = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtHoTen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        radNam = new javax.swing.JRadioButton();
        radNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        btnCapNhat = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMatKhauCu = new javax.swing.JPasswordField();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        txtNhapLai = new javax.swing.JPasswordField();
        btnChapNhan = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMaPhieu = new javax.swing.JTextField();
        txtMaDocGia = new javax.swing.JTextField();
        txtMaSach = new javax.swing.JTextField();
        dateNgayMuon = new com.toedter.calendar.JDateChooser();
        dateNgayPhaiTra = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        btnThemPhieuMuon = new javax.swing.JButton();
        btnSuaPhieuMuon = new javax.swing.JButton();
        btnXoaPhieuMuon = new javax.swing.JButton();
        btnCleanPhieuMuon = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtMaPhieuTra = new javax.swing.JTextField();
        txtMaDocGiaPhieuTra = new javax.swing.JTextField();
        txtMaSachPhieuTra = new javax.swing.JTextField();
        dateNgayTra = new com.toedter.calendar.JDateChooser();
        jPanel13 = new javax.swing.JPanel();
        btnThemPhieuTra = new javax.swing.JButton();
        btnSuaPhieuTra = new javax.swing.JButton();
        btnXoaPhieuTra = new javax.swing.JButton();
        btnClearPhieuTra = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        ItemDangXuat = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setToolTipText("");

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Nhà Xuất Bản", "Tình Trạng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSach);

        buttonGroup2.add(rdMaSach);
        rdMaSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdMaSach.setText("Mã Sách");

        buttonGroup2.add(rdTacGia);
        rdTacGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTacGia.setText("Tác Giả");

        buttonGroup2.add(rdTenSach);
        rdTenSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTenSach.setText("Tên Sách");

        buttonGroup2.add(rdTinhTrang);
        rdTinhTrang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTinhTrang.setText("Tình Trạng");

        buttonGroup2.add(rdTheLoai);
        rdTheLoai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTheLoai.setText("Thể Loại ");

        buttonGroup2.add(rdNXB);
        rdNXB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdNXB.setText("Nhà Xuất Bản");
        rdNXB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNXBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdNXB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdMaSach)
                    .addComponent(rdTacGia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdTenSach)
                    .addComponent(rdTinhTrang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdTheLoai)
                    .addComponent(rdNXB))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Tìm kiếm :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Tìm kiếm theo :");

        jLabel23.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel23.setText("TRA CỨU SÁCH ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jLabel23)))
                .addGap(279, 279, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Tìm kiếm sách", jPanel1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Họ Tên :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mã Nhân Viên :");

        txtMaNhanVien.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tên Đăng Nhập :");

        txtTenDangNhap.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Giới Tính :");

        buttonGroup1.add(radNam);
        radNam.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        radNam.setText("Nam");

        buttonGroup1.add(radNu);
        radNu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        radNu.setText("Nữ ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(radNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radNam)
                    .addComponent(radNu)))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Năm Sinh : ");

        dateNgaySinh.setDateFormatString("yyyy-MM-dd");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Địa Chỉ :");

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-change-24.png"))); // NOI18N
        btnCapNhat.setText("Thay đổi ");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dateNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtTenDangNhap, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                    .addGap(3, 3, 3)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtHoTen)
                                        .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)))))
                        .addContainerGap(68, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCapNhat)
                .addGap(159, 159, 159))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel8.setText("THÔNG TIN CỦA NHÂN VIÊN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông tin", jPanel2);

        jLabel15.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        jLabel15.setText("ĐỔI MẬT KHẨU CỦA NHÂN VIÊN");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mật khẩu cũ :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Mật khẩu mới :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Nhập lại :");

        btnChapNhan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChapNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-accept-24.png"))); // NOI18N
        btnChapNhan.setText("Chấp Nhận");
        btnChapNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChapNhanActionPerformed(evt);
            }
        });

        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMatKhauCu)
                            .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(txtNhapLai)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(btnChapNhan)
                        .addGap(82, 82, 82)
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMatKhauCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNhapLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChapNhan)
                    .addComponent(btnHuy))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Đổi mật khẩu ", jPanel3);

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu", "Mã Độc Giả", "Họ Tên", "Mã Sách", "Tên Sách", "Ngày Mượn", "Ngày Phải Trả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuMuonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPhieuMuon);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Mã Phiếu :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Mã Độc Giả :");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Mã Sách :");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Ngày Mượn :");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Ngày Phải Trả :");

        txtMaPhieu.setEditable(false);

        dateNgayMuon.setDateFormatString("yyyy-MM-dd");

        dateNgayPhaiTra.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMaPhieu)
                        .addComponent(txtMaDocGia)
                        .addComponent(txtMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(dateNgayPhaiTra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addComponent(dateNgayMuon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(dateNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateNgayPhaiTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btnThemPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-24.png"))); // NOI18N
        btnThemPhieuMuon.setText("Thêm ");
        btnThemPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhieuMuonActionPerformed(evt);
            }
        });

        btnSuaPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-repair-24.png"))); // NOI18N
        btnSuaPhieuMuon.setText("Sửa");
        btnSuaPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPhieuMuonActionPerformed(evt);
            }
        });

        btnXoaPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-delete-24.png"))); // NOI18N
        btnXoaPhieuMuon.setText("Xóa");
        btnXoaPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhieuMuonActionPerformed(evt);
            }
        });

        btnCleanPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCleanPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnCleanPhieuMuon.setText("Clear");
        btnCleanPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanPhieuMuonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnThemPhieuMuon)
                .addGap(51, 51, 51)
                .addComponent(btnSuaPhieuMuon)
                .addGap(60, 60, 60)
                .addComponent(btnXoaPhieuMuon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(btnCleanPhieuMuon)
                .addGap(69, 69, 69))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemPhieuMuon)
                    .addComponent(btnSuaPhieuMuon)
                    .addComponent(btnXoaPhieuMuon)
                    .addComponent(btnCleanPhieuMuon))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setText("LẬP PHIẾU MƯỢN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Lập phiếu mượn", jPanel4);

        tblPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu", "Mã Độc Giả", "Họ Tên", "Mã Sách", "Tên Sách", "Ngày trả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPhieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuTraMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPhieuTra);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Mã Phiếu :");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Mã Độc Giả :");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Mã Sách :");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Ngày trả :");

        txtMaPhieuTra.setEditable(false);

        dateNgayTra.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMaPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                        .addComponent(txtMaDocGiaPhieuTra)
                        .addComponent(txtMaSachPhieuTra))
                    .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtMaPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtMaDocGiaPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtMaSachPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThemPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-24.png"))); // NOI18N
        btnThemPhieuTra.setText("Thêm");
        btnThemPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhieuTraActionPerformed(evt);
            }
        });

        btnSuaPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-repair-24.png"))); // NOI18N
        btnSuaPhieuTra.setText("Sửa");
        btnSuaPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPhieuTraActionPerformed(evt);
            }
        });

        btnXoaPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-delete-24.png"))); // NOI18N
        btnXoaPhieuTra.setText("Xóa");
        btnXoaPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhieuTraActionPerformed(evt);
            }
        });

        btnClearPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClearPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnClearPhieuTra.setText("Clear");
        btnClearPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPhieuTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(btnThemPhieuTra)
                .addGap(33, 33, 33)
                .addComponent(btnSuaPhieuTra)
                .addGap(34, 34, 34)
                .addComponent(btnXoaPhieuTra)
                .addGap(27, 27, 27)
                .addComponent(btnClearPhieuTra)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemPhieuTra)
                    .addComponent(btnSuaPhieuTra)
                    .addComponent(btnXoaPhieuTra)
                    .addComponent(btnClearPhieuTra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setText("LẬP PHIẾU TRẢ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lập phiếu trả ", jPanel5);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText("File");

        ItemDangXuat.setText("Đăng xuất");
        ItemDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemDangXuatActionPerformed(evt);
            }
        });
        jMenu1.add(ItemDangXuat);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNXBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdNXBActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        capNhatThongTinNhanVien();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnChapNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChapNhanActionPerformed
        // TODO add your handling code here:
        String matKhauCu = txtMatKhauCu.getText();
        String matKhauMoi = txtMatKhauMoi.getText();
        String nhapLai = txtNhapLai.getText();

        if (!matKhauMoi.equals(nhapLai)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
            return;
        }

        DoiMatKhauController controller = new DoiMatKhauController();
        controller.doiMatKhau(matKhauCu, matKhauMoi);
    }//GEN-LAST:event_btnChapNhanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        txtMatKhauCu.setText("");
        txtMatKhauMoi.setText("");
        txtNhapLai.setText("");
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnThemPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhieuMuonActionPerformed
        try {
            int maDG = Integer.parseInt(txtMaDocGia.getText());
            int maSach = Integer.parseInt(txtMaSach.getText());
            Date ngayMuon = dateNgayMuon.getDate();
            Date ngayPhaiTra = dateNgayPhaiTra.getDate();

            if (ngayMuon == null || ngayPhaiTra == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
                return;
            }

            // ❗ KIỂM TRA NGÀY
            if (ngayMuon.after(ngayPhaiTra)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày mượn KHÔNG được lớn hơn ngày phải trả!",
                        "Lỗi ngày", JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            PhieuMuonController controller = new PhieuMuonController();
            boolean ok = controller.themPhieuMuon(maDG, maSach, ngayMuon, ngayPhaiTra);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Thêm phiếu mượn thành công!");
                loadBangPhieuMuon();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu đầu vào!");
        }
    }//GEN-LAST:event_btnThemPhieuMuonActionPerformed

    private void btnSuaPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPhieuMuonActionPerformed
        try {
            int maPM = Integer.parseInt(txtMaPhieu.getText());
            int maDG = Integer.parseInt(txtMaDocGia.getText());
            int maSach = Integer.parseInt(txtMaSach.getText());
            Date ngayMuon = dateNgayMuon.getDate();
            Date ngayPhaiTra = dateNgayPhaiTra.getDate();

            if (ngayMuon == null || ngayPhaiTra == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
                return;
            }

            // ❗ KIỂM TRA NGÀY: Ngày mượn không được lớn hơn ngày phải trả
            if (ngayMuon.after(ngayPhaiTra)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày mượn KHÔNG được lớn hơn ngày phải trả!",
                        "Lỗi ngày", JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            PhieuMuonController controller = new PhieuMuonController();
            boolean ok = controller.suaPhieuMuon(maPM, maDG, maSach, ngayMuon, ngayPhaiTra);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                loadBangPhieuMuon(); // load lại bảng
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }//GEN-LAST:event_btnSuaPhieuMuonActionPerformed

    private void tblPhieuMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonMouseClicked
        // TODO add your handling code here:
        int row = tblPhieuMuon.getSelectedRow();

        txtMaPhieu.setText(tblPhieuMuon.getValueAt(row, 0).toString());
        txtMaDocGia.setText(tblPhieuMuon.getValueAt(row, 1).toString());
        txtMaSach.setText(tblPhieuMuon.getValueAt(row, 3).toString());

        try {
            dateNgayMuon.setDate(java.sql.Date.valueOf(tblPhieuMuon.getValueAt(row, 5).toString()));
            dateNgayPhaiTra.setDate(java.sql.Date.valueOf(tblPhieuMuon.getValueAt(row, 6).toString()));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblPhieuMuonMouseClicked

    private void btnXoaPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhieuMuonActionPerformed
        int row = tblPhieuMuon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn cần xóa!");
            return;
        }

        int maPM = Integer.parseInt(tblPhieuMuon.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phiếu mượn này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        PhieuMuonController controller = new PhieuMuonController();
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.xoaPhieuMuon(maPM)) {
                JOptionPane.showMessageDialog(this, "Xóa phiếu mượn thành công!");
                //loadDataPhieuMuon();
                loadBangPhieuMuon();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }//GEN-LAST:event_btnXoaPhieuMuonActionPerformed

    private void btnCleanPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanPhieuMuonActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnCleanPhieuMuonActionPerformed

    private void ItemDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemDangXuatActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (chon == JOptionPane.YES_OPTION) {
            // Mở lại form đăng nhập
            new LoginForm().setVisible(true);

            // Đóng form hiện tại
            this.dispose();
        }
    }//GEN-LAST:event_ItemDangXuatActionPerformed

    private void btnThemPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhieuTraActionPerformed
        // TODO add your handling code here:
        try {
            int maDocGia = Integer.parseInt(txtMaDocGiaPhieuTra.getText());
            int maSach = Integer.parseInt(txtMaSachPhieuTra.getText());
            Date ngayTra = dateNgayTra.getDate();

            if (ngayTra == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trả!");
                return;
            }

            PhieuTraController controller = new PhieuTraController();
            boolean ok = controller.themPhieuTra(maDocGia, maSach, ngayTra);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Thêm phiếu trả thành công!");
                loadPhieuTraLenTable();
                clearFormPhieuTra();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu đầu vào không hợp lệ!");
        }
    }//GEN-LAST:event_btnThemPhieuTraActionPerformed

    private void btnSuaPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPhieuTraActionPerformed
        // TODO add your handling code here:
        try {
            int maPhieuTra = Integer.parseInt(txtMaPhieuTra.getText().trim());
            int maDocGia = Integer.parseInt(txtMaDocGiaPhieuTra.getText().trim());
            int maSach = Integer.parseInt(txtMaSachPhieuTra.getText().trim());
            java.util.Date ngayTra = dateNgayTra.getDate();

            if (ngayTra == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trả!");
                return;
            }

            // Kiểm tra cơ bản: ngày (nếu muốn)
            // Gọi controller
            PhieuTraController controller = new PhieuTraController();
            boolean ok = controller.suaPhieuTra(maPhieuTra, maDocGia, maSach, ngayTra);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu trả thành công!");
                loadPhieuTraLenTable();
                clearFormPhieuTra();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Mã phải là số!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật phiếu trả!");
        }

    }//GEN-LAST:event_btnSuaPhieuTraActionPerformed

    private void tblPhieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuTraMouseClicked
        // TODO add your handling code here:
        int row = tblPhieuTra.getSelectedRow();
        if (row < 0) {
            return;
        }

        // Lấy từng cột
        txtMaPhieuTra.setText(tblPhieuTra.getValueAt(row, 0).toString());
        txtMaDocGiaPhieuTra.setText(tblPhieuTra.getValueAt(row, 1).toString());
        txtMaSachPhieuTra.setText(tblPhieuTra.getValueAt(row, 3).toString());

        // Ngày trả (dùng JDateChooser)
        java.sql.Date ngay = (java.sql.Date) tblPhieuTra.getValueAt(row, 5);
        dateNgayTra.setDate(ngay);
    }//GEN-LAST:event_tblPhieuTraMouseClicked

    private void btnXoaPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhieuTraActionPerformed
        // TODO add your handling code here:
        try {
            int maPhieuTra = Integer.parseInt(txtMaPhieuTra.getText().trim());

            int chon = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            PhieuTraController controller = new PhieuTraController();
            if (chon == JOptionPane.YES_OPTION) {
                boolean ok = controller.xoaPhieuTra(maPhieuTra);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    //loadTablePhieuTra();
                    loadPhieuTraLenTable();
                    clearFormPhieuTra();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_btnXoaPhieuTraActionPerformed

    private void btnClearPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPhieuTraActionPerformed
        // TODO add your handling code here:
        clearFormPhieuTra();
    }//GEN-LAST:event_btnClearPhieuTraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemDangXuat;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChapNhan;
    private javax.swing.JButton btnCleanPhieuMuon;
    private javax.swing.JButton btnClearPhieuTra;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSuaPhieuMuon;
    private javax.swing.JButton btnSuaPhieuTra;
    private javax.swing.JButton btnThemPhieuMuon;
    private javax.swing.JButton btnThemPhieuTra;
    private javax.swing.JButton btnXoaPhieuMuon;
    private javax.swing.JButton btnXoaPhieuTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.toedter.calendar.JDateChooser dateNgayMuon;
    private com.toedter.calendar.JDateChooser dateNgayPhaiTra;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private com.toedter.calendar.JDateChooser dateNgayTra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton radNam;
    private javax.swing.JRadioButton radNu;
    private javax.swing.JRadioButton rdMaSach;
    private javax.swing.JRadioButton rdNXB;
    private javax.swing.JRadioButton rdTacGia;
    private javax.swing.JRadioButton rdTenSach;
    private javax.swing.JRadioButton rdTheLoai;
    private javax.swing.JRadioButton rdTinhTrang;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblPhieuTra;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaDocGia;
    private javax.swing.JTextField txtMaDocGiaPhieuTra;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextField txtMaPhieuTra;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMaSachPhieuTra;
    private javax.swing.JPasswordField txtMatKhauCu;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JPasswordField txtNhapLai;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
