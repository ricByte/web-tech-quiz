package controller.login;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

import beans.login.loginObject;
import beans.User;
import com.google.gson.JsonObject;

import services.GsonFactory;
import services.ParameterGetter;
import services.ResponseFactory;
import services.login.userAuthenticationService;

@WebServlet(name = "homeController", urlPatterns = {"/login"})
public class homeController extends HttpServlet {

    public homeController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject requestData = ParameterGetter.handleRequest(request);

        loginObject loginResponse = new loginObject();

        String email = GsonFactory.getJsonValue(requestData, "email");
        String password = GsonFactory.getJsonValue(requestData, "password");

        if (email != null && password != null) {

            loginResponse = userAuthenticationService.authLogin(email, password);
        }

        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, loginResponse, "loginStatus");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, false, "loginStatus", 403);
    }
}
