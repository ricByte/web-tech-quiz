package manager.game;

import beans.game.Game;
import beans.game.QuestionPlayed;
import manager.BaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QuestionPlayedManager extends BaseManager{

    public QuestionPlayedManager(Connection connection) {
        super(connection);
    }

    public static QuestionPlayed createNewQuestionForGame(Game game, int questionId) {

        String sql = "SELECT COUNT(*), ( SELECT COUNT(q.id) " +
                "   FROM question as q " +
                "   WHERE q.id = ? " +
                ") as cid " +
                "FROM question_played as qp " +
                "JOIN game as g " +
                "ON qp.game_ID = g.id " +
                "JOIN sessions as s " +
                "ON g.session_NUMBER = s.session " +
                "JOIN (SELECT u1.* " +
                "   FROM user as u1 " +
                "   LEFT JOIN sessions as s1 " +
                "   ON u1.id = s1.user_ID " +
                "   WHERE s1.session = ? " +
                ") as u " +
                "ON u.id = s.user_ID " +
                "WHERE qp.question_ID = ? ";

        QuestionPlayed questionPlayed = null;
        try {

            PreparedStatement stmt = getConn().prepareStatement(sql);
            stmt.setInt(1, questionId);
            stmt.setString(2, game.getSessionNumber());
            stmt.setInt(3, questionId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next() && (rs.getInt(1) == 0) && (rs.getInt(2) > 0)) {

                questionPlayed = insertNewQuestionPlayed(questionId, game.getId());

            }

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionPlayed;

    }

    public static QuestionPlayed insertNewQuestionPlayed(int questionId, int gameId) {
        QuestionPlayed questionPlayed = null;

        String sql = "INSERT INTO question_played (question_ID, game_ID, question_number) " +
                "VALUES (?, ?, ?)";

        int questionNumber = generateQuestionNumber(gameId);

        try {

            PreparedStatement stmt = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,questionId);
            stmt.setInt(2,gameId);
            stmt.setInt(3,questionNumber);

            int insertedRow = stmt.executeUpdate();

            if (insertedRow > 0) {
                questionPlayed = new QuestionPlayed();
                questionPlayed.setQuestionId(questionId);
                questionPlayed.setGameId(gameId);
                questionPlayed.setQuestionNumber(questionNumber);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return questionPlayed;
    }

    private static int generateQuestionNumber(int gameId) {

        int questionNumber = 1;

        String query = "SELECT count(*) " +
                "FROM question_played as qp " +
                "WHERE qp.game_ID = ?";

        try {

            PreparedStatement stmt = getConn().prepareStatement(query);
            stmt.setInt(1, gameId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                questionNumber = rs.getInt(1) + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionNumber;
    }


    public QuestionPlayed getQuestionPlayedByNumber(int questionNumber, int gameId) {

        String query = "SELECT * " +
                "FROM question_played as qp " +
                "WHERE qp.game_ID = ? " +
                "AND qp.question_number = ?";

        QuestionPlayed questionPlayed = null;

        try {

            PreparedStatement stmt = getConn().prepareStatement(query);
            stmt.setInt(1, gameId);
            stmt.setInt(2, questionNumber);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                int questionId = rs.getInt("question_ID");
                int playerAnswer = rs.getInt("player_answer");

                questionPlayed = new QuestionPlayed();

                questionPlayed.setQuestionId(questionId);
                questionPlayed.setGameId(gameId);
                questionPlayed.setQuestionNumber(questionNumber);
                questionPlayed.setPlayerAnswer(playerAnswer);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionPlayed;
    }

    public List<QuestionPlayed> getQuestionPlayedListOfRightAnswerByGameId(int questionNumber, int gameId) {

        String query = "SELECT COUNT(*) " +
                "FROM question_played as qp " +
                "JOIN question as q " +
                "ON q.id = qp.question_ID " +
                "WHERE qp.game_ID = ? " +
                "AND q.solution = qp.player_answer " +
                "AND qp.question_number < ?";

        List<QuestionPlayed> questionPlayedList = new ArrayList<QuestionPlayed>();

        try {

            PreparedStatement stmt = getConn().prepareStatement(query);
            stmt.setInt(1, gameId);
            stmt.setInt(2, questionNumber);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int questionId = rs.getInt("question_ID");
                int playerAnswer = rs.getInt("player_answer");

                QuestionPlayed questionPlayed = new QuestionPlayed();

                questionPlayed.setQuestionId(questionId);
                questionPlayed.setGameId(gameId);
                questionPlayed.setQuestionNumber(questionNumber);
                questionPlayed.setPlayerAnswer(playerAnswer);

                questionPlayedList.add(questionPlayed);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionPlayedList;
    }

    public boolean updateQuestionPlayed(QuestionPlayed questionPlayed) {

        boolean questionUpdated = false;

        String query = "UPDATE question_played " +
                "SET player_answer = ? " +
                "WHERE question_number = ? " +
                "AND game_ID = ? ";

        try {
            PreparedStatement stmt = getConn().prepareStatement(query);
            stmt.setInt(1, questionPlayed.getPlayerAnswer());
            stmt.setInt(2, questionPlayed.getQuestionNumber());
            stmt.setInt(3, questionPlayed.getGameId());

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated > 0) {
                questionUpdated = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionUpdated;
    }
}
