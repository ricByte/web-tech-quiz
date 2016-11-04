package manager;

import beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private Connection conn;

    public UserManager(Connection conn) {
        this.conn = conn;
    }

    public int login(User user) throws SQLException {

        String sql = "select count(*) as count from user where nickname=? and password=?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getNickname());
        stmt.setString(2, user.getPassword());

        ResultSet rs = stmt.executeQuery();

        int count = 0;

        if (rs.next()) {
            count = rs.getInt("count");
        }

        rs.close();

        return count;
    }

    public void register(User user) throws SQLException {
        String sql = "insert into user (email, password, nickname, cleverness, typeOfPlayer) values (?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getNickname());
        stmt.setInt(4, user.getCleverness());
        stmt.setInt(5, user.getTypeOfPlayer());

        stmt.executeUpdate();

        stmt.close();
    }
}
