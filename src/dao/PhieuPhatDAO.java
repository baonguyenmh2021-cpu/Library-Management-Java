package dao;

import model.PhieuPhat;
import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.PhieuMuon;

// PhieuPhatDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PhieuPhatDAO {
    public static List<PhieuPhat> getAll() {
    List<PhieuPhat> list = new ArrayList<>();

    try {
        String sql =
            "SELECT pp.MaPhieuPhat, pp.MaPhieu, pp.MaDocGia, pp.LyDo, pp.SoTien, pp.NgayLap, pp.TrangThai, dg.HoTen " +
            "FROM PHIEUPHAT pp " +
            "JOIN DOCGIA dg ON pp.MaDocGia = dg.MaDocGia " +
            "WHERE pp.TrangThai <> -1";

        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            PhieuPhat p = new PhieuPhat();
            p.setMaPhieuPhat(rs.getInt("MaPhieuPhat"));
            p.setMaPhieu(rs.getInt("MaPhieu"));        // <-- thêm đây
            p.setMaDocGia(rs.getInt("MaDocGia"));
            p.setTenDocGia(rs.getString("HoTen"));
            p.setLyDo(rs.getString("LyDo"));
            p.setSoTien(rs.getDouble("SoTien"));
            p.setNgayLap(rs.getDate("NgayLap"));
            p.setTrangThai(rs.getInt("TrangThai"));
            list.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    
      // Thêm phiếu phạt mới
    public static boolean insertPhieuPhat(int maPhieu, int maDocGia, String lyDo, double soTien, java.sql.Date ngayLap) {
        try {
            Connection conn = DBConnection.getConnection();

            // 1. Kiểm tra phiếu mượn tồn tại
            String sqlCheckMuon = "SELECT * FROM PHIEUMUON WHERE MaPhieu = ? AND MaDocGia = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheckMuon);
            psCheck.setInt(1, maPhieu);
            psCheck.setInt(2, maDocGia);
            ResultSet rsCheck = psCheck.executeQuery();
            if (!rsCheck.next()) {
                JOptionPane.showMessageDialog(null, "Phiếu mượn không tồn tại!");
                return false;
            }

            // 2. Kiểm tra phiếu đã trả chưa
            String sqlCheckTra = "SELECT * FROM PHIEUTRA WHERE MaPhieu = ?";
            PreparedStatement psTra = conn.prepareStatement(sqlCheckTra);
            psTra.setInt(1, maPhieu);
            ResultSet rsTra = psTra.executeQuery();
            if (rsTra.next()) {
                JOptionPane.showMessageDialog(null, "Phiếu mượn đã trả sách, không thể tạo phiếu phạt!");
                return false;
            }

            // 3. Thêm Phiếu Phạt mới, TrangThai mặc định = 0
            String sqlInsert = "INSERT INTO PHIEUPHAT (MaPhieu, MaDocGia, LyDo, SoTien, NgayLap, TrangThai) VALUES (?,?,?,?,?,0)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, maPhieu);
            psInsert.setInt(2, maDocGia);
            psInsert.setString(3, lyDo);
            psInsert.setDouble(4, soTien);
            psInsert.setDate(5, ngayLap);

            psInsert.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo phiếu phạt!");
            return false;
        }
    }
     public static boolean xoaMemPhieuPhat(int maPhieuPhat) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE PHIEUPHAT SET TrangThai = -1 WHERE MaPhieuPhat = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maPhieuPhat);
            int kq = ps.executeUpdate();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa phiếu phạt!");
            return false;
        }
    }
     public static boolean updatePhieuPhat(PhieuPhat p) {
    try {
        String sql = "UPDATE PHIEUPHAT SET LyDo = ?, SoTien = ?, NgayLap = ? WHERE MaPhieuPhat = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, p.getLyDo());
        ps.setDouble(2, p.getSoTien());
        ps.setDate(3, new java.sql.Date(p.getNgayLap().getTime()));
        ps.setInt(4, p.getMaPhieuPhat());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public static boolean thanhToanPhieuPhat(int maPhieuPhat) {
    try {
        String sql = "UPDATE PHIEUPHAT SET TrangThai = 1 WHERE MaPhieuPhat = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, maPhieuPhat);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


public static List<PhieuPhat> search(String cot, String keyword) {
    List<PhieuPhat> list = new ArrayList<>();

    String sql =
        "SELECT pp.MaPhieuPhat, pp.MaPhieu, pp.MaDocGia, pp.LyDo, pp.SoTien, pp.NgayLap, pp.TrangThai, dg.HoTen " +
        "FROM PHIEUPHAT pp " +
        "JOIN DOCGIA dg ON pp.MaDocGia = dg.MaDocGia " +
        "WHERE pp.TrangThai <> -1 AND " + cot + " LIKE ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            PhieuPhat p = new PhieuPhat();
            p.setMaPhieuPhat(rs.getInt("MaPhieuPhat"));
            p.setMaPhieu(rs.getInt("MaPhieu"));
            p.setMaDocGia(rs.getInt("MaDocGia"));
            p.setTenDocGia(rs.getString("HoTen"));
            p.setLyDo(rs.getString("LyDo"));
            p.setSoTien(rs.getDouble("SoTien"));
            p.setNgayLap(rs.getDate("NgayLap"));
            p.setTrangThai(rs.getInt("TrangThai"));
            list.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}