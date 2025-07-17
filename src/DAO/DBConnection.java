package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection openConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("Dang tai driver...");

        String url1 = "jdbc:sqlserver://localhost:1433;databaseName=quan_ly_sieu_thi;encrypt=true;trustServerCertificate=true;user=sa;password=123";
        String url2 = "jdbc:sqlserver://localhost:1433;databaseName=quan_ly_sieu_thi;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

        Connection con = null;

        try {
            System.out.println("Thu ket noi voi URL 1 (user/pass)...");
            con = DriverManager.getConnection(url1);
            System.out.println("Ket noi thanh cong voi URL 1");
        } catch (SQLException ex) {
            System.err.println("Ket noi URL1 that bai: " + ex.getMessage());
            System.out.println("Thu lai voi URL2 (integratedSecurity)...");
            try {
                con = DriverManager.getConnection(url2);
                System.out.println("Ket noi thanh cong voi URL2");
            } catch (SQLException ex2) {
                System.err.println("Ket noi URL2 that bai: " + ex2.getMessage());
                throw ex2;
            }
        }

        return con;
    }
}
