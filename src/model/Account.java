package model;

public class Account {
    private int maAccount; // MaAccount
    private String tenDangNhap; // TenDangNhap
    private String matKhau;
    private int quyen; // Quyen
    private Integer maDocGia;
    private Integer maNV;

    public Account(int maAccount, String tenDangNhap, String matKhau, int quyen, Integer maDocGia, Integer maNV) {
        this.maAccount = maAccount;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.quyen = quyen;
        this.maDocGia = maDocGia;
        this.maNV = maNV;
    }

    public Account(String tenDangNhap, String matKhau, int quyen, Integer maDocGia, Integer maNV) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.quyen = quyen;
        this.maDocGia = maDocGia;
        this.maNV = maNV;
    }

    
    public Account() {
    }
    
    
    public int getMaAccount() { return maAccount; }

    public String getMatKhau() {
        return matKhau;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setMaAccount(int maAccount) {
        this.maAccount = maAccount;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public Integer getMaDocGia() {
        return maDocGia;
    }

    public Integer getMaNV() {
        return maNV;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    public void setMaDocGia(Integer maDocGia) {
        this.maDocGia = maDocGia;
    }

    public void setMaNV(Integer maNV) {
        this.maNV = maNV;
    }


    

}
