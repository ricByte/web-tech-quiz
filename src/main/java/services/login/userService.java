package services.login;

import beans.User;
import beans.login.Session;
import services.utils.DateParser;
import services.utils.RandomAlphaNum;

import java.sql.ResultSet;
import java.util.Date;
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
            Date valid_until = rs.getDate("valid_until");

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

            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNickname(nickname);
            user.setEmail(email);
            user.setCleverness(cleverness);
            user.setTypeOfPlayer(typeof);

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

    public static Date generateValidDate() {

        return DateParser.addMin(new Date(), MinutesSessionValidity);

    }
}
