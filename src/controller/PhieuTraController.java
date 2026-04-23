/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PhieuTraDAO;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

public class PhieuTraController {

     private PhieuTraDAO dao;

    public PhieuTraController() {
        dao = new PhieuTraDAO();
    }

    // Lấy toàn bộ phiếu trả để load bảng
    public ResultSet loadPhieuTra() {
        return dao.getAllPhieuTra();
    }

    // Tìm phiếu mượn chưa trả (để xác định MaPhieuMuon)
     public boolean themPhieuTra(int maDocGia, int maSach, Date ngayTra) {
          if (PhieuTraDAO.daTraSach(maDocGia, maSach)) {
        JOptionPane.showMessageDialog(null,
            "Sách này đã được trả rồi, không thể trả lại lần nữa!");
        return false;
    }
        return PhieuTraDAO.insertPhieuTra(maDocGia, maSach, ngayTra);
    }
       public boolean suaPhieuTra(int maPhieuTra, int maDocGia, int maSach, Date ngayTra) {
        return PhieuTraDAO.updatePhieuTra(maPhieuTra, maDocGia, maSach, ngayTra);
    }
        public boolean xoaPhieuTra(int maPhieuTra) {
        return PhieuTraDAO.deletePhieuTra(maPhieuTra);
    }

}

