package services.question;

import beans.User;
import beans.question.Answer;
import beans.question.Question;
import beans.question.QuestionListResponse;
import beans.question.QuestionResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.DataBaseConnector;
import manager.question.QuestionManager;
import services.ParameterGetter;
import services.login.userService;

import javax.servlet.ServletException;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionService {

    public static QuestionResponse createQuestion(String session, JsonObject question) throws SQLException, ServletException {


        Question questionObj = QuestionService.parseJsonForQuestion(question);
        QuestionResponse response = new QuestionResponse();

        if (questionObj.getAnswers() != null) {
            DataBaseConnector dbConn = new DataBaseConnector();
            QuestionManager QuestionManager = new QuestionManager(dbConn.connectToDb());

            User user = userService.getUserFromSession(session);

            questionObj = QuestionManager.insertQuestion(questionObj, user.getId());
            response.setQuestion(questionObj);
            response.setStatus(true);

            dbConn.disconnectFromDb();
        }


        return response;

    }

    public static QuestionResponse saveQuestion(String session, JsonObject question) {

        Question questionObj = QuestionService.parseJsonToQuestion(question);
        QuestionResponse response = new QuestionResponse();
        response.setStatus(false);

        //TODO add check user permission

        if (questionObj.getAnswers() != null) {
            try {

                DataBaseConnector dbConn = new DataBaseConnector();
                QuestionManager QuestionManager = new QuestionManager(dbConn.connectToDb());
                Question questionAlreadySave = QuestionManager.getQuestionFullObject(questionObj);

                if (!questionAlreadySave.isEmpty()) {

                    questionObj = QuestionManager.updateQuestion(questionObj);

                    if (!questionObj.isEmpty())
                        response.setStatus(true);

                }

                dbConn.disconnectFromDb();

            }catch(Exception e) {

            }
        }

        response.setQuestion(questionObj);

        return response;

    }

    public static QuestionListResponse getQuestions(JsonArray questionsId) throws ServletException {

        String queryId = "";
        Question[] questions = null;

        for (int i = 0; i < questionsId.size(); i++) {

            if (i > 0)
                queryId += ",";

            queryId += "'" + questionsId.get(i).getAsString() + "'";

        }

        QuestionListResponse response = new QuestionListResponse();

        if (queryId != "") {

            DataBaseConnector dbConn = new DataBaseConnector();
            QuestionManager QuestionManager = new QuestionManager(dbConn.connectToDb());

            try {

                questions = QuestionManager.getQuestion(queryId);

                if(questions == null) {
                    questions = new Question[0];
                }
                response.setQuestions(questions);
                response.setStatus(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            dbConn.disconnectFromDb();

        }

        return response;
    }

    public static QuestionListResponse getFilledQuestion(JsonArray questionsId) {

        QuestionListResponse questionWithOutAnswer = null;

        try {
            questionWithOutAnswer = QuestionService.getQuestions(questionsId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (questionWithOutAnswer.getQuestions().length > 0) {

            for (Question question : questionWithOutAnswer.getQuestions()) {

                try {

                    Answer[] answer = AnswerService.getAnswers(question.getId());
                    question.setAnswers(answer);

                } catch (Exception e) {

                }

            }

        }

        return questionWithOutAnswer;
    }

    public static Question parseJsonToQuestion(JsonObject JsonQuestion) {

        Question ReturnedQuestion = new Question();

        try {

            String text = JsonQuestion.get("text").getAsString();
            String difficulty = JsonQuestion.get("difficulty").getAsString();
            int solution = JsonQuestion.get("solution").getAsInt();
            int QuestionId = JsonQuestion.get("id").getAsInt();

            JsonArray answers = JsonQuestion.get("answers").getAsJsonArray();

            Answer[] filledAnswers = QuestionService.fillAnswer(answers, QuestionId);

            ReturnedQuestion.setId(QuestionId);
            ReturnedQuestion.setAnswers(filledAnswers);
            ReturnedQuestion.setText(text);
            ReturnedQuestion.setDifficulty(difficulty);
            ReturnedQuestion.setSolution(solution);


        } catch(Exception ex) {

        }

        return ReturnedQuestion;

    }

    public static Question parseJsonForQuestion(JsonObject JsonQuestion) {

        Question ReturnedQuestion = new Question();

        try {

            String text = JsonQuestion.get("text").getAsString();
            String difficulty = JsonQuestion.get("difficulty").getAsString();
            int solution = JsonQuestion.get("solution").getAsInt();

            JsonArray answers = JsonQuestion.get("answers").getAsJsonArray();

            Answer[] filledAnswers = QuestionService.fillAnswer(answers);

            ReturnedQuestion.setAnswers(filledAnswers);
            ReturnedQuestion.setText(text);
            ReturnedQuestion.setDifficulty(difficulty);
            ReturnedQuestion.setSolution(solution);


        } catch(Exception ex) {

        }

        return ReturnedQuestion;

    }

    private static Answer[]  fillAnswer(JsonArray anwers, int QuestionId) {

        Answer[] answersFilled = new Answer[0];

        if (anwers.size() > 0) {
            answersFilled = new Answer[anwers.size()];
        }

        for (int i = 0; i < anwers.size(); i++) {

            JsonObject answerJson = anwers.get(i).getAsJsonObject();
            String text = null;
            String type = "text";
            int number = 0;

            try {
                text = answerJson.get("text").getAsString();
            }catch (Exception e) {

            }
            
            try {
                type = answerJson.get("type").getAsString();
            }catch(Exception e) {
                
            }

            try {
                number = answerJson.get("num").getAsInt();
            }catch(Exception e) {

            }

            Answer tempAnswer = new Answer();

            tempAnswer.setNum(number);
            tempAnswer.setFK_Question((new Question()));
            tempAnswer.getFK_Question().setId(QuestionId);

            try {

                tempAnswer.setId(answerJson.get("id").getAsInt());

            }catch(Exception e) {

            }

            getType(text, type, tempAnswer);

            answersFilled[i] = tempAnswer;

        }

        return answersFilled;
    }

    private static Answer[] fillAnswer(JsonArray anwers) {

        Answer[] answersFilled = new Answer[0];

        if (anwers.size() > 0) {
            answersFilled = new Answer[anwers.size()];
        }

        for (int i = 0; i < anwers.size(); i++) {

            JsonObject answerJson = anwers.get(i).getAsJsonObject();
            String text = null;
            String type = "text";
            int number = 0;

            try {
                text = answerJson.get("text").getAsString();
            }catch (Exception e) {

            }

            try {
                type = answerJson.get("type").getAsString();
            }catch(Exception e) {

            }

            try {
                number = answerJson.get("num").getAsInt();
            }catch(Exception e) {

            }

            Answer tempAnswer = new Answer();

            tempAnswer.setNum(number);

            try {

                tempAnswer.setId(answerJson.get("id").getAsInt());

            }catch(Exception e) {

            }

            getType(text, type, tempAnswer);

            answersFilled[i] = tempAnswer;

        }

        return answersFilled;
    }

    private static void getType(String text, String type, Answer tempAnswer) {
        switch (type) {

            case "image":
                tempAnswer.setImage(text);
                break;

            case "link":
                tempAnswer.setLink(text);
                break;

            default:
                tempAnswer.setText(text);
        }
    }

    /**
     * Create the question object retrieved from the DB
     * @param rs The result of the query
     * @return Question The object Question
     */
    public static Question retrieveQuestionObject (ResultSet rs) {

        try {

            int id = rs.getInt("id");
            String text = rs.getString("text");
            int solution = rs.getInt("solution");
            String difficulty = rs.getString("difficulty");
            int user_ID = rs.getInt("user_ID");
            Date lastModify = ParameterGetter.getDateFromDateTime(rs, "last_modify");

            String email = rs.getString("email");
            String nickname = rs.getString("nickname");
            int cleverness = rs.getInt("cleverness");
            int typeof = rs.getInt("typeOfPlayer");

            User tempUser = new User();
            tempUser.setId(user_ID);
            tempUser.setCleverness(cleverness);
            tempUser.setEmail(email);
            tempUser.setTypeOfPlayer(typeof);
            tempUser.setNickname(nickname);

            return new Question(id, text, new Answer[0], solution, difficulty, tempUser, lastModify);

        } catch(Exception e) {

        }

        return null;
    }

}
