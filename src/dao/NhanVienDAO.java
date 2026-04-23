package dao;

import database.DBConnection;
import model.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public NhanVien layThongTin(int maNhanVien) {
        String sql = "SELECT * FROM NHANVIEN WHERE MaNV = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNhanVien);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getInt("MaNV"));
                nv.setTenDangNhap(rs.getString("TenDangNhap"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                return nv;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean capNhatThongTin(NhanVien nv) {
        String sql = "UPDATE NHANVIEN SET HoTen=?, GioiTinh=?, NgaySinh=?, DiaChi=? WHERE MaNV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(4, nv.getDiaChi());
            ps.setInt(5, nv.getMaNhanVien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public NhanVien getById(int maNV) {
    NhanVien nv = null;
    String sql = "SELECT * FROM NHANVIEN WHERE MaNV = ?";

    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, maNV);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {   // ✅ CHÍNH LÀ ĐOẠN TEST BẠN HỎI
            nv = new NhanVien();
            nv.setMaNhanVien(rs.getInt("MaNV"));
            nv.setTenDangNhap(rs.getString("TenDangNhap"));
            nv.setHoTen(rs.getString("HoTen"));
            nv.setGioiTinh(rs.getString("GioiTinh"));
            nv.setNgaySinh(rs.getDate("NgaySinh"));
            nv.setDiaChi(rs.getString("DiaChi"));

            System.out.println("✅ LẤY ĐƯỢC NHÂN VIÊN: " + nv.getHoTen());
        } else {
            System.out.println("❌ KHÔNG TÌM THẤY NHÂN VIÊN VỚI ID: " + maNV);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return nv;
}
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("MaNV"),
                    rs.getString("TenDangNhap"),
                    rs.getString("HoTen"),
                    rs.getString("GioiTinh"),
                    rs.getDate("NgaySinh"),
                    rs.getString("DiaChi")
                );
                list.add(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public int themNhanVienLayMa(NhanVien nv) {
        String sql = "INSERT INTO NHANVIEN(TenDangNhap, HoTen, GioiTinh, NgaySinh, DiaChi) VALUES (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nv.getTenDangNhap());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(5, nv.getDiaChi());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) return -1;

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // ✅ MaNV vừa tạo
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
 public boolean updateNhanVien(NhanVien nv) {
        String sql = "UPDATE NHANVIEN SET TenDangNhap=?, HoTen=?, GioiTinh=?, NgaySinh=?, DiaChi=? WHERE MaNV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getTenDangNhap());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(5, nv.getDiaChi());
            ps.setInt(6, nv.getMaNhanVien());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
  public boolean deleteNhanVien(int maNV) {
        String sql = "DELETE FROM NHANVIEN WHERE MaNV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNV);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
   public List<NhanVien> searchNhanVien(String cot, String keyword) {
    List<NhanVien> list = new ArrayList<>();
    String sql = "SELECT * FROM NHANVIEN WHERE " + cot + " LIKE ?";

    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            NhanVien nv = new NhanVien();
            nv.setMaNhanVien(rs.getInt("MaNV"));
            nv.setTenDangNhap(rs.getString("TenDangNhap"));
            nv.setHoTen(rs.getString("HoTen"));
            nv.setGioiTinh(rs.getString("GioiTinh"));
            nv.setNgaySinh(rs.getDate("NgaySinh"));
            nv.setDiaChi(rs.getString("DiaChi"));
            list.add(nv);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
}
