package manager;

import java.sql.*;

public class BaseManager {
    private static Connection conn;

    public static Connection getConn() {
        return conn;
    }

    public BaseManager(Connection connection) {
        this.conn = connection;
    }
}
