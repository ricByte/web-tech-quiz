package model.response.game;

import beans.game.Game;
import beans.game.QuestionPlayed;
import beans.question.Question;

/**
 *
 */
public class GameResponse {
    private Game game;
    private int questioNumber;
    private Question question;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getQuestioNumber() {
        return questioNumber;
    }

    public void setQuestioNumber(int questioNumber) {
        this.questioNumber = questioNumber;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public static GameResponse returnCorrectGame(Game game, QuestionPlayed questionPlayed, Question question) {
        GameResponse gameResponse = new GameResponse();

        gameResponse.setQuestioNumber(questionPlayed.getQuestionNumber());
        gameResponse.setGame(game);
        gameResponse.setQuestion(question);

        return gameResponse;
    }

    public static GameResponse returnErrorGame() {
        return new GameResponse();
    }
}
