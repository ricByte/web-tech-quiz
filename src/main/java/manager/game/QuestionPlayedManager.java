package manager.game;

import beans.game.Game;
import beans.game.QuestionPlayed;
import manager.BaseManager;

import java.sql.*;


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


}
