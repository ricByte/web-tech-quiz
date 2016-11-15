package manager.question;

import beans.question.Question;
import com.google.gson.JsonArray;
import services.question.AnswerService;

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
}
