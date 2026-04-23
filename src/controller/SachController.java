/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.SachDAO;
import java.util.List;
import model.Sach;


public class SachController {
    //Doc Gia Tim kiem
     public List<Sach> loadAll() {
        return SachDAO.getAllSach();
    }

    public List<Sach> timKiem(String cot, String keyword) {
        return SachDAO.searchSach(cot, keyword);
    }

public List<Sach> laySachDangMuon(int maDocGia, String tenSach) {
    return SachDAO.getSachDangMuon(maDocGia, tenSach);
}
public List<Sach> timLichSuTheoTenSach(int maDocGia, String keyword) {
    return SachDAO.getLichSuMuonTraTheoTenSach(maDocGia, keyword);
}

 public List<Sach> getAllSach1() {
        SachDAO dao = new SachDAO();
        return dao.getAllSach1();
    }

 public boolean themSach(Sach s) {
        SachDAO dao = new SachDAO();
        return dao.themSach(s);
    }
     public boolean suaSach(Sach s) {
        SachDAO dao = new SachDAO();
        return dao.updateSach(s);
    }
     
      public boolean xoaSach(int maSach) {
        SachDAO dao = new SachDAO();
        return dao.deleteSach(maSach);
    }
      
       public List<Sach> timKiemSach(String cot, String keyword) {
    SachDAO dao = new SachDAO();
    return dao.searchSach1(cot, keyword);
}
}

