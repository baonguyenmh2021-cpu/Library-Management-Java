
package controller;

import dao.AccountDAO;
import model.Account;
import model.Session;

import javax.swing.*;

public class DoiMatKhauController {

    public boolean doiMatKhau(String matKhauCu, String matKhauMoi) {

        Account acc = Session.currentAccount;

        if (acc == null) {
            JOptionPane.showMessageDialog(null, "Bạn chưa đăng nhập!");
            return false;
        }

        if (acc.getMatKhau() == null) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi hệ thống: Session không có mật khẩu!\nVui lòng đăng nhập lại.");
            return false;
        }

        // ✅ SO SÁNH ĐÚNG – KHÔNG BAO GIỜ BỊ NULL
        if (!matKhauCu.equals(acc.getMatKhau())) {
            JOptionPane.showMessageDialog(null, "Mật khẩu cũ sai!");
            return false;
        }

        boolean kq = AccountDAO.updateMatKhau(acc.getMaAccount(), matKhauMoi);

        if (kq) {
            acc.setMatKhau(matKhauMoi); // ✅ cập nhật lại session
            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại!");
        }

        return kq;
    }
}
