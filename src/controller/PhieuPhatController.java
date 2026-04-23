package controller;

import dao.PhieuPhatDAO;
import java.util.Date;
import model.PhieuPhat;

import java.util.List;
import model.PhieuMuon;

import java.util.List;

public class PhieuPhatController {
    public List<PhieuPhat> getDanhSach() {
        return PhieuPhatDAO.getAll();
    }
      public boolean taoPhieuPhat(int maPhieu, int maDocGia, String lyDo, double soTien, java.sql.Date ngayLap) {
        return PhieuPhatDAO.insertPhieuPhat(maPhieu, maDocGia, lyDo, soTien, ngayLap);
    }
       public boolean xoaMemPhieuPhat(int maPhieuPhat) {
        return PhieuPhatDAO.xoaMemPhieuPhat(maPhieuPhat);
    }
    public boolean suaPhieuPhat(PhieuPhat p) {
    return PhieuPhatDAO.updatePhieuPhat(p);
}
public boolean thanhToan(int maPhieuPhat) {
    return PhieuPhatDAO.thanhToanPhieuPhat(maPhieuPhat);
}

public List<PhieuPhat> timKiem(String cot, String keyword) {
    return PhieuPhatDAO.search(cot, keyword);
}

}