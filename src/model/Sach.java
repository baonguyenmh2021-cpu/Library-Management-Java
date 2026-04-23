package model;


public class Sach {
    private int maSach;
    private String tenSach;
    private String tacGia;
    private String theLoai;
    private String giaSach;
    private String soLuong;
    private String tinhTrang;
    private String nhaXuatBan;

    public Sach(int maSach, String tenSach, String tacGia, String theLoai, String nhaXuatBan,String giaSach, String soLuong, String tinhTrang) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.nhaXuatBan = nhaXuatBan;
        this.giaSach = giaSach;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;

    }
    
    
    public Sach() {
    }


    public int getMaSach() {
        return maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public String getGiaSach() {
        return giaSach;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public void setGiaSach(String giaSach) {
        this.giaSach = giaSach;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

}