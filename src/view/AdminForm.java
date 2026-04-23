package view;
import java.util.Date;
import controller.DocGiaController;
import model.DocGia;
import javax.swing.JOptionPane;
import controller.NhanVienController;
import controller.PhieuMuonController;
import controller.PhieuMuonTraController;
import controller.PhieuPhatController;
import controller.PhieuTraController;
import model.NhanVien;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Sach;
import controller.SachController;
import java.awt.Color;
import java.awt.Component;
import model.PhieuMuonTra;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import main.ThemeManager;
import model.PhieuMuon;
import model.PhieuPhat;



public class AdminForm extends javax.swing.JFrame {

    
public void loadTableDocGia() {
    DefaultTableModel model = (DefaultTableModel) tblDocGia.getModel();
    model.setRowCount(0); // clear table

    DocGiaController controller = new DocGiaController();
    List<DocGia> list = controller.getAllDocGia();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (DocGia dg : list) {
        String ngaySinh = "";
        if (dg.getNgaySinh() != null) {
            ngaySinh = sdf.format(dg.getNgaySinh());
        }

        model.addRow(new Object[]{
            dg.getMaDocGia(),
            dg.getTenDangNhap(),
            dg.getHoTen(),
            dg.getGioiTinh(),
            ngaySinh,
            dg.getDiaChi()
        });
    }
}

public void resetForm() {
    txtTenDangNhap.setText("");
    txtHoTen.setText("");
    rdoNam.setSelected(false);
    rdoNu.setSelected(false);
    dateNgaySinh.setDate(null);
    txtDiaChi.setText("");
    buttonGroup2.clearSelection();
}

private void clearForm() {
    txtMaDocGia.setText("");
    txtTenDangNhap.setText("");
    txtHoTen.setText("");
    rdoNam.setSelected(true);
    dateNgaySinh.setDate(null);
    txtDiaChi.setText("");
    buttonGroup2.clearSelection();
}

public void loadTableNhanVien() {
    DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
    model.setRowCount(0); // clear table

    NhanVienController controller = new NhanVienController();
    List<NhanVien> list = controller.getAllNhanVien();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (NhanVien nv : list) {
        String ngaySinhStr = "";
        if (nv.getNgaySinh() != null) {
            ngaySinhStr = sdf.format(nv.getNgaySinh());
        }

        model.addRow(new Object[]{
            nv.getMaNhanVien(),
            nv.getTenDangNhap(),
            nv.getHoTen(),
            nv.getGioiTinh(),
            ngaySinhStr,
            nv.getDiaChi()
        });
    }
}

private void clearFormNhanVien() {
    txtMaNV.setText("");
    txtTenDangNhapNV.setText("");
    txtHoTenNV.setText("");
    rdoNamNV.setSelected(true);
    dateNgaySinhNV.setDate(null);
    txtDiaChiNV.setText("");
    buttonGroup3.clearSelection();
}

private void clearFormSach() {
    txtMaSach.setText("");
    txtTenSach.setText("");
    txtTacGia.setText("");
    txtTheLoai.setText("");
    txtNhaXuatBan.setText("");
    txtGiaSach.setText("");
    SpinnerSoLuong.setValue(0); // ✅ reset spinner về 0
}


public void loadTableSach() {
    DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
    model.setRowCount(0); // xóa dữ liệu cũ

    SachController controller = new SachController();
    List<Sach> list = controller.getAllSach1();

    for (Sach s : list) {
        model.addRow(new Object[]{
            s.getMaSach(),
            s.getTenSach(),
            s.getTacGia(),
            s.getTheLoai(),
            s.getNhaXuatBan(),
            s.getGiaSach(),
            s.getSoLuong()
        });
    }
}   

private void loadTableDangMuon() {
    PhieuMuonTraController controller = new PhieuMuonTraController();
    List<PhieuMuonTra> list = controller.getDangMuon();

    DefaultTableModel model = (DefaultTableModel) tblDangMuon.getModel();
    model.setRowCount(0);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (PhieuMuonTra p : list) {
        model.addRow(new Object[] {
            p.getHoTen(),
            p.getTenSach(),
            sdf.format(p.getNgayMuon()),
            sdf.format(p.getNgayPhaiTra())
        });
    }
}

private void loadTableDaMuon() {
    PhieuMuonTraController controller = new PhieuMuonTraController();
    List<PhieuMuonTra> list = controller.getDaMuon();

    DefaultTableModel model = (DefaultTableModel) tblDaMuon.getModel();
    model.setRowCount(0);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (PhieuMuonTra p : list) {
        model.addRow(new Object[] {
            p.getHoTen(),
            p.getTenSach(),
            sdf.format(p.getNgayMuon()),
            sdf.format(p.getNgayTra())
        });
    }
}

// Hàm tìm kiếm realtime cho Đang mượn
private void timKiemDangMuonRealtime() {
    String keyword = txtTimDangMuon.getText().trim();
    String cot = rdoHoTenDangMuon.isSelected() ? "DG.HoTen" : "S.TenSach";

    PhieuMuonTraController controller = new PhieuMuonTraController();
    List<PhieuMuonTra> list = controller.timKiemDangMuon(cot, keyword);

    DefaultTableModel model = (DefaultTableModel) tblDangMuon.getModel();
    model.setRowCount(0);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (PhieuMuonTra p : list) {
        model.addRow(new Object[] {
            p.getHoTen(),
            p.getTenSach(),
            sdf.format(p.getNgayMuon()),
            sdf.format(p.getNgayPhaiTra())
        });
    }
}

// Hàm tìm kiếm realtime cho Đã mượn
private void timKiemDaMuonRealtime() {
    String keyword = txtTimDaMuon.getText().trim();
    String cot = rdoHoTenDaMuon.isSelected() ? "DG.HoTen" : "S.TenSach";

    PhieuMuonTraController controller = new PhieuMuonTraController();
    List<PhieuMuonTra> list = controller.timKiemDaMuon(cot, keyword);

    DefaultTableModel model = (DefaultTableModel) tblDaMuon.getModel();
    model.setRowCount(0);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (PhieuMuonTra p : list) {
        model.addRow(new Object[] {
            p.getHoTen(),
            p.getTenSach(),
            sdf.format(p.getNgayMuon()),
            sdf.format(p.getNgayTra())
        });
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
            model.addRow(new Object[] {
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

 private void clearFormPhieuMuon() {
    txtMaPhieu.setText("");
    txtMaDocGiaPhieuMuon.setText("");
    txtMaSachPhieuMuon.setText("");
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
 public void loadPhieuPhatLenTable() {
    PhieuPhatController ctrl = new PhieuPhatController();
    List<PhieuPhat> list = ctrl.getDanhSach();

    // Tạo model mới, đặt tên cột
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new Object[]{
            "Mã Phiếu Phạt", "Mã Phiếu Mượn", "Mã Độc Giả", "Tên Độc Giả",
            "Lý Do", "Số Tiền", "Ngày Lập", "Trạng Thái"
    });
    tblPhieuPhat.setModel(model);

    // Thêm dữ liệu
    for (PhieuPhat p : list) {
        model.addRow(new Object[]{
                p.getMaPhieuPhat(), // 0
                p.getMaPhieu(),      // 1 ← Mã Phiếu Mượn (ẩn)
                p.getMaDocGia(),     // 2
                p.getTenDocGia(),    // 3
                p.getLyDo(),         // 4
                p.getSoTien(),       // 5
                p.getNgayLap(),      // 6
                p.getTrangThai()     // 7 (ẩn, dùng để render màu chữ)
        });
    }

    // Ẩn cột Mã Phiếu Mượn (index 1) và Trạng Thái (index 7)
    tblPhieuPhat.getColumnModel().getColumn(1).setMinWidth(0);
    tblPhieuPhat.getColumnModel().getColumn(1).setMaxWidth(0);
    tblPhieuPhat.getColumnModel().getColumn(1).setWidth(0);

    tblPhieuPhat.getColumnModel().getColumn(7).setMinWidth(0);
    tblPhieuPhat.getColumnModel().getColumn(7).setMaxWidth(0);
    tblPhieuPhat.getColumnModel().getColumn(7).setWidth(0);

    // Renderer chỉ đổi màu chữ theo Trạng Thái ẩn
    tblPhieuPhat.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Lấy giá trị Trạng Thái an toàn
            Object objTrangThai = table.getModel().getValueAt(row, 7); // cột ẩn Trạng Thái
            int trangThai = 0;
            if (objTrangThai != null) {
                if (objTrangThai instanceof Number) {
                    trangThai = ((Number) objTrangThai).intValue();
                } else {
                    try {
                        trangThai = Integer.parseInt(objTrangThai.toString());
                    } catch (NumberFormatException ex) {
                        trangThai = 0;
                    }
                }
            }

            // Chỉ đổi màu chữ, không đổi nền
            if (!isSelected) {
                if (trangThai == 1) {        // Đã đóng
                    c.setForeground(new Color(0, 200, 0)); // chữ xanh lá
                } else if (trangThai == 0) { // Chưa đóng
                    c.setForeground(new Color(220, 0, 0)); // chữ đỏ
                }
            } else { // nếu chọn
                c.setForeground(table.getSelectionForeground());
            }

            return c;
        }
    });
}


public void clearFormPhieuPhat() {
    txtMaPhieuPhat.setText("");
    txtMaPhieuMuon.setText("");
    txtMaDocGiaPhieuPhat.setText("");
    txtLyDo.setText("");
    txtTienPhat.setText("");
    dateNgayLap.setDate(null); // reset JDateChooser
}


private void timKiemDocGiaRealtime() {

    String keyword = txtTimDocGia.getText().trim();
    String cot = "";

    if (rdMaDocGia.isSelected()) cot = "MaDocGia";
    else if (rdTenDangNhap.isSelected()) cot = "TenDangNhap";
    else if (rdHoTen.isSelected()) cot = "HoTen";
    else if (rdGioiTinh.isSelected()) cot = "GioiTinh";

    if (cot.isEmpty()) return;

    DocGiaController controller = new DocGiaController();
    List<DocGia> list = controller.timKiemDocGia(cot, keyword);

    // 👇 load bảng – dùng lại code CỦA BẠN
    DefaultTableModel model =
        (DefaultTableModel) tblDocGia.getModel();
    model.setRowCount(0);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (DocGia dg : list) {
        model.addRow(new Object[]{
            dg.getMaDocGia(),
            dg.getTenDangNhap(),
            dg.getHoTen(),
            dg.getGioiTinh(),
            dg.getNgaySinh() == null ? "" : sdf.format(dg.getNgaySinh()),
            dg.getDiaChi()
        });
    }
}

private void timKiemNhanVienRealtime() {

    String keyword = txtTimNhanVien.getText().trim();
    String cot = "";

    if (rdMaNV.isSelected()) cot = "MaNV";
    else if (rdTenDangNhapNV.isSelected()) cot = "TenDangNhap";
    else if (rdHoTenNV.isSelected()) cot = "HoTen";
    else if (rdGioiTinhNV.isSelected()) cot = "GioiTinh";

    if (cot.isEmpty()) return;

    NhanVienController controller = new NhanVienController();
    List<NhanVien> list = controller.timKiemNhanVien(cot, keyword);

    // 👇 load bảng (giữ style của bạn)
    DefaultTableModel model =
        (DefaultTableModel) tblNhanVien.getModel();
    model.setRowCount(0);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    for (NhanVien nv : list) {
        model.addRow(new Object[]{
            nv.getMaNhanVien(),
            nv.getTenDangNhap(),
            nv.getHoTen(),
            nv.getGioiTinh(),
            nv.getNgaySinh() == null ? "" : sdf.format(nv.getNgaySinh()),
            nv.getDiaChi()
        });
    }
}

private void timKiemSachRealtime() {

    String keyword = txtTimKiemSach.getText().trim();
    String cot = "";

    if (rdMaSach.isSelected()) cot = "MaSach";
    else if (rdTenSach.isSelected()) cot = "TenSach";
    else if (rdTacGia.isSelected()) cot = "TacGia";
    else if (rdTheLoai.isSelected()) cot = "TheLoai";
    else if (rdNXB.isSelected()) cot = "NhaXuatBan";
    else if (rdGiaSach.isSelected()) cot = "GiaSach";
    else if (rdSoLuong.isSelected()) cot = "SoLuong";

    if (cot.isEmpty()) return;

    // trống thì load lại toàn bộ
    if (keyword.isEmpty()) {
        loadTableSach();
        return;
    }

    SachController controller = new SachController();
    List<Sach> list = controller.timKiemSach(cot, keyword);

    DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
    model.setRowCount(0);

    for (Sach s : list) {
        model.addRow(new Object[]{
            s.getMaSach(),
            s.getTenSach(),
            s.getTacGia(),
            s.getTheLoai(),
            s.getNhaXuatBan(),
            s.getGiaSach(),
            s.getSoLuong()
        });
    }
}


public void loadPhieuPhatLenTable2() {
    PhieuPhatController ctrl = new PhieuPhatController();
    List<PhieuPhat> list = ctrl.getDanhSach();

    // Tạo model mới, đặt tên cột
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new Object[]{
            "Mã Phiếu Phạt", "Mã Phiếu Mượn", "Mã Độc Giả", "Tên Độc Giả",
            "Lý Do", "Số Tiền", "Ngày Lập", "Trạng Thái"
    });
    tblPhieuPhat2.setModel(model);

    // Thêm dữ liệu
    for (PhieuPhat p : list) {
        model.addRow(new Object[]{
                p.getMaPhieuPhat(), // 0  <- Mã Phiếu Phạt (ẩn)
                p.getMaPhieu(),      // 1 ← Mã Phiếu Mượn (ẩn)
                p.getMaDocGia(),     // 2 <- Mã Độc Giả (ẩn)
                p.getTenDocGia(),    // 3
                p.getLyDo(),         // 4
                p.getSoTien(),       // 5
                p.getNgayLap(),      // 6
                p.getTrangThai()     // 7 (ẩn, dùng để render màu chữ)
        });
    }

    // Ẩn cột Mã Phiếu Mượn (index 1) và Trạng Thái (index 7)
     tblPhieuPhat2.getColumnModel().getColumn(0).setMinWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(0).setMaxWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(0).setWidth(0);
    
    tblPhieuPhat2.getColumnModel().getColumn(1).setMinWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(1).setMaxWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(1).setWidth(0);
    
    tblPhieuPhat2.getColumnModel().getColumn(2).setMinWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(2).setMaxWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(2).setWidth(0);
    
    tblPhieuPhat2.getColumnModel().getColumn(7).setMinWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(7).setMaxWidth(0);
    tblPhieuPhat2.getColumnModel().getColumn(7).setWidth(0);

    // Renderer chỉ đổi màu chữ theo Trạng Thái ẩn
    tblPhieuPhat2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Lấy giá trị Trạng Thái an toàn
            Object objTrangThai = table.getModel().getValueAt(row, 7); // cột ẩn Trạng Thái
            int trangThai = 0;
            if (objTrangThai != null) {
                if (objTrangThai instanceof Number) {
                    trangThai = ((Number) objTrangThai).intValue();
                } else {
                    try {
                        trangThai = Integer.parseInt(objTrangThai.toString());
                    } catch (NumberFormatException ex) {
                        trangThai = 0;
                    }
                }
            }

            // Chỉ đổi màu chữ, không đổi nền
            if (!isSelected) {
                if (trangThai == 1) {        // Đã đóng
                    c.setForeground(new Color(0, 200, 0)); // chữ xanh lá
                } else if (trangThai == 0) { // Chưa đóng
                    c.setForeground(new Color(220, 0, 0)); // chữ đỏ
                }
            } else { // nếu chọn
                c.setForeground(table.getSelectionForeground());
            }

            return c;
        }
    });
    
}

