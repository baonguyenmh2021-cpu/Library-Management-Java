package model;


import java.util.Date;

public class PhieuTra {
    private int maPhieu;
    private int maDocGia;
    private String hoTen;
    private int maSach;
    private String tenSach;
    private Date ngayTra;

    public PhieuTra() {}

    public PhieuTra(int maPhieu, int maDocGia, String hoTen, int maSach, String tenSach, Date ngayTra) {
        this.maPhieu = maPhieu;
        this.maDocGia = maDocGia;
        this.hoTen = hoTen;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.ngayTra = ngayTra;
    }
     public PhieuTra(int maPhieu, int maDocGia, int maSach, Date ngayTra) {
        this.maPhieu = maPhieu;
        this.maDocGia = maDocGia;
        this.maSach = maSach;
        this.ngayTra = ngayTra;
    }

    public int getMaPhieu() { return maPhieu; }
    public void setMaPhieu(int maPhieu) { this.maPhieu = maPhieu; }

    public int getMaDocGia() { return maDocGia; }
    public void setMaDocGia(int maDocGia) { this.maDocGia = maDocGia; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public int getMaSach() { return maSach; }
    public void setMaSach(int maSach) { this.maSach = maSach; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public Date getNgayTra() { return ngayTra; }
    public void setNgayTra(Date ngayTra) { this.ngayTra = ngayTra; }
}
