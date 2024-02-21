package persistance;

import exception.BlockingQueueTakeException;
import exception.ConnectionException;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final String DB_PATH = Path.of(".", "db", "supermarket").toString();
    private static final String URL = "jdbc:h2:file:%s".formatted(DB_PATH);
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

}
