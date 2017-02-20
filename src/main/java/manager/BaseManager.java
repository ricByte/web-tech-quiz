package manager;

import java.sql.*;

public class BaseManager {
    private static Connection conn;

    public static Connection getConn() {
        return conn;
    }

    public void disconnect() {

        try {
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public BaseManager(Connection connection) {
        this.conn = connection;
    }
}
