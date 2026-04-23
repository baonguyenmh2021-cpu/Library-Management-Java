package model;

import java.util.Date;

public class NhanVien {
    private int maNhanVien;
    private String tenDangNhap;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String diaChi;

    public NhanVien() {
    }

    public NhanVien(int maNhanVien, String tenDangNhap, String hoTen, String gioiTinh, Date ngaySinh, String diaChi) {
        this.maNhanVien = maNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    
}
