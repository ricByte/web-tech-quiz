package services.question;

import beans.question.Answer;
import beans.question.Question;
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
        }


        return response;

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
