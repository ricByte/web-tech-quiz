package manager.login;

import beans.User;
import beans.login.Session;
import services.login.userService;

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

    public int userExist(User user) {

        String sql = "select count(*) as count from user where nickname=? and password=?";

        try {

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

        } catch (Exception e) {

            return 0;

        }

    }

    public Session getSessionFromUser(User user) throws SQLException {

        String sql = "select s.*  " +
                "from sessions as s " +
                "join user as u " +
                "on u.id = s.user_ID " +
                "where (valid_until > NOW()) " +
                "AND u.nickname = ? " +
                "AND u.password = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getNickname());
        stmt.setString(2, user.getPassword());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            return userService.createSession(rs);

        }

        rs.close();

        return null;
    }

    public static User getUserById(int userId) throws SQLException {
        String sql ="select * " +
                    "from user as u " +
                    "where u.id = ? ";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            return userService.createUser(rs);

        }

        rs.close();

        return null;
    }

    public static User getUserByNickname(User user) throws SQLException {
        String sql ="SELECT * " +
                    "FROM user AS u " +
                    "WHERE u.nickname = ? " +
                    "AND u.password = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getNickname());
        stmt.setString(2, user.getPassword());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            return userService.createUser(rs);

        }

        rs.close();

        return null;
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

    public static Session insertSession(Session sessionToSave) {
        String sql = "insert into sessions (user_ID, session, valid_until) values (?, ?, ?)";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, sessionToSave.getUserId().getId());
            stmt.setString(2, sessionToSave.getSession());
            stmt.setTimestamp(3, new java.sql.Timestamp(sessionToSave.getValidUntil().getTimeInMillis()));
            stmt.executeUpdate();

            stmt.close();

            return sessionToSave;

        } catch (Exception e) {
            return null;
        }

    }
}
