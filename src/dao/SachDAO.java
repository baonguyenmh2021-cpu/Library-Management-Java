package dao;

import database.DBConnection;
import model.Sach;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    
    public static List<Sach> getAllSach() {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT maSach, tenSach, tacGia, theLoai, nhaXuatBan, tinhTrang FROM sach";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Sach(
                    rs.getInt("MaSach"),
                    rs.getString("TenSach"),
                    rs.getString("TacGia"),
                    rs.getString("TheLoai"),
                     rs.getString("NhaXuatBan"),
                    "", "",
                    rs.getString("TinhTrang")
                    
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Tìm kiếm theo cột bất kỳ
    public static List<Sach> searchSach(String cot, String keyword) {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT maSach, tenSach, tacGia, theLoai, nhaXuatBan, tinhTrang FROM sach WHERE " + cot + " LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Sach(
                    rs.getInt("MaSach"),
                    rs.getString("TenSach"),
                    rs.getString("TacGia"),
                    rs.getString("TheLoai"),
                    rs.getString("NhaXuatBan"),
                    "", "",
                    rs.getString("TinhTrang")
                    
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ LẤY SÁCH ĐANG MƯỢN + TÌM THEO TÊN SÁCH
    public static List<Sach> getSachDangMuon(int maDocGia, String tenSach) {
        List<Sach> list = new ArrayList<>();

        String sql =
            "SELECT S.MaSach, S.TenSach, S.TheLoai, S.TacGia, S.NhaXuatBan " +
            "FROM DOCGIA DG " +
            "JOIN PHIEUMUON PM ON DG.MaDocGia = PM.MaDocGia " +
            "JOIN SACH S ON PM.MaSach = S.MaSach " +
            "LEFT JOIN PHIEUTRA PT ON PM.MaDocGia = PT.MaDocGia AND PM.MaSach = PT.MaSach " +
            "WHERE DG.MaDocGia = ? AND PT.MaPhieu IS NULL " +
            "AND S.TenSach LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDocGia);
            ps.setString(2, "%" + tenSach + "%"); // ✅ CHỈ TÌM THEO TÊN SÁCH

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sach s = new Sach(
                    rs.getInt("MaSach"),
                    rs.getString("TenSach"),
                    rs.getString("TacGia"),
                    rs.getString("TheLoai"),
                    rs.getString("NhaXuatBan"),
                    "", "",
                    ""
                    
                );
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static List<Sach> getLichSuMuonTraTheoTenSach(int maDocGia, String keyword) {
    List<Sach> list = new ArrayList<>();

    String sql = """
        SELECT
            S.MaSach,
            S.TenSach,
            S.TheLoai,
            S.TacGia,
            S.NhaXuatBan,
            CASE 
                WHEN PT.NgayTra IS NOT NULL THEN N'Đã Trả'
                ELSE N'Đang Mượn'
            END AS TrangThai
        FROM PHIEUMUON PM
        JOIN SACH S ON PM.MaSach = S.MaSach
        LEFT JOIN PHIEUTRA PT 
            ON PM.MaDocGia = PT.MaDocGia 
            AND PM.MaSach = PT.MaSach
        WHERE PM.MaDocGia = ?
          AND S.TenSach LIKE ?
        ORDER BY PM.NgayMuon DESC
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, maDocGia);
        ps.setString(2, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Sach s = new Sach(
                rs.getInt("MaSach"),
                rs.getString("TenSach"),
                rs.getString("TacGia"),
                rs.getString("TheLoai"),
                rs.getString("NhaXuatBan"),
                "", "",
                rs.getString("TrangThai")
                
            );
            list.add(s);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
 public List<Sach> getAllSach1() {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM SACH"; // bảng SACH trong database

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sach s = new Sach(
                    rs.getInt("MaSach"),
                    rs.getString("TenSach"),
                    rs.getString("TacGia"),
                    rs.getString("TheLoai"),
                    rs.getString("NhaXuatBan"),
                    rs.getString("GiaSach"),
                    rs.getString("SoLuong"),
                    rs.getString("TinhTrang") // có thể bỏ nếu không cần hiển thị
                );
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
  public boolean themSach(Sach s) {
        String sql = "INSERT INTO SACH(TenSach, TacGia, TheLoai, NhaXuatBan, GiaSach, SoLuong, TinhTrang) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // ✅ Tính TinhTrang dựa vào SoLuong
        String tinhTrang = "Còn";
        try {
            int soLuong = Integer.parseInt(s.getSoLuong());
            if (soLuong <= 0) tinhTrang = "Hết";
        } catch (Exception e) {
            tinhTrang = "Hết"; // nếu nhập sai số lượng, mặc định Hết
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getTenSach());
            ps.setString(2, s.getTacGia());
            ps.setString(3, s.getTheLoai());
            ps.setString(4, s.getNhaXuatBan());
            ps.setString(5, s.getGiaSach());
            ps.setString(6, s.getSoLuong());
            ps.setString(7, tinhTrang);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
  
  public boolean updateSach(Sach s) {
        String sql = "UPDATE SACH SET TenSach=?, TacGia=?, TheLoai=?, NhaXuatBan=?, GiaSach=?, SoLuong=?, TinhTrang=? WHERE MaSach=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Tính TinhTrang dựa vào SoLuong
            int soLuong = Integer.parseInt(s.getSoLuong());
            String tinhTrang = soLuong > 0 ? "Còn" : "Hết";

            ps.setString(1, s.getTenSach());
            ps.setString(2, s.getTacGia());
            ps.setString(3, s.getTheLoai());
            ps.setString(4, s.getNhaXuatBan());
            ps.setString(5, s.getGiaSach());
            ps.setString(6, s.getSoLuong());
            ps.setString(7, tinhTrang);
            ps.setInt(8, s.getMaSach());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
  
    public boolean deleteSach(int maSach) {
        String sql = "DELETE FROM SACH WHERE MaSach=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maSach);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public List<Sach> searchSach1(String cot, String keyword) {
    List<Sach> list = new ArrayList<>();

    String sql = "SELECT * FROM SACH WHERE " + cot + " LIKE ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Sach s = new Sach(
                rs.getInt("MaSach"),
                rs.getString("TenSach"),
                rs.getString("TacGia"),
                rs.getString("TheLoai"),
                rs.getString("NhaXuatBan"),
                rs.getString("GiaSach"),
                rs.getString("SoLuong"),
                rs.getString("TinhTrang")
            );
            list.add(s);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}

