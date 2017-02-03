package services.game;

import beans.game.Game;
import beans.game.QuestionPlayed;
import beans.login.Session;
import beans.question.Question;
import beans.question.QuestionListResponse;
import com.google.gson.JsonArray;
import database.DataBaseConnector;
import manager.game.GameManager;
import manager.game.QuestionPlayedManager;
import manager.question.QuestionManager;
import model.reader.game.GameGetModel;
import model.response.GameResponse;
import services.login.userAuthenticationService;
import services.question.QuestionService;
import services.utils.DateParser;
import services.utils.RandomAlphaNum;

import java.sql.ResultSet;
import java.util.ArrayList;
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

    public static GameResponse getGame(GameGetModel gameGetModel) {

        List<String> gameId = new ArrayList<String>();
        String gameSearched = String.valueOf(gameGetModel.getGameId());
        gameId.add(gameSearched);

        GameResponse gameResponse = GameResponse.returnErrorGame();

        Game game = getGame(gameId);

        Session session = userAuthenticationService.verifySession(gameGetModel.getSession());

        if ((session != null) && (DateParser.isValidDate(session.getValidUntil())) ) {

            QuestionPlayed questionGame = GameService.addQuestionToGame(game);

            JsonArray questionIdArray = new JsonArray();
            questionIdArray.add(questionGame.getQuestionId());

            QuestionListResponse questionList = QuestionService.getFilledQuestion(questionIdArray);
            Question question = questionList.getQuestions()[0];

            gameResponse = GameResponse.returnCorrectGame(game, questionGame, question);

        }

        return gameResponse;

    }

    public static Game getGame(List<String> gameList) {

        Game game = null;
        try {

            DataBaseConnector dbConn = new DataBaseConnector();
            GameManager GameManager = new GameManager(dbConn.connectToDb());

            game = GameManager.getGameById(gameList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;

    }
}
