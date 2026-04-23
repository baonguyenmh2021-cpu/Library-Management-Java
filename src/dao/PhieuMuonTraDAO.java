package dao;

import model.PhieuMuonTra;
import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonTraDAO {

    // Danh sách đang mượn
    public List<PhieuMuonTra> getDangMuon() {
        List<PhieuMuonTra> list = new ArrayList<>();
        String sql = "SELECT DG.HoTen, S.TenSach, PM.NgayMuon, PM.NgayPhaiTra " +
                     "FROM PHIEUMUON PM " +
                     "JOIN DOCGIA DG ON PM.MaDocGia = DG.MaDocGia " +
                     "JOIN SACH S ON PM.MaSach = S.MaSach " +
                     "LEFT JOIN PHIEUTRA PT ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach " +
                     "WHERE PT.NgayTra IS NULL " +
                     "ORDER BY PM.NgayMuon DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PhieuMuonTra(
                    rs.getString("HoTen"),
                    rs.getString("TenSach"),
                    rs.getDate("NgayMuon"),
                    rs.getDate("NgayPhaiTra")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Danh sách đã mượn
    public List<PhieuMuonTra> getDaMuon() {
        List<PhieuMuonTra> list = new ArrayList<>();
        String sql = "SELECT DG.HoTen, S.TenSach, PM.NgayMuon, PT.NgayTra " +
                     "FROM PHIEUMUON PM " +
                     "JOIN DOCGIA DG ON PM.MaDocGia = DG.MaDocGia " +
                     "JOIN SACH S ON PM.MaSach = S.MaSach " +
                     "JOIN PHIEUTRA PT ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach " +
                     "ORDER BY PT.NgayTra DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PhieuMuonTra(
                    rs.getString("HoTen"),
                    rs.getString("TenSach"),
                    rs.getDate("NgayMuon"),
                    rs.getDate("NgayTra"),
                    true
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Tìm kiếm ĐANG MƯỢN
public List<PhieuMuonTra> timKiemDangMuon(String cot, String keyword) {
    List<PhieuMuonTra> list = new ArrayList<>();
    String sql = "SELECT DG.HoTen, S.TenSach, PM.NgayMuon, PM.NgayPhaiTra " +
                 "FROM PHIEUMUON PM " +
                 "JOIN DOCGIA DG ON PM.MaDocGia = DG.MaDocGia " +
                 "JOIN SACH S ON PM.MaSach = S.MaSach " +
                 "LEFT JOIN PHIEUTRA PT ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach " +
                 "WHERE PT.NgayTra IS NULL AND " + cot + " LIKE ? " +
                 "ORDER BY PM.NgayMuon DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new PhieuMuonTra(
                rs.getString("HoTen"),
                rs.getString("TenSach"),
                rs.getDate("NgayMuon"),
                rs.getDate("NgayPhaiTra")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

// Tìm kiếm ĐÃ MƯỢN
public List<PhieuMuonTra> timKiemDaMuon(String cot, String keyword) {
    List<PhieuMuonTra> list = new ArrayList<>();
    String sql = "SELECT DG.HoTen, S.TenSach, PM.NgayMuon, PT.NgayTra " +
                 "FROM PHIEUMUON PM " +
                 "JOIN DOCGIA DG ON PM.MaDocGia = DG.MaDocGia " +
                 "JOIN SACH S ON PM.MaSach = S.MaSach " +
                 "JOIN PHIEUTRA PT ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach " +
                 "WHERE " + cot + " LIKE ? " +
                 "ORDER BY PT.NgayTra DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new PhieuMuonTra(
                rs.getString("HoTen"),
                rs.getString("TenSach"),
                rs.getDate("NgayMuon"),
                rs.getDate("NgayTra"),
                true
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
