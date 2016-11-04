package database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DataBaseConnector {

    private DataSource ds;
    private Connection conn;

    public DataBaseConnector() throws ServletException {

        try {
            InitialContext initContext = new InitialContext();

            Context env = (Context) initContext.lookup("java:comp/env");

            this.ds = (DataSource) env.lookup("jdbc/webTech");

        } catch (NamingException e) {
            throw new ServletException();
        }

    }

    public Connection connectToDb() {

        try {
            this.conn = this.ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.conn;

    }

    public void disconnectFromDb() {

        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
