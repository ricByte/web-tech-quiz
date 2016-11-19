package services.question;


import beans.question.Answer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.DataBaseConnector;
import manager.question.AnswerManager;
import manager.question.QuestionManager;

import javax.servlet.ServletException;
import java.sql.SQLException;

public class AnswerService {

    public static int[] SaveAnswers(Answer[] answers, int QuestionId) throws SQLException, ServletException {

        int[] returnedAnswer = new int[answers.length];

        DataBaseConnector dbConn = new DataBaseConnector();
        AnswerManager AnswerManager = new AnswerManager(dbConn.connectToDb());

        for (int i = 0; i < answers.length; i++) {

            Answer answer = answers[i];

            returnedAnswer[answer.getNum()-1] = AnswerManager.registerAnswer(answer, QuestionId);

        }

        dbConn.disconnectFromDb();

        return returnedAnswer;

    }

}
