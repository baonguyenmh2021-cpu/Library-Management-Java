package dao;

import database.DBConnection;
import model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AccountDAO {

public static Account login(String user, String pass) {
        String sql = "SELECT * FROM account WHERE TenDangNhap=? AND MatKhau=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("MaAccount"),
                    rs.getString("TenDangNhap"),
                    rs.getString("MatKhau"),   // ✅ BẮT BUỘC PHẢI CÓ
                    rs.getInt("Quyen"),
                    (Integer) rs.getObject("MaDocGia"),
                    (Integer) rs.getObject("MaNV")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    // 1️⃣ Kiểm tra trùng username
public static boolean checkUsernameExists(String username) {
    String sql = "SELECT * FROM account WHERE TenDangNhap=?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        return rs.next(); // tồn tại → true

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public static boolean register(String username, String password) {

    String sqlAccount = "INSERT INTO account(TenDangNhap, MatKhau, Quyen) VALUES (?, ?, 1)";
    String sqlDocGia  = "INSERT INTO docgia(TenDangNhap) VALUES (?)";
    String sqlUpdateAccount = "UPDATE account SET MaDocGia = ? WHERE TenDangNhap = ?";

    try (Connection conn = DBConnection.getConnection()) {
        conn.setAutoCommit(false); // Bắt đầu transaction

        // 1️⃣ Thêm account
        PreparedStatement psAcc = conn.prepareStatement(sqlAccount);
        psAcc.setString(1, username);
        psAcc.setString(2, password);
        psAcc.executeUpdate();

        // 2️⃣ Thêm độc giả + lấy MaDocGia tự tăng
        PreparedStatement psDG = conn.prepareStatement(sqlDocGia, PreparedStatement.RETURN_GENERATED_KEYS);
        psDG.setString(1, username);
        psDG.executeUpdate();

        ResultSet rs = psDG.getGeneratedKeys();

        int maDocGia = 0;
        if (rs.next()) {
            maDocGia = rs.getInt(1); // Lấy mã độc giả vừa tạo
        }

        // 3️⃣ Cập nhật lại account để gán MaDocGia
        PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateAccount);
        psUpdate.setInt(1, maDocGia);
        psUpdate.setString(2, username);
        psUpdate.executeUpdate();

        conn.commit(); // ✅ Lưu toàn bộ
        return true;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}


// ✅ KIỂM TRA MẬT KHẨU CŨ
public static boolean checkOldPassword(int maAccount, String oldPass) {
    String sql = "SELECT * FROM account WHERE MaAccount=? AND MatKhau=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, maAccount);
        ps.setString(2, oldPass);

        ResultSet rs = ps.executeQuery();
        return rs.next();

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


// ✅ CẬP NHẬT MẬT KHẨU
    public static boolean updateMatKhau(int maAccount, String matKhauMoi) {
        String sql = "UPDATE account SET MatKhau=? WHERE MaAccount=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matKhauMoi);
            ps.setInt(2, maAccount);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

public boolean themAccount(Account acc) {
    String sql = """
        INSERT INTO ACCOUNT(TenDangNhap, MatKhau, Quyen, MaDocGia, MaNV)
        VALUES (?, ?, ?, ?, ?)
    """;
    try {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, acc.getTenDangNhap());
        ps.setString(2, acc.getMatKhau());
        ps.setInt(3, acc.getQuyen());
        ps.setInt(4, acc.getMaDocGia());
        ps.setNull(5, java.sql.Types.INTEGER); // ✅ chưa phải nhân viên

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public boolean updateTenDangNhapByMaDocGia(int maDocGia, String tenDangNhap) {
        String sql = "UPDATE ACCOUNT SET TenDangNhap=? WHERE MaDocGia=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setInt(2, maDocGia);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteByMaDocGia(int maDocGia) {
        String sql = "DELETE FROM ACCOUNT WHERE MaDocGia=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDocGia);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean themAccountNhanVien(String tenDangNhap, int maNV) {
    String sql = "INSERT INTO ACCOUNT(TenDangNhap, MatKhau, Quyen, MaNV) VALUES (?, '1', 2, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, tenDangNhap);
        ps.setInt(2, maNV);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

  public boolean updateTenDangNhapByMaNV(int maNV, String tenDangNhap) {
        String sql = "UPDATE ACCOUNT SET TenDangNhap=? WHERE MaNV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setInt(2, maNV);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
  
   public boolean deleteByMaNV(int maNV) {
        String sql = "DELETE FROM ACCOUNT WHERE MaNV=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNV);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

