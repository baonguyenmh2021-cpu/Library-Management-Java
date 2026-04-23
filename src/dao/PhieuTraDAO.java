package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

public class PhieuTraDAO {


   public static ResultSet getAllPhieuTra() {
    String sql = ""
        + "SELECT "
        + "  PT.MaPhieu, "
        + "  DG.MaDocGia, "
        + "  DG.HoTen, "
        + "  S.MaSach, "
        + "  S.TenSach, "
        + "  PT.NgayTra "
        + "FROM PHIEUTRA PT "
        + "JOIN DOCGIA DG ON PT.MaDocGia = DG.MaDocGia "
        + "JOIN SACH S ON PT.MaSach = S.MaSach "
        + "ORDER BY PT.NgayTra DESC";

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();   // KHÔNG ĐƯỢC đóng conn ở đây
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
   
   
   public static boolean insertPhieuTra(int maDocGia, int maSach, Date ngayTra) {
    Connection conn = null;
    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false); // transaction

        // 1) Tìm phiếu mượn chưa trả + lấy ngày phải trả
        String sqlFind = """
            SELECT MaPhieu, NgayPhaiTra
            FROM PHIEUMUON
            WHERE MaDocGia = ?
              AND MaSach = ?
              AND MaPhieu NOT IN (SELECT MaPhieu FROM PHIEUTRA)
            LIMIT 1
        """;

        PreparedStatement findPS = conn.prepareStatement(sqlFind);
        findPS.setInt(1, maDocGia);
        findPS.setInt(2, maSach);

        ResultSet rs = findPS.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(null,
                "Không tìm thấy phiếu mượn tương ứng (hoặc đã trả)!");
            return false;
        }

        int maPhieuMuon = rs.getInt("MaPhieu");
        Date ngayPhaiTra = rs.getDate("NgayPhaiTra");

        // 2) Insert PHIEUTRA
        String sqlInsert = """
            INSERT INTO PHIEUTRA(MaPhieu, MaDocGia, MaSach, NgayTra)
            VALUES (?, ?, ?, ?)
        """;

        PreparedStatement ps = conn.prepareStatement(sqlInsert);
        ps.setInt(1, maPhieuMuon);
        ps.setInt(2, maDocGia);
        ps.setInt(3, maSach);
        ps.setDate(4, new java.sql.Date(ngayTra.getTime()));

        int rows = ps.executeUpdate();

        if (rows > 0) {

            // 3) Cộng lại số lượng sách
            String update = "UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = ?";
            PreparedStatement ups = conn.prepareStatement(update);
            ups.setInt(1, maSach);
            ups.executeUpdate();

            // 4) KIỂM TRA QUÁ HẠN → TẠO PHIẾU PHẠT
            if (ngayTra.after(ngayPhaiTra)) {

                String sqlPhat = """
                    INSERT INTO PHIEUPHAT
                    (MaPhieu, MaDocGia, LyDo, SoTien, TrangThai)
                    VALUES (?, ?, ?, ?, ?)
                """;

                PreparedStatement psPhat = conn.prepareStatement(sqlPhat);
                psPhat.setInt(1, maPhieuMuon);
                psPhat.setInt(2, maDocGia);
                psPhat.setString(3, "Trả sách quá hạn");
                psPhat.setDouble(4, 10000); // tiền phạt tạm
                psPhat.setInt(5, 0); // chưa đóng

                psPhat.executeUpdate();
            }
        }

        conn.commit();
        return rows > 0;

    } catch (Exception e) {
        try {
            if (conn != null) conn.rollback();
        } catch (Exception ex) {}
        e.printStackTrace();
    }
    return false;
}

    public static int getMaSachCuCuaPhieuTra(int maPhieuTra) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT MaSach FROM PHIEUTRA WHERE MaPhieu = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maPhieuTra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ma = rs.getInt("MaSach");
                rs.close();
                // DON'T close conn here if you want to reuse; caller just closes separately if needed
                conn.close();
                return ma;
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

   public static boolean updatePhieuTra(int maPhieuTra, int maDocGia, int newMaSach, Date ngayTra) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1) Lấy mã sách cũ từ PHIEUTRA
            String sqlGet = "SELECT MaSach FROM PHIEUTRA WHERE MaPhieu = ?";
            ps = conn.prepareStatement(sqlGet);
            ps.setInt(1, maPhieuTra);
            rs = ps.executeQuery();

            if (!rs.next()) {
                rs.close();
                ps.close();
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Phiếu trả không tồn tại!");
                return false;
            }

            int oldMaSach = rs.getInt("MaSach");
            rs.close();
            ps.close();

            // 2) Nếu đổi sách -> điều chỉnh kho:
            if (oldMaSach != newMaSach) {

                // a) Kiểm tra sách mới tồn tại
                String checkNew = "SELECT SoLuong FROM SACH WHERE MaSach = ?";
                ps = conn.prepareStatement(checkNew);
                ps.setInt(1, newMaSach);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    rs.close();
                    ps.close();
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Mã sách mới không tồn tại!");
                    return false;
                }
                rs.close();
                ps.close();

                // b) Undo +1 cho sách cũ (trả trước đó đã cộng +1, giờ undo => -1)
                String decOld = "UPDATE SACH SET SoLuong = SoLuong - 1 WHERE MaSach = ?";
                ps = conn.prepareStatement(decOld);
                ps.setInt(1, oldMaSach);
                ps.executeUpdate();
                ps.close();

                // c) Add +1 cho sách mới (vì bây giờ phiếu trả là cho sách mới)
                String incNew = "UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = ?";
                ps = conn.prepareStatement(incNew);
                ps.setInt(1, newMaSach);
                ps.executeUpdate();
                ps.close();
            }

            // 3) Cập nhật bản ghi PHIEUTRA
            String sqlUpdate = "UPDATE PHIEUTRA SET MaDocGia = ?, MaSach = ?, NgayTra = ? WHERE MaPhieu = ?";
            ps = conn.prepareStatement(sqlUpdate);
            ps.setInt(1, maDocGia);
            ps.setInt(2, newMaSach);
            ps.setDate(3, new java.sql.Date(ngayTra.getTime()));
            ps.setInt(4, maPhieuTra);
            int updated = ps.executeUpdate();
            ps.close();

            if (updated <= 0) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Cập nhật phiếu trả thất bại!");
                return false;
            }
