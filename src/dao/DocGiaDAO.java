package dao;

import database.DBConnection;
import model.DocGia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DocGiaDAO {

    public DocGia layThongTin(int maDocGia) {
        String sql = "SELECT * FROM DOCGIA WHERE MaDocGia = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDocGia);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DocGia dg = new DocGia();
                dg.setMaDocGia(rs.getInt("MaDocGia"));
                dg.setTenDangNhap(rs.getString("TenDangNhap"));
                dg.setHoTen(rs.getString("HoTen"));
                dg.setGioiTinh(rs.getString("GioiTinh"));
                dg.setNgaySinh(rs.getDate("NgaySinh"));
                dg.setDiaChi(rs.getString("DiaChi"));
                return dg;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean capNhatThongTin(DocGia dg) {
        String sql = "UPDATE DOCGIA SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ? WHERE MaDocGia = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dg.getHoTen());
            ps.setString(2, dg.getGioiTinh());
            ps.setDate(3, new java.sql.Date(dg.getNgaySinh().getTime()));
            ps.setString(4, dg.getDiaChi());
            ps.setInt(5, dg.getMaDocGia());

            int rows = ps.executeUpdate();
            System.out.println("Rows updated: " + rows);
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

     public List<DocGia> getAll() {
        List<DocGia> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM DOCGIA";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DocGia dg = new DocGia();
                dg.setMaDocGia(rs.getInt("MaDocGia"));
                dg.setTenDangNhap(rs.getString("TenDangNhap"));
                dg.setHoTen(rs.getString("HoTen"));
                dg.setGioiTinh(rs.getString("GioiTinh"));
                dg.setNgaySinh(rs.getDate("NgaySinh"));
                dg.setDiaChi(rs.getString("DiaChi"));

                list.add(dg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
// ✅ THÊM ĐỘC GIẢ

public int themDocGiaLayMa(DocGia dg) {
    String sql = """
        INSERT INTO DOCGIA(TenDangNhap, HoTen, GioiTinh, NgaySinh, DiaChi)
        VALUES (?, ?, ?, ?, ?)
    """;
    try {
        PreparedStatement ps = DBConnection.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, dg.getTenDangNhap());
        ps.setString(2, dg.getHoTen());
        ps.setString(3, dg.getGioiTinh());
        ps.setDate(4, new java.sql.Date(dg.getNgaySinh().getTime()));
        ps.setString(5, dg.getDiaChi());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // ✅ MaDocGia AUTO_INCREMENT
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1;
}

   
   public boolean updateDocGia(DocGia dg) {
        String sql = "UPDATE DOCGIA SET TenDangNhap=?, HoTen=?, GioiTinh=?, NgaySinh=?, DiaChi=? WHERE MaDocGia=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dg.getTenDangNhap());
            ps.setString(2, dg.getHoTen());
            ps.setString(3, dg.getGioiTinh());
            ps.setDate(4, new java.sql.Date(dg.getNgaySinh().getTime()));
            ps.setString(5, dg.getDiaChi());
            ps.setInt(6, dg.getMaDocGia());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
   

  public boolean deleteDocGia(int maDocGia) {
        String sql = "DELETE FROM DOCGIA WHERE MaDocGia=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDocGia);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
  


public List<DocGia> searchDocGia(String cot, String keyword) {
    List<DocGia> list = new ArrayList<>();
    String sql = "SELECT * FROM DOCGIA WHERE " + cot + " LIKE ?";

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            DocGia dg = new DocGia();
            dg.setMaDocGia(rs.getInt("MaDocGia"));
            dg.setTenDangNhap(rs.getString("TenDangNhap"));
            dg.setHoTen(rs.getString("HoTen"));
            dg.setGioiTinh(rs.getString("GioiTinh"));
            dg.setNgaySinh(rs.getDate("NgaySinh"));
            dg.setDiaChi(rs.getString("DiaChi"));
            list.add(dg);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
