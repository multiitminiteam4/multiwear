package config;
import java.sql.*;

public class OracleSetting {
    private static final String URL = "jdbc:oracle:thin:@//210.94.179.19:9498/xe";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "oracle";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}