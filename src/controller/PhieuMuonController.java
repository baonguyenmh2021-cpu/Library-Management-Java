/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PhieuMuonDAO;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import model.PhieuMuon;
public class PhieuMuonController {

     private PhieuMuonDAO dao = new PhieuMuonDAO();

    public List<PhieuMuon> layDanhSachPhieuMuon() {
        return dao.layTatCaPhieuMuon();
    }
     public boolean themPhieuMuon(int maDocGia, int maSach, Date ngayMuon, Date ngayPhaiTra) {
        return PhieuMuonDAO.insertPhieuMuon(maDocGia, maSach, ngayMuon, ngayPhaiTra);
    }
 public boolean suaPhieuMuon(int maPhieu, int maDocGia, int maSach, Date ngayMuon, Date ngayPhaiTra) {
        return PhieuMuonDAO.updatePhieuMuon(maPhieu, maDocGia, maSach, ngayMuon, ngayPhaiTra);
    }
  public boolean xoaPhieuMuon(int maPM) {
    return dao.xoaPhieuMuon(maPM);
}

}
