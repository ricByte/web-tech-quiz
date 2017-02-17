package services.game;

import beans.User;
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
import model.reader.game.GameUpdateModel;
import model.response.game.GameResponse;
import model.response.game.GameUpdateResponse;
import services.login.userAuthenticationService;
import services.login.userService;
import services.question.QuestionService;
import services.utils.DateParser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static manager.game.QuestionPlayedManager.createNewQuestionForGame;
import static services.utils.RandomAlphaNum.generateNumber;

public class GameService {

    public static final int RIGHT_ANSER_VALUE = 100;
    public static final int CONSECUTIVE_RIGHT_ANSWERS = 3;
    public static final int CONSECUTIVE_WRONG_ANSWERS = 5;

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

            dbConn.disconnectFromDb();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionForGame;

    }

    public static GameResponse getGame(GameGetModel gameGetModel) {

        GameResponse gameResponse = GameResponse.returnErrorGame();

        Game game = getGameObjectInit(gameGetModel.getGameId());

        if (isVerifySession(gameGetModel.getSession())) {

            QuestionPlayed questionGame = GameService.addQuestionToGame(game);

            Question question = getQuestionByQuestionPlayed(questionGame);

            gameResponse = GameResponse.returnCorrectGame(game, questionGame, question);

        }

        return gameResponse;

    }

    public static Game getGameById(List<String> gameList) {

        Game game = null;
        try {

            DataBaseConnector dbConn = new DataBaseConnector();
            GameManager GameManager = new GameManager(dbConn.connectToDb());

            game = GameManager.getGameById(gameList);

            dbConn.disconnectFromDb();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;

    }

    public static GameUpdateResponse updateGame(GameUpdateModel gameUpdateModel) {

        GameUpdateResponse gameUpdateResponse = GameUpdateResponse.returnErrorGame();

        Game game = getGameObjectInit(gameUpdateModel.getGameId());

        if ( isVerifySession(gameUpdateModel.getSession())) {

            QuestionPlayed questionPlayed = getQuestionPlayedByNumber(gameUpdateModel.getQuestionNumber(), gameUpdateModel.getGameId());

            Question question = getQuestionByQuestionPlayed(questionPlayed);

            boolean responseRight = checkPlayerResponse(gameUpdateModel.getPlayerAnswer(), question);

            questionPlayed.setPlayerAnswer(gameUpdateModel.getPlayerAnswer());

            User user = userService.getUserFromSession(gameUpdateModel.getSession());

            if (QuestionPlayedService.updateQuestionPlayed(questionPlayed)) {
                int pointsForQuestion = 0;

                if (responseRight) {

                    pointsForQuestion = game.getPoints() + calculatePoints(question);
                    game.setPoints(pointsForQuestion);

                    if (isToUpdateLevel(questionPlayed)) {
                        userService.updateLevel(user);
                    }

                    gameUpdateResponse = GameUpdateResponse.getCorrectResponse(user, game);

                } else {
                    gameUpdateResponse = GameUpdateResponse.getFailedResponse(user, game);
                }
            }



        }

        return gameUpdateResponse;
    }

    private static boolean isToUpdateLevel(QuestionPlayed questionPlayed) {

        boolean isToUpdateLevel = false;

        try {

            DataBaseConnector dbConn = new DataBaseConnector();
            Connection connection = dbConn.connectToDb();
            QuestionPlayedManager QuestionPlayedManager = new QuestionPlayedManager(connection);

            List<QuestionPlayed> questionPlayedList = QuestionPlayedManager.getQuestionPlayedListOfRightAnswerByGameId(questionPlayed.getQuestionNumber(), questionPlayed.getGameId());

            if (questionPlayedList != null) {

                int number = scanQuestionPlayed(questionPlayedList, CONSECUTIVE_RIGHT_ANSWERS);
                int moduleConsecutiveRight = number % CONSECUTIVE_RIGHT_ANSWERS;

                if (moduleConsecutiveRight == 0)
                    isToUpdateLevel = true;
            }

            connection.close();
            dbConn.disconnectFromDb();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isToUpdateLevel;
    }

    private static int scanQuestionPlayed(List<QuestionPlayed> questionPlayedList, int numberOfResponse) {

        int numberOfResponseTrue = 0;
        int questionNumber = 1;

        for (int i = 0; i < questionPlayedList.size(); i++) {

            if (questionPlayedList.get(i).getQuestionNumber() == questionNumber) {
                if (numberOfResponseTrue == numberOfResponse) {
                    numberOfResponseTrue = 0;
                }
                numberOfResponseTrue++;
            } else {
                numberOfResponseTrue = 1;
                questionNumber = questionPlayedList.get(i).getQuestionNumber();
            }

            questionNumber++;

        }

        return numberOfResponseTrue;
    }

    private static int calculatePoints(Question question) {

        int difficulty = Integer.parseInt(question.getDifficulty());
        return difficulty * RIGHT_ANSER_VALUE;

    }

    private static boolean checkPlayerResponse(int playerAnswer, Question question) {

        return playerAnswer == question.getSolution();

    }

    public static Question getQuestionByQuestionPlayed(QuestionPlayed questionPlayed) {

        JsonArray questionIdArray = new JsonArray();
        questionIdArray.add(questionPlayed.getQuestionId());

        QuestionListResponse questionList = QuestionService.getFilledQuestion(questionIdArray);
        return questionList.getQuestions()[0];

    }

    private static QuestionPlayed getQuestionPlayedByNumber(int questionNumber, int gameId) {

        QuestionPlayed questionPlayed = null;

        try {

            DataBaseConnector dbConn = new DataBaseConnector();
            QuestionPlayedManager QuestionPlayedManager = new QuestionPlayedManager(dbConn.connectToDb());

            questionPlayed = QuestionPlayedManager.getQuestionPlayedByNumber(questionNumber, gameId);

            dbConn.disconnectFromDb();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionPlayed;

    }

    private static boolean isVerifySession(String sessionString) {
        Session session = userAuthenticationService.verifySession(sessionString);

        return (session != null) && (DateParser.isValidDate(session.getValidUntil()));
    }

    private static Game getGameObjectInit(int gameIdInt) {

        List<String> gameId = new ArrayList<String>();
        String gameSearched = String.valueOf(gameIdInt);
        gameId.add(gameSearched);

        return getGameById(gameId);
    }
}
