package controller;

import com.google.gson.JsonObject;
import services.GsonFactory;
import services.ParameterGetter;
import services.ResponseFactory;
import services.userAuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject requestData = ParameterGetter.handleRequest(request);
        Boolean registerStatus = false;
        String email = GsonFactory.getJsonValue(requestData, "email");
        String password = GsonFactory.getJsonValue(requestData, "password");
        String nickname = GsonFactory.getJsonValue(requestData, "nickname");
        int cleverness = requestData.get("cleverness").getAsInt();
//        int cleverness = (int) GsonFactory.getJsonValue(requestData, "cleverness");
        int typeOfPlayer = requestData.get("typeOfPlayer").getAsInt();
//        int typeOfPlayer = (int) GsonFactory.getJsonValue(requestData, "typeOfPlayer");

        if (email != null && password != null) {
            String Password = password.toString();
            String Email = email.toString();
            if (userAuthenticationService.verifyEmail(Email)) {
                try {
                    userAuthenticationService.registerUser(Email, Password, nickname, cleverness, typeOfPlayer);
                    registerStatus = true;
                } catch (SQLException e) {
                    response.getWriter().println("Can't register");
                }
            }
        }

        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, registerStatus, "loginStatus");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
