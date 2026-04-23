package model;

import java.util.Date;

public class PhieuMuonTra {
    private String hoTen;
    private String tenSach;
    private Date ngayMuon;
    private Date ngayPhaiTra; // chỉ dùng cho đang mượn
    private Date ngayTra;      // chỉ dùng cho đã mượn

    public PhieuMuonTra() { }

    // Constructor cho Đang mượn
    public PhieuMuonTra(String hoTen, String tenSach, Date ngayMuon, Date ngayPhaiTra) {
        this.hoTen = hoTen;
        this.tenSach = tenSach;
        this.ngayMuon = ngayMuon;
        this.ngayPhaiTra = ngayPhaiTra;
    }

    // Constructor cho Đã mượn
    public PhieuMuonTra(String hoTen, String tenSach, Date ngayMuon, Date ngayTra, boolean daMuon) {
        this.hoTen = hoTen;
        this.tenSach = tenSach;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
    }

    // getters và setters
    public String getHoTen() { return hoTen; }
    public String getTenSach() { return tenSach; }
    public Date getNgayMuon() { return ngayMuon; }
    public Date getNgayPhaiTra() { return ngayPhaiTra; }
    public Date getNgayTra() { return ngayTra; }

    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    public void setNgayMuon(Date ngayMuon) { this.ngayMuon = ngayMuon; }
    public void setNgayPhaiTra(Date ngayPhaiTra) { this.ngayPhaiTra = ngayPhaiTra; }
    public void setNgayTra(Date ngayTra) { this.ngayTra = ngayTra; }
}