private void timKiemPhieuPhatRealtime() {

    String keyword = txtTimKiem.getText().trim();
    String cot = "";

    if (rdHoTenPhieuPhat.isSelected()) {
        cot = "dg.HoTen";
    } else if (rdLyDo.isSelected()) {
        cot = "pp.LyDo";
    }

    if (cot.isEmpty()) return;

    // text trống → load lại toàn bộ
    if (keyword.isEmpty()) {
        loadPhieuPhatLenTable2();
        return;
    }

    PhieuPhatController ctrl = new PhieuPhatController();
    List<PhieuPhat> list = ctrl.timKiem(cot, keyword);

    DefaultTableModel model = (DefaultTableModel) tblPhieuPhat2.getModel();
    model.setRowCount(0);

    for (PhieuPhat p : list) {
        model.addRow(new Object[]{
            p.getMaPhieuPhat(),
            p.getMaPhieu(),
            p.getMaDocGia(),
            p.getTenDocGia(),
            p.getLyDo(),
            p.getSoTien(),
            p.getNgayLap(),
            p.getTrangThai()
        });
    }
}


    public AdminForm() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("TRANG QUẢN TRỊ ADMIN");
        this.setResizable(false);
        loadTableDocGia();
        loadTableNhanVien();
        loadTableSach();
        
        loadBangPhieuMuon();
        loadPhieuTraLenTable();
        loadPhieuPhatLenTable();
        
        loadTableDangMuon();
        loadTableDaMuon();
        
    txtTimDangMuon.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        timKiemDangMuonRealtime();
    }
    });

    txtTimDaMuon.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        timKiemDaMuonRealtime();
    }
    });
         txtTimDocGia.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        timKiemDocGiaRealtime();
    }
});

      txtTimNhanVien.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        timKiemNhanVienRealtime();
    }
});
    
        txtTimKiemSach.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        timKiemSachRealtime();
    }
});
        
        loadPhieuPhatLenTable2();
        txtTimKiem.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        timKiemPhieuPhatRealtime();
    }
});

        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        buttonGroup10 = new javax.swing.ButtonGroup();
        buttonGroup11 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnRefreshNV = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocGia = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaDocGia = new javax.swing.JTextField();
        txtTenDangNhap = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        txtDiaChi = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        btnThemDocGia = new javax.swing.JButton();
        btnSuaDocGia = new javax.swing.JButton();
        btnXoaDocGia = new javax.swing.JButton();
        btnCleanDocGia = new javax.swing.JButton();
        btnRefreshDG = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        txtTimDocGia = new javax.swing.JTextField();
        rdMaDocGia = new javax.swing.JRadioButton();
        rdTenDangNhap = new javax.swing.JRadioButton();
        rdHoTen = new javax.swing.JRadioButton();
        rdGioiTinh = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenDangNhapNV = new javax.swing.JTextField();
        txtHoTenNV = new javax.swing.JTextField();
        rdoNamNV = new javax.swing.JRadioButton();
        rdoNuNV = new javax.swing.JRadioButton();
        dateNgaySinhNV = new com.toedter.calendar.JDateChooser();
        txtDiaChiNV = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        btnThemNV = new javax.swing.JButton();
        btnSuaNhanVien = new javax.swing.JButton();
        btnXoaNV = new javax.swing.JButton();
        btnCleanNV = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        txtTimNhanVien = new javax.swing.JTextField();
        rdMaNV = new javax.swing.JRadioButton();
        rdTenDangNhapNV = new javax.swing.JRadioButton();
        rdHoTenNV = new javax.swing.JRadioButton();
        rdGioiTinhNV = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtTenSach = new javax.swing.JTextField();
        txtTacGia = new javax.swing.JTextField();
        txtTheLoai = new javax.swing.JTextField();
        txtNhaXuatBan = new javax.swing.JTextField();
        txtGiaSach = new javax.swing.JTextField();
        SpinnerSoLuong = new javax.swing.JSpinner();
        jPanel15 = new javax.swing.JPanel();
        btnThemSach = new javax.swing.JButton();
        btnSuaSach = new javax.swing.JButton();
        btnXoaSach = new javax.swing.JButton();
        btnCleanSach = new javax.swing.JButton();
        btnReFreshS = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        txtTimKiemSach = new javax.swing.JTextField();
        rdMaSach = new javax.swing.JRadioButton();
        rdTenSach = new javax.swing.JRadioButton();
        rdTacGia = new javax.swing.JRadioButton();
        rdTheLoai = new javax.swing.JRadioButton();
        rdNXB = new javax.swing.JRadioButton();
        rdGiaSach = new javax.swing.JRadioButton();
        rdSoLuong = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtMaPhieu = new javax.swing.JTextField();
        txtMaDocGiaPhieuMuon = new javax.swing.JTextField();
        txtMaSachPhieuMuon = new javax.swing.JTextField();
        dateNgayMuon = new com.toedter.calendar.JDateChooser();
        dateNgayPhaiTra = new com.toedter.calendar.JDateChooser();
        jPanel18 = new javax.swing.JPanel();
        btnThemPhieuMuon = new javax.swing.JButton();
        btnSuaPhieuMuon = new javax.swing.JButton();
        btnXoaPhieuMuon = new javax.swing.JButton();
        btnClearPhieuMuon = new javax.swing.JButton();
        btnRefreshPM = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtMaPhieuTra = new javax.swing.JTextField();
        txtMaDocGiaPhieuTra = new javax.swing.JTextField();
        txtMaSachPhieuTra = new javax.swing.JTextField();
        dateNgayTra = new com.toedter.calendar.JDateChooser();
        jPanel19 = new javax.swing.JPanel();
        btnThemPhieuTra = new javax.swing.JButton();
        btnSuaPhieuTra = new javax.swing.JButton();
        btnXoaPhieuTra = new javax.swing.JButton();
        btnClearPhieuTra = new javax.swing.JButton();
        btnRefreshPhieuTra = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPhieuPhat = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txtMaPhieuPhat = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtMaDocGiaPhieuPhat = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtMaPhieuMuon = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtLyDo = new javax.swing.JTextField();
        txtTienPhat = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        dateNgayLap = new com.toedter.calendar.JDateChooser();
        jLabel37 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        btnTaoPhieuPhat = new javax.swing.JButton();
        btnSuaPhieuPhat = new javax.swing.JButton();
        btnXoaPhieuPhat = new javax.swing.JButton();
        btnClearPhieuPhat = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnRefreshPhieuPhat = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDangMuon = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        txtTimDangMuon = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        rdoHoTenDangMuon = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblDaMuon = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        txtTimDaMuon = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        rdoHoTenDaMuon = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblPhieuPhat2 = new javax.swing.JTable();
        jPanel36 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        rdHoTenPhieuPhat = new javax.swing.JRadioButton();
        rdLyDo = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        cboTheme = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        ItemDangXuat = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnRefreshNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRefreshNV.setOpaque(true);

        tblDocGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Độc Giả", "Tên Đăng Nhập", "Họ Tên ", "Giới Tính", "Ngày Sinh ", "Địa Chỉ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblDocGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocGiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocGia);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã Độc Giả :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tên Đăng Nhập :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Họ Tên :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Giới Tính :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Ngày Sinh :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Địa Chỉ :");

        txtMaDocGia.setEditable(false);

        buttonGroup2.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        rdoNam.setText("Nam");

        buttonGroup2.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        rdoNu.setText("Nữ");

        dateNgaySinh.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaDocGia, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(txtTenDangNhap))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNu)
                    .addComponent(rdoNam)
                    .addComponent(jLabel4))
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        btnThemDocGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-24.png"))); // NOI18N
        btnThemDocGia.setText("Thêm ");
        btnThemDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDocGiaActionPerformed(evt);
            }
        });

        btnSuaDocGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-repair-24.png"))); // NOI18N
        btnSuaDocGia.setText("Sửa");
        btnSuaDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDocGiaActionPerformed(evt);
            }
        });

        btnXoaDocGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-delete-24.png"))); // NOI18N
        btnXoaDocGia.setText("Xóa");
        btnXoaDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDocGiaActionPerformed(evt);
            }
        });

        btnCleanDocGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCleanDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnCleanDocGia.setText("Clear");
        btnCleanDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanDocGiaActionPerformed(evt);
            }
        });

        btnRefreshDG.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefreshDG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-refresh-24.png"))); // NOI18N
        btnRefreshDG.setText("Refresh");
        btnRefreshDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshDGActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemDocGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaDocGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSuaDocGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCleanDocGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnRefreshDG)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemDocGia)
                            .addComponent(btnSuaDocGia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaDocGia)
                            .addComponent(btnCleanDocGia)))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnRefreshDG)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setText("Tìm độc giả :");

        txtTimDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimDocGiaActionPerformed(evt);
            }
        });

        buttonGroup8.add(rdMaDocGia);
        rdMaDocGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdMaDocGia.setText("Mã Độc Giả");

        buttonGroup8.add(rdTenDangNhap);
        rdTenDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTenDangNhap.setText("Tên Đăng Nhập");

        buttonGroup8.add(rdHoTen);
        rdHoTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdHoTen.setText("Họ Tên");

        buttonGroup8.add(rdGioiTinh);
        rdGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdGioiTinh.setText("Giới Tính");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addComponent(rdHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addComponent(rdMaDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(rdTenDangNhap)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtTimDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdMaDocGia)
                    .addComponent(rdTenDangNhap))
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdHoTen)
                    .addComponent(rdGioiTinh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(104, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        btnRefreshNV.addTab("Quản Lý Độc Giả", jPanel3);

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Nhân Viên", "Tên Đăng Nhập", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mã Nhân Viên");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Tên Đăng Nhập :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Họ Tên :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Giới Tính :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Ngày Sinh :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Địa Chỉ :");

        txtMaNV.setEditable(false);

        buttonGroup3.add(rdoNamNV);
        rdoNamNV.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        rdoNamNV.setText("Nam");

        buttonGroup3.add(rdoNuNV);
        rdoNuNV.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        rdoNuNV.setText("Nữ");

        dateNgaySinhNV.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTenDangNhapNV, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtMaNV)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDiaChiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateNgaySinhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(rdoNamNV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoNuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(txtHoTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenDangNhapNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtHoTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNamNV)
                    .addComponent(rdoNuNV)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(dateNgaySinhNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtDiaChiNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        btnThemNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-24.png"))); // NOI18N
        btnThemNV.setText("Thêm ");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });

        btnSuaNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-repair-24.png"))); // NOI18N
        btnSuaNhanVien.setText("Sửa");
        btnSuaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNhanVienActionPerformed(evt);
            }
        });

        btnXoaNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-delete-24.png"))); // NOI18N
        btnXoaNV.setText("Xóa");
        btnXoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNVActionPerformed(evt);
            }
        });

        btnCleanNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCleanNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnCleanNV.setText("Clear");
        btnCleanNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanNVActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-refresh-24.png"))); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSuaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCleanNV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(btnSuaNhanVien))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnThemNV)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaNV)
                            .addComponent(btnCleanNV)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jButton2)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setText("Tìm kiếm nhân viên :");

        buttonGroup9.add(rdMaNV);
        rdMaNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdMaNV.setText("Mã Nhân Viên");

        buttonGroup9.add(rdTenDangNhapNV);
        rdTenDangNhapNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTenDangNhapNV.setText("Tên Đăng Nhập");

        buttonGroup9.add(rdHoTenNV);
        rdHoTenNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdHoTenNV.setText("Họ Tên");

        buttonGroup9.add(rdGioiTinhNV);
        rdGioiTinhNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdGioiTinhNV.setText("Giới Tính");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rdHoTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rdMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdTenDangNhapNV, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdGioiTinhNV, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtTimNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdMaNV)
                    .addComponent(rdTenDangNhapNV))
                .addGap(18, 18, 18)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdHoTenNV)
                    .addComponent(rdGioiTinhNV))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        btnRefreshNV.addTab("Quản Lý Nhân Viên", jPanel4);

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách ", "Tên Sách", "Tác Giả", "Thể Loại", "Nhà Xuất Bản", "Giá Sách", "Số Lượng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSach);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Mã Sách :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tên Sách :");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Tác Giả :");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Thể Loại :");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Nhà Xuất Bản :");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Giá Sách :");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Số Lượng ");

        txtMaSach.setEditable(false);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaSach)
                                    .addComponent(txtTenSach, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(txtTacGia)
                                    .addComponent(txtTheLoai)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(SpinnerSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(txtGiaSach))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtGiaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(SpinnerSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btnThemSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-24.png"))); // NOI18N
        btnThemSach.setText("Thêm ");
        btnThemSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSachActionPerformed(evt);
            }
        });

        btnSuaSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-repair-24.png"))); // NOI18N
        btnSuaSach.setText("Sửa");
        btnSuaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSachActionPerformed(evt);
            }
        });

        btnXoaSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-delete-24.png"))); // NOI18N
        btnXoaSach.setText("Xóa");
        btnXoaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSachActionPerformed(evt);
            }
        });

        btnCleanSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCleanSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnCleanSach.setText("Clear");
        btnCleanSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanSachActionPerformed(evt);
            }
        });

        btnReFreshS.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReFreshS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-refresh-24.png"))); // NOI18N
        btnReFreshS.setText("Refresh");
        btnReFreshS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReFreshSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(btnThemSach)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(btnXoaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCleanSach, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(btnSuaSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReFreshS)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSach)
                    .addComponent(btnSuaSach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaSach)
                    .addComponent(btnCleanSach))
                .addGap(35, 35, 35))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnReFreshS)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setText("Tìm sách :");

        buttonGroup10.add(rdMaSach);
        rdMaSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdMaSach.setText("Mã Sách");

        buttonGroup10.add(rdTenSach);
        rdTenSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTenSach.setText("Tên Sách");

        buttonGroup10.add(rdTacGia);
        rdTacGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTacGia.setText("Tác Giả");

        buttonGroup10.add(rdTheLoai);
        rdTheLoai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdTheLoai.setText("Thể Loại");

        buttonGroup10.add(rdNXB);
        rdNXB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdNXB.setText("Nhà Xuất Bản");

        buttonGroup10.add(rdGiaSach);
        rdGiaSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdGiaSach.setText("Giá Sách");

        buttonGroup10.add(rdSoLuong);
        rdSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdSoLuong.setText("Số Lượng");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(rdTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(rdTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdGiaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                                .addComponent(rdNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtTimKiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdMaSach)
                    .addComponent(rdTenSach)
                    .addComponent(rdTacGia)
                    .addComponent(rdGiaSach))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdTheLoai)
                    .addComponent(rdNXB)
                    .addComponent(rdSoLuong))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addGap(131, 131, 131))
        );

        btnRefreshNV.addTab("Quản Lý Sách", jPanel5);

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu", "Mã Độc Giả", "Họ Tên", "Mã Sách", "Tên Sách", "Ngày Mượn", "Ngày Trả"
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
        jScrollPane4.setViewportView(tblPhieuMuon);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Mã Phiếu :");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Mã Độc Giả :");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Mã Sách :");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Ngày Mượn :");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Ngày Phải Trả :");

        txtMaPhieu.setEditable(false);

        dateNgayMuon.setDateFormatString("yyyy-MM-dd");

        dateNgayPhaiTra.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMaPhieu)
                        .addComponent(txtMaDocGiaPhieuMuon)
                        .addComponent(txtMaSachPhieuMuon, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(dateNgayPhaiTra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addComponent(dateNgayMuon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtMaDocGiaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSachPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(dateNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateNgayPhaiTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        btnThemPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-24.png"))); // NOI18N
        btnThemPhieuMuon.setText("Thêm");
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

        btnClearPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClearPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnClearPhieuMuon.setText("Clear");
        btnClearPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPhieuMuonActionPerformed(evt);
            }
        });

        btnRefreshPM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefreshPM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-refresh-24.png"))); // NOI18N
        btnRefreshPM.setText("Refresh");
        btnRefreshPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(btnThemPhieuMuon)
                .addGap(33, 33, 33)
                .addComponent(btnSuaPhieuMuon)
                .addGap(34, 34, 34)
                .addComponent(btnXoaPhieuMuon)
                .addGap(27, 27, 27)
                .addComponent(btnClearPhieuMuon)
                .addGap(29, 29, 29)
                .addComponent(btnRefreshPM))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemPhieuMuon)
                    .addComponent(btnSuaPhieuMuon)
                    .addComponent(btnXoaPhieuMuon)
                    .addComponent(btnClearPhieuMuon)
                    .addComponent(btnRefreshPM))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnRefreshNV.addTab("Quản Lý Phiếu Mượn", jPanel6);

        tblPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu", "Mã Độc Giả", "Họ Tên", "Mã Sách", "Tên Sách", "Ngày Trả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane5.setViewportView(tblPhieuTra);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Mã Phiếu :");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Mã Độc Giả :");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Mã Sách :");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Ngày trả :");

        txtMaPhieuTra.setEditable(false);

        dateNgayTra.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMaPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                        .addComponent(txtMaDocGiaPhieuTra)
                        .addComponent(txtMaSachPhieuTra))
                    .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtMaPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtMaDocGiaPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtMaSachPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
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

        btnRefreshPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefreshPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-refresh-24.png"))); // NOI18N
        btnRefreshPhieuTra.setText("Refresh");
        btnRefreshPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPhieuTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(btnThemPhieuTra)
                .addGap(33, 33, 33)
                .addComponent(btnSuaPhieuTra)
                .addGap(34, 34, 34)
                .addComponent(btnXoaPhieuTra)
                .addGap(27, 27, 27)
                .addComponent(btnClearPhieuTra)
                .addGap(33, 33, 33)
                .addComponent(btnRefreshPhieuTra)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemPhieuTra)
                    .addComponent(btnSuaPhieuTra)
                    .addComponent(btnXoaPhieuTra)
                    .addComponent(btnClearPhieuTra)
                    .addComponent(btnRefreshPhieuTra))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
            .addComponent(jScrollPane5)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnRefreshNV.addTab("Quản Lý Phiếu Trả", jPanel7);

        jPanel22.setLayout(new java.awt.BorderLayout());

        tblPhieuPhat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu Phạt", "Mã Độc Giả ", "Tên Độc Giả", "Lý do", "Số Tiền", "Ngày Lập"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPhieuPhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuPhatMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblPhieuPhat);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Mã Phiếu Phạt :");

        txtMaPhieuPhat.setEditable(false);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Mã Độc Giả :");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Mã Phiếu Mượn :");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("Lý Do Phạt :");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setText("Số Tiền phạt (VND):");

        dateNgayLap.setDateFormatString("yyyy-MM-dd");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setText("Ngày Trả :");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTienPhat)
                    .addComponent(txtMaPhieuPhat)
                    .addComponent(txtMaDocGiaPhieuPhat)
                    .addComponent(txtMaPhieuMuon)
                    .addComponent(txtLyDo)
                    .addComponent(dateNgayLap, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtMaPhieuPhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtMaDocGiaPhieuPhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(txtMaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(txtLyDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(txtTienPhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(dateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnTaoPhieuPhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoPhieuPhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-24.png"))); // NOI18N
        btnTaoPhieuPhat.setText("Tạo");
        btnTaoPhieuPhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuPhatActionPerformed(evt);
            }
        });

        btnSuaPhieuPhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaPhieuPhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-repair-24.png"))); // NOI18N
        btnSuaPhieuPhat.setText("Sửa");
        btnSuaPhieuPhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPhieuPhatActionPerformed(evt);
            }
        });

        btnXoaPhieuPhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaPhieuPhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-delete-24.png"))); // NOI18N
        btnXoaPhieuPhat.setText("Xóa");
        btnXoaPhieuPhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhieuPhatActionPerformed(evt);
            }
        });

        btnClearPhieuPhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClearPhieuPhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-clear-24.png"))); // NOI18N
        btnClearPhieuPhat.setText("Clear");
        btnClearPhieuPhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPhieuPhatActionPerformed(evt);
            }
        });

        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-pay-24.png"))); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnRefreshPhieuPhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefreshPhieuPhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-refresh-24.png"))); // NOI18N
        btnRefreshPhieuPhat.setText("Refresh");
        btnRefreshPhieuPhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPhieuPhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(btnTaoPhieuPhat)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaPhieuPhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(btnXoaPhieuPhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearPhieuPhat)))
                .addGap(22, 22, 22))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnThanhToan))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btnRefreshPhieuPhat)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoPhieuPhat)
                    .addComponent(btnSuaPhieuPhat))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearPhieuPhat)
                    .addComponent(btnXoaPhieuPhat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThanhToan)
                .addGap(18, 18, 18)
                .addComponent(btnRefreshPhieuPhat)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel23Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel22.add(jPanel23, java.awt.BorderLayout.LINE_END);

        btnRefreshNV.addTab("Quản Lý Phiếu phạt", jPanel22);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRefreshNV)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRefreshNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTabbedPane1.addTab("Quản Lý ", jPanel1);

        tblDangMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Họ Tên", "Tên Sách", "Ngày Mượn", "Ngày Phải Trả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblDangMuon);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Tìm kiếm :");

        txtTimDangMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimDangMuonActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHoTenDangMuon);
        rdoHoTenDangMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoHoTenDangMuon.setText("Họ Tên");

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jRadioButton6.setText("Tên Sách");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(rdoHoTenDangMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoHoTenDangMuon)
                    .addComponent(jRadioButton6)))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(txtTimDangMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(293, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimDangMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(30, 30, 30)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        jTabbedPane5.addTab("Đang Mượn", jPanel9);

        tblDaMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Họ Tên", "Tên Sách", "Ngày Mượn", "Ngày Trả"
            }
        ));
        jScrollPane7.setViewportView(tblDaMuon);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Tìm kiếm :");

        buttonGroup4.add(rdoHoTenDaMuon);
        rdoHoTenDaMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoHoTenDaMuon.setText("Họ Tên");

        buttonGroup4.add(jRadioButton8);
        jRadioButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jRadioButton8.setText("Tên Sách");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(rdoHoTenDaMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jRadioButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoHoTenDaMuon)
                    .addComponent(jRadioButton8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTimDaMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimDaMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane5.addTab("Đã Trả", jPanel10);

        tblPhieuPhat2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên Độc Giả", "Lý Do", "Số tiền", "Ngày Lập"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tblPhieuPhat2);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setText("Tìm kiếm :");

        buttonGroup11.add(rdHoTenPhieuPhat);
        rdHoTenPhieuPhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdHoTenPhieuPhat.setText("Họ Tên");

        buttonGroup11.add(rdLyDo);
        rdLyDo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdLyDo.setText("Lý do");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rdHoTenPhieuPhat, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdLyDo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdLyDo)
                    .addComponent(rdHoTenPhieuPhat))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );

        jTabbedPane5.addTab("Phiếu Phạt", jPanel35);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thống Kê", jPanel2);

        cboTheme.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        cboTheme.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Flat Light", "Flat Dark", "IntelliJ Light", "Nord", "Carbon", "Material Ocean", "Material Palenight", "GitHub Green", "GitHub Dark Orange", "Dracula", "Material Darker", "Solarized Light" }));
        cboTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThemeActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel33.setText("CHỌN GIAO DIỆN");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(cboTheme, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(307, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(597, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Giao Diện", jPanel11);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDocGiaActionPerformed
        // TODO add your handling code here:
            try {
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String gioiTinh = rdoNam.isSelected() ? "Nam" : "Nữ";
        Date ngaySinh = dateNgaySinh.getDate();
        String diaChi = txtDiaChi.getText().trim();

        if (tenDangNhap.isEmpty() || hoTen.isEmpty() || ngaySinh == null || diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập đầy đủ thông tin!");
            return;
        }

        DocGia dg = new DocGia();
        dg.setTenDangNhap(tenDangNhap);
        dg.setHoTen(hoTen);
        dg.setGioiTinh(gioiTinh);
        dg.setNgaySinh(ngaySinh);
        dg.setDiaChi(diaChi);

        DocGiaController controller = new DocGiaController();
        boolean kq = controller.themDocGiaVaAccount(dg);

        if (kq) {
            JOptionPane.showMessageDialog(this, "✅ Thêm độc giả + tạo account thành công!");
            loadTableDocGia();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thêm thất bại!");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
    }//GEN-LAST:event_btnThemDocGiaActionPerformed

    private void btnSuaDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDocGiaActionPerformed
        // TODO add your handling code here:
    int maDocGia = Integer.parseInt(txtMaDocGia.getText());
    String tenDangNhap = txtTenDangNhap.getText();
    String hoTen = txtHoTen.getText();
    String gioiTinh = rdoNam.isSelected() ? "Nam" : "Nữ";
    Date ngaySinh = dateNgaySinh.getDate();
    String diaChi = txtDiaChi.getText();

    DocGia dg = new DocGia(
        maDocGia,
        tenDangNhap,
        hoTen,
        gioiTinh,
        ngaySinh,
        diaChi
    );

    DocGiaController controller = new DocGiaController();

    if (controller.suaDocGia(dg)) {
        JOptionPane.showMessageDialog(this, "✅ Sửa thành công & đồng bộ Account!");
        loadTableDocGia();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Sửa thất bại!");
    } 
    }//GEN-LAST:event_btnSuaDocGiaActionPerformed

    private void tblDocGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocGiaMouseClicked
 int row = tblDocGia.getSelectedRow();
    if (row == -1) return;

    txtMaDocGia.setText(tblDocGia.getValueAt(row, 0).toString());
    txtTenDangNhap.setText(tblDocGia.getValueAt(row, 1).toString());
    txtHoTen.setText(tblDocGia.getValueAt(row, 2).toString());

    String gt = tblDocGia.getValueAt(row, 3).toString();
    if (gt.equals("Nam")) rdoNam.setSelected(true);
    else rdoNu.setSelected(true);

    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(tblDocGia.getValueAt(row, 4).toString());
        dateNgaySinh.setDate(d);
    } catch (Exception e) {
        e.printStackTrace();
    }

    txtDiaChi.setText(tblDocGia.getValueAt(row, 5).toString());
    }//GEN-LAST:event_tblDocGiaMouseClicked

    private void btnXoaDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDocGiaActionPerformed
    int row = tblDocGia.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả cần xóa!");
        return;
    }

    int maDocGia = Integer.parseInt(
        tblDocGia.getValueAt(row, 0).toString()
    );

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa độc giả này (và tài khoản liên quan)?",
        "Xác nhận",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    DocGiaController controller = new DocGiaController();

    if (controller.xoaDocGiaVaAccount(maDocGia)) {
        JOptionPane.showMessageDialog(this, "✅ Xóa thành công!");
        loadTableDocGia();
        resetForm(); // nếu bạn có hàm reset form
    } else {
        JOptionPane.showMessageDialog(this, "❌ Xóa thất bại!");
    }
    }//GEN-LAST:event_btnXoaDocGiaActionPerformed

    private void btnCleanDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanDocGiaActionPerformed
        clearForm();
    }//GEN-LAST:event_btnCleanDocGiaActionPerformed

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

    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        // TODO add your handling code here:
          String tenDangNhap = txtTenDangNhapNV.getText().trim();
    String hoTen = txtHoTenNV.getText().trim();
    String gioiTinh = rdoNamNV.isSelected() ? "Nam" : "Nữ";
    Date ngaySinh = dateNgaySinhNV.getDate();
    String diaChi = txtDiaChiNV.getText().trim();

    // ✅ Kiểm tra nhập liệu
    if (tenDangNhap.isEmpty() || hoTen.isEmpty() || ngaySinh == null || diaChi.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    // ✅ Vì MaNV là AUTO_INCREMENT → truyền 0
    NhanVien nv = new NhanVien(0, tenDangNhap, hoTen, gioiTinh, ngaySinh, diaChi);

    NhanVienController controller = new NhanVienController();

    if (controller.themNhanVienVaAccount(nv)) {
        JOptionPane.showMessageDialog(this, "✅ Thêm nhân viên & tạo tài khoản thành công!");
        loadTableNhanVien();
        clearFormNhanVien();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Thêm nhân viên thất bại!");
    }
    }//GEN-LAST:event_btnThemNVActionPerformed

    private void btnCleanNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanNVActionPerformed
        // TODO add your handling code here:
        clearFormNhanVien();
    }//GEN-LAST:event_btnCleanNVActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
         int row = tblNhanVien.getSelectedRow();
    if (row == -1) return;

    txtMaNV.setText(tblNhanVien.getValueAt(row, 0).toString());
    txtTenDangNhapNV.setText(tblNhanVien.getValueAt(row, 1).toString());
    txtHoTenNV.setText(tblNhanVien.getValueAt(row, 2).toString());

    String gt = tblNhanVien.getValueAt(row, 3).toString();
    if (gt.equals("Nam")) rdoNamNV.setSelected(true);
    else rdoNuNV.setSelected(true);

    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(tblNhanVien.getValueAt(row, 4).toString());
        dateNgaySinhNV.setDate(d);
    } catch (Exception e) {
        e.printStackTrace();
    }

    txtDiaChiNV.setText(tblNhanVien.getValueAt(row, 5).toString());
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnSuaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNhanVienActionPerformed
        // TODO add your handling code here:
         int maNV = Integer.parseInt(txtMaNV.getText());
    String tenDangNhap = txtTenDangNhapNV.getText();
    String hoTen = txtHoTenNV.getText();
    String gioiTinh = rdoNamNV.isSelected() ? "Nam" : "Nữ";
    Date ngaySinh = dateNgaySinhNV.getDate();
    String diaChi = txtDiaChiNV.getText();

    NhanVien nv = new NhanVien(
        maNV,
        tenDangNhap,
        hoTen,
        gioiTinh,
        ngaySinh,
        diaChi
    );

    NhanVienController controller = new NhanVienController();

    if (controller.suaNhanVien(nv)) {
        JOptionPane.showMessageDialog(this, "✅ Sửa thành công & đồng bộ Account!");
        loadTableNhanVien();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Sửa thất bại!");
    }
    }//GEN-LAST:event_btnSuaNhanVienActionPerformed

    private void btnXoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNVActionPerformed
        // TODO add your handling code here:
         int row = tblNhanVien.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
        return;
    }

    int maNV = Integer.parseInt(
        tblNhanVien.getValueAt(row, 0).toString()
    );

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa nhân viên này (và tài khoản liên quan)?",
        "Xác nhận",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    NhanVienController controller = new NhanVienController();

    if (controller.xoaNhanVienVaAccount(maNV)) {
        JOptionPane.showMessageDialog(this, "✅ Xóa thành công!");
        loadTableNhanVien();
        clearFormNhanVien();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Xóa thất bại!");
    }
    }//GEN-LAST:event_btnXoaNVActionPerformed

    private void btnThemSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSachActionPerformed
        // TODO add your handling code here:
          String tenSach = txtTenSach.getText().trim();
    String tacGia = txtTacGia.getText().trim();
    String theLoai = txtTheLoai.getText().trim();
    String nhaXuatBan = txtNhaXuatBan.getText().trim();
    String giaSach = txtGiaSach.getText().trim();

    int soLuong = (Integer) SpinnerSoLuong.getValue(); // ✅ Lấy số lượng từ spinner

    // Kiểm tra nhập liệu
    if (tenSach.isEmpty() || tacGia.isEmpty() || theLoai.isEmpty() 
        || nhaXuatBan.isEmpty() || giaSach.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }
    if (soLuong < 0) {
    JOptionPane.showMessageDialog(this, "Số lượng không được nhỏ hơn 0!");
    return;
}
    // Tính TinhTrang dựa vào số lượng
    String tinhTrang = soLuong > 0 ? "Còn" : "Hết";

    // Tạo object Sach
    Sach s = new Sach(0, tenSach, tacGia, theLoai, nhaXuatBan, giaSach, String.valueOf(soLuong), tinhTrang);

    SachController controller = new SachController();

    if (controller.themSach(s)) {
        JOptionPane.showMessageDialog(this, "✅ Thêm sách thành công!");
        loadTableSach(); // load lại bảng
        clearFormSach();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Thêm sách thất bại!");
    }
    }//GEN-LAST:event_btnThemSachActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        // TODO add your handling code here:
         int row = tblSach.getSelectedRow();
    if (row == -1) return;
     txtMaSach.setText(tblSach.getValueAt(row, 0).toString());
    txtTenSach.setText(tblSach.getValueAt(row, 1).toString());
    txtTacGia.setText(tblSach.getValueAt(row, 2).toString());
    txtTheLoai.setText(tblSach.getValueAt(row, 3).toString());
    txtNhaXuatBan.setText(tblSach.getValueAt(row, 4).toString());
    txtGiaSach.setText(tblSach.getValueAt(row, 5).toString());
    SpinnerSoLuong.setValue(Integer.parseInt(tblSach.getValueAt(row, 6).toString()));
    }//GEN-LAST:event_tblSachMouseClicked

    private void btnSuaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSachActionPerformed
        // TODO add your handling code here:
        int row = tblSach.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sách cần sửa!");
        return;
    }
    
    int maSach = Integer.parseInt(tblSach.getValueAt(row, 0).toString());
    String tenSach = txtTenSach.getText().trim();
    String tacGia = txtTacGia.getText().trim();
    String theLoai = txtTheLoai.getText().trim();
    String nhaXuatBan = txtNhaXuatBan.getText().trim();
    String giaSach = txtGiaSach.getText().trim();
    int soLuong = (Integer) SpinnerSoLuong.getValue();

    if (tenSach.isEmpty() || tacGia.isEmpty() || theLoai.isEmpty() 
        || nhaXuatBan.isEmpty() || giaSach.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }
    if (soLuong < 0) {
    JOptionPane.showMessageDialog(this, "Số lượng không được nhỏ hơn 0!");
    return;
}
    
    
    
    // Tạo object Sach, TinhTrang sẽ được tính trong DAO
    Sach s = new Sach(maSach, tenSach, tacGia, theLoai, nhaXuatBan, giaSach, String.valueOf(soLuong), "");

    SachController controller = new SachController();

    if (controller.suaSach(s)) {
        JOptionPane.showMessageDialog(this, "✅ Sửa sách thành công!");
        loadTableSach(); // load lại bảng
        clearFormSach();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Sửa sách thất bại!");
    }
    }//GEN-LAST:event_btnSuaSachActionPerformed

    private void btnCleanSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanSachActionPerformed
        // TODO add your handling code here:
        clearFormSach();
    }//GEN-LAST:event_btnCleanSachActionPerformed

    private void btnXoaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSachActionPerformed
        // TODO add your handling code here:
         int row = tblSach.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sách cần xóa!");
        return;
    }

    int maSach = Integer.parseInt(tblSach.getValueAt(row, 0).toString());

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa sách này?",
        "Xác nhận",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    SachController controller = new SachController();

    if (controller.xoaSach(maSach)) {
        JOptionPane.showMessageDialog(this, "✅ Xóa sách thành công!");
        loadTableSach(); // load lại bảng
        clearFormSach();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Xóa sách thất bại!");
    }
    }//GEN-LAST:event_btnXoaSachActionPerformed

    private void txtTimDangMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimDangMuonActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_txtTimDangMuonActionPerformed

    private void btnClearPhieuPhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPhieuPhatActionPerformed
        // TODO add your handling code here:
        clearFormPhieuPhat();
    }//GEN-LAST:event_btnClearPhieuPhatActionPerformed

    private void tblPhieuMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonMouseClicked
        // TODO add your handling code here:
         int row = tblPhieuMuon.getSelectedRow();

    txtMaPhieu.setText(tblPhieuMuon.getValueAt(row, 0).toString());
    txtMaDocGiaPhieuMuon.setText(tblPhieuMuon.getValueAt(row, 1).toString());
    txtMaSachPhieuMuon.setText(tblPhieuMuon.getValueAt(row, 3).toString());

    try {
        dateNgayMuon.setDate(java.sql.Date.valueOf(tblPhieuMuon.getValueAt(row, 5).toString()));
        dateNgayPhaiTra.setDate(java.sql.Date.valueOf(tblPhieuMuon.getValueAt(row, 6).toString()));
    } catch (Exception e) {}
    }//GEN-LAST:event_tblPhieuMuonMouseClicked

    private void btnClearPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPhieuMuonActionPerformed
        // TODO add your handling code here:
        clearFormPhieuMuon();
    }//GEN-LAST:event_btnClearPhieuMuonActionPerformed

    private void btnThemPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhieuMuonActionPerformed
        // TODO add your handling code here:
        try {
        int maDG = Integer.parseInt(txtMaDocGiaPhieuMuon.getText());
        int maSach = Integer.parseInt(txtMaSachPhieuMuon.getText());
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
        // TODO add your handling code here:
        try {
        int maPM = Integer.parseInt(txtMaPhieu.getText());
        int maDG = Integer.parseInt(txtMaDocGiaPhieuMuon.getText());
        int maSach = Integer.parseInt(txtMaSachPhieuMuon.getText());
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

    private void btnXoaPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhieuMuonActionPerformed
        // TODO add your handling code here:
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

    private void tblPhieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuTraMouseClicked
        // TODO add your handling code here:
               int row = tblPhieuTra.getSelectedRow();
    if (row < 0) return;

    // Lấy từng cột
    txtMaPhieuTra.setText(tblPhieuTra.getValueAt(row, 0).toString());
    txtMaDocGiaPhieuTra.setText(tblPhieuTra.getValueAt(row, 1).toString());
    txtMaSachPhieuTra.setText(tblPhieuTra.getValueAt(row, 3).toString());

    // Ngày trả (dùng JDateChooser)
    java.sql.Date ngay = (java.sql.Date) tblPhieuTra.getValueAt(row, 5);
    dateNgayTra.setDate(ngay);
    }//GEN-LAST:event_tblPhieuTraMouseClicked

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

    private void btnClearPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPhieuTraActionPerformed
        // TODO add your handling code here:
        clearFormPhieuTra();
    }//GEN-LAST:event_btnClearPhieuTraActionPerformed

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

    private void tblPhieuPhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuPhatMouseClicked
        // TODO add your handling code here:
         int row = tblPhieuPhat.getSelectedRow();
    if (row >= 0) {
        // Lấy giá trị từ model để tránh nhầm index khi cột ẩn
     
        Object objMaPhieuPhat = tblPhieuPhat.getModel().getValueAt(row, 0);
        txtMaPhieuPhat.setText(objMaPhieuPhat != null ? objMaPhieuPhat.toString() : "");
        
     
        Object objMaPhieuMuon = tblPhieuPhat.getModel().getValueAt(row, 1); // Mã Phiếu Mượn
        txtMaPhieuMuon.setText(objMaPhieuMuon != null ? objMaPhieuMuon.toString() : "");
        
        Object objMaDocGia = tblPhieuPhat.getModel().getValueAt(row, 2);
        txtMaDocGiaPhieuPhat.setText(objMaDocGia != null ? objMaDocGia.toString() : "");

        Object objLyDo = tblPhieuPhat.getModel().getValueAt(row, 4);
        txtLyDo.setText(objLyDo != null ? objLyDo.toString() : "");

        Object objSoTien = tblPhieuPhat.getModel().getValueAt(row, 5);
        txtTienPhat.setText(objSoTien != null ? objSoTien.toString() : "");

        Object objNgay = tblPhieuPhat.getModel().getValueAt(row, 6);
        if (objNgay != null) {
            if (objNgay instanceof java.util.Date) {
                dateNgayLap.setDate((java.util.Date) objNgay);
            } else if (objNgay instanceof java.sql.Date) {
                dateNgayLap.setDate(new java.util.Date(((java.sql.Date) objNgay).getTime()));
            } else {
                dateNgayLap.setDate(null);
            }
        } else {
            dateNgayLap.setDate(null);
        }
    }
    }//GEN-LAST:event_tblPhieuPhatMouseClicked

    private void btnSuaPhieuPhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPhieuPhatActionPerformed
        // TODO add your handling code here:
          int row = tblPhieuPhat.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu phạt để sửa!");
        return;
    }

    try {
        PhieuPhat p = new PhieuPhat();
        // Lấy MaPhieuPhat từ cột 0
        p.setMaPhieuPhat((int) tblPhieuPhat.getModel().getValueAt(row, 0));

        // Chỉ sửa LyDo, SoTien, NgayLap
        p.setLyDo(txtLyDo.getText());
        p.setSoTien(Double.parseDouble(txtTienPhat.getText()));

        java.util.Date ngay = dateNgayLap.getDate();
        if (ngay != null) {
            p.setNgayLap(ngay);
        } else {
            p.setNgayLap(null);
        }

        PhieuPhatController ctrl = new PhieuPhatController();
        if (ctrl.suaPhieuPhat(p)) {
            JOptionPane.showMessageDialog(this, "Cập nhật phiếu phạt thành công!");
            loadPhieuPhatLenTable(); // reload bảng
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số tiền phải là số hợp lệ!");
    } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnSuaPhieuPhatActionPerformed

    private void btnXoaPhieuPhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhieuPhatActionPerformed
        // TODO add your handling code here:
    int row = tblPhieuPhat.getSelectedRow();
    if (row >= 0) {
        int maPhieuPhat = (int) tblPhieuPhat.getValueAt(row, 0); // cột Mã Phiếu Phạt
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa phiếu phạt này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            PhieuPhatController ctrl = new PhieuPhatController();
            boolean ok = ctrl.xoaMemPhieuPhat(maPhieuPhat);
            if (ok) {
                JOptionPane.showMessageDialog(null, "Xóa phiếu phạt thành công!");
                loadPhieuPhatLenTable(); // Refresh bảng
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn phiếu phạt cần xóa!");
    }
    }//GEN-LAST:event_btnXoaPhieuPhatActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
         int row = tblPhieuPhat.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu phạt để thanh toán!");
        return;
    }

    int maPhieuPhat = (int) tblPhieuPhat.getModel().getValueAt(row, 0);
    int trangThai = (int) tblPhieuPhat.getModel().getValueAt(row, 7); // cột ẩn TrangThai

    if (trangThai == 1) {
        JOptionPane.showMessageDialog(this, "Phiếu phạt đã thanh toán rồi!");
        return;
    }

    PhieuPhatController ctrl = new PhieuPhatController();
    if (ctrl.thanhToan(maPhieuPhat)) {
        JOptionPane.showMessageDialog(this, "Thanh toán phiếu phạt thành công!");
        loadPhieuPhatLenTable(); // reload bảng
    } else {
        JOptionPane.showMessageDialog(this, "Thanh toán thất bại!");
    }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void cboThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThemeActionPerformed
        // TODO add your handling code here:

    ThemeManager.applyTheme(
        cboTheme.getSelectedItem().toString()
    );

    }//GEN-LAST:event_cboThemeActionPerformed

    private void txtTimDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimDocGiaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtTimDocGiaActionPerformed

    private void btnRefreshDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshDGActionPerformed
        // TODO add your handling code here:
      

    // 1. Xóa ô tìm kiếm
    txtTimDocGia.setText("");

    // 2. Bỏ chọn tất cả radio tìm kiếm
    rdMaDocGia.setSelected(false);
    rdTenDangNhap.setSelected(false);
    rdHoTen.setSelected(false);
    rdGioiTinh.setSelected(false);
    buttonGroup8.clearSelection();
    clearForm();

    // 3. Load lại toàn bộ danh sách
    loadTableDocGia();

    }//GEN-LAST:event_btnRefreshDGActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
          // 1. Xóa ô tìm kiếm
    txtTimKiemSach.setText("");

    // 2. Bỏ chọn tất cả radio tìm kiếm
    rdMaNV.setSelected(false);
    rdTenDangNhapNV.setSelected(false);
    rdHoTenNV.setSelected(false);
    rdGioiTinhNV.setSelected(false);
    buttonGroup9.clearSelection();
    
    clearFormNhanVien();

    // 3. Load lại toàn bộ danh sách
    loadTableNhanVien();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnReFreshSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReFreshSActionPerformed
        // TODO add your handling code here:
      txtTimKiemSach.setText("");

    // 2. Bỏ chọn tất cả radio tìm kiếm
    rdMaSach.setSelected(false);
    rdTenSach.setSelected(false);
    rdTacGia.setSelected(false);
    rdGiaSach.setSelected(false);
    rdTheLoai.setSelected(false);
    rdNXB.setSelected(false);
    rdSoLuong.setSelected(false);
    buttonGroup10.clearSelection();
    clearFormSach();

    // 3. Load lại toàn bộ danh sách
    loadTableSach();
    }//GEN-LAST:event_btnReFreshSActionPerformed

    private void btnRefreshPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPMActionPerformed
        // TODO add your handling code here
        clearFormPhieuMuon();
        loadBangPhieuMuon();
    }//GEN-LAST:event_btnRefreshPMActionPerformed

    private void btnRefreshPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPhieuTraActionPerformed
        // TODO add your handling code here:
        clearFormPhieuTra();
        loadPhieuTraLenTable();
    }//GEN-LAST:event_btnRefreshPhieuTraActionPerformed

    private void btnRefreshPhieuPhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPhieuPhatActionPerformed
        // TODO add your handling code here:
         loadPhieuPhatLenTable(); // Refresh bảng
         clearFormPhieuPhat();     // Xóa form nhập liệu nếu có
    }//GEN-LAST:event_btnRefreshPhieuPhatActionPerformed

    private void btnTaoPhieuPhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuPhatActionPerformed
        // TODO add your handling code here:
        try {
        int maPhieu = Integer.parseInt(txtMaPhieuMuon.getText().trim());
        int maDocGia = Integer.parseInt(txtMaDocGiaPhieuPhat.getText().trim());
        String lyDo = txtLyDo.getText().trim();
        double soTien = Double.parseDouble(txtTienPhat.getText().trim());
        java.sql.Date ngayLap = new java.sql.Date(dateNgayLap.getDate().getTime());

        PhieuPhatController ctrl = new PhieuPhatController();
        boolean ok = ctrl.taoPhieuPhat(maPhieu, maDocGia, lyDo, soTien, ngayLap);

        if (ok) {
            JOptionPane.showMessageDialog(null, "Tạo phiếu phạt thành công!");
            loadPhieuPhatLenTable(); // Refresh bảng
            clearFormPhieuPhat();     // Xóa form nhập liệu nếu có
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Dữ liệu nhập không hợp lệ!");
    }
    }//GEN-LAST:event_btnTaoPhieuPhatActionPerformed

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
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemDangXuat;
    private javax.swing.JSpinner SpinnerSoLuong;
    private javax.swing.JButton btnCleanDocGia;
    private javax.swing.JButton btnCleanNV;
    private javax.swing.JButton btnCleanSach;
    private javax.swing.JButton btnClearPhieuMuon;
    private javax.swing.JButton btnClearPhieuPhat;
    private javax.swing.JButton btnClearPhieuTra;
    private javax.swing.JButton btnReFreshS;
    private javax.swing.JButton btnRefreshDG;
    private javax.swing.JTabbedPane btnRefreshNV;
    private javax.swing.JButton btnRefreshPM;
    private javax.swing.JButton btnRefreshPhieuPhat;
    private javax.swing.JButton btnRefreshPhieuTra;
    private javax.swing.JButton btnSuaDocGia;
    private javax.swing.JButton btnSuaNhanVien;
    private javax.swing.JButton btnSuaPhieuMuon;
    private javax.swing.JButton btnSuaPhieuPhat;
    private javax.swing.JButton btnSuaPhieuTra;
    private javax.swing.JButton btnSuaSach;
    private javax.swing.JButton btnTaoPhieuPhat;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemDocGia;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnThemPhieuMuon;
    private javax.swing.JButton btnThemPhieuTra;
    private javax.swing.JButton btnThemSach;
    private javax.swing.JButton btnXoaDocGia;
    private javax.swing.JButton btnXoaNV;
    private javax.swing.JButton btnXoaPhieuMuon;
    private javax.swing.JButton btnXoaPhieuPhat;
    private javax.swing.JButton btnXoaPhieuTra;
    private javax.swing.JButton btnXoaSach;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup10;
    private javax.swing.ButtonGroup buttonGroup11;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private javax.swing.JComboBox<String> cboTheme;
    private com.toedter.calendar.JDateChooser dateNgayLap;
    private com.toedter.calendar.JDateChooser dateNgayMuon;
    private com.toedter.calendar.JDateChooser dateNgayPhaiTra;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private com.toedter.calendar.JDateChooser dateNgaySinhNV;
    private com.toedter.calendar.JDateChooser dateNgayTra;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel44;
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
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JRadioButton rdGiaSach;
    private javax.swing.JRadioButton rdGioiTinh;
    private javax.swing.JRadioButton rdGioiTinhNV;
    private javax.swing.JRadioButton rdHoTen;
    private javax.swing.JRadioButton rdHoTenNV;
    private javax.swing.JRadioButton rdHoTenPhieuPhat;
    private javax.swing.JRadioButton rdLyDo;
    private javax.swing.JRadioButton rdMaDocGia;
    private javax.swing.JRadioButton rdMaNV;
    private javax.swing.JRadioButton rdMaSach;
    private javax.swing.JRadioButton rdNXB;
    private javax.swing.JRadioButton rdSoLuong;
    private javax.swing.JRadioButton rdTacGia;
    private javax.swing.JRadioButton rdTenDangNhap;
    private javax.swing.JRadioButton rdTenDangNhapNV;
    private javax.swing.JRadioButton rdTenSach;
    private javax.swing.JRadioButton rdTheLoai;
    private javax.swing.JRadioButton rdoHoTenDaMuon;
    private javax.swing.JRadioButton rdoHoTenDangMuon;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNamNV;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoNuNV;
    private javax.swing.JTable tblDaMuon;
    private javax.swing.JTable tblDangMuon;
    private javax.swing.JTable tblDocGia;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblPhieuPhat;
    private javax.swing.JTable tblPhieuPhat2;
    private javax.swing.JTable tblPhieuTra;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDiaChiNV;
    private javax.swing.JTextField txtGiaSach;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtHoTenNV;
    private javax.swing.JTextField txtLyDo;
    private javax.swing.JTextField txtMaDocGia;
    private javax.swing.JTextField txtMaDocGiaPhieuMuon;
    private javax.swing.JTextField txtMaDocGiaPhieuPhat;
    private javax.swing.JTextField txtMaDocGiaPhieuTra;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextField txtMaPhieuMuon;
    private javax.swing.JTextField txtMaPhieuPhat;
    private javax.swing.JTextField txtMaPhieuTra;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMaSachPhieuMuon;
    private javax.swing.JTextField txtMaSachPhieuTra;
    private javax.swing.JTextField txtNhaXuatBan;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JTextField txtTenDangNhapNV;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTheLoai;
    private javax.swing.JTextField txtTienPhat;
    private javax.swing.JTextField txtTimDaMuon;
    private javax.swing.JTextField txtTimDangMuon;
    private javax.swing.JTextField txtTimDocGia;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiemSach;
    private javax.swing.JTextField txtTimNhanVien;
    // End of variables declaration//GEN-END:variables
}
