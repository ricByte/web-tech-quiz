package services.game;

import beans.game.Game;
import beans.game.QuestionPlayed;
import beans.login.Session;
import beans.question.Question;
import beans.question.QuestionListResponse;
import com.google.gson.JsonArray;
import database.DataBaseConnector;
import manager.game.GameManager;
import model.reader.game.NewGameModel;
import model.response.game.GameResponse;
import services.login.userAuthenticationService;
import services.question.QuestionService;
import services.utils.DateParser;


public class NewGameService {

    public static GameResponse createNewGame(NewGameModel newGameModel) {

        GameResponse gameResponse = GameResponse.returnErrorGame();

        try {

            DataBaseConnector dbConn = new DataBaseConnector();

            GameManager GameManager = new GameManager(dbConn.connectToDb());

            Session session = userAuthenticationService.verifySession(newGameModel.getSession());

            if ((session != null) && (DateParser.isValidDate(session.getValidUntil()))) {

                Game newGame = GameManager.insertNewGame(session);
                QuestionPlayed questionGame = GameService.addQuestionToGame(newGame);

                JsonArray questionIdArray = new JsonArray();
                questionIdArray.add(questionGame.getQuestionId());

                QuestionListResponse questionList = QuestionService.getFilledQuestion(questionIdArray);
                Question question = questionList.getQuestions()[0];
                gameResponse = GameResponse.returnCorrectGame(newGame, questionGame, question);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameResponse;

    }
}
