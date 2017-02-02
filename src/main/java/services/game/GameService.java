package services.game;

import beans.game.Game;
import beans.game.QuestionPlayed;
import database.DataBaseConnector;
import manager.game.QuestionPlayedManager;
import manager.question.QuestionManager;
import services.utils.RandomAlphaNum;

import java.sql.ResultSet;
import java.util.List;

import static manager.game.QuestionPlayedManager.createNewQuestionForGame;
import static services.utils.RandomAlphaNum.generateNumber;

public class GameService {
    public static Game createGame(ResultSet rs) {

        Game game = null;

        try {

            String sessionNumber = rs.getString("session_NUMBER");
            int points = rs.getInt("points");
            int id = rs.getInt("id");

            game = new Game();
            game.setId(id);
            game.setPoints(points);
            game.setSessionNumber(sessionNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;
    }

    public static QuestionPlayed addQuestionToGame(Game game) {

        QuestionPlayed questionForGame = null;

        try {

            DataBaseConnector dbConn = new DataBaseConnector();

            QuestionManager QuestionManager = new QuestionManager(dbConn.connectToDb());
            QuestionPlayedManager QuestionPlayedManager = new QuestionPlayedManager(dbConn.connectToDb());

            List<Integer> questionExtremis = QuestionManager.getMaxMinQuestionId();

            if (questionExtremis != null) {

                while (questionForGame == null) {
                    int questionId = generateNumber(questionExtremis.get(0), questionExtremis.get(1));
                    questionForGame = createNewQuestionForGame(game, questionId);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionForGame;

    }
}
