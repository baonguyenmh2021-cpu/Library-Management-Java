package controller;

import dao.AccountDAO;
import dao.DocGiaDAO;
import model.DocGia;
import java.util.List;
import model.Account;


public class DocGiaController {
    private DocGiaDAO dao = new DocGiaDAO();

    public DocGia layThongTin(int maDocGia) {
        return dao.layThongTin(maDocGia);
    }

    public boolean capNhatThongTin(DocGia dg) {
        return dao.capNhatThongTin(dg);
    }
    

public List<DocGia> getAllDocGia() {
        return dao.getAll();
    }

public boolean themDocGiaVaAccount(DocGia dg) {
    DocGiaDAO docGiaDAO = new DocGiaDAO();
    AccountDAO accountDAO = new AccountDAO();

    // 1. Thêm docgia → lấy mã
    int maDocGia = docGiaDAO.themDocGiaLayMa(dg);
    if (maDocGia == -1) return false;

    // 2. Tạo account
    Account acc = new Account(
        dg.getTenDangNhap(),
        "1",       // ✅ mật khẩu mặc định
        1,         // ✅ quyền độc giả
        maDocGia,
        null
    );

    // 3. Thêm account
    return accountDAO.themAccount(acc);
}

public boolean suaDocGia(DocGia dg) {
        DocGiaDAO docGiaDAO = new DocGiaDAO();
        AccountDAO accountDAO = new AccountDAO();

        boolean suaDG = docGiaDAO.updateDocGia(dg);

        if (!suaDG) return false;

        // ✅ CẬP NHẬT LẠI TÊN ĐĂNG NHẬP TRONG ACCOUNT
        accountDAO.updateTenDangNhapByMaDocGia(
            dg.getMaDocGia(),
            dg.getTenDangNhap()
        );

        return true;
    }
 public boolean xoaDocGiaVaAccount(int maDocGia) {
        AccountDAO accountDAO = new AccountDAO();
        DocGiaDAO docGiaDAO = new DocGiaDAO();

        // ✅ Xóa Account trước (vì có khóa ngoại)
        accountDAO.deleteByMaDocGia(maDocGia);

        // ✅ Sau đó xóa DocGia
        return docGiaDAO.deleteDocGia(maDocGia);
    }
 
 
 public List<DocGia> timKiemDocGia(String cot, String keyword) {
    return dao.searchDocGia(cot, keyword);
}

}
