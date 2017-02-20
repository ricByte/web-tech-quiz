package manager.question;

import beans.question.Answer;
import services.question.AnswerService;

import java.sql.*;

public class AnswerManager {
    private static Connection conn;

    public AnswerManager(Connection conn) {
        this.conn = conn;
    }

//    public  void createUserTable() throws SQLException {
//        String query = "CREATE TABLE `user` (\n" +
//                "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
//                "  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
//                "  `password` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
//                "  `nickname` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,\n" +
//                "  `cleverness` int(1) DEFAULT '0',\n" +
//                "  `typeOfPlayer` int(1) DEFAULT '0',\n" +
//                "  PRIMARY KEY (`id`)\n" +
//                ") ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
//
//        PreparedStatement stmt = conn.prepareStatement(query);
//
//        ResultSet rs = stmt.executeQuery();
//
//    }

    public void disconnect() {

        try {
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int registerAnswer(Answer answer, int questionId) throws SQLException {

        String sql = "INSERT " +
                "INTO answer (link, image, text, question_ID, num) " +
                "values (?, ?, ?, ?, ?)";
        int id = 0;

        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        String text = (answer.getText() != null) ? answer.getText() : "";

        stmt.setString(1, answer.getLink());
        stmt.setString(2, answer.getImage());
        stmt.setString(3, text);
        stmt.setInt(4, questionId);
        stmt.setInt(5, answer.getNum());

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();

        if (rs.next()) {
            id = rs.getInt(1);
        }

        stmt.close();

        return id;
    }

    public static Answer[] getAnswersFromQuestionId(int QuestionId) throws SQLException {

        String query = "SELECT * " +
                "FROM answer as a " +
                "WHERE question_ID = ?";

        String queryCount = "SELECT COUNT(*) as count " +
                "FROM answer as a " +
                "WHERE question_ID = ?";


        Answer[] answers;
        int count = 0;

        PreparedStatement stmt = conn.prepareStatement(queryCount);
        stmt.setInt(1, QuestionId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            count = rs.getInt("count");
        }

        if (count > 0) {

            answers = new Answer[count];
            PreparedStatement stmt2 = conn.prepareStatement(query);
            stmt2.setInt(1, QuestionId);

            ResultSet rs2 = stmt2.executeQuery();
            int i = 0;

            while (rs2.next()) {

                Answer tempAnswer = AnswerService.createAnswer(rs2);

                if (tempAnswer != null) {

                    answers[i] = tempAnswer;

                    i++;
                }

            }

            rs2.close();
            stmt2.close();

            return answers;
        }

        rs.close();
        stmt.close();

        return null;

    }
    
    public static Answer updateAnswer(Answer answer) {

        Answer answerReturned = null;

        String updateAnswer = "UPDATE answer " +
                "SET link=?, text=?, image=?, num=? " +
                "WHERE num = ? " +
                "AND question_ID = ?";


        try {

            PreparedStatement stmtUpdate = conn.prepareStatement(updateAnswer, Statement.RETURN_GENERATED_KEYS);

            stmtUpdate.setString(1, answer.getLink());
            stmtUpdate.setString(2, answer.getText());
            stmtUpdate.setString(3, answer.getImage());
            stmtUpdate.setInt(4, answer.getNum());
            stmtUpdate.setInt(5, answer.getNum());
            stmtUpdate.setInt(6, answer.getFK_Question().getId());

            stmtUpdate.executeUpdate();

            answerReturned = answer;

            stmtUpdate.close();

        } catch (Exception e) {

        }

        return answerReturned;
        
    }

}
