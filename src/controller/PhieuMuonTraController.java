package controller;

import dao.PhieuMuonTraDAO;
import model.PhieuMuonTra;
import java.util.List;

public class PhieuMuonTraController {
    private PhieuMuonTraDAO dao = new PhieuMuonTraDAO();

    public List<PhieuMuonTra> getDangMuon() {
        return dao.getDangMuon();
    }

    public List<PhieuMuonTra> getDaMuon() {
        return dao.getDaMuon();
    }
    public List<PhieuMuonTra> timKiemDangMuon(String cot, String keyword) {
    return dao.timKiemDangMuon(cot, keyword);
}

public List<PhieuMuonTra> timKiemDaMuon(String cot, String keyword) {
    return dao.timKiemDaMuon(cot, keyword);
}

}
