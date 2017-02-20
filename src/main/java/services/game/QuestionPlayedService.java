package services.game;

import beans.game.QuestionPlayed;
import database.DataBaseConnector;
import manager.game.QuestionPlayedManager;
import manager.question.QuestionManager;

import javax.servlet.ServletException;
import java.sql.Connection;


public class QuestionPlayedService {

    public static boolean updateQuestionPlayed(QuestionPlayed questionPlayed) {

        QuestionPlayedManager QuestionPlayedManager = getQuestionPlayedManager();

        boolean updateQuestionPlayed = QuestionPlayedManager.updateQuestionPlayed(questionPlayed);

        QuestionPlayedManager.disconnect();

        return updateQuestionPlayed;

    }

    private static QuestionPlayedManager getQuestionPlayedManager() {
        DataBaseConnector dbConn = null;

        try {
            dbConn = new DataBaseConnector();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        Connection connection = dbConn.connectToDb();
        return new QuestionPlayedManager(connection);
    }

}
