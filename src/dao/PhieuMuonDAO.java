package dao;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.PhieuMuon;
import java.util.Date;
import javax.swing.JOptionPane;

public class PhieuMuonDAO {

    // ✅ LẤY TẤT CẢ SÁCH ĐANG MƯỢN CỦA 1 ĐỘC GIẢ
    public static ResultSet getSachDangMuon(int maDocGia) {
        String sql = """
            SELECT
                S.MaSach,
                S.TenSach,
                S.TheLoai,
                S.TacGia,
                S.NhaXuatBan
            FROM DOCGIA DG
            JOIN PHIEUMUON PM ON DG.MaDocGia = PM.MaDocGia
            JOIN SACH S ON PM.MaSach = S.MaSach
            LEFT JOIN PHIEUTRA PT 
                ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach
            WHERE DG.MaDocGia = ?
            AND PT.MaPhieu IS NULL
        """;

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maDocGia);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ TÌM THEO TÊN TRONG DANH SÁCH ĐANG MƯỢN
    public static ResultSet timSachDangMuon(int maDocGia, String tenSach) {
        String sql = """
            SELECT
                S.MaSach,
                S.TenSach,
                S.TheLoai,
                S.TacGia,
                S.NhaXuatBan
            FROM DOCGIA DG
            JOIN PHIEUMUON PM ON DG.MaDocGia = PM.MaDocGia
            JOIN SACH S ON PM.MaSach = S.MaSach
            LEFT JOIN PHIEUTRA PT 
                ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach
            WHERE DG.MaDocGia = ?
            AND PT.MaPhieu IS NULL
            AND S.TenSach LIKE ?
        """;

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maDocGia);
            ps.setString(2, "%" + tenSach + "%");
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

