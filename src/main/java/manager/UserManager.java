package manager;

import beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private static Connection conn;

    public UserManager(Connection conn) {
        this.conn = conn;
    }

    public  void createUserTable() throws SQLException {
        String query = "CREATE TABLE `user` (\n" +
                "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `password` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `nickname` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
                "  `cleverness` int(1) DEFAULT '0',\n" +
                "  `typeOfPlayer` int(1) DEFAULT '0',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";

        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();

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
        stmt.setInt(4, 1);
        stmt.setInt(5, user.getTypeOfPlayer());

        stmt.executeUpdate();

        stmt.close();
    }

    public Boolean checkIfAlreadyRegister(User user) throws SQLException {
        String sql = "select count(*) as count from user where email=?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getEmail());

        ResultSet rs = stmt.executeQuery();

        int count = 0;
        Boolean alreadyRegister = false;

        if (rs.next()) {
            count = rs.getInt("count");
            if(count > 0) alreadyRegister = true;
        }

        rs.close();

        return alreadyRegister;
    }

    public static User getUserFromNickname(String nickname, String password) throws SQLException {
        String sql = "select * from user where nickname=? and password=?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, nickname);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        User users = new User();

        if (rs.next()) {
            String email = rs.getString("email");
            int cleverness = rs.getInt("cleverness");
            int typeOfPlayer = rs.getInt("typeOfPlayer");
            users = new User(email, nickname, null, cleverness, typeOfPlayer);
            return users;
        }

        rs.close();

        return null;
    }
}
