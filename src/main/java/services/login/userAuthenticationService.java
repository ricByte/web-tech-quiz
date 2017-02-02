package services.login;

import beans.User;
import beans.login.Session;
import beans.login.loginObject;
import database.DataBaseConnector;
import manager.login.UserManager;

import javax.servlet.ServletException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class userAuthenticationService {

    private static String getMd5(String input) {
        String md5 = null;

        if (null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static loginObject authLogin(String email, String password)  {
        String md5Password = getMd5(password);

        try {
            DataBaseConnector dbConn = new DataBaseConnector();
            UserManager userManager = new UserManager(dbConn.connectToDb());

            User user = new User(email, md5Password);
            int userCount = userManager.userExist(user);

            if (userCount > 0) {

                Session sessionSaved = userManager.getSessionFromUser(user);
                User savedUser;

                //check if has session valid
                if (sessionSaved != null) {

                    savedUser = userManager.getUserById(sessionSaved.getUserId().getId());

                } else {

                    savedUser = userManager.getUserByNickname(user);
                    sessionSaved = new Session();
                    sessionSaved.setUserId(savedUser);
                    sessionSaved.setSession(userService.generateSessionNumber());
                    sessionSaved.setValidUntil(userService.generateValidDate());
                    if (userManager.insertSession(sessionSaved) == null)
                        return loginObject.createFailedLoginObject();

                }

                return loginObject.createSuccessfullLogin(savedUser, sessionSaved);
            }

            dbConn.disconnectFromDb();

            return loginObject.createFailedLoginObject();

        } catch(Exception e) {
            return null;
        }

    }

    public static User registerUser(String email, String password, String nickname, int cleverness, int typeOfPlayer) throws ServletException, SQLException {
        String md5Password = getMd5(password);

        DataBaseConnector dbConn = new DataBaseConnector();
        UserManager userManager = new UserManager(dbConn.connectToDb());

        User user = new User(email, nickname, md5Password, cleverness, typeOfPlayer);
        if(!userManager.checkIfAlreadyRegister(user)) {
            userManager.register(user);
            return user;
        }
        dbConn.disconnectFromDb();

        return  null;
    }

    public static Boolean verifyEmail(String email) {
        return true;
    }

    public static Session verifySession(String sessionSlug) {
        Session session = null;

        if (sessionSlug != null) {
            DataBaseConnector dbConn = null;

            try {

                dbConn = new DataBaseConnector();
                UserManager userManager = new UserManager(dbConn.connectToDb());

                session = userManager.getSessionFromSession(sessionSlug);

            } catch (ServletException e) {
                e.printStackTrace();
            }

        }

        return session;
    }
}
