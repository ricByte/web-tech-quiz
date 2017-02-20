package services.question;


import beans.question.Answer;
import database.DataBaseConnector;
import manager.question.AnswerManager;

import javax.servlet.ServletException;
import java.sql.ResultSet;
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
        AnswerManager.disconnect();

        return returnedAnswer;

    }

    public static Answer[] getAnswers(int QuestionId) {
        Answer[] responsedAnswer = new Answer[0];
        try {

            DataBaseConnector dbConn = new DataBaseConnector();
            AnswerManager AnswerManager = new AnswerManager(dbConn.connectToDb());

            try {

                responsedAnswer = AnswerManager.getAnswersFromQuestionId(QuestionId);

            } catch(Exception e) {

            }

            dbConn.disconnectFromDb();
            AnswerManager.disconnect();

        }catch(Exception e) {

        }

        return responsedAnswer;

    }

    public static Answer[] UpdateAnswers(Answer[] answers) {

        Answer[] answerReturned = new Answer[answers.length];

        try {

            DataBaseConnector dbConn = new DataBaseConnector();
            AnswerManager AnswerManager = new AnswerManager(dbConn.connectToDb());

            int i = 0;

            for (Answer answer : answers) {

                if (AnswerManager.updateAnswer(answer) != null) {

                    answerReturned[i] = answer;

                } else {

                    answerReturned = null;
                    break;

                }

                i++;

            }

            dbConn.disconnectFromDb();
            AnswerManager.disconnect();

        } catch (Exception e) {
            answerReturned = null;
        }

        return answerReturned;

    }

    public static Answer createAnswer(ResultSet rs) {

        try {

            int id = rs.getInt("id");

            String link = rs.getString("link");

            String image = rs.getString("image");

            int num = rs.getInt("num");

            String text = rs.getString("text");

            return new Answer(id ,link, image, text, num);

        }catch(Exception e) {
            return null;
        }

    }

}
