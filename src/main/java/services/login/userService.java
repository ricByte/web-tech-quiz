package services.login;

import beans.User;
import beans.login.Session;
import beans.login.loginObject;
import database.DataBaseConnector;
import manager.login.UserManager;
import services.utils.DateParser;
import services.utils.RandomAlphaNum;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class userService {

    private static int MinutesSessionValidity = 120;

    public static int getMinutesSessionValidity() {
        return MinutesSessionValidity;
    }

    public static Session createSession(ResultSet rs) {

        try {

            String sessionNumber = rs.getString("session");
            int userId = rs.getInt("user_ID");
            Timestamp valid_until = rs.getTimestamp("valid_until");

            User user = new User();
            user.setId(userId);

            Session session = new Session();
            session.setSession(sessionNumber);
            session.setUserId(user);
            session.setValidUntil(valid_until);

            return session;

        } catch (Exception e) {

            return null;

        }
    }

    public static User  createUser(ResultSet rs) {

        try {

            String email = rs.getString("email");
            String nickname = rs.getString("nickname");
            int cleverness = rs.getInt("cleverness");
            int typeof = rs.getInt("typeOfPlayer");
            String password = rs.getString("password");

            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNickname(nickname);
            user.setEmail(email);
            user.setCleverness(cleverness);
            user.setTypeOfPlayer(typeof);
            user.setPassword(password);

            return user;

        } catch (Exception e) {

            return null;

        }
    }

    public static String generateSessionNumber() {

        try {

            return RandomAlphaNum.generateId();

        } catch (Exception e) {

            return "";

        }

    }

    public static GregorianCalendar generateValidDate() {

        GregorianCalendar gc = new GregorianCalendar();
        DateParser.addMin(gc, MinutesSessionValidity);
        return gc;

    }

    public static boolean updateLevel(User user) {
        UserManager UserManager = getuserManager();
        return UserManager.updateLevel(user);
    }

    public static User getUserFromSession(String session) {

        UserManager UserManager = getuserManager();

        User user = UserManager.getUserFromSession(session);

        return user;
    }

    private static UserManager getuserManager() {
        DataBaseConnector dbConn = null;

        try {
            dbConn = new DataBaseConnector();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        Connection connection = dbConn.connectToDb();
        return new UserManager(connection);
    }

    public static loginObject autoLogin(String session){

        User user = getUserFromSession(session);

        loginObject loginObject = beans.login.loginObject.createFailedLoginObject();

        if (user != null) {
            UserManager UserManager = getuserManager();
            Session Session = UserManager.getSessionFromUser(user);
            if (Session != null)
                loginObject = loginObject.createSuccessfullLogin(user, Session);
        }

        return loginObject;
    }
}
