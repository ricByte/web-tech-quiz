package controller.question;

import com.google.gson.JsonArray;
import services.ParameterGetter;
import services.ResponseFactory;
import services.question.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 21/11/16.
 */
@WebServlet(name = "QuestionHome", urlPatterns = {"/question/get/*"})
public class QuestionHome extends HttpServlet {

    public QuestionHome() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        JsonArray QuestionIds = ParameterGetter.serializeGet(pathInfo);
        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);

        try {
            ResponseFactory.sendResponse(responseHeader, QuestionService.getQuestions(QuestionIds), "getQuestion");
        } catch (Exception e) {
            ResponseFactory.sendResponse(responseHeader, "{}", "getQuestion");
        }


    }
}
