package connectSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DB_ServerName = "LAPTOP-32K4MEK9\\SQLEXPRESS";
    private static final String DB_Login = "sa";
    private static final String DB_password ="12345";
    private static final String DB_databaseName = "QuanLyThiTotNghiepTHPT";
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String DB_URL = "jdbc:sqlserver://"+ DB_ServerName + ":1433"+";databaseName=" + DB_databaseName +
            "; encrypt = true; trustServerCertificate = true";
            return DriverManager.getConnection(DB_URL, DB_Login, DB_password);
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            e.printStackTrace();
        }
        return null;
    }
    
}