// 4) KIỂM TRA QUÁ HẠN → TẠO / HỦY PHIẾU PHẠT
String sqlNgayPhaiTra = "SELECT NgayPhaiTra FROM PHIEUMUON WHERE MaPhieu = ?";
ps = conn.prepareStatement(sqlNgayPhaiTra);
ps.setInt(1, maPhieuTra);
rs = ps.executeQuery();

if (rs.next()) {
    Date ngayPhaiTra = rs.getDate("NgayPhaiTra");
    rs.close();
    ps.close();

    if (ngayTra.after(ngayPhaiTra)) {
        // 👉 TRẢ TRỄ

        // Kiểm tra đã có phiếu phạt chưa (chưa bị hủy)
        String checkPhat = """
            SELECT MaPhieuPhat
            FROM PHIEUPHAT
            WHERE MaPhieu = ? AND TrangThai != -1
        """;
        ps = conn.prepareStatement(checkPhat);
        ps.setInt(1, maPhieuTra);
        rs = ps.executeQuery();

        if (!rs.next()) {
            rs.close();
            ps.close();

            // Chưa có → thêm mới
            String insertPhat = """
                INSERT INTO PHIEUPHAT
                (MaPhieu, MaDocGia, LyDo, SoTien, TrangThai)
                VALUES (?, ?, ?, ?, 0)
            """;
            ps = conn.prepareStatement(insertPhat);
            ps.setInt(1, maPhieuTra);
            ps.setInt(2, maDocGia);
            ps.setString(3, "Trả sách quá hạn");
            ps.setDouble(4, 10000); // tiền phạt tạm
            ps.executeUpdate();
            ps.close();
        } else {
            rs.close();
            ps.close();
            // Đã có phiếu phạt → không làm gì
        }

    } else {
        // 👉 KHÔNG TRỄ → HỦY PHIẾU PHẠT (NẾU CÓ)
        String huyPhat = """
            UPDATE PHIEUPHAT
            SET TrangThai = -1
            WHERE MaPhieu = ? AND TrangThai = 0
        """;
        ps = conn.prepareStatement(huyPhat);
        ps.setInt(1, maPhieuTra);
        ps.executeUpdate();
        ps.close();
    }
}

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (Exception e) {}
        }
    }
   
   public static boolean deletePhieuTra(int maPhieu) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        // 1) Lấy mã sách
        String sqlGet = "SELECT MaSach FROM PHIEUTRA WHERE MaPhieu = ?";
        ps = conn.prepareStatement(sqlGet);
        ps.setInt(1, maPhieu);
        rs = ps.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Phiếu trả không tồn tại!");
            conn.rollback();
            return false;
        }

        int maSach = rs.getInt("MaSach");
        rs.close();
        ps.close();
        
        // 1.5) XÓA PHIẾU PHẠT LIÊN QUAN (NẾU CÓ)
String sqlDeletePhat = "DELETE FROM PHIEUPHAT WHERE MaPhieu = ?";
ps = conn.prepareStatement(sqlDeletePhat);
ps.setInt(1, maPhieu);
ps.executeUpdate();
ps.close();


        // 2) XÓA phiếu trả
        String sqlDelete = "DELETE FROM PHIEUTRA WHERE MaPhieu = ?";
        ps = conn.prepareStatement(sqlDelete);
        ps.setInt(1, maPhieu);

        int deleted = ps.executeUpdate();
        ps.close();

        if (deleted <= 0) {
            conn.rollback();
            JOptionPane.showMessageDialog(null, "Xóa phiếu trả thất bại!");
            return false;
        }

        // 3) Hoàn trả kho sách (+1)
        String sqlUpdate = "UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = ?";
        ps = conn.prepareStatement(sqlUpdate);
        ps.setInt(1, maSach);
        ps.executeUpdate();
        ps.close();

        conn.commit();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (Exception e) {}
    }

    return false;
}
public static boolean daTraSach(int maDocGia, int maSach) {
    try {
        Connection conn = DBConnection.getConnection();
        String sql = """
            SELECT 1 
            FROM PHIEUTRA
            WHERE MaDocGia = ? AND MaSach = ?
            LIMIT 1
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maDocGia);
        ps.setInt(2, maSach);

        ResultSet rs = ps.executeQuery();

        boolean daTra = rs.next();

        rs.close();
        ps.close();
        conn.close();

        return daTra;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}



    public static Date getNgayPhaiTra(int maDocGia, int maSach) {
    Date ngayPhaiTra = null;
    try {
        String sql = """
            SELECT PM.NgayPhaiTra
            FROM PHIEUMUON PM
            LEFT JOIN PHIEUTRA PT 
            ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach
            WHERE PM.MaDocGia = ? 
              AND PM.MaSach = ?
              AND PT.MaPhieu IS NULL
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, maDocGia);
        ps.setInt(2, maSach);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ngayPhaiTra = rs.getDate("NgayPhaiTra");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return ngayPhaiTra;
}

}
