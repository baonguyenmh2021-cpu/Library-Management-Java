package database;

import java.sql.Connection;
import database.DBConnection;

public class TestConnect {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println(" KET NOI MYSQL THANH CONG!");
        } else {
            System.out.println(" KET NOI MYSQL THAT BAI!");
        }
    }
}
