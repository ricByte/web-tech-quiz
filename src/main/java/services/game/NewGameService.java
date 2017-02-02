package services.game;

import beans.game.Game;
import beans.game.QuestionPlayed;
import beans.login.Session;
import beans.question.QuestionResponse;
import database.DataBaseConnector;
import manager.game.GameManager;
import model.reader.game.NewGameModel;
import model.response.GameResponse;
import services.login.userAuthenticationService;


public class NewGameService {

    public static GameResponse createNewGame(NewGameModel newGameModel) {

        Game newGame = null;
        QuestionPlayed questionGame = null;

        GameResponse gameResponse = GameResponse.returnErrorGame();

        try {

            DataBaseConnector dbConn = new DataBaseConnector();

            GameManager GameManager = new GameManager(dbConn.connectToDb());

            Session session = userAuthenticationService.verifySession(newGameModel.getSession());

            if (session != null) {
                newGame = GameManager.insertNewGame(session);
                questionGame = GameService.addQuestionToGame(newGame);
                gameResponse = GameResponse.returnCorrectGame(newGame, questionGame);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameResponse;

    }
}
