/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import dao.AccountDAO;

public class RegisterController {

    public String register(String user, String pass1, String pass2) {

        if (user.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
            return "Không được để trống!";
        }

        if (!pass1.equals(pass2)) {
            return "Mật khẩu không khớp!";
        }

        if (AccountDAO.checkUsernameExists(user)) {
            return "Tên đăng nhập đã tồn tại!";
        }

        boolean ok = AccountDAO.register(user, pass1);
        return ok ? "Đăng ký thành công!" : "Đăng ký thất bại!";
    }
}
