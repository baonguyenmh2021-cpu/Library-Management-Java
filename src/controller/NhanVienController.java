
package controller;

import dao.AccountDAO;
import dao.NhanVienDAO;
import java.util.List;
import model.NhanVien;

public class NhanVienController {

    private NhanVienDAO dao = new NhanVienDAO();

    public NhanVien layThongTin(int maNhanVien) {
        return dao.layThongTin(maNhanVien);
    }

    public boolean capNhatThongTin(NhanVien nv) {
        return dao.capNhatThongTin(nv);
    }
        public List<NhanVien> getAllNhanVien() {
        NhanVienDAO dao = new NhanVienDAO();
        return dao.getAllNhanVien();
    }
       public boolean themNhanVienVaAccount(NhanVien nv) {

        NhanVienDAO nvDAO = new NhanVienDAO();
        AccountDAO accDAO = new AccountDAO();

        // ✅ Thêm nhân viên, lấy mã NV mới
        int maNV = nvDAO.themNhanVienLayMa(nv);
        if (maNV == -1) return false;

        // ✅ Tạo account cho nhân viên (Quyen = 2, mật khẩu = "1")
        return accDAO.themAccountNhanVien(nv.getTenDangNhap(), maNV);
    }
       
    public boolean suaNhanVien(NhanVien nv) {
        NhanVienDAO nvDAO = new NhanVienDAO();
        AccountDAO accDAO = new AccountDAO();

        boolean suaNV = nvDAO.updateNhanVien(nv);

        if (!suaNV) return false;

        // ✅ Cập nhật TenDangNhap trong Account nếu đổi
        accDAO.updateTenDangNhapByMaNV(
            nv.getMaNhanVien(),
            nv.getTenDangNhap()
        );

        return true;
    }
    
       public boolean xoaNhanVienVaAccount(int maNV) {
        AccountDAO accDAO = new AccountDAO();
        NhanVienDAO nvDAO = new NhanVienDAO();

        // ✅ Xóa Account trước (vì có khóa ngoại)
        accDAO.deleteByMaNV(maNV);

        // ✅ Sau đó xóa Nhân Viên
        return nvDAO.deleteNhanVien(maNV);
    }
       
    public List<NhanVien> timKiemNhanVien(String cot, String keyword) {
        return dao.searchNhanVien(cot, keyword);
    }
}
