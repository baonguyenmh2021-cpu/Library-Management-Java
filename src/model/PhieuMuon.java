package model;

import java.util.Date;

public class PhieuMuon {
    private int maPhieu;
    private int maDocGia;
    private String hoTen;
    private int maSach;
    private String tenSach;
    private Date ngayMuon;
    private Date ngayPhaiTra;
    

    // getter setter đầy đủ nha

    public PhieuMuon() {
    }

    public PhieuMuon(int maPhieu, int maDocGia, String hoTen, int maSach, String tenSach, Date ngayMuon, Date ngayPhaiTra) {
        this.maPhieu = maPhieu;
        this.maDocGia = maDocGia;
        this.hoTen = hoTen;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.ngayMuon = ngayMuon;
        this.ngayPhaiTra = ngayPhaiTra;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public void setMaDocGia(int maDocGia) {
        this.maDocGia = maDocGia;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public void setNgayPhaiTra(Date ngayPhaiTra) {
        this.ngayPhaiTra = ngayPhaiTra;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public int getMaDocGia() {
        return maDocGia;
    }

    public String getHoTen() {
        return hoTen;
    }

    public int getMaSach() {
        return maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public Date getNgayPhaiTra() {
        return ngayPhaiTra;
    }
    
}
