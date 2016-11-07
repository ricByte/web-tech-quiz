package controller;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Map;

import beans.loginObject;
import beans.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import services.GsonFactory;
import services.ParameterGetter;
import services.ResponseFactory;
import services.userAuthenticationService;

@WebServlet(name = "homeController", urlPatterns = {"/login"})
public class homeController extends HttpServlet {

    public homeController(){
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject requestData = ParameterGetter.handleRequest(request);
        Boolean loginStatus = false;
        loginObject lo = new loginObject();

        String email = GsonFactory.getJsonValue(requestData, "email");
        String password = GsonFactory.getJsonValue(requestData, "password");

        if (email != null && password != null) {

            try {
                loginStatus = userAuthenticationService.authLogin(email,password);
                if(loginStatus){
                    User userLogged = userAuthenticationService.getUserForLogin(email, password);
                    lo = new loginObject(userLogged, loginStatus);
                }
            } catch (SQLException e) {

            }
        }

        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, lo, "loginStatus");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, false, "loginStatus", 403);
    }
}
