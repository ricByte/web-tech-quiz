package services.question;

import beans.question.Answer;
import beans.question.Question;
import beans.question.QuestionListResponse;
import beans.question.QuestionResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.DataBaseConnector;
import manager.question.QuestionManager;

import javax.servlet.ServletException;
import java.sql.SQLException;

public class QuestionService {

    public static QuestionResponse createQuestion(String session, JsonObject question) throws SQLException, ServletException {


        Question questionObj = QuestionService.parseJsonToQuestion(question);
        QuestionResponse response = new QuestionResponse();

        if (questionObj.getAnswers() != null) {
            DataBaseConnector dbConn = new DataBaseConnector();
            QuestionManager QuestionManager = new QuestionManager(dbConn.connectToDb());

            questionObj = QuestionManager.insertQuestion(questionObj);
            response.setQuestion(questionObj);
            response.setStatus(true);

            dbConn.disconnectFromDb();
        }


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

    public static QuestionListResponse getFilledQuestion(JsonArray questionsId) throws ServletException {

        QuestionListResponse questionWithOutAnswer = QuestionService.getQuestions(questionsId);

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

    private static Answer[]  fillAnswer(JsonArray anwers) {

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

            answersFilled[i] = tempAnswer;

        }

        return answersFilled;
    }

}
