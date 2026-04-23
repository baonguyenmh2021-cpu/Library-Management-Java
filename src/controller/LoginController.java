package controller;

import dao.AccountDAO;
import model.Account;
import model.Session;

public class LoginController {

    public Account login(String user, String pass) {
        Account acc = AccountDAO.login(user, pass);

        if (acc != null) {
            Session.currentAccount = acc; // ✅ LƯU SESSION
            System.out.println("PASS SAU LOGIN: " + acc.getMatKhau());
        }

        return acc;
    }
}
