package model.response;

import beans.game.Game;
import beans.game.QuestionPlayed;

/**
 *
 */
public class GameResponse {
    private Game game;
    private QuestionPlayed questionPlayed;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public QuestionPlayed getQuestionPlayed() {
        return questionPlayed;
    }

    public void setQuestionPlayed(QuestionPlayed questionPlayed) {
        this.questionPlayed = questionPlayed;
    }

    public static GameResponse returnCorrectGame(Game game, QuestionPlayed questionPlayed) {
        GameResponse gameResponse = new GameResponse();

        gameResponse.setQuestionPlayed(questionPlayed);
        gameResponse.setGame(game);

        return gameResponse;
    }

    public static GameResponse returnErrorGame() {
        return new GameResponse();
    }
}
