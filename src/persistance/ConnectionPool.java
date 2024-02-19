package persistance;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String DB_PATH = Path.of(".", "db", "supermarket.db").toString();
    private static final String URL = "jdbc:h2:D:\\test_cpp\\supermarketdb\\db\\supermarket";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }
}
