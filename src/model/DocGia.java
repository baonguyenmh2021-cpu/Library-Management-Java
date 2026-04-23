package model;

import java.util.Date;

public class DocGia {
    private int maDocGia;
    private String tenDangNhap;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String diaChi;

    public DocGia() {
    }

    public DocGia(int maDocGia, String tenDangNhap, String hoTen, String gioiTinh, Date ngaySinh, String diaChi) {
        this.maDocGia = maDocGia;
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
    }

    public int getMaDocGia() {
        return maDocGia;
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

    public void setMaDocGia(int maDocGia) {
        this.maDocGia = maDocGia;
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