  public List<PhieuMuon> layTatCaPhieuMuon() {
        List<PhieuMuon> list = new ArrayList<>();

        String sql = """
            SELECT 
                PM.MaPhieu,
                DG.MaDocGia, 
                DG.HoTen,
                S.MaSach,
                S.TenSach,
                PM.NgayMuon,
                PM.NgayPhaiTra
            FROM PHIEUMUON PM
            JOIN DOCGIA DG ON PM.MaDocGia = DG.MaDocGia
            JOIN SACH S ON PM.MaSach = S.MaSach
            ORDER BY PM.MaPhieu DESC
        """;

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();

                pm.setMaPhieu(rs.getInt("MaPhieu"));
                pm.setMaDocGia(rs.getInt("MaDocGia"));
                pm.setHoTen(rs.getString("HoTen"));
                pm.setMaSach(rs.getInt("MaSach"));
                pm.setTenSach(rs.getString("TenSach"));
                pm.setNgayMuon(rs.getDate("NgayMuon"));
                pm.setNgayPhaiTra(rs.getDate("NgayPhaiTra"));

                list.add(pm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
  
  public static boolean insertPhieuMuon(
        int maDocGia, int maSach, Date ngayMuon, Date ngayPhaiTra) {

    Connection conn = null;
    try {
        conn = DBConnection.getConnection();

        /* ===============================
         * 0) KIỂM TRA ĐỘC GIẢ TỒN TẠI
         * =============================== */
        String checkDG = "SELECT 1 FROM DOCGIA WHERE MaDocGia = ?";
        PreparedStatement psDG = conn.prepareStatement(checkDG);
        psDG.setInt(1, maDocGia);
        ResultSet rsDG = psDG.executeQuery();

        if (!rsDG.next()) {
            JOptionPane.showMessageDialog(
                null,
                "Độc giả không tồn tại, không thể lập phiếu mượn!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        /* ===============================
         * 1) KIỂM TRA SỐ LƯỢNG SÁCH
         * =============================== */
        String checkSach = "SELECT SoLuong FROM SACH WHERE MaSach = ?";
        PreparedStatement psSach = conn.prepareStatement(checkSach);
        psSach.setInt(1, maSach);
        ResultSet rsSach = psSach.executeQuery();

        if (rsSach.next()) {
            int soLuong = rsSach.getInt("SoLuong");
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "Sách đã hết, không thể mượn!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
                );
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Sách không tồn tại!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        /* ===============================
         * 2) INSERT PHIẾU MƯỢN
         * =============================== */
        String sql = """
            INSERT INTO PHIEUMUON(MaDocGia, MaSach, NgayMuon, NgayPhaiTra)
            VALUES (?, ?, ?, ?)
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maDocGia);
        ps.setInt(2, maSach);
        ps.setDate(3, new java.sql.Date(ngayMuon.getTime()));
        ps.setDate(4, new java.sql.Date(ngayPhaiTra.getTime()));

        int rows = ps.executeUpdate();

        /* ===============================
         * 3) GIẢM SỐ LƯỢNG SÁCH
         * =============================== */
        if (rows > 0) {
            String update = "UPDATE SACH SET SoLuong = SoLuong - 1 WHERE MaSach = ?";
            PreparedStatement ups = conn.prepareStatement(update);
            ups.setInt(1, maSach);
            ups.executeUpdate();
            return true;
        }

    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
        JOptionPane.showMessageDialog(
            null,
            "Dữ liệu không hợp lệ (vi phạm ràng buộc)!",
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

  
  public static int getMaSachCu(int maPM) {
    try {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT MaSach FROM PHIEUMUON WHERE MaPhieu = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maPM);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("MaSach");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; // lỗi
}

  public static boolean updatePhieuMuon(int maPM, int maDocGia, int newMaSach, Date ngayMuon, Date ngayPhaiTra) {
    try {
        Connection conn = DBConnection.getConnection();

        // Lấy mã sách cũ
        int oldMaSach = getMaSachCu(maPM);

        // 1) Nếu đổi sách → xử lý tồn kho
        if (oldMaSach != newMaSach) {

            // Kiểm tra số lượng sách mới
            String check = "SELECT SoLuong FROM SACH WHERE MaSach = ?";
            PreparedStatement psCheck = conn.prepareStatement(check);
            psCheck.setInt(1, newMaSach);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                int soLuong = rs.getInt("SoLuong");
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(null,
                        "Sách mới đã hết, không thể đổi!");
                    return false;
                }
            }

            // +1 số lượng sách cũ
            String up1 = "UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = ?";
            PreparedStatement psUp1 = conn.prepareStatement(up1);
            psUp1.setInt(1, oldMaSach);
            psUp1.executeUpdate();

            // -1 số lượng sách mới
            String up2 = "UPDATE SACH SET SoLuong = SoLuong - 1 WHERE MaSach = ?";
            PreparedStatement psUp2 = conn.prepareStatement(up2);
            psUp2.setInt(1, newMaSach);
            psUp2.executeUpdate();
        }

        // 2) Update phiếu mượn
        String sql = """
            UPDATE PHIEUMUON
            SET MaDocGia = ?, MaSach = ?, NgayMuon = ?, NgayPhaiTra = ?
            WHERE MaPhieu = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maDocGia);
        ps.setInt(2, newMaSach);
        ps.setDate(3, new java.sql.Date(ngayMuon.getTime()));
        ps.setDate(4, new java.sql.Date(ngayPhaiTra.getTime()));
        ps.setInt(5, maPM);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
    }



  public static boolean xoaPhieuMuon(int maPhieu) {
    try {
        Connection conn = DBConnection.getConnection();

        // Lấy MaSach để cộng lại số lượng
        String get = "SELECT MaSach FROM PHIEUMUON WHERE MaPhieu = ?";
        PreparedStatement ps1 = conn.prepareStatement(get);
        ps1.setInt(1, maPhieu);
        ResultSet rs = ps1.executeQuery();

        int maSach = -1;
        if (rs.next()) {
            maSach = rs.getInt("MaSach");
        } else {
            return false;
        }

        // Xóa phiếu mượn
        String delete = "DELETE FROM PHIEUMUON WHERE MaPhieu = ?";
        PreparedStatement ps2 = conn.prepareStatement(delete);
        ps2.setInt(1, maPhieu);

        int rows = ps2.executeUpdate();

        if (rows > 0) {
            // Cộng lại số lượng sách
            String update = "UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = ?";
            PreparedStatement ps3 = conn.prepareStatement(update);
            ps3.setInt(1, maSach);
            ps3.executeUpdate();
        }

        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}



}
