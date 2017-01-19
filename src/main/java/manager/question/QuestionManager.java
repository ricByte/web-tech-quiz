package manager.question;

import beans.question.Question;
import com.google.gson.JsonArray;
import services.question.AnswerService;
import services.question.QuestionService;

import javax.servlet.ServletException;
import java.sql.*;

public class QuestionManager {
    private static Connection conn;

    public QuestionManager(Connection conn) {
        this.conn = conn;
    }

    public static Question insertQuestion(Question question) throws SQLException, ServletException {
        String sql = "insert into question (text, difficulty) values (?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, question.getText());
        stmt.setString(2, question.getDifficulty());

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();

        if (rs.next()){
            int id = rs.getInt(1);
            question.setId(id);

            int[] savedAnswer = AnswerService.SaveAnswers(question.getAnswers(), question.getId());

            for (int i = 0; i < savedAnswer.length; i++) {
                question.getAnswers()[i].setId(savedAnswer[i]);
            }

            int solutionDb = savedAnswer[question.getSolution()-1];

            String addAnswer = "UPDATE question " +
                    "SET solution=? " +
                    "WHERE id=?";

            PreparedStatement stmtFK = conn.prepareStatement(addAnswer);

            stmtFK.setInt(1, solutionDb);
            stmtFK.setInt(2, id);

            stmtFK.executeUpdate();
            stmtFK.close();

            question.setSolution(solutionDb);
        }

        rs.close();
        stmt.close();

        return question;
    }

    /**
     * Extract a set of values using a prefix with index es. prop1, prop2
     *
     * @param question the question to search in DB
     * @returns question The object to write in the Db
     */
    public static Question updateQuestion(Question question) {

        String updateQuestion = "UPDATE question " +
                "SET solotion=?, text=?, difficulty=?, user_ID=?";

        String getQuestion = "SELECT q.*, u.id, u.email, u.nickname, u.cleverness, u.typeOfPlayer, GROUP_CONCAT(a.id) " +
                "FROM question as q " +
                "JOIN user as u " +
                "ON u.id = q.user_ID " +
                "JOIN  answer as a " +
                "ON a.question_ID = q.id " +
                "WHERE q.id = ?";

        Question questionUpdated = new Question();

        try {

            PreparedStatement stmtGet = conn.prepareStatement(getQuestion);

            stmtGet.setInt(1, question.getId());
            ResultSet rsGet = stmtGet.executeQuery();

            if (rsGet.next()) {

                questionUpdated = QuestionService.retrieveQuestionObject(rsGet);
                questionUpdated.setAnswers(AnswerService.getAnswers(questionUpdated.getId()));

            }

        } catch(Exception e) {

            questionUpdated = new Question();

        }

        return questionUpdated;

    }

    public static Question[] getQuestion(String questionsId) throws SQLException {

        String query = "SELECT q.* , u.email, u.nickname, u.cleverness, u.typeOfPlayer " +
                "FROM question as q " +
                "JOIN user as u " +
                "ON u.id = q.user_ID " +
                "WHERE q.id in ("+ questionsId +")";
        String queryCount = "SELECT COUNT(*) as count " +
                "FROM question " +
                "WHERE id in ("+ questionsId +")";

        if (questionsId.equals("'all'")) {

            query = "SELECT q.* , u.id, u.email, u.nickname, u.cleverness, u.typeOfPlayer " +
                    "FROM question as q " +
                    "JOIN user as u " +
                    "ON u.id = q.user_ID";
            queryCount = "SELECT COUNT(*) as count " +
                    "FROM question ";

        }

        Question[] Questions;
        int count = 0;

        PreparedStatement stmt = conn.prepareStatement(queryCount);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            count = rs.getInt("count");
        }


        if (count > 0) {

            Questions = new Question[count];
            PreparedStatement stmt2 = conn.prepareStatement(query);

            ResultSet rs2 = stmt2.executeQuery();
            int i = 0;

            while (rs2.next()) {

                Questions[i] = QuestionService.retrieveQuestionObject(rs2);

                i++;
            }

            rs2.close();
            stmt2.close();

            return Questions;
        }

        rs.close();
        stmt.close();

        return null;
    }
}
