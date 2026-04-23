-- 1. Xóa database cũ để tạo lại từ đầu cho sạch
DROP DATABASE IF EXISTS QuanLyThuVien;

-- 2. Tạo database mới
CREATE DATABASE QuanLyThuVien CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE QuanLyThuVien;

-- 3. Bảng ĐỘC GIẢ
CREATE TABLE DOCGIA (
    MaDocGia INT PRIMARY KEY AUTO_INCREMENT,
    TenDangNhap VARCHAR(50),
    HoTen NVARCHAR(100),
    GioiTinh NVARCHAR(10),
    NgaySinh DATE,
    DiaChi NVARCHAR(200)
);

-- 4. Bảng NHÂN VIÊN
CREATE TABLE NHANVIEN (
    MaNV INT PRIMARY KEY AUTO_INCREMENT,
    TenDangNhap VARCHAR(50),
    HoTen NVARCHAR(100),
    GioiTinh NVARCHAR(10),
    NgaySinh DATE,
    DiaChi NVARCHAR(200),
    ChucVu NVARCHAR(50)
);

-- 5. Bảng SÁCH
CREATE TABLE SACH (
    MaSach INT PRIMARY KEY AUTO_INCREMENT,
    TenSach NVARCHAR(200),
    TacGia NVARCHAR(100),
    TheLoai NVARCHAR(100),
    NhaXuatBan NVARCHAR(100),
    GiaSach DOUBLE,
    SoLuong INT,
    TinhTrang NVARCHAR(50)
);

-- 6. Bảng PHIẾU MƯỢN
CREATE TABLE PHIEUMUON (
    MaPhieu INT PRIMARY KEY AUTO_INCREMENT,
    MaDocGia INT,
    MaSach INT,
    NgayMuon DATE,
    NgayPhaiTra DATE,
    FOREIGN KEY (MaDocGia) REFERENCES DOCGIA(MaDocGia),
    FOREIGN KEY (MaSach) REFERENCES SACH(MaSach)
);

-- 7. Bảng PHIẾU TRẢ
CREATE TABLE PHIEUTRA (
    MaPhieu INT PRIMARY KEY AUTO_INCREMENT,
    MaDocGia INT,
    MaSach INT,
    NgayTra DATE,
    FOREIGN KEY (MaDocGia) REFERENCES DOCGIA(MaDocGia),
    FOREIGN KEY (MaSach) REFERENCES SACH(MaSach)
);

-- 8. Bảng ACCOUNT
CREATE TABLE ACCOUNT (
    MaAccount INT PRIMARY KEY AUTO_INCREMENT,
    TenDangNhap VARCHAR(50),
    MatKhau VARCHAR(100),
    Quyen INT, -- 1 = DOCGIA, 0 = ADMIN, 2 = NHANVIEN
    MaDocGia INT,
    MaNV INT,
    FOREIGN KEY (MaDocGia) REFERENCES DOCGIA(MaDocGia),
    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
);

-- 9. Bảng PHIẾU PHẠT (ĐÃ CẬP NHẬT TRẠNG THÁI)
CREATE TABLE PHIEUPHAT (
    MaPhieuPhat INT PRIMARY KEY AUTO_INCREMENT,
    MaPhieu INT,       -- Link tới phiếu mượn gốc
    MaDocGia INT,      -- Người bị phạt
    LyDo NVARCHAR(200),
    SoTien DOUBLE,
    NgayLap DATE,
    -- CỘT QUAN TRỌNG: 0 = Chưa đóng (Nợ), 1 = Đã đóng, -1 = Đã hủy (Xóa mềm)
    TrangThai INT DEFAULT 0, 
    FOREIGN KEY (MaPhieu) REFERENCES PHIEUMUON(MaPhieu),
    FOREIGN KEY (MaDocGia) REFERENCES DOCGIA(MaDocGia)
);


-- ************ CHÈN DỮ LIỆU MẪU ************

-- Dữ liệu DOCGIA
INSERT INTO DOCGIA VALUES
(1,'phamhaingoc1','Phạm Ngọc Hải','Nam','1996-01-01','510 Lý Thái Tổ'),
(2,'phamhaingoc2','Phạm Hải Ngọc','Nam','1994-05-15','20 Lý Thái Tổ'),
(3,'phamminhhoang','Phạm Minh Hoàng','Nam','1994-11-20','20 Lý Thái Tổ'),
(7,'nguoithanhhai','Nguyễn Thanh Hải','Nữ','1994-08-01','201 Lý Thái Tổ'),
(15,'nguyenhuuhoanghieu','Nguyễn Hữu Hoàng Hiếu','Nữ','1994-03-25','120 Lý Thái Tổ');

-- Dữ liệu NHANVIEN
INSERT INTO NHANVIEN VALUES
(999,'admin','Admin','Nam','1990-10-10','HCM','Quản trị'),
(4,'thanhhang','Trần Thanh Hằng','Nữ','2006-02-05','HCM','Thủ Thư');

-- Dữ liệu ACCOUNT
INSERT INTO ACCOUNT VALUES 
(15,'nguyenhuuhoanghieu','123456',1,15,NULL),
(1,'phamhaingoc1','123456',1,1,NULL),
(2,'phamhaingoc2','123456',1,2,NULL),
(3,'phamminhhoang','123456',1,3,NULL),
(7,'nguyenthanhhai','123456',1,7,NULL),
(4,'thanhhang','123',2,NULL,4),
(999,'admin','1',0,NULL,999);

-- Dữ liệu SÁCH
INSERT INTO SACH VALUES
(1,'Lập trình hướng đối tượng','Không biết','Lập trình','HCMUS',1500,3,'Còn'),
(2,'Nhập môn lập trình','Không biết','Lập trình','HCMUS',1500,10,'Còn'),
(3,'Kỹ Thuật Lập Trình','Không biết','Lập trình','HCMUS',3000,5,'Còn'),
(4,'Thiết Kế Phần Mềm','Không biết','Lập trình','HCMUS',4000,0,'Hết');

-- Dữ liệu PHIẾU MƯỢN
INSERT INTO PHIEUMUON VALUES
(1,1,1,'2017-01-01','2017-02-01'), 
(4,1,3,'2018-02-03','2018-03-02'),
(2,2,1,'2017-02-01','2017-03-01'),
(3,2,2,'2017-03-01','2017-04-01');

-- Dữ liệu PHIẾU TRẢ
INSERT INTO PHIEUTRA VALUES
(1,1,2,'2016-01-01'),
(2,2,1,'2016-02-01'),
(3,2,2,'2016-03-01');

-- Dữ liệu PHIẾU PHẠT (CÓ TRẠNG THÁI)
INSERT INTO PHIEUPHAT (MaPhieu, MaDocGia, LyDo, SoTien, NgayLap, TrangThai) VALUES
(1, 1, 'Làm rách bìa sách', 20000, '2017-02-01', 1),       -- 1: Đã đóng tiền (Xong)
(2, 2, 'Trả sách quá hạn 5 ngày', 10000, '2017-03-06', 0),  -- 0: Đang nợ (Chưa đóng)
(4, 1, 'Ghi nhầm phiếu', 50000, '2018-03-05', -1);          -- -1: Đã hủy (Xóa mềm)