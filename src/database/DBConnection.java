package database;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/quanlythuvien";
            String user = "root";
            String pass = "nguyen";

      //      Class.forName("com.mysql.cj.jdbc.Driver"); // bắt buộc với MySQL

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
