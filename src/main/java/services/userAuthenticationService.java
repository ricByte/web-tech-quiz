package services;

import beans.User;
import database.DataBaseConnector;
import manager.UserManager;

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

    public static Boolean authLogin(String email, String password) throws ServletException, SQLException {
        String md5Password = getMd5(password);

        DataBaseConnector dbConn = new DataBaseConnector();
        UserManager userManager = new UserManager(dbConn.connectToDb());

        User user = new User(email, md5Password);
        int userCount = userManager.login(user);

        if (userCount > 0)
            return true;

        return false;
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

        return  null;
    }

    public static Boolean verifyEmail(String email) {
        return true;
    }
}
